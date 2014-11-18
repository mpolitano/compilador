package ir.ast;

import ir.ASTVisitor;

public class WhileStmt extends Statement {
	private Expression condition;
	private Block whileBlock;
	
	public WhileStmt(Expression cond, Block whileBl, int my_line, int my_column) {
		this.condition = cond;
		this.whileBlock = whileBl;
		lineNumber= my_line;
		colNumber= my_column;
	}

	public Expression getCondition() {
		return condition;
	}

	public void setCondition(Expression condition) {
		this.condition = condition;
	}

	public Block getBlock() {
		return whileBlock;
	}

	public void setBlock(Block newBlock) {
		this.whileBlock = newBlock;
	}

	
	@Override
	public String toString() {
		return "while " + condition + '\n' + whileBlock.toString();
	}


	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}

}
