/*
	Class that visit AST and generate a tree address code 
*/

package ir.intermediateCode;

import ir.ASTVisitor;
import ir.ast.*;
import java.util.*;

public class TACVisitor implements ASTVisitor<Expression>{

List<TAInstructions> TAC;
int line;

public TACVisitor(){
	TAC= new LinkedList();
	line=0;
}

//visit program
	public Expression visit(Program prog){
		for(MethodLocation m : prog.getMethods()){
			m.accept(this);
		}
		return null;
	}
	
// visit statements
	public Expression visit(AssignStmt stmt){
		Expression expr= stmt.getExpression().accept(this);
		//Aca podria tener una expresion compuesta o un literal
		addInstr(new TAInstructions(TAInstructions.Instr.Assign,expr,stmt.getLocation()));
		return null;
	}

	public Expression visit(ReturnStmt stmt){return new LabelExpr("borrar") ;}
	
//This visitor generate TAC for if-else statement, if if's condition is a boolean literal only generate code for if or else block  	
	public Expression visit(IfStmt stmt){
			LabelExpr lif= new LabelExpr("if "+ Integer.toString(line));
			LabelExpr endif= new LabelExpr("endif "+ Integer.toString(line));
			Expression expr=stmt.getCondition().accept(this); //Generate TAC for evaluate if condition
			if (expr instanceof Location){
				if (stmt.getElseBlock()==null){
					addInstr(new TAInstructions(TAInstructions.Instr.JTrue,expr,lif));//if condition=true jump to if's block
					addInstr(new TAInstructions(TAInstructions.Instr.JTrue,expr,endif));//if condition=false jump to if block's end 
					addInstr(new TAInstructions(TAInstructions.Instr.PutLabel, lif));
					stmt.getIfBlock().accept(this);
					addInstr(new TAInstructions(TAInstructions.Instr.PutLabel, endif));		
				}else{
					LabelExpr endElse= new LabelExpr("endElse "+ Integer.toString(line));
					addInstr(new TAInstructions(TAInstructions.Instr.JTrue,expr,lif));//if condition=true jump to if's block
					addInstr(new TAInstructions(TAInstructions.Instr.Jmp,expr,endif));//if condition=false jump to if block's end 
					addInstr(new TAInstructions(TAInstructions.Instr.PutLabel, lif));
					stmt.getIfBlock().accept(this);
					addInstr(new TAInstructions(TAInstructions.Instr.Jmp,endElse));//if condition=false jump to if block's end 
					stmt.getElseBlock().accept(this);
					addInstr(new TAInstructions(TAInstructions.Instr.PutLabel, endElse));					
				}
		}else{//intanceof literal
			if (((Literal)expr).getValue()== true){
					addInstr(new TAInstructions(TAInstructions.Instr.PutLabel, lif)); //put label for read only
					stmt.getIfBlock().accept(this);
					addInstr(new TAInstructions(TAInstructions.Instr.PutLabel, endif));		
			}else{
					if ((((Literal)expr).getValue()== true) && (stmt.getElseBlock()!=null)){
						addInstr(new TAInstructions(TAInstructions.Instr.PutLabel, lif)); //put label for read only
						stmt.getElseBlock().accept(this);
						addInstr(new TAInstructions(TAInstructions.Instr.PutLabel, endif));		
					}
			}

		}		
		return null;
	}
	
	//Generate TAC for a block
	public Expression visit(Block stmt){
		for (Statement s: stmt.getStatements()){
			s.accept(this);
		}
		return null;
	}
	public Expression visit(BreakStmt stmt){return new LabelExpr("borrar") ;}
	public Expression visit(ContinueStmt stmt){return new LabelExpr("borrar");}
	public Expression visit(ForStmt stmt){return new LabelExpr("borrar");}
	
	public Expression visit(SecStmt stmt){return null;}
	
