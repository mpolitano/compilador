/**
*
* This class represents a RefLocation node in the AST. It inherit from Expression.
* 
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;

public abstract class RefLocation extends Expression {
	/**
	* Class's atributes.
	*/
	protected Location ref;
	
	/**
	* Method to set the RefLocation's Location. 
	*
	* @param my_ref - the Location to be seted.
	*/
	public void setLocation(Location my_ref) {
		this.ref = my_ref;
	}
	
	/**
	* Method to get the RefLocation's Location. 
	*
	* @return ref.
	*/
	public Location getLocation() {
		return ref;
	}

	/**
	* Method to get the RefLocation's Type. 
	*
	* @return ref's Type.
	*/
	public Type getType(){
		return ref.getType();	
	}

}
