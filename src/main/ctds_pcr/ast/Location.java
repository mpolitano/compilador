package ctds_pcr.ast;

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

	//pre: offset>0 && offset<=6
	//pos: offset>6
	/*Gen a location in stack, for save value of a register that are use for param passage */
	public Location genLocationInStack(int methodOffset){
		if(offset>0 && offset<=6){
			switch(offset){
				case 1:methodOffset=methodOffset-4 ;
				case 2:methodOffset=methodOffset-8 ;
				case 3:methodOffset=methodOffset-12 ;
				case 4:methodOffset=methodOffset-14 ;
				case 5:methodOffset=methodOffset-16 ;
				case 6:methodOffset=methodOffset-20 ;
			}
			Location stackLocation= new VarLocation(".stackLocation",-1,-1);
			stackLocation.setOffset(methodOffset);
			return stackLocation;
	}else return null; //"Error in generate ASM code, genLocationInStack shuld be used for 0<offset<=6"	
}
}
