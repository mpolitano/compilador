/**
*
* This class represents a ExterninvkCallExpr node in the AST. It inherit from CallExpr.
*
* @autor cornejo-politano-raverta.
*
*/

package ctds_pcr.ast;
import ctds_pcr.ASTVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;


public class ExterninvkCallExpr extends CallExpr {
	/**
	* Class's atributes.
	*/
	private String method;
	private List<Expression> args;
	private List<Location> formalParameters; //Ghost list for drive the same form call to local method like extern methods.  

	/**
	* Constructor of a ExterninvkCallExpr object.
	*
	* @param my_method - name of the method called.
	* @param a - List of Expression.
	* @param my_returnType - Type which return the call.
	* @param my_line/my_col - to report an error.
	*
	*/
	public ExterninvkCallExpr(String my_method, List<Expression> a, Type my_returnType,int my_line, int my_column) {
		this.method=my_method;
		this.args = a;
		this.type=my_returnType;
		lineNumber=my_line;
		colNumber= my_column;
		//Code for drive call extern method like as local methods
		formalParameters=new LinkedList<Location>();
		for(Expression expr: args){
			Location l= new VarLocation("ExternParam",-1,-1);//Make a ghost formal parameter
			formalParameters.add(l);		
		}
		setParametersOffset(formalParameters);	
	}

	/**
	*
	* Methods set and get
	*
	*/
	
	/**
	* Method to get the ExterninvkCallExpr's method.
	*
	* @return String
	*/
	public String getMethod() {
		return this.method;
	}

	/**
	* Method to set the ExterninvkCallExpr's name. 
	*
	* @param my_method - A String to be seted.
	*/
	public void setName(String my_method) {
		this.method = my_method;
	}

	/**
	* Method to get the ExterninvkCallExpr's Arguments.
	*
	* @return the list of arguments (List<Expression>)
	*/
	public List<Expression> getArguments() {
		return args;
	}

	/**
	* Method to set the ExterninvkCallExpr's arguments. 
	*
	* @param args - The list of arguments to be seted.
	*/
	public void setArgs(List<Expression> args) {
		this.args = args;
	}

	/**
	* Method to set the ExterninvkCallExpr's list of parameters. 
	*
	* @param listParameters - The list of parameters to be seted.
	*/
	private void setParametersOffset(List<Location> listParameters){
      int register=1; //Each register has a numer since 1 to 6
      int i=0;
      while (i<6 && listParameters.size()>i){
        listParameters.get(i).setOffset(register);
         register++;    
         i++; 
      }
      int offset=12;//since 7 parameter to the last, put these in stack. 
      while(listParameters.size()>i){
           listParameters.get(i).setOffset(offset);
           offset= offset+4;
      }
          
    }

	/**
	* Method to get the ExterninvkCallExpr's Formal Parameters.
	*
	* @return the list of formalParameters (List<Location>)
	*/
	public List<Location> getFormalParameters(){
		return formalParameters;		
	}
	
	/**
	* New implementation of the method toString.
	*
	* @see String#toString().
	*/
	@Override
	public String toString() {
		String rtn = method.toString() + "(";
		for (Expression arg: args) {
			rtn += arg + ", ";
		}
		
		if (!args.isEmpty()){
			rtn = rtn.substring(0, rtn.length()-2);
		}
		
		rtn += ")";
		
		return rtn;
	}

	/**
	* New implementation of the method accept.
	*
	* @see AST#accept(ASTVisitor<T> v).
	*/
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

    

}
