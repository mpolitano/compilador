/**
*
* Enumeration class which represents the diferents and possible Types.
*
* @autor cornejo-politano-raverta.
*
*/
package ctds_pcr.ast;

public enum Type {
	
	/**
	* The values of the class enumeration.
	*/
	INT,
	INTARRAY,
	FLOATARRAY,
	BOOLEANARRAY,
	VOID,
	FLOAT,
	BOOLEAN,
	UNDEFINED,
	STRING;
	
	/**
	* New implementation of the method toString.
	*
	* @see String#toString().
	*/
	@Override
	public String toString() {
		switch(this) {
			case INT:
				return "int";
			case VOID:
				return "void";
			case UNDEFINED:
				return "undefined";
			case INTARRAY:
				return "int[]";
			case FLOATARRAY:
 				return "float[]";
			case BOOLEANARRAY:
				return "boolean[]";
			case FLOAT:
				return "float";
			case BOOLEAN:
				return "boolean";
			case STRING:
				return "string";
		}
		
		return null;
	}
	
	/**
	* Method to know if it is an array type.
	*
	* @return boolean
	*/
	public boolean isArray() {
		if (this == Type.INTARRAY) {
			return true;
		}
		
		return false;
	}

	/**
	* Method to convert a simple type to an array type.
	*
	* @return Type (ARRAY)
	*/
	public Type toArray(){
		switch(this){
			case INT:
				return INTARRAY;
			case FLOAT:
				return FLOATARRAY;
			case BOOLEAN:
				return BOOLEANARRAY;
		}
		return UNDEFINED;
	}

	/**
	* Method to convert a array type to an simple type.
	*
	* @return Type 
	*/
	public Type fromArray(){
		switch (this){
			case INTARRAY: return Type.INT;
			case FLOATARRAY: return Type.FLOAT;
			case BOOLEANARRAY: return Type.BOOLEAN;	
			default: return Type.UNDEFINED;
		}
	}
}
//For build a Type's Object= Type.INT|INTARRAY|VOID|UNDEFINED 
