/**
*
* Class AST is used for management the abstrac syntax tree.
*
* It is abstract because many of the class which inherit of him will implements more methods or use those who are already done.
*
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;

import ctds_pcr.ASTVisitor;


public abstract class AST {

	/**
	* Class's atributes.
	*/
	protected int lineNumber;  //lineNumber/colNumber - needed to report an error and mark in what column and line 
	protected int colNumber;
	
	/**
	*
	* Methods set and get
	*
	*/

	/**
	* Method to get the line number. 
	*
	* @return the line number (int).
	*/
	public int getLineNumber() {
		return lineNumber;
	}
	
	/**
	* Method to set the line number
	*
	* @param ln - A int to be seted
	*/
	public void setLineNumber(int ln) {
		lineNumber = ln;
	}
	
	/**
	* Method to get the column number. 
	*
	* @return the column number (int).
	*/
	public int getColumnNumber() {
		return colNumber;
	}
	
	/**
	* Method to set the column number
	*
	* @param cn - A int to be seted
	*/
	public void setColumnNumber(int cn) {
		colNumber = cn;
	}
	
	/**
	* Method needed by the visitor patron, it is abstrac because the class which inherit implements it
	*
	* @see ASTVisitor
	*/
	public abstract<T> T accept(ASTVisitor<T> v);
}
