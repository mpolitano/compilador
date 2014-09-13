package ir.ast;

import ir.ASTVisitor;
import java.util.*;

public class Program extends AST{

	private	List<Location> fields;
	private List<MethodLocation> methods;

	public	Program(List<Location> my_fields, List<MethodLocation> my_methods){
		fields= my_fields;
		methods= my_methods;
	}

	public List<MethodLocation> getMethods(){
		return methods;
	}

	public List<Location> getFields(){
		return fields;
	}

}
