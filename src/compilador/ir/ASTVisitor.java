package ir;

import ir.ast.*;

// Abstract visitor
public interface ASTVisitor<T> {
// visit statements
	public T visit(AssignStmt stmt);
	public T visit(ReturnStmt stmt);
	public T visit(IfStmt stmt);
	
// visit expressions
	public T visit(BinOpExpr expr);;
	
// visit literals	
	public T visit(IntLiteral lit);

// visit locations	
	public T visit(VarLocation loc);
}
