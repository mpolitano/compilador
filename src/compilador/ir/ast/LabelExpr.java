package ir.ast;
import java_cup.runtime.*;
import ir.ASTVisitor;

public class LabelExpr extends Expression{
	String label;

	public LabelExpr(String my_label){
		label= my_label;
	}


	public String toString(){
		return label;
	}
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
} 
