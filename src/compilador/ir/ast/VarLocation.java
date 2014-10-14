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

	public String toAsmCode(){
		if (offset==0) return id+ "(%rip)";
		else{	 
					if(offset>0 && offset<=6){
						switch(offset){
							case 1: return "%rdi";
							case 2: return "%rsi";
							case 3: return "%rdx";
							case 4: return "%rcx";
							case 5: return "%r8";
							case 6: return "%r9";						
						}
					}else return offset + "(%rbp)";
							
				}	
		return null;		
	}

}
