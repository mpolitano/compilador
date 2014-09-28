package ir.ast;

public abstract class Literal<TValue> extends Expression {
	protected TValue value;
	/*
	 * @return: returns Type of Literal instance
	 */
	public abstract Type getType();

	public TValue getValue(){
		return value;
	}
	
}
