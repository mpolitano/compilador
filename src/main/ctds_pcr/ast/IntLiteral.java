/**
*
* This class represents a IntLiteral node in the AST. It inherit from Literal<Integer>.
* 
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;

import ctds_pcr.ASTVisitor;

public class IntLiteral extends Literal<Integer> {
	/**
	* Class's atributes.
	*/
	private String rawValue;
	
	/**
	 * Constructor of a IntLiteral object.
	 * 
	 * @param: val - the IntLiteral's value in String.
	 * @param my_line/my_col - to report an error.
	 */
	public IntLiteral(String val, int my_line,int my_column){
		rawValue = val; // Will convert to int value in semantic check
		value = Integer.parseInt(val);//Parses the string argument as a signed decimal integer.
		lineNumber= my_line;
		colNumber=my_column;
	}

	/**
	 * Constructor of a IntLiteral object.
	 * 
	 * @param: val - the IntLiteral's value in int.
	 * @param my_line/my_col - to report an error.
	 */
	public IntLiteral(int val, int my_line,int my_column){
		rawValue = Integer.toString(val); // Will convert to int value in semantic check
		value = new Integer(val);//Parses the string argument as a signed decimal integer.
		lineNumber= my_line;
		colNumber=my_column;
	}

	/**
	*
	* Methods set and get
	*
	*/

	/**
	* New implementation of the method getType().
	*
	* @return IntLiteral's Type.
	*
	* @see Literal<TValue>#getType()
	*/
	@Override
	public Type getType() {
		return Type.INT;
	}

	/**
	* Method to get the IntLiteral's value like a String. 
	*
	* @return the IntLiteral's value in String.
	*/
	public String getStringValue() {
		return rawValue;
	}

	/**
	* Method to set the IntLiteral's string value. 
	*
	* @param The String to be seted.
	*/
	public void setStringValue(String stringValue) {
		this.rawValue = stringValue;
	}

	/**
	* Method to get the IntLiteral's value like a Integer. 
	*
	* @return the IntLiteral's value in Integer.
	*/
	public Integer getValue() {
		return value;
	}

	/**
	* Method to set the IntLiteral's int value. 
	*
	* @param The int to be seted.
	*/
	public void setValue(int value) {
		this.value = value;
	}
	
	/**
	* Method to get the IntLiteral's rawValue. 
	*
	* @return the IntLiteral's rawValue (String).
	*/
	public String getRawValue() {
		return rawValue;
	}

	/**
	* Method to set the IntLiteral's String rawValue. 
	*
	* @param The String to be seted.
	*/
	public void setRawValue(String rawValue) {
		this.rawValue = rawValue;
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
		return rawValue;
	}

	/** Method which return a respresentation of IntLiteral's value in ASM code*/
	public String toAsmCode(){
		return "$"+value.toString();
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
