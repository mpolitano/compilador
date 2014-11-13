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
						if (this.getType()==Type.FLOAT)
							switch(offset){
								case 1: return "%xmm0";
								case 2: return "%xmm1";
								case 3: return "%xmm2";
								case 4: return "%xmm3";
								case 5: return "%xmm4";
								case 6: return "%xmm5";				
						}
						else
							switch(offset){
								case 1: return "%edi";
								case 2: return "%esi";
								case 3: return "%edx";
								case 4: return "%ecx";
								case 5: return "%r8d";//referencing the lower r8 32 bits
								case 6: return "%r9d";////referencing the lower r9 32 bits						
							}
					}else return offset + "(%rbp)";
					return null;								
				}	
	
	}

}
