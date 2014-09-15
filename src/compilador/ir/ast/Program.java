package ir.ast;

import ir.ASTVisitor;
import java.util.*;

public class Program extends AST{

	private	List<Location> fields;
	private List<MethodLocation> methods;
	private String id;

	public	Program(String my_id,List<Location> my_fields, List<MethodLocation> my_methods){
		fields= my_fields;
		methods= my_methods;
		id=my_id;
	}

	public List<MethodLocation> getMethods(){
		return methods;
	}

	public List<Location> getFields(){
		return fields;
	}

	public String getId(){
		return id;
	}

	public String toString(){
		return "Fields: " + fields.toString() + "\n"+ "Methods: " + methods.toString();
	}
}
