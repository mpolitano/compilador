package ir.ast;
import ir.ASTVisitor;
import java.util.ArrayList;
import java.util.List;


public class ExterninvkCallStmt extends CallStmt {
	private String method;
	public ExterninvkCallStmt(String my_method, List<Expression> my_listExpr) {
		this.method= my_method;
		this.args = my_listExpr;
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

}