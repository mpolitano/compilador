package ir.optimization;

import ir.ast.*;
import ir.ASTVisitor;
import java.util.LinkedList;
import java.util.List;
/* Precondition= Type Check has been made 
Visit each node in AST and propagate constante in each expression. 
Visitor's prune sub-tree that contain Expression only. Then in Three Addres Code Generation
this resuts are analized, and will make code for reachables part in AST(break,continue and return statement aren't analized).  
*/


public final class ConstPropVisitor implements ASTVisitor<Expression>{
//visit program
	public Expression visit(Program prog){
		for (MethodLocation m:prog.getMethods())//constant propagation in each program's methods
			m.accept(this);
		return null;
	}
	
/* 
Visit Statements and  make constant propagation in each Expresion contained in Statement. 
The Visitor's return type is always NULL when it visit Statement. It make change Statement object inside,
making a constant propagation. Then, in Three Adress Code generation will be evaluate if's,while's and 
for's condition and then will generate code only for reachables sentences.
*/

	
	/* Visit assign's expression and make constant propagation */
	public Expression visit(AssignStmt stmt){
		Expression e=stmt.getExpression().accept(this); //constant propagation in assign's expression
		stmt.setExpression(e);
		return null;
	}

	/* Visit return's expression and make constant propagation */
	public Expression visit(ReturnStmt stmt){
		Expression e= stmt.getExpression().accept(this);
		stmt.setExpression(e);
		return null;
	}
	
//Visit if's condition and make constant propagation, if this is a BooleanLiteral then TACVisitor prune IfStmt	
	public Expression visit(IfStmt stmt){
		Expression e= stmt.getCondition().accept(this);
		stmt.setCondition(e);
		if (e instanceof BooleanLiteral)
			if(((BooleanLiteral)e).getValue())//for optimization: only visit block that will be reachables.
				stmt.getIfBlock().accept(this);
			else	
				if (stmt.getElseBlock()!=null) stmt.getElseBlock().accept(this);//else block's can be null. 
		else{ //haven't information in compilation time. Make constant propagation in if's blocks.
				stmt.getIfBlock().accept(this);
				if (stmt.getElseBlock()!=null) stmt.getElseBlock().accept(this);//else block's can be null. 
			}
		return null;
	}
	
	/*Visit each statement in a block and make constant propagation */
	public Expression visit(Block stmt){
		for(Statement s: stmt.getStatements())//visit each block's statetement
			s.accept(this);
		return null;
	}
	
	/*Nothing for doing in break statement*/
	public Expression visit(BreakStmt stmt){return null;}
	
	/*Nothing for doing in continue statement*/
	public Expression visit(ContinueStmt stmt){return null;}

	/*Visit initialValue and finalValue expression in ForStatement and make constant propagation. 
	Then, if is necessary, make a constant propagation in For's block */
	public Expression visit(ForStmt stmt){
		Expression begin= stmt.getInitialValue().accept(this);//constant propagation in initial expression
		Expression end=stmt.getFinalValue().accept(this);//constant propagation in final expression
		stmt.setInitialValue(begin);
		stmt.setFinalValue(end);
		/*if begin and final are constant, can knows if block will be executed or not.
		so, for optimization: make constant propagation if block will be reachable*/
		if (begin instanceof IntLiteral && end instanceof IntLiteral)
			if(((IntLiteral)begin).getValue()<= ((IntLiteral)end).getValue())
				stmt.getBlock().accept(this);
		else//make constant progation in for's body. Don't know in compilation time if body will be executed or not
			stmt.getBlock().accept(this);
		return null;
	}

	/*Nothing for doing in sec Statement*/
	public Expression visit(SecStmt stmt){return null;}

	/*Visit while condition and make constant progagation. Then, if is necessary make a constant propagation
	in while's body */
	public Expression visit(WhileStmt stmt){
		Expression e= stmt.getCondition().accept(this);//constant propagation in while condition
		stmt.setCondition(e);
		/*if while's condition is constant, can knows while's block will be executed or not. 
		so, for optimization: make constante propagation if block will be reachable*/
		if (e instanceof BooleanLiteral)
			if (((BooleanLiteral)e).getValue())
				stmt.getBlock().accept(this);
		else//make constant progation in while's body. Don't know in compilation time if body will be executed or not
			stmt.getBlock().accept(this);
		return null;
	}

