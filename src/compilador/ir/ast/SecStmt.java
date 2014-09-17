package ir.ast;

import ir.ASTVisitor;

public class SecStmt extends Statement {

	public SecStmt(){};

	public String toString(){
		return ";";		
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}
