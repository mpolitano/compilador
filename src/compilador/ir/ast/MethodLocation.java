package ir.ast;
import ir.ASTVisitor;
import java.util.*;


public class MethodLocation extends Location {

	private List<Location> formalParameters;//List of formal parameters
	private Block body;//Method's body
	private int localOffset;

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
		return "Method: " + id;
	//	return "\n"+id + "(" + formalParameters.toString() + ")" + "\n" + body.toString() + "\n" ;
	}

	public void setOffset(int my_offset){
		localOffset=my_offset;	
	}

	public int getOffset(){
		return localOffset;
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

//method for reports how many local location has this method	
	public int amoutLocalLocation(){
			return (-1 * ((localOffset+8)/4)); //Calc amount local location
	}

//Obtanin place in stack and return a offset value
	public int newLocalLocation(){
		localOffset=localOffset-4;
		return localOffset;
	}
				
}
