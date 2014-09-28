package ir.ast;

import ir.ASTVisitor;

public class BooleanLiteral extends Literal<Boolean> {
	/*
	 * Constructor for int literal that takes a string as an input
	 * @param: String integer
	 */
	public BooleanLiteral(boolean val,int my_line,int my_column){
		value=val;
		lineNumber=my_line;
		colNumber= my_column;
	}

	@Override
	public Type getType() {
		return Type.BOOLEAN;
	}

	public void setValue(boolean value) {
		this.value = value;
	}
	@Override
	public String toString() {
		if (value){
			return "True";		
		}else{
					return "False";
				 }
	}
	
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
