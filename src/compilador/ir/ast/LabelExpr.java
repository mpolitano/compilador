package ir.ast;
import java_cup.runtime.*;
import ir.ASTVisitor;

public class LabelExpr extends Expression{
	private String label;
	private Location info;

	public LabelExpr(String my_label){
		label= my_label;
	}

	public LabelExpr(String my_label,Location my_info){
		label= my_label;
		info=my_info;
	}

	public String getLabel(){
		return label;
	}

	public Location getInfo(){
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
