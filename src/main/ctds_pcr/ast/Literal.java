/**
*
* Class Literal<TValue> represent a Literal node in the ast. Inherit from Expression.
*
* It is abstract because many of the class which inherit of him will implements more methods or use those who are already done.
*
* And need to be instanced with TValue.
*
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;

public abstract class Literal<TValue> extends Expression {
	/**
	* Class's atributes.
	*/
	protected TValue value;
	
	/**
	* Method to get the Literal's Type.
	* It will be implemented for son classes.
	* @return: The Type of Literal instance.
	*/
	public abstract Type getType();

	/**
	* Method to get the Literal's value.
	* 
	* @return: value of Literal instance.
	*/
	public TValue getValue(){
		return value;
	}
	
}
