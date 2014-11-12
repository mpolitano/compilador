package ir.codeGeneration;

import java.util.ArrayList;
import java.util.List;

import ir.ASTVisitor;
import ir.ast.*;
import ir.error.Error; // define class error
import java.util.*;

// break checker, concrete visitor 
public class floatCheckVisitor implements ASTVisitor<Boolean> {
private List<Error> errors;

	public floatCheckVisitor(){
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
		boolean thereIsFloat=false;
		thereIsFloat=stmt.getIfBlock().accept(this);
		if(stmt.getElseBlock()!=null && !thereIsFloat){
			thereIsFloat=stmt.getElseBlock().accept(this)|| thereIsFloat;
		}
		return thereIsFloat;
	}
	public Boolean visit(Block stmt){
		boolean thereIsFloat=false;
		for (Statement s: stmt.getStatements()){
			if (s.accept(this)){
				thereIsFloat=true;
				break;
			}
		}
		return thereIsFloat;
	}
	public Boolean visit(BreakStmt stmt){return false;}
	public Boolean visit(ContinueStmt stmt){return false;}
	public Boolean visit(ForStmt stmt){return stmt.getBlock().accept(this);}	
	public Boolean visit(SecStmt stmt){return false;}
	public Boolean visit(WhileStmt stmt){return stmt.getBlock().accept(this);}	
	public Boolean visit(MethodCallStmt stmt){return false;}
	public Boolean visit(ExterninvkCallStmt stmt){
		if (stmt.getMethod().equals("\"print_float_1\""))
			return true;
		else
			return false;
	}

//Visit Location
	public Boolean visit(VarLocation var){return false;}
	public Boolean visit(MethodLocation method){		
		Block methodBody=method.getBody();
		if(methodBody.accept(this))
			method.setFloat(true);
		return false;
	}
	public Boolean visit(ArrayLocation array){return false;}
	public Boolean visit(RefVarLocation array){return false;}
	public Boolean visit(RefArrayLocation array){return false;}
	
// visit expressions
	public Boolean visit(BinOpExpr expr){return false;}
	public Boolean visit(ExterninvkCallExpr expr){
		if (expr.getMethod().equals("\"print_float\""))
			return true;
		else
			return false;
	}
	public Boolean visit(MethodCallExpr expr){return false;}	
	public Boolean visit(UnaryOpExpr expr){return false;}	
	public Boolean visit(LabelExpr expr){return false;}

// visit literals	
	public Boolean visit(IntLiteral lit){return false;}
	public Boolean visit(FloatLiteral lit){;return false;}
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

































		/*for (Statement s: methodBody.getStatements()){
			if(s instanceof ExterninvkCallStmt){ //Si hay un print_float algun externinvk
				System.out.println(((ExterninvkCallStmt)s).getMethod());
				if (((ExterninvkCallStmt)s).getMethod().equals("\"print_float\""))
					loc.setFloat(true);	
			}
			if ((s instanceof WhileStmt) || (s instanceof IfStmt) || (s instanceof ForStmt)) //Si hay un print_float en algun ciclo
				for (Statement sta: s.getBlock().getStatements()){
					if(sta instanceof ExterninvkCallStmt){
						if (((ExterninvkCallStmt)sta).getMethod().equals("\"print_float\""))
							loc.setFloat(true);	
					}
			}*/