package ir.ast;

import ir.ASTVisitor;

public class ForStmt extends Statement {
	private Location id;
	private Expression initialValue;
	private Expression finalValue;
	private Block block;
	
	public ForStmt(Location i, Expression init, Expression fin, Block bl) {
		this.id = i;
		this.initialValue = init;
		this.finalValue = fin;
		this.block = bl;
	}

	public Location getId() {
		return id;
	}

	public void setId(Location id) {
		this.id = id;
	}

	public Expression getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(Expression initialValue) {
		this.initialValue = initialValue;
	}

	public Expression getFinalValue() {
		return finalValue;
	}

	public void setFinalValue(Expression finalValue) {
		this.finalValue = finalValue;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}
	
	@Override
	public String toString() {
		String rtn = "for " + id + "=" + initialValue + ", " + finalValue + '\n';
		rtn += block.toString();
		return rtn;
	}
/*
	@Override
	public <T> T accept(ASTVisitor<T> v) {
		return v.visit(this);
	}
*/
}
