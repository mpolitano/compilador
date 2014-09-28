/*
	Class that visit AST and generate a tree address code 
*/

package ir.intermediateCode;

import ir.ASTVisitor;
import ir.ast.*;
import java.util.*;

public class TACVisitor implements ASTVisitor<Object>{

List<TAInstructions> TAC;
int line;

public TACVisitor(){
	TAC= new LinkedList();
	line=0;
}

//visit program
	public Object visit(Program prog){
		for(MethodLocation m : prog.getMethods()){
			m.accept(this);
		}
		return null;
	}
	
// visit statements
	public Object visit(AssignStmt stmt){
		Object expr= stmt.getExpression().accept(this);
		//Aca podria tener una expresion compuesta o un literal
		TAC.add(line, new TAInstructions(TAInstructions.Instr.Assign,expr,stmt.getLocation()));
		line++;	
		return null;
	}

	public Object visit(ReturnStmt stmt){return new String() ;}
	public Object visit(IfStmt stmt){return new String() ;}
	public Object visit(Block stmt){
		for (Statement s: stmt.getStatements()){
			s.accept(this);
		}
		return null;
	}
	public Object visit(BreakStmt stmt){return new String() ;}
	public Object visit(ContinueStmt stmt){return new String() ;}
	public Object visit(ForStmt stmt){return new String() ;}
	public Object visit(SecStmt stmt){return new String() ;}
	public Object visit(WhileStmt stmt){return new String() ;}
	public Object visit(MethodCallStmt stmt){return new String() ;}
	public Object visit(ExterninvkCallStmt stmt){return new String() ;}

//Visit Location
	public Object visit(VarLocation var){return var;}
	public Object visit(MethodLocation method){
		method.getBody().accept(this);
		return null;
	}
	public Object visit(ArrayLocation array){return array;}
	
// visit expressions
	public Object visit(BinOpExpr expr){
		Expression lo= (Expression) expr.getLeftOperand().accept(this);
		Expression ro= (Expression) expr.getRightOperand().accept(this);
		BinOpType op= expr.getOperator();
		Location result= new VarLocation(Integer.toString(line),expr.getLineNumber(),expr.getColumnNumber(),-1);//Ojo que el auxiliar para calcular los cambios a flot van a tener el mismo nombre que result
		switch(op){
			case PLUS: if (expr.getType()==Type.INT){//If expr.type=int so leftop and rightop will be int
							TAC.add(line, new TAInstructions(TAInstructions.Instr.AddI,lo,ro,result));line++;
						}else{//stmt.getExpression()
							if (lo.getType()==Type.INT){//lo is int
								Location floatLo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
								TAC.add(line, new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));line++;//convert lo to float
								TAC.add(line, new TAInstructions(TAInstructions.Instr.AddF,floatLo,ro,result));line++; //calc add	
							}else{
									if (ro.getType()==Type.INT){//ro is int
										Location floatRo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
										TAC.add(line, new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatRo));line++;//convert ro to float
								 		TAC.add(line, new TAInstructions(TAInstructions.Instr.AddF,lo,floatRo,result));line++; //calc add	
								 	}else{//ninguno es int
								 			TAC.add(line, new TAInstructions(TAInstructions.Instr.AddF,lo,ro,result));line++; //calc add	
								 	}
								 }
							
						}
						return result;
		
