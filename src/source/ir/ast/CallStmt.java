package ir.ast;
import ir.ASTVisitor;

import java.util.List;

public abstract class CallStmt extends Statement {
	protected List<Expression> args;
	
	public List<Expression> getArguments() {
		return args;
	}

	public void setArgs(List<Expression> args) {
		this.args = args;
	}

}
