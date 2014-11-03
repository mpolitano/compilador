package ir.ast;
import java_cup.runtime.*;
import ir.ASTVisitor;

public class ArrayLocation extends Location {
	private int blockId;
	private IntLiteral size; //this must be > 0


	public ArrayLocation(String my_name,int my_line, int my_col,int my_block_id, IntLiteral my_size){
		this.id =  my_name;
		this.blockId = my_block_id;
		this.lineNumber=my_line;	
		this.colNumber= my_col;	
		this.size=my_size;
	}

	
	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	public IntLiteral getSize(){
		return size;
	}

	public Expression getExpression(){
		return expr;
	}

	public void setExpression(Expression my_expression){
		expr=my_expression;
	}
	
	@Override
	public String toString() {
		if (expr!=null){
			return type.toString()+" "+id+"["+ size +"] " + expr.toString() ;
		}else{return type.toString()+" "+id+"["+ size +"] ";}	
		
	}

	public String toAsmCode(){
		if (offset==0) return id+ "(%rip)";
		else{	 
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


	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}
