package ir.ast;
import ir.ASTVisitor;
import java.util.*;


public class MethodLocation extends Location {

	private List<Location> formalParameters;//List of formal parameters
	private Block body;//Method's body

	public MethodLocation(String my_name){
		this.id=my_name;
	}
	
	public Block getBody(){
		return body;	
	}

	public List<Location> getFormalParameters(){
		return formalParameters;
	}

	public void setParameters(List<Location> p){
		formalParameters=p;
	}

	public void setBody(Block b){
		body=b;
	}

	public String toString(){
		return "\n"+id + "(" + formalParameters.toString() + ")" + "\n" + body.toString() + "\n" ;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
}
