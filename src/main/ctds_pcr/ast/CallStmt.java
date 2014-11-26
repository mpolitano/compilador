/**
*
* Class CallStmt represent a CallStmt node in the ast. Inherit from Statement.
*
* It is abstract because many of the class which inherit of him implements some methods or use those who already are.
*
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;
import ctds_pcr.ASTVisitor;

import java.util.List;

public abstract class CallStmt extends Statement {
	/**
	* Class's atributes.
	*/
	protected List<Expression> args;
	
	/**
	*
	* Methods set and get
	*
	*/

	/** 
	* Method to get the list of CallStmt's arguments
	*
	* @return the list of arguments (List<Expression>) 
	*/
	public List<Expression> getArguments() {
		return args;
	}

	/**
	* Method to set the CallStmt's value 
	*
	* @param args - A list<Expression> to be seted.
	*/
	public void setArgs(List<Expression> args) {
		this.args = args;
	}

}