	/* Visit each arguments in Method invocation, and make constant propagation. Then reemplaze the original list,
	for the new list that contain all expression that can be resolve in compilation time solve.*/
	public Expression visit(MethodCallStmt stmt){
		List<Expression> args= new LinkedList<Expression>();
		for (Expression e:stmt.getArguments()){//make constant propagation in actual parameters
			args.add(e.accept(this));
		}
		stmt.setArgs(args);
		return null;
	}

	/* Visit each arguments in Method invocation, and make constant propagation. Then reemplaze the original list,
	for the new list that contain all expression that can be resolve in compilation time solve.*/
	public Expression visit(ExterninvkCallStmt stmt){
		List<Expression> args= new LinkedList<Expression>();
		for (Expression e:stmt.getArguments()){ //make constant propagation in actual parameters
			args.add(e.accept(this));
		}
		stmt.setArgs(args);
		return null;
	}

//Visit Location

	/*Nothing for doing in VarLocation*/
	public Expression visit(VarLocation var){return var;}
	
	/* Visit method's body and make constant propagation*/
	public Expression visit(MethodLocation method){
		method.getBody().accept(this);
		return method;
	}
	
	/*Nothing for doing in ArrayLocation*/
	public Expression visit(ArrayLocation array){return array;}
	
	/*Nothing for doing in RefVarLocation*/
	public Expression visit(RefVarLocation var){return var;}
	
