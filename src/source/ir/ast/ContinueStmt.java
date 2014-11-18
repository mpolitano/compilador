package ir.ast;

import ir.ASTVisitor;

public class ContinueStmt extends Statement {

	public ContinueStmt(int my_line, int my_col) {
		lineNumber= my_line;
		colNumber=my_col;
 }	
	@Override
	public String toString() {
		return "continue";
	}


	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}
