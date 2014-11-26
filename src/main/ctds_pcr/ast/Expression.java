/**
*
* Class Expression represent a Expression node in the ast. Inherit from AST.
*
* It is abstract because many of the class which inherit of him will implements more methods or use those who are already done.
*
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;

public abstract class Expression extends AST {
	
	/**
	* Class's atributes.
	*/
	protected Expression expr;
	protected Type type;
	
	/**
	*
	* Methods set and get
	*
	*/

	/**
	* Method to get the Expression's type. 
	*
	* @return the Expression's type (Type).
	*/
	public Type getType() {
		return this.type;
	}
	
	/**
	* Method to set the Expression's type. 
	*
	* @param t - the Expression's type (Type).
	*/
	public void setType(Type t) {
		this.type = t;
	}

	/**
	* Method which return the respresentation of the Expression in ASM code
	*
	* @return a Strign which represents the Expression in ASM code.
	*/
	public String toAsmCode(){
		return this.getClass()+ " Undefined";
	}
}
