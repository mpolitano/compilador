/**
*
* This class represents a FloatLiteral node in the AST. It inherit from Literal<Float>.
* 
* @autor cornejo-politano-raverta.
*
*/

package ctds_pcr.ast;

import ctds_pcr.ASTVisitor;

public class FloatLiteral extends Literal<Float> {
	/**
	* Class's atributes.
	*/
	private String rawValue;
	private Float value;
	
	/**
	 * Constructor of a FloatLiteral object.
	 * 
	 * @param: val - the FloatLiteral's value.
	 * @param my_line/my_col - to report an error.
	 */
	public FloatLiteral(String val,int my_line, int my_column){
		rawValue = val; // Will convert to int value in semantic check
		value = Float.parseFloat(val);//Returns a new float with value represented by the String val
		lineNumber= my_line;
		colNumber=my_column;
	}

	/**
	 * Constructor of a FloatLiteral object.
	 * 
	 * @param: val - the FloatLiteral's value.
	 * @param my_line/my_col - to report an error.
	 */
	public FloatLiteral(float val,int my_line, int my_column){
		rawValue = Float.toString(val); // Will convert to int value in semantic check
		value = val;//Returns a new float with value represented by the String val
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
	* @return FloatLiteral's Type.
	*
	* @see Literal<TValue>#getType()
	*/
	@Override
	public Type getType() {
		return Type.FLOAT;
	}

	/**
	* Method to get the FloatLiteral's value like a String. 
	*
	* @return the FloatLiteral's value in String.
	*/
	public String getStringValue() {
		return rawValue;
	}

	/**
	* Method to set the FloatLiteral's string value. 
	*
	* @param stringValue- A String to be seted.
	*/
	public void setStringValue(String stringValue) {
		this.rawValue = stringValue;
	}

	/**
	* Method to get the FloatLiteral's value. 
	*
	* @return the FloatLiteral's value.
	*/
	public Float getValue() {
		return value;
	}

	/**
	* Method to set the FloatLiteral's string value. 
	*
	* @param value - the float to be seted.
	*/
	public void setValue(float value) {
		this.value = value;
		this.rawValue=Float.toString(value);
	}
	
	/**
	* Method to get the FloatLiteral's rawvalue. 
	*
	* @return the FloatLiteral's rawvalue.
	*/
	public String getRawValue() {
		return rawValue;
	}

	/**
	* Method to set the FloatLiteral's string rawValue. 
	*
	* @param rawValue - A String to be seted.
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
	
	/** Method which return a respresentation of BooleanLiteral's value in ASM code*/
	public String toAsmCode(){
		if(value<0) //for drive label with value<0, we add _n if value is <0
			return ".FloatLiteral_n"+ getStringValue().substring(1,getStringValue().length());
		else
			return ".FloatLiteral_"+ getStringValue();
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
