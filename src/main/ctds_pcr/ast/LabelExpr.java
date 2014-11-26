/**
*
* This class represents a LabelExpr node in the AST. It inherit from Expression.
* 
* @autor cornejo-politano-raverta.
*
*/

package ctds_pcr.ast;
import java_cup.runtime.*;
import ctds_pcr.ASTVisitor;

public class LabelExpr extends Expression{
	/**
	* Class's atributes.
	*/
	private String label;
	private RefLocation info;

	/**
	 * Constructor of a LabelExpr object.
	 * 
	 * @param: my_label - the LabelExpr's value.
	 */
	public LabelExpr(String my_label){
		label= my_label;
	}

	/**
	 * Constructor of a LabelExpr object.
	 * 
	 * @param: my_label - the LabelExpr's value.
	 * @param: my:info - the LabelExpr's RefLocation.
	 */
	public LabelExpr(String my_label,RefLocation my_info){
		label= my_label;
		info=my_info;
	}

	/**
	* Method to get the LabelExpr's label. 
	*
	* @return the LabelExpr's label (String).
	*/
	public String getLabel(){
		return label;
	}

	/**
	* Method to get the LabelExpr's info. 
	*
	* @return the LabelExpr's info (RefLocation).
	*/
	public RefLocation getInfo(){
		return info;
	}


	public String toString(){
		return label;
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