			case MINUS: if (expr.getType()==Type.INT){//If expr.type=int so leftop and rightop will be int
							TAC.add(line, new TAInstructions(TAInstructions.Instr.SubI,lo,ro,result));line++;
						}else{ //(expr.getType==Type.FLOAT)
							if (lo.getType()==Type.INT){//lo is int
								Location floatLo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
								TAC.add(line, new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));line++;//convert lo to float
								TAC.add(line, new TAInstructions(TAInstructions.Instr.SubF,floatLo,ro,result));line++; //calc add	
							}else{
									if (ro.getType()==Type.INT){//ro is int
										Location floatRo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
										TAC.add(line, new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatRo));line++;//convert ro to float
								 		TAC.add(line, new TAInstructions(TAInstructions.Instr.SubF,lo,floatRo,result));line++; //calc add	
								 	}else{//ninguno es int
								 			TAC.add(line, new TAInstructions(TAInstructions.Instr.SubF,lo,ro,result));line++; //calc add	
								 	}
								 }
							
						}
						return result;
						
		}
		return result;

	}
	
	//Push de los parametros, llamada al metodo, pop de los parametros y retorno el valor de resultado del metodo(es un literal siempre)
	public Object visit(ExterninvkCallExpr expr){
		for(Expression e: expr.getArguments()){//Loop for push parameters
			Object value= e.accept(this);
			if (value instanceof Literal){
				TAC.add(line,new TAInstructions(TAInstructions.Instr.ParamPush,((Literal)value).getValue()));//Parameter's value Push 
				line++;
			}else{//value intance of location
					TAC.add(line,new TAInstructions(TAInstructions.Instr.ParamPush,value));//Parameter's location Push 	
					line++;
				}				 
		}
		Location result= new VarLocation(Integer.toString(line),expr.getLineNumber(),expr.getColumnNumber(),-1);//Location for save procedure's result
		TAC.add(line,new TAInstructions(TAInstructions.Instr.CallExtern,expr.getMethod(),result));//Call sub-rutina 	
		line++;
		for(Expression e: expr.getArguments()){//Loop for pop parameters
			Object value= e.accept(this);
			if (value instanceof Literal){
				TAC.add(line,new TAInstructions(TAInstructions.Instr.ParamPop,((Literal)value).getValue()));//Parameter's value Pop
				line++;
			}else{//value intance of location
					TAC.add(line,new TAInstructions(TAInstructions.Instr.ParamPop,value));//Parameter's location Pop 	
					line++;
				}	
		}
		return result;
	}
	//Push de los parametros, llamada al metodo, pop de los parametros y retorno el valor de resultado del metodo(es un literal siempre)
	public Object visit(MethodCallExpr expr){
		for(Expression e: expr.getArguments()){//Loop for push parameters
			Object value= e.accept(this);
			if (value instanceof Literal){
				TAC.add(line,new TAInstructions(TAInstructions.Instr.ParamPush,((Literal)value).getValue()));//Parameter's value Push 
				line++;
			}else{//value intance of location
					TAC.add(line,new TAInstructions(TAInstructions.Instr.ParamPush,value));//Parameter's location Push 	
					line++;
				}				 
		}
		Location result= new VarLocation(Integer.toString(line),expr.getLineNumber(),expr.getColumnNumber(),-1);//Location for save procedure's result
		TAC.add(line,new TAInstructions(TAInstructions.Instr.Call,expr.getMethod().getId(),result));//Call sub-rutina 	
		line++;
		for(Expression e: expr.getArguments()){//Loop for pop parameters
			Object value= e.accept(this);
			if (value instanceof Literal){
				TAC.add(line,new TAInstructions(TAInstructions.Instr.ParamPop,((Literal)value).getValue()));//Parameter's value Pop
				line++;
			}else{//value intance of location
					TAC.add(line,new TAInstructions(TAInstructions.Instr.ParamPop,value));//Parameter's location Pop 	
					line++;
				}	
			
		}
		return result;//return method's result 
	}

//Aplicar el operador unario a la expresion
	public Object visit(UnaryOpExpr expr){//return literal or location where will be the value
		Object value=expr.getExpression().accept(this);//Obtengo la expresion		
		UnaryOpType operator= expr.getOperator();
		if (value instanceof Literal){
			switch (operator){
				case MINUS: if (((Literal)value).getValue() instanceof Integer){
								return -((Integer)(((Literal)value).getValue()));
							}else{return -((Float)(((Literal)value).getValue()));}		
				case NOT:	return  !((Boolean)((Literal)value).getValue());
			}		
		}else{
				Location temp= new VarLocation(Integer.toString(line),expr.getLineNumber(),expr.getColumnNumber(),-1);//place for current calculus
				switch (operator){
					case MINUS: 
								TAC.add(line, new TAInstructions(TAInstructions.Instr.MinusI,value,temp));//save sentence for calc this value
								line++;
								break; 		
					case NOT:
								TAC.add(line, new TAInstructions(TAInstructions.Instr.Not,value,temp));//save sentence for calc this value
								line++;
								break; 							
				}
				return temp;//return the temp place where will be the value of this aplication					
		}	
		return null;	
	}

// visit literals	
	public Object visit(IntLiteral lit){return lit;}
	public Object visit(FloatLiteral lit){return lit;}
	public Object visit(BooleanLiteral lit){return lit;}
	public Object visit(StringLiteral lit){	return lit;}


	public List<TAInstructions> getTAC(){
		return TAC;
	}

}