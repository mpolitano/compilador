/**
*This class is an implementation of ASTVisitor that make a optimization in 
*frame. Re-assign offset for each location for forbit that these live more time than
*the time where can be referencing. For its, each blocks will have an offset and a set
*of all block's local variables(location defined by user or location defined in Three address 
*code generation phase) and FrameOptimization calculate a new offset for each variable and
*frame size for each block.
*
*@author Cornejo-Politano-Raverta
*
*Precondition: Each block cointain a set<location> that are stored in block's frame
*Poscondition: All location's offset has been modified and cointain a local block offset
*
*Instuitive idea
*	{//Block 1
*		sub %rsp, amountLocalLocation Block1 *4 
*			vBlock1 offset= -4
*		{//Block 2
*		sub %rsp, amountLocalLocation Block2 *4
*			vBlock2 ofset= -8
*		sub %rsp, amountLocalLocation Block2 *4	
*		}//End Block 2
*		add %rsp, amountLocalLocation Block1 *4
*	}//end Block 1
*/

package ctds_pcr.optimization;
import ctds_pcr.ASTVisitor;
import ctds_pcr.ast.*;
import java.util.Stack;

public class FrameOptimization implements ASTVisitor{
	
	private Stack<Integer> blockStack; //Stack for stored block's offset when tour AST
 	
 	/**class constructor*/
	public FrameOptimization(){
		blockStack= new Stack<Integer>();
	}

	/**Propagate FrameOptimization visitor to each methods in program*/
	public Object visit(Program prog){
		for(MethodLocation m: prog.getMethods())
			m.accept(this);
		return null;
	}
	
// visit statements

	/**Isn't need make anything */
	public Object visit(AssignStmt stmt){return null;}
	
	/**Isn't need make anything */
	public Object visit(ReturnStmt stmt){return null;}
	
	/**Propagate FrameOptimization visitor to if block and else block*/
	public Object visit(IfStmt stmt){
		stmt.getIfBlock().accept(this);
		if (stmt.getElseBlock()!=null)
			stmt.getElseBlock().accept(this);
		return null;
	}
	
	/*Visit Block */
	public Object visit(Block stmt){
		Integer currentBlockOffset; 
		int amountLocation=0;//amount of block locations has a amount of 4-byte block location
		/*Initialize block offset
		  If is a first block in a method, must start to assign offset since 0 (-4,-8,...).
		  If is a block into a other block, must start with to assign offset since block's offset that cointain its.
		 */
		if (blockStack.empty())
			currentBlockOffset= new Integer(0);
		else
			currentBlockOffset= new Integer(blockStack.peek().intValue());			 
		//Re-assign offset for each block location
		for(Location l: stmt.getAllLocation()){			
			currentBlockOffset=currentBlockOffset-4;
			l.setOffset(currentBlockOffset.intValue());
			//if a location is an ArrayLocation must reverve place for all position
			if (l instanceof ArrayLocation){
				currentBlockOffset= currentBlockOffset - ((4* ((ArrayLocation)l).getSize().getValue())) ; //save place for 2..n position of array
				amountLocation= amountLocation+((ArrayLocation)l).getSize().getValue();//increment amount block location in array size
			}else 
				amountLocation++;
		}

		stmt.setOffset(amountLocation*4); //set block offset

		/* push current block offset and propagate FrameOptimization to block's statements*/	
		blockStack.push(currentBlockOffset); 
		for(Statement s: stmt.getStatements())
				s.accept(this);						
		blockStack.pop(); //pop current block
		return null;
	}

	public Object visit(BreakStmt stmt){return null;}
	public Object visit(ContinueStmt stmt){return null;}
	public Object visit(ForStmt stmt){
		stmt.getBlock().accept(this);
		return null;
	}
	public Object visit(SecStmt stmt){return null;}
	
	/**Propagate FrameOptimization to while block */
	public Object visit(WhileStmt stmt){
		stmt.getBlock().accept(this);
		return null;
	}
	public Object visit(MethodCallStmt stmt){return null;}
	public Object visit(ExterninvkCallStmt stmt){return null;}

//Visit Location

	/**Isn't need make anything */
	public Object visit(VarLocation var){return null;}
	
	/**Propagate FrameOptimization to method block */
	public Object visit(MethodLocation method){
		method.getBody().accept(this);
		return null;
	}

	/**Isn't need make anything */
	public Object visit(ArrayLocation array){return null;}
	
	/**Isn't need make anything */
	public Object visit(RefVarLocation var){return null;}
	
	/**Isn't need make anything */
	public Object visit(RefArrayLocation array){return null;}
	
// visit expressions

	/**Isn't need make anything */
	public Object visit(BinOpExpr expr){return null;}
	
	/**Isn't need make anything */
	public Object visit(ExterninvkCallExpr expr){return null;}
	
	/**Isn't need make anything */
	public Object visit(MethodCallExpr expr){return null;}	
	
	/**Isn't need make anything */
	public Object visit(UnaryOpExpr expr){return null;}
	
	/**Isn't need make anything */
	public Object visit(LabelExpr expr){return null;}

// visit literals	
	
	/**Isn't need make anything */
	public Object visit(IntLiteral lit){return null;}
	
	/**Isn't need make anything */
	public Object visit(FloatLiteral lit){return null;}
	
	/**Isn't need make anything */
	public Object visit(BooleanLiteral lit){return null;}
	
	/**Isn't need make anything */
	public Object visit(StringLiteral lit){return null;}

}
