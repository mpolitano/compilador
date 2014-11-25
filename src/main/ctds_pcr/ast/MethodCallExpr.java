package ctds_pcr.ast;
import ctds_pcr.ASTVisitor;
import java.util.ArrayList;
import java.util.List;


public class MethodCallExpr extends CallExpr {
	private MethodLocation method;
	private List<Expression> args;
	

	public MethodCallExpr(MethodLocation my_method, List<Expression> a, int my_line, int my_col) {
		this.method=my_method;
		this.args = a;
		lineNumber=my_line;
		colNumber=my_col;
	}
	
	public MethodLocation getMethod() {
		return this.method;
	}

	public void setName(MethodLocation my_method) {
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