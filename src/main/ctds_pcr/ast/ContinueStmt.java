/**
*
* This class represents a ContinueStmt node in the AST. It inherit from Statement.
*
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;

import ctds_pcr.ASTVisitor;

public class ContinueStmt extends Statement {
	/**
	 * Constructor of a ContinueStmt object.
	 * 
	 * @param my_line/my_col - to report an error.
	 */
	public ContinueStmt(int my_line, int my_col) {
		lineNumber= my_line;
		colNumber=my_col;
 	}	

 	/**
	* New implementation of the method toString().
	*
	* @see String#toString()
	*/
	@Override
	public String toString() {
		return "continue";
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
