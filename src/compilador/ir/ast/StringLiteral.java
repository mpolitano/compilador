package ir.ast;
import ir.ASTVisitor;

public class StringLiteral extends Literal<String> {
	
	public StringLiteral(String my_value,int my_line, int my_column){
		this.value = my_value;
		lineNumber=my_line;
		colNumber= my_column;
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

	public String toAsmCode(){
		return "$"+value;	
	}


}

