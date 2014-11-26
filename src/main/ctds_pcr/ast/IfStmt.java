/**
*
* This class represents an IfStmt node in the AST. It inherit from Statement.
*
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;

import ctds_pcr.ASTVisitor;

public class IfStmt extends Statement {
	/**
	* Class's atributes.
	*/
	private Expression condition;
	private Block ifBlock;
	private Block elseBlock;
	
	/**
	* Constructor of an IfStmt object.
	*
	* @param con - if's condition.
	* @param ifBl - if's block.
	* @param elseBl - else's block.
	* @param my_line/my_col - to report an error.
	*
	*/
	public IfStmt(Expression cond, Block ifBl, Block elseBl,int my_line, int my_column) {
		this.condition = cond;
		this.ifBlock = ifBl;
		this.elseBlock = elseBl;
		lineNumber= my_line;
		colNumber= my_column;
	}

	/**
	*
	* Methods set and get
	*
	*/

	/**
	* Method to get the IfStmt's condition. 
	*
	* @return the IfStmt's condition (Expression).
	*/
	public Expression getCondition() {
		return condition;
	}

	/**
	* Method to set the IfStmt's condition. 
	*
	* @param condition - the condition to be seted
	*/
	public void setCondition(Expression condition) {
		this.condition = condition;
	}

	/**
	* Method to get the IfStmt's block. 
	*
	* @return the IfStmt's block(Block).
	*/
	public Block getIfBlock() {
		return ifBlock;
	}

	/**
	* Method to set the IfStmt's block. 
	*
	* @param ifBlock - the block to be seted
	*/
	public void setIfBlock(Block ifBlock) {
		this.ifBlock = ifBlock;
	}

	/**
	* Method to get the elses's block. 
	*
	* @return the else's block(Block).
	*/
	public Block getElseBlock() {
		return elseBlock;
	}

	/**
	* Method to set the else's block. 
	*
	* @param elseBlock - the else's block to be seted
	*/
	public void setElseBlock(Block elseBlock) {
		this.elseBlock = elseBlock;
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
		String rtn = "if " + condition + '\n' + ifBlock.toString();
		
		if (elseBlock != null) {
			rtn += "else \n" + elseBlock;
		}
		
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
