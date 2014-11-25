package ctds_pcr.ast;
import java_cup.runtime.*;
import ctds_pcr.ASTVisitor;

public class RefVarLocation extends RefLocation {

	public RefVarLocation(VarLocation my_ref,int my_line, int my_col){
		this.ref =  my_ref;
		this.lineNumber=my_line;	
		this.colNumber= my_col;	
		this.type= my_ref.getType();
	}

//Constructor for build a RefVarLocation and VarLocation
	public RefVarLocation(String id,int my_line,int my_col,Type t,int offset){
		ref= new VarLocation(id,my_line,my_col);
		ref.setType(t);
		this.type=t;
		this.lineNumber=my_line;	
		this.colNumber= my_col;			
		ref.setOffset(offset);
	}
	
	@Override
	public String toString() {
		return ref.getId();
	}

	public String toAsmCode(){
		return ref.toAsmCode();
		/*if (ref.getOffset()==0) return ref.getId() + "(%rip)";
		else return ref.getOffset() + "(%rbp)";*/
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}
