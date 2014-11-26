/**
*
* This class represents a RefVarLocation node in the AST. It inherit from RefLocation.
* 
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;
import java_cup.runtime.*;
import ctds_pcr.ASTVisitor;

public class RefVarLocation extends RefLocation {

	/**
	 * Constructor of a RefVarLocation object.
	 * 
	 * @param: my_ref - VarLocation.
	 * @param: my_line/my_col - to report errors
	 */
	public RefVarLocation(VarLocation my_ref,int my_line, int my_col){
		this.ref =  my_ref;
		this.lineNumber=my_line;	
		this.colNumber= my_col;	
		this.type= my_ref.getType();
	}

	/**
	 * Constructor of a RefVarLocation and VarLocation object.
	 * 
	 * @param id - VarLocation's id.
	 * @param t - VarLocation's type.
	 * @param t - RefVarLocation's Type
	 * @param offset - RefVarLocation's offset
	 * @param my_line/my_col - to report errors
	 */
		public RefVarLocation(String id,int my_line,int my_col,Type t,int offset){
		ref= new VarLocation(id,my_line,my_col);
		ref.setType(t);
		this.type=t;
		this.lineNumber=my_line;	
		this.colNumber= my_col;			
		ref.setOffset(offset);
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
		return ref.getId();
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
