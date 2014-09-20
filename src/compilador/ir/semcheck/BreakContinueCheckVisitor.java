package ir.semcheck;

import java.util.ArrayList;
import java.util.List;

import ir.ASTVisitor;
import ir.ast.*;
import ir.error.Error; // define class error
import java.util.*;

// break checker, concrete visitor 
public class BreakContinueCheckVisitor implements ASTVisitor<Boolean> {
private List<Error> errors;

	public BreakContinueCheckVisitor(){
		errors= new LinkedList<Error>();
	}

//visit program
	public Boolean visit(Program prog){
		for(MethodLocation m: prog.getMethods()){
			m.accept(this);
		}
		return false;
	}

// visit statements
	public Boolean visit(AssignStmt stmt){return false;}
	public Boolean visit(ReturnStmt stmt){return false;}
	public Boolean visit(IfStmt stmt){
		stmt.getIfBlock().accept(this);
		if(stmt.getElseBlock()!= null){
			stmt.getElseBlock().accept(this);
		}
		return false;
	}

	public Boolean visit(Block stmt){
		for (Statement s:stmt.getStatements()){
				if (s instanceof BreakStmt || s instanceof ContinueStmt ){
					addError(stmt,"Error in Break or Continue : ");
				}
				if(s instanceof IfStmt){s.accept(this);}
				if(s instanceof Block){s.accept(this);}
		}	
		return false;
	}

	public Boolean visit(BreakStmt stmt){return false;}
	public Boolean visit(ContinueStmt stmt){return false;}
	public Boolean visit(ForStmt stmt){return false;}	
	public Boolean visit(SecStmt stmt){return false;}
	public Boolean visit(WhileStmt stmt){return false;}

//Visit Location
	public Boolean visit(VarLocation var){return false;}
	public Boolean visit(MethodLocation method){return method.getBody().accept(this);}
	public Boolean visit(ArrayLocation array){return false;}
	
// visit expressions
	public Boolean visit(BinOpExpr expr){return false;}
	public Boolean visit(ExterninvkCallExpr expr){return false;}
	public Boolean visit(MethodCallExpr expr){return false;}	
	public Boolean visit(UnaryOpExpr expr){return false;}

// visit literals	
	public Boolean visit(IntLiteral lit){return false;}
	public Boolean visit(FloatLiteral lit){return false;}
	public Boolean visit(BooleanLiteral lit){return false;}
	public Boolean visit(StringLiteral lit){return false;}


	private void addError(AST a, String desc) {
		errors.add(new Error(a.getLineNumber(), a.getColumnNumber(), desc));
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

}

