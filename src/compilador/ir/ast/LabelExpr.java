package ir.ast;
import java_cup.runtime.*;
import ir.ASTVisitor;

public class LabelExpr extends Expression{
	private String label;
	private RefLocation info;

	public LabelExpr(String my_label){
		label= my_label;
	}

	public LabelExpr(String my_label,RefLocation my_info){
		label= my_label;
		info=my_info;
	}

	public String getLabel(){
		return label;
	}

	public RefLocation getInfo(){
		return info;
	}

	public String toString(){
		return label;
	}
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
} 
