package ir.ast;

public abstract class Location extends Expression {
	protected String id;
	
	public void setId(String ide) {
		this.id = ide;
	}
	
	public String getId() {
		return id;
	}


}