	public Expression visit(WhileStmt stmt){return new LabelExpr("borrar");}
	public Expression visit(MethodCallStmt stmt){return new LabelExpr("borrar");}
	public Expression visit(ExterninvkCallStmt stmt){return new LabelExpr("borrar");}

//Visit Location
	public Expression visit(VarLocation var){return var;}
	
	public Expression visit(MethodLocation method){
		addInstr(new TAInstructions(TAInstructions.Instr.MethodDecl,new LabelExpr(method.getId())));//Start method declaration
		method.getBody().accept(this);
		addInstr(new TAInstructions(TAInstructions.Instr.MethodDeclEnd,new LabelExpr(method.getId())));//end Method declaration
		return null;
	}

	//Asumiendo que las arrayLocation las visito unicamente en expresiones, accedemos a la posicion y la almacenamos en un teporal
	public Expression visit(ArrayLocation array){return array;}
	
// visit expressions
	public Expression visit(BinOpExpr expr){
		Expression lo= (Expression) expr.getLeftOperand().accept(this);//literal or location
		Expression ro= (Expression) expr.getRightOperand().accept(this);
		BinOpType op= expr.getOperator();
		Location result= new VarLocation(Integer.toString(line),expr.getLineNumber(),expr.getColumnNumber(),-1);//Ojo que el auxiliar para calcular los cambios a flot van a tener el mismo nombre que result
		switch(op){
			case PLUS: if (expr.getType()==Type.INT){//If expr.type=int so leftop and rightop will be int
							addInstr(new TAInstructions(TAInstructions.Instr.AddI,lo,ro,result));
						}else{//expe.type=float
							if (lo.getType()==Type.INT){//lo is int
								Location floatLo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
								addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float
								addInstr(new TAInstructions(TAInstructions.Instr.AddF,floatLo,ro,result)); //calc add	
							}else{
									if (ro.getType()==Type.INT){//ro is int
										Location floatRo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
										addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatRo));//convert ro to float
								 		addInstr(new TAInstructions(TAInstructions.Instr.AddF,lo,floatRo,result)); //calc add	
								 	}else{//ninguno es int
								 			addInstr(new TAInstructions(TAInstructions.Instr.AddF,lo,ro,result)); //calc add	
								 	}
								 }
							
						}
						return result;
		
