/**
*
* Class MethodCallStmt represent a MethodCallStmt node in the ast. Inherit from CallStmt.
*
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;
import ctds_pcr.ASTVisitor;
import java.util.ArrayList;
import java.util.List;


public class MethodCallStmt extends CallStmt {
	/**
	* Class's atributes.
	*/
	protected MethodLocation method;
	
	/**
	 * Constructor of a MethodCallStmt object.
	 * 
	 * @param: my_method - MethodLocation.
	 * @param: my_listExpr - list of Method expressions
	 */
	public MethodCallStmt(MethodLocation my_method, List<Expression> my_listExpr) {
		this.method=my_method;
		this.args = my_listExpr;
	}
	
	/**
	*
	* Methods set and get
	*
	*/
	
	/**
	* Method to get the MethodCallStmt's MethodCallStmt. 
	*
	* @return MethodLocation.
	*/
	public MethodLocation getMethod() {
		return this.method;
	}

	/**
	* Method to set the MethodCallStmt's name. 
	*
	* @param my_method - the MethodLocation to be seted.
	*/
	public void setName(MethodLocation my_method) {
		this.method = my_method;
	}

	/**
	* New implementation of the method toString().
	* 
	* @return a String.
	*
	* @see String#toString()
	*/
	@Override
	public String toString() {
		String rtn = method.getId() + "(";
		for (Expression arg: args) {
			rtn += arg + ", ";
		}
		
		if (!args.isEmpty()){
			rtn = rtn.substring(0, rtn.length()-2);
		}
		
		rtn += ")";
		
		return rtn+ method.getBody().toString();
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