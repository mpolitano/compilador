package ctds_pcr.ast;

import ctds_pcr.ASTVisitor;

public class IntLiteral extends Literal<Integer> {
	private String rawValue;
	
	/*
	 * Constructor for int literal that takes a string as an input
	 * @param: String integer
	 */
	public IntLiteral(String val, int my_line,int my_column){
		rawValue = val; // Will convert to int value in semantic check
		value = Integer.parseInt(val);//Parses the string argument as a signed decimal integer.
		lineNumber= my_line;
		colNumber=my_column;
	}


	public IntLiteral(int val, int my_line,int my_column){
		rawValue = Integer.toString(val); // Will convert to int value in semantic check
		value = new Integer(val);//Parses the string argument as a signed decimal integer.
		lineNumber= my_line;
		colNumber=my_column;
	}

	@Override
	public Type getType() {
		return Type.INT;
	}

	public String getStringValue() {
		return rawValue;
	}

	public void setStringValue(String stringValue) {
		this.rawValue = stringValue;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(int value) {
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

	public String toAsmCode(){
		return "$"+value.toString();
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}