/**
*
* This class represents a StringLiteral node in the AST. It inherit from Literal<String>.
* 
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;
import ctds_pcr.ASTVisitor;

public class StringLiteral extends Literal<String> {
	
	/**
	 * Constructor of a StringLiteral object.
	 * 
	 * @param: my_value - the StringLiteral's value.
	 * @param my_line/my_col - to report an error.
	 */
	public StringLiteral(String my_value,int my_line, int my_column){
		this.value = my_value;
		lineNumber=my_line;
		colNumber= my_column;
	}

	/**
	* Method to set the StringLiteral's value. 
	*
	* @param my_value - the value to be seted.
	*/
	public void setValue(String my_value) {
		this.value = my_value;
	}
	
	/**
	* Method to get the StringLiteral's value. 
	*
	* @return the StringLiteral's value
	*/
	public String getValue() {
		return value;
	}

	public String toString(){
		return value;
	}

	/**
	* Method to get the StringLiteral's Type. 
	*
	* @return the StringLiteral's Type
	*/
	public Type getType(){
		return Type.STRING;
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

	/** Method which return a respresentation of StringLiteral's value in ASM code*/
	public String toAsmCode(){
		return "$"+value;	
	}

	/**
	* Method which evaluate a StringLiteral with this object to know if are the same
	*
	* @return a boolean value
	*/
	public boolean equals(StringLiteral cad){
		return this.getValue().equals(cad.getValue());
	}


}

