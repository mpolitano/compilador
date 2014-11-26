/**
*
* Enumeration class which represents the diferents and possible type of unary operators.
*
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;

public enum UnaryOpType {
	
	/**
	* The values of the class enumeration.
	*/
	NOT, // !
	MINUS; // -
	
	/**
	* New implementation of the method toString.
	*
	* @see String#toString().
	*/
	@Override
	public String toString() {
		switch(this) {
			case NOT:
				return "!";
			case MINUS: 
				return "-";
		}
		
		return null;
	}
}
