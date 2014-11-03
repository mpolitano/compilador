package ir.ast;
import java_cup.runtime.*;
import ir.ASTVisitor;

public class RefArrayLocation extends RefLocation {
	private Expression expr; 

	public RefArrayLocation(ArrayLocation my_ref,int my_line, int my_col,Expression my_expr){
		this.ref =  my_ref;
		this.lineNumber=my_line;	
		this.colNumber= my_col;	
		this.type= my_ref.getType();
		this.expr=my_expr;
	}

	public Expression getExpression(){
		return expr;
	}

	public void setExpression(Expression my_expression){
		expr=my_expression;
	}
	
	@Override
	public String toString() {
			return type.toString()+" "+ref.getId()+"["+ ((ArrayLocation)ref).getSize().toString() +"] " + expr.toString() ;
	}


	public String toAsmCode(){
		return ref.toAsmCode();
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}
