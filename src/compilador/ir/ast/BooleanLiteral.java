package ir.ast;

import ir.ASTVisitor;

public class BooleanLiteral extends Literal {
	private boolean value;	

	/*
	 * Constructor for int literal that takes a string as an input
	 * @param: String integer
	 */
	public BooleanLiteral(boolean val){
		value=val;
	}

	@Override
	public Type getType() {
		return Type.BOOLEAN;
	}

	public boolean getValue() {
		return value;
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
/*
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
*/
}
