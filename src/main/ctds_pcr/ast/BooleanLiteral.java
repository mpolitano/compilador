/**
*
* This class represents a BooleanLiteral node in the AST. It inherit from Literal<Boolean>.
* 
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;

import ctds_pcr.ASTVisitor;

public class BooleanLiteral extends Literal<Boolean> {
	
	/**
	 * Constructor of a BooleanLiteral object.
	 * 
	 * @param: val - the BooleanLiteral's value.
	 * @param my_line/my_col - to report an error.
	 */
	public BooleanLiteral(boolean val,int my_line,int my_column){
		value=val;
		lineNumber=my_line;
		colNumber= my_column;
	}

	/**
	*
	* Methods set and get
	*
	*/

	/**
	* New implementation of the method getType().
	*
	* @return BooleanLiteral's Type.
	*
	* @see Literal<TValue>#getType()
	*/
	@Override
	public Type getType() {
		return Type.BOOLEAN;
	}


	/**
	* Method to set the BooleanLiteral's value 
	*
	* @param value - A boolean to be seted.
	*/
	public void setValue(boolean value) {
		this.value = value;
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
		if (value){
			return "True";		
		}else{
					return "False";
				 }
	}

	/** Method which return a respresentation of BooleanLiteral's value in ASM code*/
	public String toAsmCode(){
		if (value)
			return "$1";
		else
			return "$0";
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
