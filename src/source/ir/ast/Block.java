package ir.ast;


import java.util.List;
import ir.ASTVisitor;
import java.util.LinkedList;

public class Block extends Statement {
	private List<Statement> statements;
	private List<Location> field;
	private int blockId;
	private IntLiteral offset;
	
	public Block(int bId) {
		statements = new LinkedList<Statement>();
		blockId = bId;
		offset= new IntLiteral(Integer.toString(0),-1,-1);
	}
	
	public Block(int bId, List<Statement> s) {
		blockId = bId;
		field= new LinkedList<Location>();
		offset= new IntLiteral(Integer.toString(0),-1,-1);
		statements = s;
	}
	
	public Block(int bId,  List<Location> f,List<Statement> s) {
		blockId = bId;
		statements = s;
		field= f;
		offset= new IntLiteral(Integer.toString(0),-1,-1);
	}
	public void addStatement(Statement s) {
		this.statements.add(s);
	}
		
	public List<Statement> getStatements() {
		return this.statements;
	} 
		
	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	@Override
	public String toString() {
		String rtn = "";
		
	    for (Statement s: statements) {
			rtn += s.toString() + '\n';
		}
		
		if (rtn.length() > 0) return rtn.substring(0, rtn.length() - 1); // remove last new line char
		
		return rtn; 
	}

	public IntLiteral getOffset(){
		return offset;
	}

	public void setOffset(int offset){
		this.offset.setValue(offset);
	}


	public List<Location> getFields(){
		return field;	
	}
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

	public void addField(Location l){
		field.add(l);
	}

}