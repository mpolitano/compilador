package ctds_pcr;

import ctds_pcr.ast.*;

// Abstract visitor
public interface ASTVisitor<T> {

//visit program
	public T visit(Program prog);
	
// visit statements
	public T visit(AssignStmt stmt);
	public T visit(ReturnStmt stmt);
	public T visit(IfStmt stmt);
	public T visit(Block stmt);
	public T visit(BreakStmt stmt);
	public T visit(ContinueStmt stmt);
	public T visit(ForStmt stmt);
	public T visit(SecStmt stmt);
	public T visit(WhileStmt stmt);
	public T visit(MethodCallStmt stmt);
	public T visit(ExterninvkCallStmt stmt);

//Visit Location
	public T visit(VarLocation var);
	public T visit(MethodLocation method);
	public T visit(ArrayLocation array);
	public T visit(RefVarLocation var);
	public T visit(RefArrayLocation array);
	
// visit expressions
	public T visit(BinOpExpr expr);
	public T visit(ExterninvkCallExpr expr);
	public T visit(MethodCallExpr expr);	
	public T visit(UnaryOpExpr expr);
	public T visit(LabelExpr expr);

// visit literals	
	public T visit(IntLiteral lit);
	public T visit(FloatLiteral lit);
	public T visit(BooleanLiteral lit);
	public T visit(StringLiteral lit);
}
