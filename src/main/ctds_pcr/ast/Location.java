/**
*
* Class Location represent a Locarion node in the ast. Inherit from Expression.
*
* It is abstract because many of the class which inherit of him will implements more methods or use those who are already done.
*
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;

public abstract class Location extends Expression {
	/**
	* Class's atributes.
	*/
	protected String id;
	protected int offset=999999;	//999999 means offset not seted

	/**
	*
	* Methods set and get
	*
	*/
	
	/**
	* Method to set the Location's id. 
	*
	* @param id - the String to be seted.
	*/
	public void setId(String ide) {
		this.id = ide;
	}
	
	/**
	* Method to get the Location's id. 
	*
	* @return Location's id.
	*/
	public String getId() {
		return id;
	}

	/**
	* Method to set the Location's offset. 
	*
	* @param my_offset - the int to be seted.
	*/
	public void setOffset(int my_offset){
		offset=my_offset;	
	}

	/**
	* Method to get the Location's offset. 
	*
	* @return Location's offset.
	*/
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
