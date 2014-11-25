package ctds_pcr.ast;
import ctds_pcr.ASTVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;


public class ExterninvkCallExpr extends CallExpr {
	private String method;
	private List<Expression> args;
	private List<Location> formalParameters; //Ghost list for drive the same form call to local method like extern methods.  

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
	
	public String getMethod() {
		return this.method;
	}

	public void setName(String my_method) {
		this.method = my_method;
	}

	public List<Expression> getArguments() {
		return args;
	}

	public void setArgs(List<Expression> args) {
		this.args = args;
	}
	
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


	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

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

		public List<Location> getFormalParameters(){
			return formalParameters;		
		}

}
