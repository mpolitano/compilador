/**
*
* This class represents a VarLocation node in the AST. It inherit from Location
* 
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;
import java_cup.runtime.*;
import ctds_pcr.ASTVisitor;

public class VarLocation extends Location {

	/**
	* Constructor of an VarLocation object.
	*
	* @param my_name - name of the var location
	* @param my_line/my_column - to report an error. 
	*
	*/
	public VarLocation(String my_name,int my_line, int my_col){
		this.id =  my_name;
		this.lineNumber=my_line;	
		this.colNumber= my_col;	
	}
	
	/**
	* New implementation of the method toString.
	*
	* @see String#toString().
	*/
	@Override
	public String toString() {
		return id;
	}

	/**
	* New implementation of the method accept.
	*
	* @see AST#accept(ASTVisitor<T> v)
	*/
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

	/** Method which return a respresentation of VarLocation's value in ASM code*/
	public String toAsmCode(){
		if (offset==0) return id+ "(%rip)";
		else{	 					
					if (this.getType()==Type.FLOAT){
						if(offset>0 && offset<=8){
							switch(offset){
								case 1: return "%xmm0";
								case 2: return "%xmm1";
								case 3: return "%xmm2";
								case 4: return "%xmm3";
								case 5: return "%xmm4";
								case 6: return "%xmm5";
								case 7: return "%xmm6";
								case 8: return "%xmm7";				
						}
					}else return offset + "(%rbp)";
				}else//isn't type float
					if(offset>0 && offset<=6){	
						switch(offset){
								case 1: return "%edi";
								case 2: return "%esi";
								case 3: return "%edx";
								case 4: return "%ecx";
								case 5: return "%r8d";//referencing the lower r8 32 bits
								case 6: return "%r9d";////referencing the lower r9 32 bits						
							}
				}else return offset + "(%rbp)";								
			}	
	return null;
	}

}
