/**
*
* This class represents a RefArrayLocation node in the AST. It inherit from RefLocation.
* 
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;
import java_cup.runtime.*;
import ctds_pcr.ASTVisitor;

public class RefArrayLocation extends RefLocation {
	/**
	* Class's atributes.
	*/
	private Expression expr; 

	/**
	 * Constructor of a RefArrayLocation object.
	 * 
	 * @param my_ref - ArrayLocation.
	 * @param my_line/my_col - to report an error.
	 * @param my_expr - the RefArrayLocation's expression 
	 */
	public RefArrayLocation(ArrayLocation my_ref,int my_line, int my_col,Expression my_expr){
		this.ref =  my_ref;
		this.lineNumber=my_line;	
		this.colNumber= my_col;	
		this.type= my_ref.getType();
		this.expr=my_expr;
	}

	/**
	* Method to get the RefArrayLocation's Expression. 
	*
	* @return expr.
	*/
	public Expression getExpression(){
		return expr;
	}

	/**
	* Method to set the RefArrayLocation's Expression. 
	*
	* @param my_expression - the expression to be seted.
	*/
	public void setExpression(Expression my_expression){
		expr=my_expression;
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
			return type.toString()+" "+ref.getId()+"["+ ((ArrayLocation)ref).getSize().toString() +"] " + expr.toString() ;
	}

	/** Method which return a respresentation of IntLiteral's value in ASM code*/
	public String toAsmCode(){
		return ref.toAsmCode();
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
