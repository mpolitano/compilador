package ir.ast;

import ir.ASTVisitor;

public class FloatLiteral extends Literal<Float> {
	private String rawValue;
	private Float value;
	
	/*
	 * Constructor for int literal that takes a string as an input
	 * @param: String integer
	 */
	public FloatLiteral(String val,int my_line, int my_column){
		rawValue = val; // Will convert to int value in semantic check
		value = Float.parseFloat(val);//Returns a new float with value represented by the String val
		lineNumber= my_line;
		colNumber=my_column;
	}

	public FloatLiteral(float val,int my_line, int my_column){
		rawValue = Float.toString(val); // Will convert to int value in semantic check
		value = val;//Returns a new float with value represented by the String val
		lineNumber= my_line;
		colNumber=my_column;
	}
	@Override
	public Type getType() {
		return Type.FLOAT;
	}

	public String getStringValue() {
		return rawValue;
	}

	public void setStringValue(String stringValue) {
		this.rawValue = stringValue;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
	
	public String getRawValue() {
		return rawValue;
	}

	public void setRawValue(String rawValue) {
		this.rawValue = rawValue;
	}

	@Override
	public String toString() {
		return rawValue;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}
