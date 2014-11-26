/**
*
* This class represents a SecStmt node in the AST. It inherit from Statement.
* 
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;

import ctds_pcr.ASTVisitor;

public class SecStmt extends Statement {
	/**
	 * Constructor of a SecStmt object.
	 * 
	 * @param: my_line/my_column - to report an error
	 */
	public SecStmt(int my_line,int my_column){
		lineNumber=my_line;
		colNumber=my_column;
	};

	public String toString(){
		return ";";		
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
