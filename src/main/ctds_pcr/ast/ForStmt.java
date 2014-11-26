/**
*
* This class represents an ForStmt node in the AST. It inherit from Statement.
*
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;

import ctds_pcr.ASTVisitor;

public class ForStmt extends Statement {
	/**
	* Class's atributes.
	*/
	private RefLocation id;
	private Expression initialValue;
	private Expression finalValue;
	private Block block;
	
	/**
	* Constructor of an ForStmt object.
	*
	* @param i - RefLocation.
	* @param init - initialization expression.
	* @param fin - final expression.
	* @param bl - for's block.
	* @param my_line/my_col - to report an error.
	*
	*/
	public ForStmt(RefLocation i, Expression init, Expression fin, Block bl,int my_line,int my_column) {
		this.id = i;
		this.initialValue = init;
		this.finalValue = fin;
		this.block = bl;
		lineNumber=my_line;
		colNumber=my_column;
	}

	/**
	*
	* Methods set and get
	*
	*/

	/**
	* Method to get the ForStmt's id. 
	*
	* @return the ForStmt's id (RefLocation).
	*/
	public RefLocation getId() {
		return id;
	}

	/**
	* Method to set the ForStmt's id. 
	*
	* @param id - the RefLocation to be seted.
	*/
	public void setId(RefLocation id) {
		this.id = id;
	}

	/**
	* Method to get the ForStmt's initial value. 
	*
	* @return the ForStmt's initialValue (Expression).
	*/
	public Expression getInitialValue() {
		return initialValue;
	}

	/**
	* Method to set the ForStmt's initial value. 
	*
	* @param initialValue - a Expression to be seted.
	*/
	public void setInitialValue(Expression initialValue) {
		this.initialValue = initialValue;
	}

	/**
	* Method to get the ForStmt's final value. 
	*
	* @return the ForStmt's finalValue (Expression).
	*/
	public Expression getFinalValue() {
		return finalValue;
	}

	/**
	* Method to set the ForStmt's final value. 
	*
	* @param initialValue - a Expression to be seted.
	*/
	public void setFinalValue(Expression finalValue) {
		this.finalValue = finalValue;
	}

	/**
	* Method to get the ForStmt's block. 
	*
	* @return the ForStmt's block(Block).
	*/
	public Block getBlock() {
		return block;
	}

	/**
	* Method to get the ForStmt's block. 
	*
	* @param block - the Block to be seted.
	*/
	public void setBlock(Block block) {
		this.block = block;
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
		String rtn = "for " + id + "=" + initialValue + ", " + finalValue + '\n';
		rtn += block.toString();
		return rtn;
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
