/**
*
* Class MethodLocation represent a MethodLocation node in the ast. Inherit from Location.
*
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;
import ctds_pcr.ASTVisitor;
import java.util.*;


public class MethodLocation extends Location {
	/**
	* Class's atributes.
	*/
	private List<Location> formalParameters;//List of formal parameters
	private Block body;//Method's body
	private int localOffset;
	private boolean hayFloat;
	
	/**
	 * Constructor of a MethodLocation object.
	 * 
	 * @param: my_name - String.
	 */
	public MethodLocation(String my_name){
		this.id=my_name;
	}
	
	/**
	*
	* Methods set and get
	*
	*/

	/**
	* Method to get the MethodLocation's body. 
	*
	* @return MethodLocation's body (Block).
	*/
	public Block getBody(){
		return body;	
	}

	/**
	* Method to get the MethodLocation's FormalParameters. 
	*
	* @return MethodLocation's body (Block).
	*/
	public List<Location> getFormalParameters(){
		return formalParameters;
	}

	/**
	* Method to set the MethodLocation's Parameters. 
	*
	* @param p - the list of formalParameters to be seted (List<Location>).
	*/
	public void setParameters(List<Location> p){
		formalParameters=p;
	}

	/**
	* Method to set the MethodLocation's Body. 
	*
	* @param b - the body to be seted (Block).
	*/
	public void setBody(Block b){
		body=b;
	}

	public String toString(){
		return "Method: " + id;
	//	return "\n"+id + "(" + formalParameters.toString() + ")" + "\n" + body.toString() + "\n" ;
	}

	/**
	* Method to set the MethodLocation's offset. 
	*
	* @param my_offset - the int to be seted.
	*/
	public void setOffset(int my_offset){
		localOffset=my_offset;	
	}

	/**
	* Method to get the MethodLocation's offset. 
	*
	* @return the local offset.
	*/
	public int getOffset(){
		return localOffset;
	}

	/**
	* New implementation of the method accept.
	*
	* @see AST#accept(ASTVisitor<T> v)
	*/
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

//method for reports how many local location has this method	
	public int amoutLocalLocation(){
			return (-1 * (localOffset)/4); //Calc amount local location
	}

//Obtanin place in stack and return a offset value
	public int newLocalLocation(){
		localOffset=localOffset-4;
		return localOffset;
	}
	
	/**
	* Method to set the flag of float. 
	*
	* @param a - boolean flag.
	*/			
	public void setFloat(boolean a){
		this.hayFloat=a;
	}

	/**
	* Method to get the flag of float. 
	*
	* @return boolean flag.
	*/	
	public boolean getFloat(){
		return this.hayFloat;
	}			
}
