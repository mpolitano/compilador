package ir.ast;
import ir.ASTVisitor;

public class StringLiteral extends Literal {
	protected String value;
	
	public StringLiteral(String my_value){
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

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}


}

