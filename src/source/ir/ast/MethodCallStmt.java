package ir.ast;
import ir.ASTVisitor;
import java.util.ArrayList;
import java.util.List;


public class MethodCallStmt extends CallStmt {
	protected MethodLocation method;
	
	public MethodCallStmt(MethodLocation my_method, List<Expression> my_listExpr) {
		this.method=my_method;
		this.args = my_listExpr;
	}
	
	public MethodLocation getMethod() {
		return this.method;
	}

	public void setName(MethodLocation my_method) {
		this.method = my_method;
	}

	@Override
	public String toString() {
		String rtn = method.getId() + "(";
		for (Expression arg: args) {
			rtn += arg + ", ";
		}
		
		if (!args.isEmpty()){
			rtn = rtn.substring(0, rtn.length()-2);
		}
		
		rtn += ")";
		
		return rtn+ method.getBody().toString();
	}


	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}