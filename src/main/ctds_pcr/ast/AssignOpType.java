/**
*
* Enumeration class which represents the diferents and possible type of assignation.
*
* @autor cornejo-politano-raverta.
*
*/


package ctds_pcr.ast;

public enum AssignOpType {
	
	/**
	* The values of the class enumeration.
	*/
	INCREMENT,
	DECREMENT,
	ASSIGN;
	
	/**
	* New implementation of the method toString.
	*
	* @see String#toString().
	*/
	@Override
	public String toString() {
		switch(this) {
			case INCREMENT:
				return "+=";
			case DECREMENT:
				return "-=";
			case ASSIGN:
				return "=";
		}
		
		return null;		
	}
}
