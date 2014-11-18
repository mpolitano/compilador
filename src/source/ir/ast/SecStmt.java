package ir.ast;

import ir.ASTVisitor;

public class SecStmt extends Statement {

	public SecStmt(int my_line,int my_column){
		lineNumber=my_line;
		colNumber=my_column;
	};

	public String toString(){
		return ";";		
	}

	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}