	/*Visit refArrayLocation expression and make constant propagation here*/
	public Expression visit(RefArrayLocation array){
		Expression e=array.getExpression().accept(this);
		array.setExpression(e);
		return array;
	}
	
// visit expressions
	public Expression visit(BinOpExpr expr){
		Expression lo= expr.getLeftOperand().accept(this);
		Expression ro= expr.getRightOperand().accept(this);
		if (lo instanceof BooleanLiteral && ro instanceof BooleanLiteral)
			switch(expr.getOperator()){
				case AND: return new BooleanLiteral(((BooleanLiteral)lo).getValue() && ((BooleanLiteral)ro).getValue() ,lo.getLineNumber(),lo.getColumnNumber());
				case OR:return new BooleanLiteral(((BooleanLiteral)lo).getValue() || ((BooleanLiteral)ro).getValue() ,lo.getLineNumber(),lo.getColumnNumber());	
			}
		else	
			if (lo instanceof IntLiteral && ro instanceof IntLiteral) //prune this node
				switch (expr.getOperator()){ 
					case PLUS: return makeLiteral( ((IntLiteral)lo).getValue() + ((IntLiteral)ro).getValue(), lo.getLineNumber(),ro.getLineNumber());
					case MINUS: return makeLiteral( ((IntLiteral)lo).getValue() - ((IntLiteral)ro).getValue(), lo.getLineNumber(),ro.getLineNumber());
					case MULTIPLY: return makeLiteral( ((IntLiteral)lo).getValue() * ((IntLiteral)ro).getValue(), lo.getLineNumber(),ro.getLineNumber());
					case DIVIDE: return makeLiteral( ((IntLiteral)lo).getValue() / ((IntLiteral)ro).getValue(), lo.getLineNumber(),ro.getLineNumber());
					case MOD: return makeLiteral( ((IntLiteral)lo).getValue() % ((IntLiteral)ro).getValue(), lo.getLineNumber(),ro.getLineNumber());
					case LE: return new BooleanLiteral(((IntLiteral)lo).getValue() < ((IntLiteral)ro).getValue() ,lo.getLineNumber(),lo.getColumnNumber());	
					case LEQ: return new BooleanLiteral(((IntLiteral)lo).getValue() <= ((IntLiteral)ro).getValue() ,lo.getLineNumber(),lo.getColumnNumber());	
					case GE: return new BooleanLiteral(((IntLiteral)lo).getValue() > ((IntLiteral)ro).getValue() ,lo.getLineNumber(),lo.getColumnNumber());	
					case GEQ: return new BooleanLiteral(((IntLiteral)lo).getValue() >= ((IntLiteral)ro).getValue() ,lo.getLineNumber(),lo.getColumnNumber());	
					case NEQ: return new BooleanLiteral(((IntLiteral)lo).getValue().equals(((IntLiteral)ro).getValue()) ,lo.getLineNumber(),lo.getColumnNumber());	
					case CEQ: return new BooleanLiteral(!(((IntLiteral)lo).getValue().equals(((IntLiteral)ro).getValue())) ,lo.getLineNumber(),lo.getColumnNumber());	
				}
			else
				if (lo instanceof IntLiteral && ro instanceof FloatLiteral)
					switch (expr.getOperator()){ 
						case PLUS: return makeLiteral( ((IntLiteral)lo).getValue() + ((FloatLiteral)ro).getValue(), lo.getLineNumber(),ro.getLineNumber());
						case MINUS: return makeLiteral( ((IntLiteral)lo).getValue() - ((FloatLiteral)ro).getValue(), lo.getLineNumber(),ro.getLineNumber());
						case MULTIPLY: return makeLiteral( ((IntLiteral)lo).getValue() * ((FloatLiteral)ro).getValue(), lo.getLineNumber(),ro.getLineNumber());
						case DIVIDE: return makeLiteral( ((IntLiteral)lo).getValue() / ((FloatLiteral)ro).getValue(), lo.getLineNumber(),ro.getLineNumber());
						case LE: return new BooleanLiteral(((IntLiteral)lo).getValue() < ((FloatLiteral)ro).getValue(),lo.getLineNumber(),lo.getColumnNumber());	
						case LEQ:return new BooleanLiteral(((IntLiteral)lo).getValue() <= ((FloatLiteral)ro).getValue(),lo.getLineNumber(),lo.getColumnNumber());	
						case GE:return new BooleanLiteral(((IntLiteral)lo).getValue() > ((FloatLiteral)ro).getValue(),lo.getLineNumber(),lo.getColumnNumber());	
						case GEQ:return new BooleanLiteral(((IntLiteral)lo).getValue() >= ((FloatLiteral)ro).getValue(),lo.getLineNumber(),lo.getColumnNumber());	
//						case NEQ:return new BooleanLiteral(((IntLiteral)lo).getValue() != ((FloatLiteral)ro).getValue(),lo.getLineNumber(),lo.getColumnNumber());	
//						case CEQ: return new BooleanLiteral(((IntLiteral)lo).getValue() == ((FloatLiteral)ro).getValue(),lo.getLineNumber(),lo.getColumnNumber());	
				}
				else
					if (lo instanceof FloatLiteral && ro instanceof IntLiteral)
						switch (expr.getOperator()){ 
							case PLUS: return makeLiteral( ((FloatLiteral)lo).getValue() + ((IntLiteral)ro).getValue(), lo.getLineNumber(),ro.getLineNumber());
							case MINUS: return makeLiteral( ((FloatLiteral)lo).getValue() - ((IntLiteral)ro).getValue(), lo.getLineNumber(),ro.getLineNumber());
							case MULTIPLY: return makeLiteral( ((FloatLiteral)lo).getValue() * ((IntLiteral)ro).getValue(), lo.getLineNumber(),ro.getLineNumber());
							case DIVIDE: return makeLiteral( ((FloatLiteral)lo).getValue() / ((IntLiteral)ro).getValue(), lo.getLineNumber(),ro.getLineNumber());
							case LE: return new BooleanLiteral(((FloatLiteral)lo).getValue() < ((IntLiteral)ro).getValue(),lo.getLineNumber(),lo.getColumnNumber());	
							case LEQ: return new BooleanLiteral(((FloatLiteral)lo).getValue() <= ((IntLiteral)ro).getValue(),lo.getLineNumber(),lo.getColumnNumber());	
							case GE:return new BooleanLiteral(((FloatLiteral)lo).getValue() > ((IntLiteral)ro).getValue(),lo.getLineNumber(),lo.getColumnNumber());	
							case GEQ:return new BooleanLiteral(((FloatLiteral)lo).getValue() >= ((IntLiteral)ro).getValue(),lo.getLineNumber(),lo.getColumnNumber());	
//							case NEQ:
//							case CEQ: 
					}
					else
						if (lo instanceof FloatLiteral && ro instanceof FloatLiteral)
						switch (expr.getOperator()){ 
							case PLUS: return makeLiteral( ((FloatLiteral)lo).getValue() + ((FloatLiteral)ro).getValue(), lo.getLineNumber(),ro.getLineNumber());
							case MINUS: return makeLiteral( ((FloatLiteral)lo).getValue() - ((FloatLiteral)ro).getValue(), lo.getLineNumber(),ro.getLineNumber());
							case MULTIPLY: return makeLiteral( ((FloatLiteral)lo).getValue() * ((FloatLiteral)ro).getValue(), lo.getLineNumber(),ro.getLineNumber());
							case DIVIDE: return makeLiteral( ((FloatLiteral)lo).getValue() / ((FloatLiteral)ro).getValue(), lo.getLineNumber(),ro.getLineNumber());
							case LE: return new BooleanLiteral(((FloatLiteral)lo).getValue() < ((FloatLiteral)ro).getValue(),lo.getLineNumber(),lo.getColumnNumber());	
							case LEQ:return new BooleanLiteral(((FloatLiteral)lo).getValue() <= ((FloatLiteral)ro).getValue(),lo.getLineNumber(),lo.getColumnNumber());	
							case GE:return new BooleanLiteral(((FloatLiteral)lo).getValue() > ((FloatLiteral)ro).getValue(),lo.getLineNumber(),lo.getColumnNumber());	
							case GEQ:return new BooleanLiteral(((FloatLiteral)lo).getValue() >= ((FloatLiteral)ro).getValue(),lo.getLineNumber(),lo.getColumnNumber());	
							//case NEQ:
							//case CEQ: 
					}






		//If left or right operand could be prune reemplaze in your ocurent in current node
		if (lo instanceof Literal)
			expr.setLeftOperand(lo);
		else//cases are disjoint
			if(ro instanceof Literal)
				expr.setRightOperand(ro);

		return expr; //by default return current node 
	}

