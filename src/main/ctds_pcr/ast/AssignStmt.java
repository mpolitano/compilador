package ctds_pcr.ast;

import ctds_pcr.ASTVisitor;

public class AssignStmt extends Statement {
	private RefLocation location;
	private Expression expr;
	private AssignOpType operator;

	public AssignStmt(RefLocation loc, AssignOpType op, Expression e, int my_line, int my_col) {
		this.location = loc;
		this.expr = e;
		this.operator = op;
		lineNumber=my_line;
		colNumber= my_col;
	}
	
	public void setLocation(RefLocation loc) {
		this.location = loc;
	}
	
	public RefLocation getLocation() {
		return this.location;
	}
	
	public void setExpression(Expression e) {
		this.expr = e;
	}
	
	public Expression getExpression() {
		return this.expr;
	}
	
	public AssignOpType getOperator() {
		return operator;
	}

	public void setOperator(AssignOpType operator) {
		this.operator = operator;
	}
	
	@Override
	public String toString() {
		return location.toString() + " " + operator.toString() + " " + expr.toString();
		
	}


	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}
