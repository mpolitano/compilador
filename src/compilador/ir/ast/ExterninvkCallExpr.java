package ir.ast;
import ir.ASTVisitor;
import java.util.ArrayList;
import java.util.List;


public class ExterninvkCallExpr extends CallExpr {
	private String method;
	private Type returnType;
	private List<Expression> args;
	

	public ExterninvkCallExpr(String my_method, List<Expression> a, Type my_returnType) {
		this.method=my_method;
		this.args = a;
		this.returnType=my_returnType;
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

}
