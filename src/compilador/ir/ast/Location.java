package ir.ast;

public abstract class Location extends Expression {
	protected String id;
	protected int offset=999999;	//999999 means offset not seted

	public void setId(String ide) {
		this.id = ide;
	}
	
	public String getId() {
		return id;
	}

	public void setOffset(int my_offset){
		offset=my_offset;	
	}

	public int getOffset(){
		return offset;
	}

}
