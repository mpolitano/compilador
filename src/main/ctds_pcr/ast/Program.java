/**
*
* Class Program represent a Program node in the ast. Inherit from AST.
*
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;

import ctds_pcr.ASTVisitor;
import java.util.*;

public class Program extends AST{
	/**
	* Class's atributes.
	*/
	private	List<Location> fields;
	private List<MethodLocation> methods;
	private String id;

	/**
	 * Constructor of a Program object.
	 * 
	 * @param: my_id - Program's id.
	 * @param: my_fields - list of Program's fields
	 * @param: my_methods - list of Program's methods
	 */
	public Program(String my_id,List<Location> my_fields, List<MethodLocation> my_methods){
		fields= my_fields;
		methods= my_methods;
		id=my_id;
	}

	/**
	*
	* Methods set and get
	*
	*/

	/**
	* Method to get the Program's methods. 
	*
	* @return the list of methods.
	*/
	public List<MethodLocation> getMethods(){
		return methods;
	}

	/**
	* Method to get the Program's locations. 
	*
	* @return the list of locations.
	*/
	public List<Location> getFields(){
		return fields;
	}

	/**
	* Method to get the Program's id. 
	*
	* @return the Program's id.
	*/
	public String getId(){
		return id;
	}

	public String toString(){
		return "Fields: " + fields.toString() + "\n"+ "Methods: " + methods.toString();
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
}
