/*
	Class that visit AST and generate a tree address code 
*/

package ir.intermediateCode;

import ir.ASTVisitor;
import ir.ast.*;
import java.util.*;
import ir.codeGeneration.floatCheckVisitor;
public class TACVisitor implements ASTVisitor<Expression>{

private List<TAInstructions> TAC;
private List<TAInstructions> listString; //List for save the string used as parameter in externivink calls.
int stringLabel; 
private int line;
private Stack<LabelExpr> loopsEndLabel; //for drive break statement
private Stack<LabelExpr> loopsLabel;//for drive continue statement
private MethodLocation currentMethod;

public TACVisitor(){
	TAC= new LinkedList();
	line=0;
	stringLabel=0;
	loopsLabel= new Stack<LabelExpr>();
	loopsEndLabel= new Stack<LabelExpr>();
	listString= new LinkedList<TAInstructions>();
}

//visit program
	public Expression visit(Program prog){
		floatCheckVisitor floatVisitor= new floatCheckVisitor(); //Visit AST for detect externinvk call with float.
		prog.accept(floatVisitor);
		addInstr(new TAInstructions(TAInstructions.Instr.ProgramDecl,new LabelExpr(prog.getId())));//declare a program
		if (prog.getFields()!=null)
			for (Location l: prog.getFields()){
				l.accept(this);
			}
		if (prog.getMethods()!=null)	
			for(MethodLocation m : prog.getMethods()){
				m.accept(this);
			}
			TAC.addAll(listString);//Apend generate String Literal instructions to end of list.
		return null;
	}
	
// visit statements
	public Expression visit(AssignStmt stmt){//Aca podria tener una expresion compuesta o un literal
		RefLocation result;
		Expression expr= stmt.getExpression().accept(this);	
		switch(stmt.getOperator()){
			case INCREMENT: 
							switch(expr.getType()){
								case INT:
									result= new RefVarLocation(Integer.toString(line),expr.getLineNumber(),expr.getColumnNumber(),Type.INT,currentMethod.newLocalLocation());//Variable auxiliar, created a VarLocation and set offset
									addInstr(new TAInstructions(TAInstructions.Instr.AddI,stmt.getLocation(),expr,result)); //a+=i make a=a+i
									//Instruccion for assing result.
									if (stmt.getLocation() instanceof RefArrayLocation){//todo resolve the semantic of +=
										Expression dir=((RefArrayLocation)stmt.getLocation()).getExpression().accept(this);//can be an IntLiteral or RefVarLocation
										addInstr(new TAInstructions(TAInstructions.Instr.WriteArray,result,dir,stmt.getLocation()));
									}else
										addInstr(new TAInstructions(TAInstructions.Instr.Assign,result,stmt.getLocation()));
									return null;
								case FLOAT:
											result= new RefVarLocation(Integer.toString(line),expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());//Variable auxiliar para decrementar la expresion
											addInstr(new TAInstructions(TAInstructions.Instr.AddF,stmt.getLocation(),result,result)); //decremento la expresion
											if (stmt.getLocation() instanceof RefArrayLocation){
												Expression dir=((RefArrayLocation)stmt.getLocation()).getExpression().accept(this);
												addInstr(new TAInstructions(TAInstructions.Instr.WriteArray,result,dir,stmt.getLocation()));
											}else
												addInstr(new TAInstructions(TAInstructions.Instr.Assign,result,stmt.getLocation()));
											return null;
							}
			case DECREMENT: 								
							switch(expr.getType()){
								case INT:
										result= new RefVarLocation(Integer.toString(line),expr.getLineNumber(),expr.getColumnNumber(),Type.INT,currentMethod.newLocalLocation());//Variable auxiliar para decrementar la expresion.Created VarLocation and set offset
										addInstr(new TAInstructions(TAInstructions.Instr.SubI,stmt.getLocation(),expr,result)); //decremento la expresion
												if (stmt.getLocation() instanceof RefArrayLocation){
													Expression dir=((RefArrayLocation)stmt.getLocation()).getExpression().accept(this);
													addInstr(new TAInstructions(TAInstructions.Instr.WriteArray,result,dir,stmt.getLocation()));
												}else
													addInstr(new TAInstructions(TAInstructions.Instr.Assign,result,stmt.getLocation()));
												return null;
								case FLOAT:result= new RefVarLocation(Integer.toString(line),expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());//Variable auxiliar para decrementar la expresion. Created VarLocation and set offset
										addInstr(new TAInstructions(TAInstructions.Instr.SubF,stmt.getLocation(),expr,result)); //decremento la expresion
										if (stmt.getLocation() instanceof RefArrayLocation){
											Expression dir=((RefArrayLocation)stmt.getLocation()).getExpression().accept(this);
											addInstr(new TAInstructions(TAInstructions.Instr.WriteArray,result,dir,stmt.getLocation()));
										}else
											addInstr(new TAInstructions(TAInstructions.Instr.Assign,result,stmt.getLocation()));
										return null;
							}
			case ASSIGN: 					
							if (stmt.getLocation() instanceof RefArrayLocation){
								Expression dir=((RefArrayLocation)stmt.getLocation()).getExpression().accept(this);
								addInstr(new TAInstructions(TAInstructions.Instr.WriteArray,expr,dir,stmt.getLocation()));
							}else
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
			LabelExpr lif= new LabelExpr(".if_"+ Integer.toString(line));
			LabelExpr endif= new LabelExpr(".endif_"+ Integer.toString(line));
			Expression expr=stmt.getCondition().accept(this); //Generate TAC for evaluate if condition
			if (expr instanceof RefLocation){
				if (stmt.getElseBlock()==null){
					addInstr(new TAInstructions(TAInstructions.Instr.JTrue,expr,lif));//if condition=true jump to if's block
					addInstr(new TAInstructions(TAInstructions.Instr.Jmp,endif));//if condition=false jump to if block's end 
					addInstr(new TAInstructions(TAInstructions.Instr.PutLabel, lif));
					stmt.getIfBlock().accept(this);
					addInstr(new TAInstructions(TAInstructions.Instr.PutLabel, endif));		
				}else{
					LabelExpr endElse= new LabelExpr(".endElse_"+ Integer.toString(line));
					addInstr(new TAInstructions(TAInstructions.Instr.JTrue,expr,lif));//if condition=true jump to if's block
					addInstr(new TAInstructions(TAInstructions.Instr.Jmp,endif));//if condition=false jump to if block's end 
					addInstr(new TAInstructions(TAInstructions.Instr.PutLabel, lif));
					stmt.getIfBlock().accept(this);
					addInstr(new TAInstructions(TAInstructions.Instr.Jmp,endElse));//if condition=false jump to if block's end 
					addInstr(new TAInstructions(TAInstructions.Instr.PutLabel, endif));					
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
				l.accept(this);
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
		if (loopLabel.getLabel().contains(".For_Loop")){
			RefLocation forVar= loopLabel.getInfo();
			addInstr(new TAInstructions(TAInstructions.Instr.AddI, forVar,new IntLiteral(1,stmt.getLineNumber(),stmt.getColumnNumber()),forVar));//add 1 to for control variable
			addInstr(new TAInstructions(TAInstructions.Instr.Jmp,loopLabel));//jump to for condition eval 		
		}else{
			addInstr(new TAInstructions(TAInstructions.Instr.Jmp,loopLabel));//jump to while condition eval 		
		}
		return null;
	}
	
	public Expression visit(ForStmt stmt){
		RefLocation forVar=stmt.getId();
		Expression initialValue= stmt.getInitialValue().accept(this);
		Expression finalValue= stmt.getFinalValue().accept(this);
		RefLocation conditionValue= new RefVarLocation(Integer.toString(line),stmt.getLineNumber(),stmt.getColumnNumber(),Type.BOOLEAN,currentMethod.newLocalLocation());
		LabelExpr for_loop= new LabelExpr(".For_Loop_"+ line,forVar);
		LabelExpr end_for= new LabelExpr(".End_For_"+ line,forVar);
		loopsLabel.push(for_loop);
		loopsEndLabel.push(end_for);
		addInstr(new TAInstructions(TAInstructions.Instr.Assign,initialValue,forVar)); //Set variable for with initial value 
		addInstr(new TAInstructions(TAInstructions.Instr.PutLabel,for_loop));
		addInstr(new TAInstructions(TAInstructions.Instr.LEI, forVar, finalValue,conditionValue));	
		addInstr(new TAInstructions(TAInstructions.Instr.JFalse,conditionValue, end_for));	
		stmt.getBlock().accept(this);//generate for block TAC
		addInstr(new TAInstructions(TAInstructions.Instr.AddI,forVar,new IntLiteral(1,stmt.getLineNumber(),stmt.getColumnNumber()),forVar));	
		addInstr(new TAInstructions(TAInstructions.Instr.Jmp,for_loop));		
		addInstr(new TAInstructions(TAInstructions.Instr.PutLabel,end_for));
		loopsLabel.pop();
		loopsEndLabel.pop();
		return null;
	}

	
	public Expression visit(SecStmt stmt){return null;}
	
	public Expression visit(WhileStmt stmt){
		Expression cond= stmt.getCondition();		
		LabelExpr while_condition=new LabelExpr(".While_condition_"+line);
		LabelExpr end_while= new LabelExpr(".End_While_"+line);			
		if (cond instanceof BooleanLiteral){
			if (((BooleanLiteral)cond).getValue()== true){
		 		loopsLabel.push(while_condition);
				loopsEndLabel.push(end_while);
		 		addInstr(new TAInstructions(TAInstructions.Instr.PutLabel,while_condition));
				stmt.getBlock().accept(this);
				addInstr(new TAInstructions(TAInstructions.Instr.Jmp,while_condition));
				addInstr(new TAInstructions(TAInstructions.Instr.PutLabel,end_while));//put end while condition for break jump
				loopsLabel.pop();
				loopsEndLabel.pop();
			}
		}else{//instance of Expression so cond.accept(this) return a VarLocation
	 		loopsLabel.push(while_condition);
			loopsEndLabel.push(end_while);
			addInstr(new TAInstructions(TAInstructions.Instr.PutLabel,while_condition));
			RefLocation condEval=(RefLocation)cond.accept(this);		
			addInstr(new TAInstructions(TAInstructions.Instr.JFalse,condEval,end_while));
			stmt.getBlock().accept(this);//generate TAC for body while
			addInstr(new TAInstructions(TAInstructions.Instr.Jmp,while_condition));
			addInstr(new TAInstructions(TAInstructions.Instr.PutLabel,end_while));
			loopsLabel.pop();
			loopsEndLabel.pop();
		}
		return null;
	}

//Idem a MethodCallStmt ver si no me trae probelmeas
	public Expression visit(MethodCallStmt stmt){
		List<Location> formalParameters= stmt.getMethod().getFormalParameters();
		List<Expression>actualParametersEval= new LinkedList<Expression>();
		int i=0; 
		Location dest;
		for(Expression e: stmt.getArguments()){
			Expression value= e.accept(this);
			actualParametersEval.add(value);
		}
		for(Expression value: actualParametersEval){//Loop for push parameters
			if (i<6){
				dest= new VarLocation("PushParamDest",-1,-1);
				dest.setOffset(i+1);
				addInstr(new TAInstructions(TAInstructions.Instr.ParamPush,value,dest));//Parameter's value Push 		 
			}else{
					dest=new VarLocation("PushParamDest",-1,-1);
					dest.setOffset(currentMethod.getOffset()-(4 * (i-5)));
					addInstr(new TAInstructions(TAInstructions.Instr.ParamPush,value,dest));//Parameter's value Push
				}
			i++;
		}		
		addInstr(new TAInstructions(TAInstructions.Instr.Call,new LabelExpr(stmt.getMethod().getId())));//Call sub-rutina 	
		if(formalParameters.size()>6){
			Expression bytesForPop=	new IntLiteral((formalParameters.size()-6)*4,-1,-1);
			addInstr(new TAInstructions(TAInstructions.Instr.ParamPop,bytesForPop));//pop parameters that stay en stack
		}		
		return null;//metho's return anything
	}

//Idem a ExterninvkCallStmt ver si no me trae probelmeas	
	public Expression visit(ExterninvkCallStmt stmt){
		List<Location> formalParameters= stmt.getFormalParameters();
		List<Expression>actualParametersEval= new LinkedList<Expression>();
		int i=0;		
		Location dest;
		for(Expression e: stmt.getArguments()){
			Expression value= e.accept(this);
			actualParametersEval.add(value);
		}
		for(Expression value: actualParametersEval){//Loop for push parameters
			if (value instanceof StringLiteral){//Generate a representation for String Literal pased as parameter
				String label=".StringLiteral"+Integer.toString(stringLabel);//label for StringLiteral				
				if(i<6){ 
						dest= new VarLocation("PushParamDest",-1,-1);
						dest.setOffset(i+1); 
						addInstr(new TAInstructions(TAInstructions.Instr.ParamPush,new StringLiteral(label,-1,-1),dest));//Parameter's value Push 	 
				}else{
						dest=new VarLocation("PushParamDest",-1,-1);
						dest.setOffset(currentMethod.getOffset()-(4 * (i-5)));
						addInstr(new TAInstructions(TAInstructions.Instr.ParamPush,new StringLiteral(label,-1,-1),dest));//Parameter's value Push	
					 }					
				((StringLiteral)value).setValue(label+":\n \t"+".string "+value.toString());
				listString.add(new TAInstructions(TAInstructions.Instr.PutStringLiteral,value));
				stringLabel++;	
			}else{//value isn't stringLiteral
					if (i<6){
						dest= new VarLocation("PushParamDest",-1,-1);
						dest.setOffset(i+1);
						addInstr(new TAInstructions(TAInstructions.Instr.ParamPush,value,dest));//Parameter's value Push 		 
					}else{
						dest=new VarLocation("PushParamDest",-1,-1);
						dest.setOffset(currentMethod.getOffset()-(4 * (i-5)));
						addInstr(new TAInstructions(TAInstructions.Instr.ParamPush,value,dest));//Parameter's value Push
					}
				}
				i++;
		}
		addInstr(new TAInstructions(TAInstructions.Instr.CallExtern,new LabelExpr(stmt.getMethod())));//Call sub-rutina 	
		if(stmt.getArguments().size()>6){
			Expression bytesForPop=	new IntLiteral((stmt.getArguments().size()-6)*4,-1,-1);
			addInstr(new TAInstructions(TAInstructions.Instr.ParamPop,bytesForPop));//pop parameters that stay en stack
 		}
		return null;//method return anything
	}

//Visit Location
	public Expression visit(VarLocation var){
		addInstr(new TAInstructions(TAInstructions.Instr.LocationDecl,var));
		return null;
	}

	public Expression visit(RefVarLocation var){
		return var;
	}
	
	//Start Method declaration, save parameters from register into stack and visit method's blocks
	public Expression visit(MethodLocation method){
		currentMethod= method;
		addInstr(new TAInstructions(TAInstructions.Instr.MethodDecl,method));//Start method declaration
		int i=1;
		for (Location l: method.getFormalParameters()){//loop for save parameter passed into register in stack
			RefLocation from= new RefVarLocation("SaveParam",-1,-1,l.getType(),i);
			RefLocation dest= new RefVarLocation((VarLocation)l,-1,-1);
			addInstr(new TAInstructions(TAInstructions.Instr.SaveParam,from,dest)); //ReadArray from destination	
			i++;
		}
		method.getBody().accept(this);
		addInstr(new TAInstructions(TAInstructions.Instr.MethodDeclEnd,method));//end Method declaration
		return null;
	}

	//Access to array position and return this in temp location
	public Expression visit(RefArrayLocation array){
		Expression dir= array.getExpression().accept(this);
		RefVarLocation varArray= new RefVarLocation(Integer.toString(line),array.getLineNumber(),array.getColumnNumber(),array.getType().fromArray(),currentMethod.newLocalLocation());
		addInstr(new TAInstructions(TAInstructions.Instr.ReadArray,array,dir,varArray)); //ReadArray from destination
		varArray.setType(array.getType().fromArray());//set new label with type
		return varArray;
	}	


	public Expression visit(ArrayLocation array){
		addInstr(new TAInstructions(TAInstructions.Instr.LocationDecl,array));
		return null;
	}

	
// visit expressions
	public Expression visit(BinOpExpr expr){
		Expression lo= (Expression) expr.getLeftOperand().accept(this);//literal or location
		Expression ro= (Expression) expr.getRightOperand().accept(this);
		BinOpType op= expr.getOperator();
		RefLocation result= new RefVarLocation(Integer.toString(line),expr.getLineNumber(),expr.getColumnNumber(),expr.getType(),currentMethod.newLocalLocation());//Ojo que el auxiliar para calcular los cambios a flot van a tener el mismo nombre que result,set offset for result location
		switch(op){
			case PLUS: if (lo.getType()==Type.INT && ro.getType()==Type.INT){//If expr.type=int so leftop and rightop will be int
							addInstr(new TAInstructions(TAInstructions.Instr.AddI,lo,ro,result));
							result.setType(Type.INT);
						}else{//expe.type=float
							if (lo.getType()==Type.INT){//lo is int
								RefLocation floatLo= new RefVarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());
								floatLo.setType(Type.FLOAT);
								addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float
								addInstr(new TAInstructions(TAInstructions.Instr.AddF,floatLo,ro,result)); //calc add	
								result.setType(Type.FLOAT);
							}else{
									if (ro.getType()==Type.INT){//ro is int
										RefLocation floatRo= new RefVarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());
										floatRo.setType(Type.FLOAT);
										addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,ro,floatRo));//convert ro to float
								 		addInstr(new TAInstructions(TAInstructions.Instr.AddF,lo,floatRo,result)); //calc add	
								 		result.setType(Type.FLOAT);
								 	}else{//ninguno es int
								 			addInstr(new TAInstructions(TAInstructions.Instr.AddF,lo,ro,result)); //calc add	
								 			result.setType(Type.FLOAT);
								 	}
								 }
							
						}
						return result;
		
			case MINUS: if (lo.getType()==Type.INT && ro.getType()==Type.INT){//If expr.type=int so leftop and rightop will be int
							addInstr(new TAInstructions(TAInstructions.Instr.SubI,lo,ro,result));
							result.setType(Type.INT);
						}else{ //(expr.getType==Type.FLOAT)
							if (lo.getType()==Type.INT){//lo is int
								RefLocation floatLo= new RefVarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());
								floatLo.setType(Type.FLOAT);
								addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float
								addInstr(new TAInstructions(TAInstructions.Instr.SubF,floatLo,ro,result));//calc add	
								result.setType(Type.FLOAT);
							}else{
									if (ro.getType()==Type.INT){//ro is int
										RefLocation floatRo= new RefVarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());
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
			case MULTIPLY: if (lo.getType()==Type.INT && ro.getType()==Type.INT){//If expr.type=int so leftop and rightop will be int
							addInstr(new TAInstructions(TAInstructions.Instr.MultI,lo,ro,result));
							result.setType(Type.INT);
						}else{ //(expr.getType==Type.FLOAT)
							if (lo.getType()==Type.INT){//lo is int
								RefLocation floatLo= new RefVarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());
								addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float
								addInstr(new TAInstructions(TAInstructions.Instr.MultF,floatLo,ro,result));//calc add	
								result.setType(Type.FLOAT);
							}else{
									if (ro.getType()==Type.INT){//ro is int
										RefLocation floatRo= new RefVarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());
										addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,ro,floatRo));//convert ro to float
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
						if(lo.getType()==Type.INT && ro.getType()==Type.INT){//semantica de la division entera
							addInstr(new TAInstructions(TAInstructions.Instr.DivI,lo,ro,result));	
							result.setType(Type.INT);
						}else{//expr.getType()==Type.FLOAT	 
						 		if (lo.getType()==Type.INT && ro.getType()==Type.FLOAT ){
									RefLocation floatLo= new RefVarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());
									addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float										
									addInstr(new TAInstructions(TAInstructions.Instr.DivF,floatLo,ro,result));	
									result.setType(Type.FLOAT);
								}else{
										if (lo.getType()==Type.FLOAT && ro.getType()==Type.INT ){//If expr.type=int so leftop and rightop will be int
												RefLocation floatRo= new RefVarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());
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
								RefLocation floatLo= new RefVarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());
								addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float	
								addInstr(new TAInstructions(TAInstructions.Instr.LesF,floatLo,ro,result));	
							}else{
									if (lo.getType()==Type.FLOAT && ro.getType()==Type.INT ){//If expr.type=int so leftop and rightop will be int
											RefLocation floatRo= new RefVarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());
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
								RefLocation floatLo= new RefVarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());
								addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float	
								addInstr(new TAInstructions(TAInstructions.Instr.LEF,floatLo,ro,result));	
							}else{
									if (lo.getType()==Type.FLOAT && ro.getType()==Type.INT ){//If expr.type=int so leftop and rightop will be int
											RefLocation floatRo= new RefVarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());
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
								RefLocation floatLo= new RefVarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());
								addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float	
								addInstr(new TAInstructions(TAInstructions.Instr.GrtF,floatLo,ro,result));	
							}else{
									if (lo.getType()==Type.FLOAT && ro.getType()==Type.INT ){//If expr.type=int so leftop and rightop will be int
											RefLocation floatRo= new RefVarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());
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
								RefLocation floatLo= new RefVarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());
								addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float	
								addInstr(new TAInstructions(TAInstructions.Instr.GEF,floatLo,ro,result));	
							}else{
									if (lo.getType()==Type.FLOAT && ro.getType()==Type.INT ){//If expr.type=int so leftop and rightop will be int
											RefLocation floatRo= new RefVarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());
											addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,ro,floatRo));//convert lo to float	
											addInstr(new TAInstructions(TAInstructions.Instr.GEF,lo,floatRo,result));					
									}else{addInstr(new TAInstructions(TAInstructions.Instr.GEF,lo,ro,result));}//Both type operators are float
							}
						}
						result.setType(Type.BOOLEAN);
						return result;						
			case NEQ: 
					if(lo.getType()==Type.INT && ro.getType()==Type.INT ){
						addInstr(new TAInstructions(TAInstructions.Instr.DifI,lo,ro,result));	
					}else{//expr.getType()==Type.FLOAT	 
					 		if (lo.getType()==Type.INT && ro.getType()==Type.FLOAT ){
								RefLocation floatLo= new RefVarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());
								addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float	
								addInstr(new TAInstructions(TAInstructions.Instr.DifF,floatLo,ro,result));	
							}else{
									if (lo.getType()==Type.FLOAT && ro.getType()==Type.INT ){//If expr.type=int so leftop and rightop will be int
											RefLocation floatRo= new RefVarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());
											addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,ro,floatRo));//convert lo to float	
											addInstr(new TAInstructions(TAInstructions.Instr.DifF,lo,floatRo,result));					
									}else{addInstr(new TAInstructions(TAInstructions.Instr.DifF,lo,ro,result));}//Both type operators are float
							}
						}
						result.setType(Type.BOOLEAN);
						return result;						
			case CEQ: 
					if(lo.getType()==Type.INT && ro.getType()==Type.INT ){
						addInstr(new TAInstructions(TAInstructions.Instr.EqualI,lo,ro,result));	
					}else{//expr.getType()==Type.FLOAT	 
					 		if (lo.getType()==Type.INT && ro.getType()==Type.FLOAT ){
								RefLocation floatLo= new RefVarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());
								addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,lo,floatLo));//convert lo to float	
								addInstr(new TAInstructions(TAInstructions.Instr.EqualF,floatLo,ro,result));	
							}else{
									if (lo.getType()==Type.FLOAT && ro.getType()==Type.INT ){//If expr.type=int so leftop and rightop will be int
											RefLocation floatRo= new RefVarLocation(Integer.toString(line), expr.getLineNumber(),expr.getColumnNumber(),Type.FLOAT,currentMethod.newLocalLocation());
											addInstr(new TAInstructions(TAInstructions.Instr.ToFloat,ro,floatRo));//convert lo to float	
											addInstr(new TAInstructions(TAInstructions.Instr.EqualF,lo,floatRo,result));		
									}else{
									addInstr(new TAInstructions(TAInstructions.Instr.EqualF,lo,ro,result));}//Both type operators are float
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
		List<Location> formalParameters= expr.getFormalParameters();
		List<Expression> actualParametersEval= new LinkedList<Expression>();
		int i=0;		
		Location dest;
		for(Expression e: expr.getArguments()){//Loop for generate TAC instructions for calc parameters
			Expression value= e.accept(this);
			actualParametersEval.add(value);
		}
		for(Expression value: actualParametersEval){
			if (value instanceof StringLiteral){//Generate a representation for String Literal pased as parameter
				String label=".StringLiteral"+Integer.toString(stringLabel);//label for StringLiteral				
				if(i<6){ 
						dest= new VarLocation("PushParamDest",-1,-1);
						dest.setOffset(i+1); 
						addInstr(new TAInstructions(TAInstructions.Instr.ParamPush,new StringLiteral(label,-1,-1),dest));//Parameter's value Push 	 
				}else{
						dest=new VarLocation("PushParamDest",-1,-1);
						dest.setOffset(currentMethod.getOffset()-(4 * (i-5)));
						addInstr(new TAInstructions(TAInstructions.Instr.ParamPush,new StringLiteral(label,-1,-1),dest));//Parameter's value Push	
					 }					
				((StringLiteral)value).setValue(label+":\n \t"+".string "+value.toString());
				listString.add(new TAInstructions(TAInstructions.Instr.PutStringLiteral,value));
				stringLabel++;	
			}else{//value isn't stringLiteral
					if (i<6){
						dest= new VarLocation("PushParamDest",-1,-1);
						dest.setOffset(i+1);
						addInstr(new TAInstructions(TAInstructions.Instr.ParamPush,value,dest));//Parameter's value Push 		 
					}else{
						dest=new VarLocation("PushParamDest",-1,-1);
						dest.setOffset(currentMethod.getOffset()-(4 * (i-5)));
						addInstr(new TAInstructions(TAInstructions.Instr.ParamPush,value,dest));//Parameter's value Push
					}
				}
				i++;
		}
		RefLocation result= new RefVarLocation(Integer.toString(line),expr.getLineNumber(),expr.getColumnNumber(),expr.getType(),currentMethod.newLocalLocation());//Location for save procedure's result
		addInstr(new TAInstructions(TAInstructions.Instr.CallExternWithReturn,new LabelExpr(expr.getMethod()),result));//Call sub-rutina with Return	
		if(expr.getArguments().size()>6){
			Expression bytesForPop=	new IntLiteral((expr.getArguments().size()-6)*4,-1,-1);
			addInstr(new TAInstructions(TAInstructions.Instr.ParamPop,bytesForPop));//pop parameters that stay en stack
 		}
		return result;
	}

	//Push de los parametros, llamada al metodo, pop de los parametros y retorno el valor de resultado del metodo(es un literal siempre)
	public Expression visit(MethodCallExpr expr){
		List<Location> formalParameters= expr.getMethod().getFormalParameters();
		int i=0; 
		Location dest;
		List<Expression> actualParametersEval= new LinkedList<Expression>();
		for(Expression e: expr.getArguments()){//Loop for push parameters
			Expression value= e.accept(this);
			actualParametersEval.add(value);
		}
		for(Expression value: actualParametersEval){//Loop for push parameters
			if (i<6){
				dest= new VarLocation("PushParamDest",-1,-1);
				dest.setOffset(i+1);
				addInstr(new TAInstructions(TAInstructions.Instr.ParamPush,value,dest));//Parameter's value Push 		 
			}else{
					dest=new VarLocation("PushParamDest",-1,-1);
					dest.setOffset(currentMethod.getOffset()-(4 * (i-5)));
					addInstr(new TAInstructions(TAInstructions.Instr.ParamPush,value,dest));//Parameter's value Push
				}
			i++;
		}		
		RefLocation result= new RefVarLocation(Integer.toString(line),expr.getLineNumber(),expr.getColumnNumber(),expr.getType(),currentMethod.newLocalLocation());//Location for save procedure's result
		addInstr(new TAInstructions(TAInstructions.Instr.CallWithReturn,new LabelExpr(expr.getMethod().getId()),result));//Call sub-rutina 	
		if(formalParameters.size()>6){
			Expression bytesForPop=	new IntLiteral((formalParameters.size()-6)*4,-1,-1);
			addInstr(new TAInstructions(TAInstructions.Instr.ParamPop,bytesForPop));//pop parameters that stay en stack
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
				RefLocation temp= new RefVarLocation(Integer.toString(line),expr.getLineNumber(),expr.getColumnNumber(),value.getType(),currentMethod.newLocalLocation());//place for current calculus
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

//agrego el label de float al final del programa.
	public Expression visit(FloatLiteral lit){
		System.out.println(lit.getValue());
		StringLiteral value=new StringLiteral(lit.getStringValue(),-1,-1);
		String label=".FloatLiteral_"+lit.getStringValue();//label for FloatLiteral
		value.setValue(label+":\n \t"+".float "+lit.getValue());
		listString.add(new TAInstructions(TAInstructions.Instr.PutStringLiteral,value));
		return lit;}
	public Expression visit(BooleanLiteral lit){return lit;}
	public Expression visit(StringLiteral lit){return lit;}


	public List<TAInstructions> getTAC(){
		return TAC;
	}

//Auxiliar methods


	private void saveLocalParameters(List<Expression> formalParameters){
		//Problemas con metodos con mas de 6 parametros.
		Expression p=null;
		int i=0;
		while(i<6 && i<formalParameters.size()){
			p= formalParameters.get(i);
			if (p instanceof RefLocation)
				if (((RefLocation) p).getLocation().getOffset()<=6 && ((RefLocation)p).getLocation().getOffset()>0)
					addInstr(new TAInstructions(TAInstructions.Instr.SaveParam, p,new IntLiteral(4,-1,-1))); 	 
			i++;
		}
	}

	private void loadLocalParameterFromStack(List<Expression> formalParameters){
		//Problemas con metodos con mas de 6 parametros.
		Expression p=null;
		int i=0;
		while(i<6 && i<formalParameters.size()){
			p= formalParameters.get(i);
			if (p instanceof RefLocation)
				if (((RefLocation) p).getLocation().getOffset()<=6 && ((RefLocation)p).getLocation().getOffset()>0)
					addInstr(new TAInstructions(TAInstructions.Instr.LoadParam, p,new IntLiteral(4,-1,-1))); 	 
			i++;
		}
	}

	private void addInstr(TAInstructions inst){
		TAC.add(line, inst);
		line++;
	}	

}
