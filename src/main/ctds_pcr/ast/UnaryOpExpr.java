/**
*
* This class represents a UnaryOpExpr node in the AST. It inherit from Expression
* 
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;
import ctds_pcr.ASTVisitor;

public class UnaryOpExpr extends Expression {
	
	/**
	* Class's atributes.
	*/
	private UnaryOpType operator;
	private Expression expression;
	
	/**
	* Constructor of an UnaryOpExpr object.
	*
	* @param op - type of the unary operation.
	* @param expr - the expression of the unary operation.
	* @param my_line/my_column - to report an error. 
	*
	*/
	public UnaryOpExpr(UnaryOpType op, Expression expr,int my_line,int my_column){
		this.operator = op;
		this.expression = expr;
		lineNumber=my_line;
		colNumber= my_column;
	}

	/**
	*
	* Methods set and get.
	*
	*/

	/**
	* Method to get the operator of the unary operation. 
	*
	* @return the operator
	*/
	public UnaryOpType getOperator() {
		return operator;
	}

	/**
	* Method to set the operator of the unary operation. 
	*
	* @param operator - the operator to be seted
	*/
	public void setOperator(UnaryOpType operator) {
		this.operator = operator;
	}

	/**
	* Method to get the expression of the unary operation. 
	*
	* @return the expression
	*/
	public Expression getExpression() {
		return expression;
	}

	/**
	* Method to set the expression of the unary operation. 
	*
	* @param expression - the expression to be seted
	*/
	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	/**
	* New implementation of the method toString.
	*
	* @see String#toString().
	*/
	@Override
	public String toString() {
		return operator.toString() + expression;
	}

	/**
	* New implementation of the method accept.
	*
	* @see AST#accept(ASTVisitor<T> v)
	*/
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}


}
