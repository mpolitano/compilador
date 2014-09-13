package ir.ast;
import java_cup.runtime.*;
import ir.ASTVisitor;

public class VarLocation extends Location {
	private int blockId;


	public VarLocation(String my_name,int my_line, int my_col,int my_block_id){
		this.id =  my_name;
		this.blockId = my_block_id;
		this.lineNumber=my_line;	
		this.colNumber= my_col;	
	}


	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}
	
	@Override
	public String toString() {
		return id;
	}
/*
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
*/
}