			case MINUS: if (expr.getType()==Type.INT){//If expr.type=int so leftop and rightop will be int
							addInstr(new TAInstructions(TAInstructions.Instr.SubI,lo,ro,result));
						}else{ //(expr.getType==Type.FLOAT)
							if (lo.getType()==Type.INT){//lo is int
								Location floatLo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
								addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float
								addInstr(new TAInstructions(TAInstructions.Instr.SubF,floatLo,ro,result));//calc add	
							}else{
									if (ro.getType()==Type.INT){//ro is int
										Location floatRo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
										addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatRo));//convert ro to float
								 		addInstr(new TAInstructions(TAInstructions.Instr.SubF,lo,floatRo,result)); //calc add	
								 	}else{//ninguno es int
								 			addInstr(new TAInstructions(TAInstructions.Instr.SubF,lo,ro,result));//calc add	
								 	}
								 }
							
						}
						return result;
			case MULTIPLY: if (expr.getType()==Type.INT){//If expr.type=int so leftop and rightop will be int
							addInstr(new TAInstructions(TAInstructions.Instr.MultI,lo,ro,result));
						}else{ //(expr.getType==Type.FLOAT)
							if (lo.getType()==Type.INT){//lo is int
								Location floatLo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
								addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float
								addInstr(new TAInstructions(TAInstructions.Instr.MultF,floatLo,ro,result));//calc add	
							}else{
									if (ro.getType()==Type.INT){//ro is int
										Location floatRo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
										addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatRo));//convert ro to float
								 		addInstr(new TAInstructions(TAInstructions.Instr.MultF,lo,floatRo,result)); //calc add	
								 	}else{//ninguno es int
								 			addInstr(new TAInstructions(TAInstructions.Instr.MultF,lo,ro,result));//calc add	
								 	}
								 }
							
						}
						return result;
			case DIVIDE:
						if(expr.getType()==Type.INT ){
							addInstr(new TAInstructions(TAInstructions.Instr.DivI,lo,ro,result));	
						}else{//expr.getType()==Type.FLOAT	 
						 		if (lo.getType()==Type.INT && ro.getType()==Type.FLOAT ){
									Location floatLo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
									addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float	
									addInstr(new TAInstructions(TAInstructions.Instr.DivF,floatLo,ro,result));	
								}else{
										if (lo.getType()==Type.FLOAT && ro.getType()==Type.INT ){//If expr.type=int so leftop and rightop will be int
												Location floatRo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
												addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,ro,floatRo));//convert lo to float	
												addInstr(new TAInstructions(TAInstructions.Instr.DivF,lo,floatRo,result));					
										}else{addInstr(new TAInstructions(TAInstructions.Instr.DivF,lo,ro,result));}//Both type operators are float
								}
							}
							return result;
			case MOD: 
						addInstr(new TAInstructions(TAInstructions.Instr.Mod,lo,ro,result));
						return result;
			case LE: 
					if(lo.getType()==Type.INT && ro.getType()==Type.INT ){
						addInstr(new TAInstructions(TAInstructions.Instr.LesI,lo,ro,result));	
					}else{//expr.getType()==Type.FLOAT	 
					 		if (lo.getType()==Type.INT && ro.getType()==Type.FLOAT ){
								Location floatLo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
								addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float	
								addInstr(new TAInstructions(TAInstructions.Instr.LesF,floatLo,ro,result));	
							}else{
									if (lo.getType()==Type.FLOAT && ro.getType()==Type.INT ){//If expr.type=int so leftop and rightop will be int
											Location floatRo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
											addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,ro,floatRo));//convert lo to float	
											addInstr(new TAInstructions(TAInstructions.Instr.LesF,lo,floatRo,result));					
									}else{addInstr(new TAInstructions(TAInstructions.Instr.LesF,lo,ro,result));}//Both type operators are float
							}
						}
						return result;						
			case LEQ: 
					if(lo.getType()==Type.INT && ro.getType()==Type.INT ){
						addInstr(new TAInstructions(TAInstructions.Instr.LEI,lo,ro,result));	
					}else{//expr.getType()==Type.FLOAT	 
					 		if (lo.getType()==Type.INT && ro.getType()==Type.FLOAT ){
								Location floatLo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
								addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float	
								addInstr(new TAInstructions(TAInstructions.Instr.LEF,floatLo,ro,result));	
							}else{
									if (lo.getType()==Type.FLOAT && ro.getType()==Type.INT ){//If expr.type=int so leftop and rightop will be int
											Location floatRo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
											addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,ro,floatRo));//convert lo to float	
											addInstr(new TAInstructions(TAInstructions.Instr.LEF,lo,floatRo,result));					
									}else{addInstr(new TAInstructions(TAInstructions.Instr.LEF,lo,ro,result));}//Both type operators are float
							}
						}
						return result;											
			case GE: 
					if(lo.getType()==Type.INT && ro.getType()==Type.INT ){
						addInstr(new TAInstructions(TAInstructions.Instr.GrtI,lo,ro,result));	
					}else{//expr.getType()==Type.FLOAT	 
					 		if (lo.getType()==Type.INT && ro.getType()==Type.FLOAT ){
								Location floatLo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
								addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float	
								addInstr(new TAInstructions(TAInstructions.Instr.GrtF,floatLo,ro,result));	
							}else{
									if (lo.getType()==Type.FLOAT && ro.getType()==Type.INT ){//If expr.type=int so leftop and rightop will be int
											Location floatRo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
											addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,ro,floatRo));//convert lo to float	
											addInstr(new TAInstructions(TAInstructions.Instr.GrtF,lo,floatRo,result));					
									}else{addInstr(new TAInstructions(TAInstructions.Instr.GrtF,lo,ro,result));}//Both type operators are float
							}
						}
						return result;						
			case GEQ: 
					if(lo.getType()==Type.INT && ro.getType()==Type.INT ){
						addInstr(new TAInstructions(TAInstructions.Instr.GEI,lo,ro,result));	
					}else{//expr.getType()==Type.FLOAT	 
					 		if (lo.getType()==Type.INT && ro.getType()==Type.FLOAT ){
								Location floatLo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
								addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float	
								addInstr(new TAInstructions(TAInstructions.Instr.GEF,floatLo,ro,result));	
							}else{
									if (lo.getType()==Type.FLOAT && ro.getType()==Type.INT ){//If expr.type=int so leftop and rightop will be int
											Location floatRo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
											addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,ro,floatRo));//convert lo to float	
											addInstr(new TAInstructions(TAInstructions.Instr.GEF,lo,floatRo,result));					
									}else{addInstr(new TAInstructions(TAInstructions.Instr.GEF,lo,ro,result));}//Both type operators are float
							}
						}
						return result;						
			case NEQ: 
					if(lo.getType()==Type.INT && ro.getType()==Type.INT ){
						addInstr(new TAInstructions(TAInstructions.Instr.Dif,lo,ro,result));	
					}else{//expr.getType()==Type.FLOAT	 
					 		if (lo.getType()==Type.INT && ro.getType()==Type.FLOAT ){
								Location floatLo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
								addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float	
								addInstr(new TAInstructions(TAInstructions.Instr.Dif,floatLo,ro,result));	
							}else{
									if (lo.getType()==Type.FLOAT && ro.getType()==Type.INT ){//If expr.type=int so leftop and rightop will be int
											Location floatRo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
											addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,ro,floatRo));//convert lo to float	
											addInstr(new TAInstructions(TAInstructions.Instr.Dif,lo,floatRo,result));					
									}else{addInstr(new TAInstructions(TAInstructions.Instr.Dif,lo,ro,result));}//Both type operators are float
							}
						}
						return result;						
			case CEQ: 
					if(lo.getType()==Type.INT && ro.getType()==Type.INT ){
						addInstr(new TAInstructions(TAInstructions.Instr.Equal,lo,ro,result));	
					}else{//expr.getType()==Type.FLOAT	 
					 		if (lo.getType()==Type.INT && ro.getType()==Type.FLOAT ){
								Location floatLo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
								addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float	
								addInstr(new TAInstructions(TAInstructions.Instr.Equal,floatLo,ro,result));	
							}else{
									if (lo.getType()==Type.FLOAT && ro.getType()==Type.INT ){//If expr.type=int so leftop and rightop will be int
											Location floatRo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
											addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,ro,floatRo));//convert lo to float	
											addInstr(new TAInstructions(TAInstructions.Instr.Equal,lo,floatRo,result));					
									}else{addInstr(new TAInstructions(TAInstructions.Instr.Equal,lo,ro,result));}//Both type operators are float
							}
						}
						return result;						
			case AND: 
						addInstr(new TAInstructions(TAInstructions.Instr.And,lo,ro,result));
						return result;						
			case OR: 
						addInstr(new TAInstructions(TAInstructions.Instr.Or,lo,ro,result));
						return result;						
		}
		return result;
	}
	
	//Push de los parametros, llamada al metodo, pop de los parametros y retorno el valor de resultado del metodo(es un literal siempre)
	public Expression visit(ExterninvkCallExpr expr){
		for(Expression e: expr.getArguments()){//Loop for push parameters
			Expression value= e.accept(this);
			if (value instanceof Literal){
				addInstr(new TAInstructions(TAInstructions.Instr.ParamPush,value));//Parameter's value Push 
			}else{//value intance of location
					addInstr(new TAInstructions(TAInstructions.Instr.ParamPush,value));//Parameter's location Push 	
     			 }				 
		}
		Location result= new VarLocation(Integer.toString(line),expr.getLineNumber(),expr.getColumnNumber(),-1);//Location for save procedure's result
		addInstr(new TAInstructions(TAInstructions.Instr.CallExtern,new LabelExpr(expr.getMethod()),result));//Call sub-rutina 	
		for(Expression e: expr.getArguments()){//Loop for pop parameters
			Expression value= e.accept(this);
			if (value instanceof Literal){
				addInstr(new TAInstructions(TAInstructions.Instr.ParamPop,value));//Parameter's value Pop
			}else{//value intance of location
					addInstr(new TAInstructions(TAInstructions.Instr.ParamPop,value));//Parameter's location Pop 	
				}	
		}
		return result;
	}
	//Push de los parametros, llamada al metodo, pop de los parametros y retorno el valor de resultado del metodo(es un literal siempre)
	public Expression visit(MethodCallExpr expr){
		for(Expression e: expr.getArguments()){//Loop for push parameters
			Expression value= e.accept(this);
			if (value instanceof Literal){
				addInstr(new TAInstructions(TAInstructions.Instr.ParamPush,value));//Parameter's value Push 
			}else{//value intance of location
					addInstr(new TAInstructions(TAInstructions.Instr.ParamPush,value));//Parameter's location Push 	
				}				 
		}
		Location result= new VarLocation(Integer.toString(line),expr.getLineNumber(),expr.getColumnNumber(),-1);//Location for save procedure's result
		addInstr(new TAInstructions(TAInstructions.Instr.Call,new LabelExpr(expr.getMethod().getId()),result));//Call sub-rutina 	
		for(Expression e: expr.getArguments()){//Loop for pop parameters
			Expression value= e.accept(this);
			if (value instanceof Literal){
				addInstr(new TAInstructions(TAInstructions.Instr.ParamPop,value));//Parameter's value Pop
			}else{//value intance of location
					addInstr(new TAInstructions(TAInstructions.Instr.ParamPop,value));//Parameter's location Pop 	
				}	
			
		}
		return result;//return method's result 
	}

