/**
*
* This class represents a WhileStmt node in the AST. It inherit from Statement.
* 
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;

import ctds_pcr.ASTVisitor;

public class WhileStmt extends Statement {
	/**
	* Class's atributes.
	*/
	private Expression condition;
	private Block whileBlock;
	
	/**
	 * Constructor of a WhileStmt object.
	 * 
	 * @param cond - the WhileStmt's condition.
	 * @param whileBl - the WhileStmt's block
	 * @param my_line/my_col - to report an error.
	 */
	public WhileStmt(Expression cond, Block whileBl, int my_line, int my_column) {
		this.condition = cond;
		this.whileBlock = whileBl;
		lineNumber= my_line;
		colNumber= my_column;
	}

	/**
	*
	* Methods set and get
	*
	*/

	/**
	* Method to get the WhileStmt's condition. 
	*
	* @return Expression.
	*/
	public Expression getCondition() {
		return condition;
	}

	/**
	* Method to set the WhileStmt's condition. 
	*
	* @param condition - the condition to be seted.
	*/
	public void setCondition(Expression condition) {
		this.condition = condition;
	}

	/**
	* Method to get the WhileStmt's block. 
	*
	* @return Block.
	*/
	public Block getBlock() {
		return whileBlock;
	}

	/**
	* Method to set the WhileStmt's block. 
	*
	* @param newBlock - the block to be seted.
	*/
	public void setBlock(Block newBlock) {
		this.whileBlock = newBlock;
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
		return "while " + condition + '\n' + whileBlock.toString();
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
