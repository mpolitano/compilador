package ir.intermediateCode;
import ir.ast.*;
public class TAInstructions{
	
	public enum Instr {
	//Statement instructios
	Assign,
	//Arithmetical binary Instructions
	AddF, AddI, SubF, SubI,
	//Aritmetical unary instructions
	MinusI, MinusF,MultI, MultF,DivF, Resto,
	//Logical binary Instuctions
	And, Or,
	//Logical unary Instuctions
	Not,
	//relational binary instructions
	Equal, Dif, GE,LE,Grt,Les,
	//Jump Instructions
	Jmp, JTrue,
	//Instructions for call procedure
	Call, ParamPush, ParamPop, CallExtern,
	//Conversion instruction
	ToFloat
	};

	public Object op1,op2;
	public Location destination;
	public Instr inst;

	public TAInstructions(Instr myInst, Object my_op1, Location my_destination){
		inst=myInst;
		op1=my_op1;
		destination=my_destination;
	}

	public TAInstructions(Instr myInst, Object my_op1, Object my_op2, Location my_destination){
		inst=myInst;
		op1=my_op1;
		op2=my_op2;
		destination=my_destination;
	}

	public TAInstructions(Instr myInst, Object my_op1){
		inst=myInst;
		op1=my_op1;
	}

	public String toString(){
		String labelOp;
		switch (inst){
			case Assign: labelOp= "Assign";break;
			case AddF: labelOp= "AddF";break;
			case AddI: labelOp= "AddI";break;
			case SubF: labelOp= "SubF";break;
			case SubI: labelOp= "SubI";break;
			case MinusF: labelOp= "MinusF";break;
			case MinusI: labelOp= "MinusI";break;
			case MultF: labelOp= "MultF";break;
			case MultI: labelOp= "MultI";break;
			case DivF: labelOp= "DivF";break;
			case Resto: labelOp= "Resto";break;
			case Call: labelOp= "Call";break;
			case ParamPush: labelOp= "ParamPush";break;
			case ParamPop: labelOp= "ParamPop";break;
			case CallExtern: labelOp= "CallExtern";break;
			case ToFloat: labelOp= "ToFloat";break;
			default: labelOp="toString error"; break;	
		}
		String aux= labelOp+" ";
		if (op1!=null){aux=aux+ "Op1: "+ op1.toString()+" ";}
		if (op2!=null){aux=aux+ "Op2: "+ op2.toString()+" ";}
		if (destination!=null){aux=aux+ "Destination: "+ destination.toString()+" ";}
		return aux;
	}
}

//Assign Expression Location