	/* Visit each arguments in Method invocation, and make constant propagation. Then reemplaze the original list,
	for the new list that contain all expression that can be resolve in compilation time solve.*/		
	public Expression visit(ExterninvkCallExpr expr){
		List<Expression> args= new LinkedList<Expression>();
		for (Expression e:expr.getArguments()){//make constant propagation in actual parameters
			args.add(e.accept(this));
		}
		expr.setArgs(args);
		return expr;
	}
	
	/* Visit each arguments in Method invocation, and make constant propagation. Then reemplaze the original list,
	for the new list that contain all expression that can be resolve in compilation time solve.*/
	public Expression visit(MethodCallExpr expr){
		List<Expression> args= new LinkedList<Expression>();
		for (Expression e:expr.getArguments()){//make constant propagation in actual parameters
			args.add(e.accept(this));
		}
		expr.setArgs(args);	
		return expr;		
	}

	public Expression visit(UnaryOpExpr expr){
		Expression e= expr.getExpression().accept(this);
		expr.setExpression(e);
		if (e instanceof Literal){
			switch(expr.getOperator()){
				case NOT: return new BooleanLiteral(!((BooleanLiteral)e).getValue(),expr.getLineNumber(),expr.getColumnNumber());
				case MINUS: if(e instanceof IntLiteral) return makeLiteral(((IntLiteral)e).getValue(),expr.getLineNumber(),expr.getColumnNumber());
					   else /*intance of FloatLiteral*/ return makeLiteral(((FloatLiteral)e).getValue(),expr.getLineNumber(),expr.getColumnNumber());
			}		
		}		
		return expr;
	}

	/*Nothing for do in LabelExpr */
	public Expression visit(LabelExpr expr){return expr;}

// visit literals	
	public Expression visit(IntLiteral lit){
		return lit;
	}
	public Expression visit(FloatLiteral lit){
		return lit;
	}
	public Expression visit(BooleanLiteral lit){
		return lit;
	}
	public Expression visit(StringLiteral lit){
		return lit;
	}

//Auxiliary Methods

	private FloatLiteral makeLiteral(float f, int line, int col){
		return new FloatLiteral(f,line,col);
	}
	private IntLiteral makeLiteral(int f, int line, int col){
		return new IntLiteral(f,line,col);
	}

	private int value(Integer i){
		return i.intValue();
	}
	private float value(Float f){
		return f.floatValue();
	}

	private int value(Object n){return -1;}

	public static void main(String[] args){
		BinOpExpr e=new BinOpExpr(new IntLiteral(1,-1,-1), BinOpType.PLUS, new IntLiteral(1,-1,-1),-1,-1);
		Integer i= new Integer(2);
		Float f= new Float(2.0);
		System.out.println(e.accept(new ConstPropVisitor()).toString());
		System.out.println(i.equals(f));
	}
}