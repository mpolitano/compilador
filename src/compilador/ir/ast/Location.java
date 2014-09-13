package ir.ast;

public abstract class Location extends Expression {
	protected String id;
	protected int blockId;
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public int getBlockId() {
		return blockId;
	}
}