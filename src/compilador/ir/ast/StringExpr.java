package ir.ast;

public class StringExpr extends Literal {
	protected String value;
	
	public StringExpr(String my_value){
		this.value = my_value;
	}

	public void setValue(String my_value) {
		this.value = my_value;
	}
	
	public String getValue() {
		return value;
	}

	public String toString(){
		return value;
	}

	public Type getType(){
		return Type.STRING;
	}

}
