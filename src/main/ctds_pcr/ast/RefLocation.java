package ctds_pcr.ast;

public abstract class RefLocation extends Expression {
	protected Location ref;
	
	public void setLocation(Location my_ref) {
		this.ref = my_ref;
	}
	
	public Location getLocation() {
		return ref;
	}

	public Type getType(){
		return ref.getType();	
	}

}
