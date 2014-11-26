/**
*
* This class represents a Block node in the AST. It inherit from Statement.
*
* @autor cornejo-politano-raverta.
* 
*/
package ctds_pcr.ast;


import java.util.List;
import ctds_pcr.ASTVisitor;
import java.util.LinkedList;

public class Block extends Statement {
	
	/**
	* Class's atributes.
	*/
	private List<Statement> statements;
	private List<Location> field;
	private int blockId;
	private IntLiteral offset;
	private List<Location> auxiliaryLocation;
	

	/**
	* Constructor of a Block object.
	*
	* @param bId - block's id.
	*
	*/
	public Block(int bId) {
		statements = new LinkedList<Statement>();
		blockId = bId;
		offset= new IntLiteral(Integer.toString(0),-1,-1);
		auxiliaryLocation= new LinkedList<Location>();
	}
	
	/**
	* Constructor of a Block object.
	*
	* @param bId - block's id.
	* @param s - list which contains the differents statements of the block.
	*
	*/
	public Block(int bId, List<Statement> s) {
		blockId = bId;
		field= new LinkedList<Location>();
		offset= new IntLiteral(Integer.toString(0),-1,-1);
		statements = s;
		auxiliaryLocation= new LinkedList<Location>();
	}

	/**
	* Constructor of a Block object.
	*
	* @param bId - block's id.
	* @param f - list whit block's locations.
	* @param s - list which contains the differents statements of the block.
	*
	*/
	public Block(int bId,  List<Location> f,List<Statement> s) {
		blockId = bId;
		statements = s;
		field= f;
		offset= new IntLiteral(Integer.toString(0),-1,-1);
		auxiliaryLocation= new LinkedList<Location>();	
	}

	/**
	*
	* Methods set and get.
	*
	*/	

	/** Method to get the block's statements list. */
	public List<Statement> getStatements() {
		return this.statements;
	} 

	/**
	* Method to set the block's statements.
	*
	* @param statements - A list to be seted in the statements atribute of the object.
	*
	*/
	public void setStatements(List<Statement> statements){
		this.statements=statements;
	}
	
	/** Method to get the block's id. */	
	public int getBlockId() {
		return blockId;
	}

	/**
	* Method to set the block's id
	*
	* @param blockId - a int to be seted in the blockId atribute of the object.
	*
	*/
	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	/** Method to get the block's Offset. */
	public IntLiteral getOffset(){
		return offset;
	}

	/**
	* Method to set the block's offset.
	*
	* @param blockId - a int to be seted in the offset atribute of the object.
	*
	*/
	public void setOffset(int offset){
		this.offset.setValue(offset);
	}

	/** Method to get the block's fields. */
	public List<Location> getFields(){
		return field;	
	}

	/** Method to get all the block's locations(field and auxiliaryLocation). */
	public List<Location> getAllLocation(){
		List<Location> auxList= new LinkedList<Location>(field);
		auxList.addAll(auxiliaryLocation);
		return auxList;	
	}

	/**
	* Method to add a field in the auxiliaryLocation list.
	*
	* @param l - Location to be added in the auxiliariLocation list.
	*/
	public void addField(Location l){
		auxiliaryLocation.add(l);
	}

	/**
	* This method add a statement in the statements list (List<Statement> statements).
	*
	* @param s - Statement to be added in statements list.
	*/
	public void addStatement(Statement s) {
		this.statements.add(s);
	}

	/**
	* New implementation of the method toString().
	*
	* @see String#toString()
	*/
	@Override
	public String toString() {
		String rtn = "";
		
	    for (Statement s: statements) {
			rtn += s.toString() + '\n';
		}
		
		if (rtn.length() > 0) return rtn.substring(0, rtn.length() - 1); // remove last new line char
		
		return rtn; 
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

	


}
