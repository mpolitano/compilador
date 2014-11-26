/*
	Class that implements a visitor for tour AST
	and make type checking.
 */
package ctds_pcr.semcheck;

import java.util.ArrayList;
import java.util.List;

import ctds_pcr.ASTVisitor;
import ctds_pcr.ast.*;
import ctds_pcr.error.Error; // define class error
import java.util.*;

// type checker, concrete visitor 
public class TypeCheckVisitor implements ASTVisitor<Type> {
	
	private List<Error> errors;

	public TypeCheckVisitor(){
		errors= new LinkedList<Error>();
	}
//visit program
	public Type visit(Program prog){
		for(Location l: prog.getFields()){
			l.accept(this);
		}
		for(MethodLocation m: prog.getMethods()){
			m.accept(this);
		}
		return Type.UNDEFINED;
	};

// visit statements
	public Type visit(AssignStmt stmt){
		Type locationType= stmt.getLocation().getType();
		if (stmt.getLocation() instanceof RefArrayLocation){locationType=locationType.fromArray();}
		Type exprType=stmt.getExpression().accept(this);
		switch(stmt.getOperator()){
			case ASSIGN: if (locationType==exprType){return Type.UNDEFINED;}//There isn't an error
					else{addError(stmt,"Type Error in Assign: ");return Type.UNDEFINED;}	//There is an error
			case DECREMENT: case INCREMENT:
											switch (locationType){
												case INT: case FLOAT: if (locationType==exprType){return Type.UNDEFINED;}//There isn't an error
																	  else{addError(stmt,"Type Error in Assign: ");return Type.UNDEFINED;}//There is an error	
												
												default:addError(stmt,"Type Error in Assign: "); 
														return Type.UNDEFINED;					  	
											}

		}
		return Type.UNDEFINED;//default return
   	};

	public Type visit(ReturnStmt stmt){
		if (stmt.getExpression()!= null){
			return stmt.getExpression().accept(this); //return expresion type
		}else{return Type.VOID;}
	};

	public Type visit(IfStmt stmt){
		Type conditionType= stmt.getCondition().accept(this);
		if (conditionType!=Type.BOOLEAN){
			addError(stmt, "If's condition must be a Boolean expression");
		}
		stmt.getIfBlock().accept(this); //Visit if block
		if (stmt.getElseBlock()!=null){
			stmt.getElseBlock().accept(this);
		}		
		return Type.UNDEFINED; //Return a UNDEFINED, because if statement hasn't a type. It ins't an error.
	};

	public Type visit(Block stmt){
		for(Statement s:stmt.getStatements()){
			s.accept(this);//Type check in block's body.
		}	
		return Type.UNDEFINED;//Return a UNDEFINED, because block hasn't a type. It ins't an error.
	};

	public Type visit(BreakStmt stmt){return Type.UNDEFINED;};
	public Type visit(ContinueStmt stmt){return Type.UNDEFINED;};
	
	public Type visit(ForStmt stmt){
		Expression initialValue= stmt.getInitialValue();
		Expression finalValue= stmt.getFinalValue();
		if(initialValue.accept(this)!=Type.INT || finalValue.accept(this)!=Type.INT){//check for integer expression in initial value and final value
			addError(stmt,"For loop must have integer expressions: ");
		}
		stmt.getBlock().accept(this);//visit ford block		
		return Type.UNDEFINED;
	};
	public Type visit(SecStmt stmt){return Type.UNDEFINED;};//Should't do anything
	
	public Type visit(WhileStmt stmt){
		Type conditionType= stmt.getCondition().accept(this);
		if (conditionType!=Type.BOOLEAN){
			addError(stmt, "While's condition must be a Boolean expression");
		}
		stmt.getBlock().accept(this); //Visit while block	
		return Type.UNDEFINED; //Return a UNDEFINED, because while statement hasn't a type. It ins't an error.
	};

	public Type visit(MethodCallStmt stmt){
		List<Location> formalParameters=stmt.getMethod().getFormalParameters();
		List<Expression> actualParameters= stmt.getArguments();
		if (formalParameters.size() !=actualParameters.size() ){addError(stmt,"Formal Parameters mismatched with actual parameters: "); return Type.UNDEFINED;}
		else{//formal parameters list and actual parameters list have same size
				for (int i=0; i<formalParameters.size(); i++){
					if (formalParameters.get(i).accept(this)!= actualParameters.get(i).accept(this)){
						addError(stmt,"Formal Parameters mismatched with actual parameters: ");	
						return Type.UNDEFINED;
					}
				}
				return Type.UNDEFINED; //Return UNDEFINED TYPE.	
		}
	};	
	
	public Type visit(ExterninvkCallStmt stmt){
		List<Expression> expressionExter= stmt.getArguments();s
		for (Expression e:stmt.getArguments()){
			e.accept(this); //Check type in Externinvk actual parameters. 
		}
		return Type.UNDEFINED;//return the method's return type
	}



//Visit Location
	public Type visit(VarLocation loc){return loc.getType();};
	
	public Type visit(MethodLocation loc){
		Block methodBody=loc.getBody();
		methodBody.accept(this);
		for (Statement s: methodBody.getStatements()){
			if (s instanceof ReturnStmt){
				if (loc.getType()==Type.VOID){//If method's return t(s.getBlock().getStatements()ype is void so return statement can't has a expression
					if (((ReturnStmt)s).getExpression()!=null){
						addError(s,"Void Method can't have a 'return <expr>' statement");	
					}
				}else{//else method's return type must be equal to expression type
					if (s.accept(this)!= loc.getType()){
						addError(s,"Type return not valid");
					}
				}
			}
		}
	
		return loc.getType();
	};
	
	public Type visit(ArrayLocation loc){
		return loc.getType().fromArray();//return base array type
	};

