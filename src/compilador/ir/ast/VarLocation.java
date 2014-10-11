package ir.ast;
import java_cup.runtime.*;
import ir.ASTVisitor;

public class VarLocation extends Location {

	public VarLocation(String my_name,int my_line, int my_col){
		this.id =  my_name;
		this.lineNumber=my_line;	
		this.colNumber= my_col;	
	}
	
	@Override
	public String toString() {
		return id;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}
