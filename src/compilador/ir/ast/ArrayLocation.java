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
	
	@Override
	public String toString() {
		return type.toString()+" "+id+"["+ size +"]";
	}
/*
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
*/
}