	public Type visit(RefVarLocation var){
		return var.getType();
	}

	public Type visit(RefArrayLocation array){
			if (array.getExpression().accept(this)!=Type.INT){
				addError(array, "Type error in Array index Expressio:[expr],expr must be an Int expression ");		
			}
			return array.getType().fromArray();
	}
	

	/*Method for make type checking in BinOpExpr. Are allowed all aritmetical and relational operation between 
		float and int operand */
	public Type visit(BinOpExpr expr){
		Type lExprType= expr.getLeftOperand().accept(this);
		Type rExprType= expr.getRightOperand().accept(this);
		Type t;
		switch (expr.getOperator()){
			case MINUS:case PLUS: case MULTIPLY: t=getTypeArithBinOp(lExprType,rExprType);expr.setType(t);return t;
			case DIVIDE://Semantica de la divison entera para 2 enteros, division entre reales para float,int o float,float 		
						if (lExprType==Type.INT && rExprType==Type.INT){expr.setType(Type.INT);return Type.INT;}//Semantica de la division entera
						else{if ((lExprType==Type.INT || lExprType==Type.FLOAT) && (rExprType==Type.INT || rExprType==Type.FLOAT)){expr.setType(Type.FLOAT);return Type.FLOAT;}
								else{addError(expr,"Type operator not support: ");return Type.UNDEFINED;}										
   							}
			case MOD: if (lExprType==Type.INT && rExprType==Type.INT){expr.setType(Type.INT);return Type.INT;}
					  else{addError(expr,"Type operator not support: ");return Type.UNDEFINED;}//Bin Operator MOD is available for int only.
			case LE:case LEQ: case GE: case GEQ: if ((lExprType==Type.INT || lExprType==Type.FLOAT)&&  (rExprType==Type.INT || rExprType==Type.FLOAT)){expr.setType(Type.BOOLEAN);return Type.BOOLEAN;}//Dice que comparo si son del mismo tipo unicamente, VER 
					 							else{addError(expr,"Type operator not support: ");return Type.UNDEFINED;}	
			case CEQ: case NEQ:
								switch (lExprType){
									case INT: case FLOAT:
														switch(rExprType){
															case INT: case FLOAT: expr.setType(Type.BOOLEAN);return Type.BOOLEAN;
															default:addError(expr,"Type operator not support: "); 
																	return Type.UNDEFINED;
														}
									case BOOLEAN:
												switch(rExprType){
													case BOOLEAN: expr.setType(Type.BOOLEAN);return Type.BOOLEAN;
													default:addError(expr,"Type operator not support: ");
															return Type.UNDEFINED;
												}									
									default: return Type.UNDEFINED;	
								}
			case AND: case OR: 
								if(lExprType==Type.BOOLEAN && rExprType==Type.BOOLEAN){expr.setType(Type.BOOLEAN);return Type.BOOLEAN;}
								else{return Type.UNDEFINED;}
		}
		return Type.UNDEFINED;  
	};

	public Type visit(ExterninvkCallExpr expr){
		for (Expression e:expr.getArguments()){
			e.accept(this); //Check type in Externinvk actual parameters. 
		}
		expr.setType(expr.getType());
		return expr.getType();//return the method's return type
	};
	
	//This method Should check if actual parameters's type and formal parameter's type are equal and return method's return type
	public Type visit(MethodCallExpr expr){
		List<Location> formalParameters=expr.getMethod().getFormalParameters();
		List<Expression> actualParameters= expr.getArguments();
		if (formalParameters.size() !=actualParameters.size() ){addError(expr,"Formal Parameters mismatched with actual parameters: ");}
		else{//formal parameters list and actual parameters list have same size
				for (int i=0; i<formalParameters.size(); i++){
					if (formalParameters.get(i).accept(this)!= actualParameters.get(i).accept(this)){
						addError(expr,"Formal Parameters mismatched with actual parameters: ");	
					}
				}
		}
		expr.setType(expr.getMethod().getType());
		return expr.getMethod().getType(); //Return method's return type.	
	};	
	
	public Type visit(UnaryOpExpr expr){
		Type t= expr.getExpression().accept(this); //Obtain expression type	
		switch (expr.getOperator()){
			case MINUS:
				switch (t){	
					case INT: expr.setType(t);return t;
					case FLOAT:expr.setType(t); return t; 
					default: 
							addError(expr, "Type operator not support");
							return Type.UNDEFINED;
				}
			case NOT: 
				switch (t){
					case BOOLEAN: expr.setType(t);return t;	
					default: 
							addError(expr, "Type operator not support");
							return Type.UNDEFINED;
				}

		}
		return Type.UNDEFINED;

	};

	public Type visit(LabelExpr expr){
		return Type.UNDEFINED;
	}

// visit literals	
	public Type visit(IntLiteral lit){return lit.getType();};
	public Type visit(FloatLiteral lit){return lit.getType();};
	public Type visit(BooleanLiteral lit){return lit.getType();};
	public Type visit(StringLiteral lit){return lit.getType();};


	private void addError(AST a, String desc) {
		errors.add(new Error(a.getLineNumber(), a.getColumnNumber(), desc));
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}


//Metodos Auxiliares

	private Type getTypeArithBinOp(Type lo, Type ro){
		switch(lo){
			case INT: 
					switch (ro){
						case INT: return Type.INT;
						case FLOAT: return Type.FLOAT;
						default: return Type.UNDEFINED;	
					}
			case FLOAT:	
					switch (ro){
						case INT: return Type.FLOAT;
						case FLOAT: return Type.FLOAT;
						default: return Type.UNDEFINED;	
					}				
			default: return Type.UNDEFINED; //If type isn't numeral, artithmetical operators aren't defined.
		}
	}

}
