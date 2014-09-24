package ir.ast;

public enum Type {
	INT,
	INTARRAY,
	FLOATARRAY,
	BOOLEANARRAY,
	VOID,
	FLOAT,
	BOOLEAN,
	UNDEFINED,
	STRING;
	
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
	
	public boolean isArray() {
		if (this == Type.INTARRAY) {
			return true;
		}
		
		return false;
	}

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
