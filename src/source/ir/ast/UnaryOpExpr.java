package ir.ast;
import ir.ASTVisitor;

public class UnaryOpExpr extends Expression {
	private UnaryOpType operator;
	private Expression expression;
	
	/*
	 * @param: String enumValue should be either '-' or '!'. Expression expr is an expression
	 */
	public UnaryOpExpr(UnaryOpType op, Expression expr,int my_line,int my_column){
		this.operator = op;
		this.expression = expr;
		lineNumber=my_line;
		colNumber= my_column;
	}

	public UnaryOpType getOperator() {
		return operator;
	}

	public void setOperator(UnaryOpType operator) {
		this.operator = operator;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	@Override
	public String toString() {
		return operator.toString() + expression;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}


}
