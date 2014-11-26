/**
*
* Class MethodCallExpr represent a MethodCallExpr node in the ast. Inherit from CallExpr.
*
* @autor cornejo-politano-raverta.
*
*/

package ctds_pcr.ast;
import ctds_pcr.ASTVisitor;
import java.util.ArrayList;
import java.util.List;


public class MethodCallExpr extends CallExpr {
	/**
	* Class's atributes.
	*/
	private MethodLocation method;
	private List<Expression> args;
	
	/**
	 * Constructor of a MethodCallExpr object.
	 * 
	 * @param: my_method - MethodLocation.
	 * @param: a - list of Method expressions
	 * @param my_line/my_col - to report an error.
	 */
	public MethodCallExpr(MethodLocation my_method, List<Expression> a, int my_line, int my_col) {
		this.method=my_method;
		this.args = a;
		lineNumber=my_line;
		colNumber=my_col;
	}
	
	/**
	*
	* Methods set and get
	*
	*/
	
	/**
	* Method to get the MethodCallExpr's MethodCallExpr. 
	*
	* @return MethodLocation.
	*/
	public MethodLocation getMethod() {
		return this.method;
	}

	/**
	* Method to set the MethodCallExpr's name. 
	*
	* @param my_method - the MethodLocation to be seted.
	*/
	public void setName(MethodLocation my_method) {
		this.method = my_method;
	}

	/**
	* Method to get the MethodCallExpr's arguments. 
	*
	* @return the list of arguments.
	*/
	public List<Expression> getArguments() {
		return args;
	}

	/**
	* Method to set the MethodCallExpr's arguments. 
	*
	* @param args - the list of arguments to be seted.
	*/
	public void setArgs(List<Expression> args) {
		this.args = args;
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
