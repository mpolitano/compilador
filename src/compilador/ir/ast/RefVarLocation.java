package ir.ast;
import java_cup.runtime.*;
import ir.ASTVisitor;

public class RefVarLocation extends RefLocation {

	public RefVarLocation(VarLocation my_ref,int my_line, int my_col){
		this.ref =  my_ref;
		this.lineNumber=my_line;	
		this.colNumber= my_col;	
		this.type= my_ref.getType();
	}

//Constructor for build a RefVarLocation and VarLocation
	public RefVarLocation(String id,int my_line,int my_col,Type t){
		ref= new VarLocation(id,my_line,my_col);
		ref.setType(t);
		this.type=t;
		this.lineNumber=my_line;	
		this.colNumber= my_col;			
	}
	
	@Override
	public String toString() {
		return ref.getId();
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}
