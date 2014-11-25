package ctds_pcr.optimization;

import ctds_pcr.ast.*;
import ctds_pcr.ASTVisitor;
import java.util.LinkedList;
import java.util.List;
/* 
Precondition= Type Check has been made  (also is desirable that ConstPropagation Has been made(for have more information))
Poscondition= Nodes in AST that being after returnStmt,BreakStmt,ContinueStmt has been prune.
			  Nodes in AST that will be unreachables because exist a condition that can be decided
			  in compilation time  has been prune(while's block, if's blocks,for's block).

Visit Statement nodes in AST an prune sub-tree that can't reachables. 
If a visiting return null so current statement visited is prune.
If a visiting return a Statement, it should be reemplze the before statement.
Expression nodes aren't visited.
*/


public final class PruneUnreachableCode implements ASTVisitor<Statement>{
//visit program
	public  Statement visit(Program prog){
		for (MethodLocation m:prog.getMethods())//Prune unreachable code in methods
			m.accept(this);
		return null;
	}
	
/* 
Visit Statements and  prune unreachables statements.
*/

	
	/* Nothing for do in visit Assign statement*/
	public  Statement visit(AssignStmt stmt){
		return stmt;
	}

	/* Nothing for do in visit Return statement*/
	public  Statement visit(ReturnStmt stmt){
		return stmt;
	}
	
//Visit if's condition and prune unreachable code
	public  Statement visit(IfStmt stmt){
		Expression expr= stmt.getCondition();
		if (stmt.getCondition() instanceof BooleanLiteral)//if we know condition's value in compilation time will know wich block will be executed
			if (((BooleanLiteral)expr).getValue()){					
					return stmt.getIfBlock().accept(this);//prune if's blocks and return this
			}else{
					if (stmt.getElseBlock()!=null){//else block can be null				
						return stmt.getElseBlock().accept(this);//prune if's blocks and return this					
					}else
							return null;//prune if, because cointain unreachables statements
			}
		else{//we don't know condition's value in compilation time. Make prune unreachables statements in blocks and return.
			stmt.setIfBlock((Block)stmt.getIfBlock().accept(this));
			if (stmt.getElseBlock()!=null) stmt.setElseBlock((Block)stmt.getElseBlock().accept(this));
			return stmt;
		}
	}
	
	/*Prune unreachables statements in a block*/
	public  Statement visit(Block stmt){
		int i=0;
		List<Statement> statements=stmt.getStatements();
		Statement s;
		while(i<statements.size()){
			s= statements.get(i);
			if (s instanceof BreakStmt || s instanceof ContinueStmt || s instanceof ReturnStmt){//stament after break,continue and return are unreachable.
				stmt.setStatements(statements.subList(0,i+1)); //prune unreachable code: returns element from 0 until i inclusive
				break;
			}else{
				  Statement newStmt= s.accept(this);
				  if (newStmt!=null){//reemplaze last statement visited
				  	statements.set(i,newStmt);		
					i++;//increment index
				  }else//newStmt=null, so prune unreachable code
				  		statements.remove(i);
				  	//should't incrementate index
			}
			
		}		
		return stmt;
	}
	
	/*Nothing for doing in break statement*/
	public  Statement visit(BreakStmt stmt){return stmt;}
	
	/*Nothing for doing in continue statement*/
	public  Statement visit(ContinueStmt stmt){return stmt;}

	/*Prune unreachable statements in For Statement*/
	public  Statement visit(ForStmt stmt){
		Expression begin= stmt.getInitialValue();
		Expression end= stmt.getFinalValue();
		/*If begin and end expression are IntLiteral, we knows in compilation time 
		if for's body will be executed or not. If never will be executed we don't generate code for its.*/
		if ( begin instanceof IntLiteral && end instanceof IntLiteral ) 
			if (((IntLiteral)begin).getValue() >= ((IntLiteral)end).getValue())
				return null;
		
		stmt.setBlock((Block)stmt.getBlock().accept(this));	
		return stmt;	
	}

	/*Nothing for doing in sec Statement*/
	public  Statement visit(SecStmt stmt){return stmt;}

	/*Prune unreachable statements in While statement */
	public  Statement visit(WhileStmt stmt){
		if (stmt.getCondition() instanceof BooleanLiteral)
			if(!(((BooleanLiteral) stmt.getCondition()).getValue()))
				return null; //while block is unreachable	
			
		stmt.setBlock((Block)stmt.getBlock().accept(this));
		return stmt;
	}

	/* Nothing for doing in methodCall Statement*/
	public  Statement visit(MethodCallStmt stmt){
		return stmt;
	}

	/* Nothing for doing*/
	public  Statement visit(ExterninvkCallStmt stmt){
		return stmt;
	}

//Visit Location

	/*Nothing for doing in VarLocation*/
	public  Statement visit(VarLocation var){return null;}
	
	/* Visit method's body and prune unreachables statements*/
	public  Statement visit(MethodLocation method){
		method.setBody((Block)(method.getBody()).accept(this));
		return null;
	}
	
	/*Nothing for doing in ArrayLocation*/
	public  Statement visit(ArrayLocation array){return null;}
	
	/*Nothing for doing in RefVarLocation*/
	public  Statement visit(RefVarLocation var){return null;}
	
	/*Nothing for doing*/
	public  Statement visit(RefArrayLocation array){
		return null;
	}
	
// never visit expressions,

	public  Statement visit(BinOpExpr expr){
		return null;
	}

	
	public  Statement visit(ExterninvkCallExpr expr){
		return null;
	}
	
	public  Statement visit(MethodCallExpr expr){
		return null;		
	}

	public  Statement visit(UnaryOpExpr expr){
		return null;
	}

	
	public  Statement visit(LabelExpr expr){
		return null;
	}

//never visit literals	
	public  Statement visit(IntLiteral lit){
		return null;
	}
	public  Statement visit(FloatLiteral lit){
		return null;
	}
	public  Statement visit(BooleanLiteral lit){
		return null;
	}
	public  Statement visit(StringLiteral lit){
		return null;
	}
}