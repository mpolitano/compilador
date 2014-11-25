package ctds_pcr.ast;

import ctds_pcr.ASTVisitor;

public class BreakStmt extends Statement {
	public BreakStmt(int my_line, int my_col) {
		lineNumber= my_line;
		colNumber=my_col;
 }
	
	@Override
	public String toString() {
		return "break";
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
