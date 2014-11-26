/**
*
* This class represents an ArrayLocation Node in the AST. It inherit from Location.
*
* @autor cornejo-politano-raverta.
*
*/

package ctds_pcr.ast;
import java_cup.runtime.*;
import ctds_pcr.ASTVisitor;


public class ArrayLocation extends Location {
	
	/**
	* Class's atributes.
	*/
	private int blockId;
	private IntLiteral size; //this must be > 0

	/**
	* Constructor of an ArrayLocation object.
	*
	* @param my_name - name of the ArrayLoaction.
	* @param my_line/my_col - to report an error.
	* @param my_block_id - id of the block where is the ArrayLocation.
	* @param my_size - size of the ArrayLocation.
	*
	*/
	public ArrayLocation(String my_name,int my_line, int my_col,int my_block_id, IntLiteral my_size){
		this.id =  my_name;
		this.blockId = my_block_id;
		this.lineNumber=my_line;	
		this.colNumber= my_col;	
		this.size=my_size;
	}

	/**
	*
	* Methods set and get
	*
	*/

	/**
	* Method to get the ArrayLocation's blockId. 
	*
	* @return the ArrayLocation's blockId (int).
	*/
	public int getBlockId() {
		return blockId;
	}

	/**
	* Method to set the ArrayLocation's blockId. 
	*
	* @param blockId - A int to be seted.
	*/
	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	/**
	* Method to get the ArrayLocation's size. 
	*
	* @return the ArrayLocation's size (IntLiteral).
	*/
	public IntLiteral getSize(){
		return size;
	}

	/**
	* Method to get the ArrayLocation's expression. 
	*
	* @return the ArrayLocation's expr (Expression).
	*/
	public Expression getExpression(){
		return expr;
	}

	/**
	* Method to set the ArrayLocation's expression. 
	*
	* @param my_expression - The Expression to be seted.
	*/
	public void setExpression(Expression my_expression){
		expr=my_expression;
	}
	
	/**
	* New implementation of the method toString.
	*
	* @see String#toString().
	*/
	@Override
	public String toString() {
		if (expr!=null){
			return type.toString()+" "+id+"["+ size +"] " + expr.toString() ;
		}else{return type.toString()+" "+id+"["+ size +"] ";}	
		
	}

	/**
	* Method which return the respresentation of the arrayLocation in ASM code
	*
	* @return a Strign which represents the ArrayLocarion in ASM code.
	*/
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

	/**
	* New implementation of the method accept.
	*
	* @see AST#accept(ASTVisitor<T> v).
	*/
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}
