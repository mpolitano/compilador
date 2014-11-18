package ir.ast;

import ir.ASTVisitor;

public class ReturnStmt extends Statement {
	private Expression expression; // the return expression
	
	public ReturnStmt(int my_line,int my_column) {
		lineNumber= my_line;
		colNumber=my_column;
	}
	
	public ReturnStmt() {
		this.expression = null;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	@Override
	public String toString() {
		if (expression == null) {
			return "return";
		}
		else {
			return "return " + expression;
		}
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}
