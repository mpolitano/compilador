/*
	Class that visit AST and generate a tree address code 
*/

package ir.intermediateCode;

import ir.ASTVisitor;
import ir.ast.*;
import java.util.*;

public class TACVisitor implements ASTVisitor<Expression>{

private List<TAInstructions> TAC;
private int line;
private Queue<LabelExpr> loopsEndLabel;
private Queue<LabelExpr> loopsLabel;


public TACVisitor(){
	TAC= new LinkedList();
	line=0;
	loopsLabel= new LinkedList<LabelExpr>();
	loopsEndLabel= new LinkedList<LabelExpr>();
}

//visit program
	public Expression visit(Program prog){
		addInstr(new TAInstructions(TAInstructions.Instr.ProgramDecl,new LabelExpr(prog.getId())));//declare a program
		if (prog.getFields()!=null)
			for (Location l: prog.getFields()){
				addInstr(new TAInstructions(TAInstructions.Instr.LocationDecl,l));
			}
		if (prog.getMethods()!=null)	
			for(MethodLocation m : prog.getMethods()){
				m.accept(this);
			}
		return null;
	}
	
// visit statements
	public Expression visit(AssignStmt stmt){//Aca podria tener una expresion compuesta o un literal
		Location aux;
		Expression expr= stmt.getExpression().accept(this);	
		switch(stmt.getOperator()){
			case INCREMENT: 
							switch(expr.getType()){
								case INT:aux= new VarLocation(Integer.toString(line),expr.getLineNumber(),expr.getColumnNumber(),-1);//Variable auxiliar para decrementar la expresion
									addInstr(new TAInstructions(TAInstructions.Instr.AddI,expr,new IntLiteral(1,expr.getLineNumber(),expr.getColumnNumber()),aux)); //decremento la expresion
									if (stmt.getLocation() instanceof ArrayLocation)
										addInstr(new TAInstructions(TAInstructions.Instr.WriteArray,aux,stmt.getLocation()));
									else
										addInstr(new TAInstructions(TAInstructions.Instr.Assign,aux,stmt.getLocation()));
									return null;
								case FLOAT:aux= new VarLocation(Integer.toString(line),expr.getLineNumber(),expr.getColumnNumber(),-1);//Variable auxiliar para decrementar la expresion
											addInstr(new TAInstructions(TAInstructions.Instr.AddF,expr,new FloatLiteral(1,expr.getLineNumber(),expr.getColumnNumber()),aux)); //decremento la expresion
											if (stmt.getLocation() instanceof ArrayLocation)
												addInstr(new TAInstructions(TAInstructions.Instr.WriteArray,aux,stmt.getLocation()));
											else
												addInstr(new TAInstructions(TAInstructions.Instr.Assign,aux,stmt.getLocation()));
											return null;
							}
			case DECREMENT: 								
							switch(expr.getType()){
								case INT:aux= new VarLocation(Integer.toString(line),expr.getLineNumber(),expr.getColumnNumber(),-1);//Variable auxiliar para decrementar la expresion
										addInstr(new TAInstructions(TAInstructions.Instr.SubI,expr,new IntLiteral(1,expr.getLineNumber(),expr.getColumnNumber()),aux)); //decremento la expresion
												if (stmt.getLocation() instanceof ArrayLocation)
													addInstr(new TAInstructions(TAInstructions.Instr.WriteArray,aux,stmt.getLocation()));
												else
													addInstr(new TAInstructions(TAInstructions.Instr.Assign,aux,stmt.getLocation()));
												return null;
								case FLOAT:aux= new VarLocation(Integer.toString(line),expr.getLineNumber(),expr.getColumnNumber(),-1);//Variable auxiliar para decrementar la expresion
										addInstr(new TAInstructions(TAInstructions.Instr.SubF,expr,new FloatLiteral(1,expr.getLineNumber(),expr.getColumnNumber()),aux)); //decremento la expresion
										if (stmt.getLocation() instanceof ArrayLocation)
											addInstr(new TAInstructions(TAInstructions.Instr.WriteArray,aux,stmt.getLocation()));
										else
											addInstr(new TAInstructions(TAInstructions.Instr.Assign,aux,stmt.getLocation()));
										return null;
							}
			case ASSIGN: 					
							addInstr(new TAInstructions(TAInstructions.Instr.Assign,expr,stmt.getLocation()));
							return null;
		}
		return null;
	}

//This method generate return expresion TAC
	public Expression visit(ReturnStmt stmt){
		if (stmt.getExpression()!=null){
			Expression retExpr= stmt.getExpression().accept(this);
			addInstr(new TAInstructions(TAInstructions.Instr.Ret, retExpr));
		}
		return null;
	}
	
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
			if (((BooleanLiteral)expr).getValue()== true){
					addInstr(new TAInstructions(TAInstructions.Instr.PutLabel, lif)); //put label for read only
					stmt.getIfBlock().accept(this);
					addInstr(new TAInstructions(TAInstructions.Instr.PutLabel, endif));		
			}else{
					if ((((BooleanLiteral)expr).getValue()== true) && (stmt.getElseBlock()!=null)){
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
		if (stmt.getFields()!=null)
			for (Location l: stmt.getFields()){
				addInstr(new TAInstructions(TAInstructions.Instr.LocationDecl,l));
			}
		if (stmt.getStatements()!=null)
			for (Statement s: stmt.getStatements()){
				s.accept(this);
			}		
		return null;
	}

	public Expression visit(BreakStmt stmt){
		addInstr(new TAInstructions(TAInstructions.Instr.Jmp,loopsEndLabel.peek()));//add intruction for jump to end of loop
		return null;
	}
	public Expression visit(ContinueStmt stmt){
		LabelExpr loopLabel=loopsLabel.peek();
		if (loopLabel.getLabel().contains("For_Loop")){
			Location forVar= loopLabel.getInfo();
			addInstr(new TAInstructions(TAInstructions.Instr.AddI, forVar,new IntLiteral(1,stmt.getLineNumber(),stmt.getColumnNumber()),forVar));//add 1 to for control variable
			addInstr(new TAInstructions(TAInstructions.Instr.Jmp,loopLabel));//jump to for condition eval 		
		}else{
			addInstr(new TAInstructions(TAInstructions.Instr.Jmp,loopLabel));//jump to while condition eval 		
		}
		return null;
	}
	
	public Expression visit(ForStmt stmt){
		Location forVar=stmt.getId();
		Expression initialValue= stmt.getInitialValue().accept(this);
		Expression finalValue= stmt.getFinalValue().accept(this);
		Location conditionValue= new VarLocation(Integer.toString(line),stmt.getLineNumber(),stmt.getColumnNumber(),-1);
		LabelExpr for_loop= new LabelExpr("For_Loop_"+ line,forVar);
		LabelExpr end_for= new LabelExpr("End_For_"+ line,forVar);
		loopsLabel.add(for_loop);
		loopsEndLabel.add(end_for);
		addInstr(new TAInstructions(TAInstructions.Instr.Assign,initialValue,forVar)); //Set variable for with initial value 
		addInstr(new TAInstructions(TAInstructions.Instr.PutLabel,for_loop));
		addInstr(new TAInstructions(TAInstructions.Instr.LesI, forVar, finalValue,conditionValue));	
		addInstr(new TAInstructions(TAInstructions.Instr.JFalse, end_for));	
		stmt.getBlock().accept(this);//generate for block TAC
		addInstr(new TAInstructions(TAInstructions.Instr.AddI,forVar,new IntLiteral(1,stmt.getLineNumber(),stmt.getColumnNumber()),forVar));	
		addInstr(new TAInstructions(TAInstructions.Instr.Jmp,for_loop));		
		addInstr(new TAInstructions(TAInstructions.Instr.PutLabel,end_for));	
		loopsLabel.remove();
		loopsEndLabel.remove();
		return null;
	}

	
	public Expression visit(SecStmt stmt){return null;}
	
	public Expression visit(WhileStmt stmt){
		Expression cond= stmt.getCondition();		
		LabelExpr while_condition=new LabelExpr("While_condition "+line);
		LabelExpr end_while= new LabelExpr("End_While "+line);
		if (cond instanceof Location){
			loopsLabel.add(while_condition);
			loopsEndLabel.add(end_while);
			addInstr(new TAInstructions(TAInstructions.Instr.PutLabel,while_condition));
			Location condEval=(Location)cond.accept(this);		
			addInstr(new TAInstructions(TAInstructions.Instr.JFalse,condEval,end_while));
			stmt.getBlock().accept(this);//generate TAC for body while
			addInstr(new TAInstructions(TAInstructions.Instr.JTrue, cond,while_condition));
			addInstr(new TAInstructions(TAInstructions.Instr.PutLabel,end_while));
			loopsLabel.remove();
			loopsEndLabel.remove();
		}else{
			if (((BooleanLiteral)cond).getValue()== true){
		 		addInstr(new TAInstructions(TAInstructions.Instr.PutLabel,while_condition));
				stmt.getBlock().accept(this);
				addInstr(new TAInstructions(TAInstructions.Instr.Jmp,while_condition));
			}
		}
	
		return null;
	}

//Idem a MethodCallStmt ver si no me trae probelmeas
	public Expression visit(MethodCallStmt stmt){
		for(Expression e: stmt.getArguments()){//Loop for push parameters
			Expression value= e.accept(this);
			if (value instanceof Literal){
				addInstr(new TAInstructions(TAInstructions.Instr.ParamPush,value));//Parameter's value Push 
			}else{//value intance of location
					addInstr(new TAInstructions(TAInstructions.Instr.ParamPush,value));//Parameter's location Push 	
				}				 
		}
		Location result= new VarLocation(Integer.toString(line),stmt.getLineNumber(),stmt.getColumnNumber(),-1);//Location for save procedure's result
		addInstr(new TAInstructions(TAInstructions.Instr.Call,new LabelExpr(stmt.getMethod().getId()),result));//Call sub-rutina 	
		for(Expression e: stmt.getArguments()){//Loop for pop parameters
			Expression value= e.accept(this);
			if (value instanceof Literal){
				addInstr(new TAInstructions(TAInstructions.Instr.ParamPop,value));//Parameter's value Pop
			}else{//value intance of location
					addInstr(new TAInstructions(TAInstructions.Instr.ParamPop,value));//Parameter's location Pop 	
				}		
		}
		return null;
	}

//Idem a ExterninvkCallStmt ver si no me trae probelmeas	
	public Expression visit(ExterninvkCallStmt stmt){
		for(Expression e: stmt.getArguments()){//Loop for push parameters
			Expression value= e.accept(this);
			if (value instanceof Literal){
				addInstr(new TAInstructions(TAInstructions.Instr.ParamPush,value));//Parameter's value Push 
			}else{//value intance of location
					addInstr(new TAInstructions(TAInstructions.Instr.ParamPush,value));//Parameter's location Push 	
     			 }				 
		}
		Location result= new VarLocation(Integer.toString(line),stmt.getLineNumber(),stmt.getColumnNumber(),-1);//Location for save procedure's result
		addInstr(new TAInstructions(TAInstructions.Instr.CallExtern,new LabelExpr(stmt.getMethod()),result));//Call sub-rutina 	
		for(Expression e: stmt.getArguments()){//Loop for pop parameters
			Expression value= e.accept(this);
			if (value instanceof Literal){
				addInstr(new TAInstructions(TAInstructions.Instr.ParamPop,value));//Parameter's value Pop
			}else{//value intance of location
					addInstr(new TAInstructions(TAInstructions.Instr.ParamPop,value));//Parameter's location Pop 	
				}	
		}
		return null;
	}

//Visit Location
	public Expression visit(VarLocation var){return var;}
	
	public Expression visit(MethodLocation method){
		addInstr(new TAInstructions(TAInstructions.Instr.MethodDecl,new LabelExpr(method.getId())));//Start method declaration
		method.getBody().accept(this);
		addInstr(new TAInstructions(TAInstructions.Instr.MethodDeclEnd,new LabelExpr(method.getId())));//end Method declaration
		return null;
	}

	//Asumiendo que las arrayLocation las visito unicamente en expresiones, accedemos a la posicion y la almacenamos en un teporal
	public Expression visit(ArrayLocation array){
		VarLocation varArray= new VarLocation(Integer.toString(line),array.getLineNumber(),array.getColumnNumber(),-1);
		addInstr(new TAInstructions(TAInstructions.Instr.ReadArray,array,varArray)); //ReadArray from destination
		varArray.setType(array.getType().fromArray());//set new label with type
		return varArray;
	}	
	
// visit expressions
	public Expression visit(BinOpExpr expr){
		Expression lo= (Expression) expr.getLeftOperand().accept(this);//literal or location
		Expression ro= (Expression) expr.getRightOperand().accept(this);
		BinOpType op= expr.getOperator();
		Location result= new VarLocation(Integer.toString(line),expr.getLineNumber(),expr.getColumnNumber(),-1);//Ojo que el auxiliar para calcular los cambios a flot van a tener el mismo nombre que result
		switch(op){
			case PLUS: if (expr.getType()==Type.INT){//If expr.type=int so leftop and rightop will be int
							addInstr(new TAInstructions(TAInstructions.Instr.AddI,lo,ro,result));
							result.setType(Type.INT);
						}else{//expe.type=float
							if (lo.getType()==Type.INT){//lo is int
								Location floatLo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
								floatLo.setType(Type.FLOAT);
								addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float
								addInstr(new TAInstructions(TAInstructions.Instr.AddF,floatLo,ro,result)); //calc add	
								result.setType(Type.FLOAT);
							}else{
									if (ro.getType()==Type.INT){//ro is int
										Location floatRo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
										floatRo.setType(Type.FLOAT);
										addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatRo));//convert ro to float
								 		addInstr(new TAInstructions(TAInstructions.Instr.AddF,lo,floatRo,result)); //calc add	
								 		result.setType(Type.FLOAT);
								 	}else{//ninguno es int
								 			addInstr(new TAInstructions(TAInstructions.Instr.AddF,lo,ro,result)); //calc add	
								 			result.setType(Type.FLOAT);
								 	}
								 }
							
						}
						return result;
		
			case MINUS: if (expr.getType()==Type.INT){//If expr.type=int so leftop and rightop will be int
							addInstr(new TAInstructions(TAInstructions.Instr.SubI,lo,ro,result));
							result.setType(Type.INT);
						}else{ //(expr.getType==Type.FLOAT)
							if (lo.getType()==Type.INT){//lo is int
								Location floatLo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
								floatLo.setType(Type.FLOAT);
								addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float
								addInstr(new TAInstructions(TAInstructions.Instr.SubF,floatLo,ro,result));//calc add	
								result.setType(Type.FLOAT);
							}else{
									if (ro.getType()==Type.INT){//ro is int
										Location floatRo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
										floatRo.setType(Type.FLOAT);
										addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatRo));//convert ro to float
								 		addInstr(new TAInstructions(TAInstructions.Instr.SubF,lo,floatRo,result)); //calc add	
								 		result.setType(Type.FLOAT);
								 	}else{//ninguno es int
								 			addInstr(new TAInstructions(TAInstructions.Instr.SubF,lo,ro,result));//calc add	
								 			result.setType(Type.FLOAT);
								 	}
								 }
							
						}
						return result;
			case MULTIPLY: if (expr.getType()==Type.INT){//If expr.type=int so leftop and rightop will be int
							addInstr(new TAInstructions(TAInstructions.Instr.MultI,lo,ro,result));
							result.setType(Type.INT);
						}else{ //(expr.getType==Type.FLOAT)
							if (lo.getType()==Type.INT){//lo is int
								Location floatLo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
								floatLo.setType(Type.FLOAT);
								addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float
								addInstr(new TAInstructions(TAInstructions.Instr.MultF,floatLo,ro,result));//calc add	
								result.setType(Type.FLOAT);
							}else{
									if (ro.getType()==Type.INT){//ro is int
										Location floatRo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
										floatRo.setType(Type.FLOAT);
										addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatRo));//convert ro to float
								 		addInstr(new TAInstructions(TAInstructions.Instr.MultF,lo,floatRo,result)); //calc add	
								 		result.setType(Type.FLOAT);
								 	}else{//ninguno es int
								 			addInstr(new TAInstructions(TAInstructions.Instr.MultF,lo,ro,result));//calc add	
								 			result.setType(Type.FLOAT);
								 	}
								 }
							
						}
						return result;
			case DIVIDE:
						if(expr.getType()==Type.INT ){//semantica de la division entera
							addInstr(new TAInstructions(TAInstructions.Instr.DivI,lo,ro,result));	
							result.setType(Type.INT);
						}else{//expr.getType()==Type.FLOAT	 
						 		if (lo.getType()==Type.INT && ro.getType()==Type.FLOAT ){
									Location floatLo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
									floatLo.setType(Type.FLOAT);
									addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float										
									addInstr(new TAInstructions(TAInstructions.Instr.DivF,floatLo,ro,result));	
									result.setType(Type.FLOAT);
								}else{
										if (lo.getType()==Type.FLOAT && ro.getType()==Type.INT ){//If expr.type=int so leftop and rightop will be int
												Location floatRo= new VarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),-1);
												floatRo.setType(Type.FLOAT);
												addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,ro,floatRo));//convert lo to float	
												addInstr(new TAInstructions(TAInstructions.Instr.DivF,lo,floatRo,result));					
												result.setType(Type.FLOAT);
										}else{addInstr(new TAInstructions(TAInstructions.Instr.DivF,lo,ro,result));result.setType(Type.FLOAT);}//Both type operators are float
								}
							}
							return result;
			case MOD: 
						addInstr(new TAInstructions(TAInstructions.Instr.Mod,lo,ro,result));
						result.setType(Type.INT);
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
						result.setType(Type.BOOLEAN);
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
						result.setType(Type.BOOLEAN);
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
						result.setType(Type.BOOLEAN);
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
						result.setType(Type.BOOLEAN);
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
						result.setType(Type.BOOLEAN);
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
						result.setType(Type.BOOLEAN);
						return result;						
			case AND: 
						addInstr(new TAInstructions(TAInstructions.Instr.And,lo,ro,result));
						result.setType(Type.BOOLEAN);
						return result;						
			case OR: 
						addInstr(new TAInstructions(TAInstructions.Instr.Or,lo,ro,result));
						result.setType(Type.BOOLEAN);
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
		result.setType(expr.getType());
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
		result.setType(expr.getType());
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
				temp.setType(value.getType());//new value will have same value that before
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