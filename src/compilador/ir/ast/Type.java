package ir.ast;

public enum Type {
	INT,
	INTARRAY,
	FLOATARRAY,
	BOOLEANARRAY,
	VOID,
	FLOAT,
	BOOLEAN,
	UNDEFINED;
	
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
		}
		
		return null;
	}
	
	public boolean isArray() {
		if (this == Type.INTARRAY) {
			return true;
		}
		
		return false;
	}
}
//For build a Type's Object= Type.INT|INTARRAY|VOID|UNDEFINED 
