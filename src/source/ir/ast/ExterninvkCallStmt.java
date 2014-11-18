package ir.ast;
import ir.ASTVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class ExterninvkCallStmt extends CallStmt {
	private String method;
	private List<Location> formalParameters; //Ghost list for drive the same form call to local method like extern methods.  

	public ExterninvkCallStmt(String my_method, List<Expression> my_listExpr) {
		this.method= my_method;
		this.args = my_listExpr;
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
