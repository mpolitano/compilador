/**
*
* This class represents a ReturnStmt node in the AST. It inherit from Statement.
* 
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;

import ctds_pcr.ASTVisitor;

public class ReturnStmt extends Statement {
	/**
	* Class's atributes.
	*/
	private Expression expression; // the return expression
	
	/**
	 * Constructor of a ReturnStmt object.
	 * 
	 * @param: my_line/my_column - to report an error
	 */
	public ReturnStmt(int my_line,int my_column) {
		lineNumber= my_line;
		colNumber=my_column;
	}
	
	/**
	 * Constructor of a ReturnStmt object.
	 * */
	public ReturnStmt() {
		this.expression = null;
	}

	/**
	* Method to get the ReturnStmt's expression. 
	*
	* @return expression.
	*/
	public Expression getExpression() {
		return expression;
	}

	/**
	* Method to set the ReturnStmt's expression. 
	*
	* @param expression - the expression to be seted.
	*/
	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	/**
	* New implementation of the method toString().
	* 
	* @return a String.
	*
	* @see String#toString()
	*/
	@Override
	public String toString() {
		if (expression == null) {
			return "return";
		}
		else {
			return "return " + expression;
		}
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
