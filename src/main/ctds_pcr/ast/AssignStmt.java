/**
*
* This class represents an AssignStmt Node in the AST. It inherit from Statement.
*
* @autor cornejo-politano-raverta
* 
*/

package ctds_pcr.ast;

import ctds_pcr.ASTVisitor;

public class AssignStmt extends Statement {
	/**
	* Class's atributes.
	*/
	private RefLocation location;
	private Expression expr;
	private AssignOpType operator;


	/**
	* Constructor of an AssignStmt object.
	*
	* @param loc - where will be done the assignation.
	* @param op - type of the assignation.
	* @param e - the expression to be assigned in loc.
	* @param my_line/my_col - to report an error.
	*
	*/
	public AssignStmt(RefLocation loc, AssignOpType op, Expression e, int my_line, int my_col) {
		this.location = loc;
		this.expr = e;
		this.operator = op;
		lineNumber=my_line;
		colNumber= my_col;
	}
	

	/**
	*
	* Methods set and get
	*
	*/

	/**
	* Method to set the assignStmt's Location. 
	*
	* @param loc - The location to be seted.
	*/
	public void setLocation(RefLocation loc) {
		this.location = loc;
	}
	
	/** Method to get the location of the assignStmt. */
	public RefLocation getLocation() {
		return this.location;
	}
	
	/**
	* Method to set the assignStmt's expression. 
	*
	* @param e - The expression to be seted.
	*/
	public void setExpression(Expression e) {
		this.expr = e;
	}
	
	/** Method to get the assignStmt's expression. */
	public Expression getExpression() {
		return this.expr;
	}
	
	/** Method to get the assignStmt's operator. */
	public AssignOpType getOperator() {
		return operator;
	}

	/**
	* Method to set the assignStmt's operator. 
	*
	* @param operator - The operator to be seted.
	*/
	public void setOperator(AssignOpType operator) {
		this.operator = operator;
	}
	
	/**
	* New implementation of the method toString.
	*
	* @see String#toString().
	*/
	@Override
	public String toString() {
		return location.toString() + " " + operator.toString() + " " + expr.toString();
		
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