//Aplicar el operador unario a la expresion
	public Expression visit(UnaryOpExpr expr){//return literal or location where will be the value
		Expression value=expr.getExpression().accept(this);//Obtengo la expresion		
		UnaryOpType operator= expr.getOperator();
		if (value instanceof Literal){
			switch (operator){
				case MINUS: if (value instanceof IntLiteral){
								return new IntLiteral(-((Integer)(((Literal)value).getValue())),value.getLineNumber(),value.getColumnNumber()); // debo retornar otro literal
							}else{return new FloatLiteral(-((Float)(((Literal)value).getValue())),value.getLineNumber(),value.getColumnNumber());} //debo retornar otro literal 		
				case NOT:	return  new BooleanLiteral(!((Boolean)((Literal)value).getValue()),value.getLineNumber(),value.getColumnNumber());
			}		
		}else{
				Location temp= new VarLocation(Integer.toString(line),expr.getLineNumber(),expr.getColumnNumber(),-1);//place for current calculus
				switch (operator){
					case MINUS: 
								addInstr(new TAInstructions(TAInstructions.Instr.MinusI,value,temp));//save sentence for calc this value
								break; 		
					case NOT:
								addInstr(new TAInstructions(TAInstructions.Instr.Not,value,temp));//save sentence for calc this value								
								break; 							
				}
				return temp;//return the temp place where will be the value of this aplication					
		}	
		return null;	
	}

	public Expression visit(LabelExpr expr){
		return expr;
	}

// visit literals	
	public Expression visit(IntLiteral lit){return lit;}
	public Expression visit(FloatLiteral lit){return lit;}
	public Expression visit(BooleanLiteral lit){return lit;}
	public Expression visit(StringLiteral lit){	return lit;}


	public List<TAInstructions> getTAC(){
		return TAC;
	}

//Auxiliar methods

	private void addInstr(TAInstructions inst){
		TAC.add(line, inst);
		line++;
	}	

}