/**
*	Class that containt the Instrucction.
*	@author:Cornejo-Politano-Raverta.
*/
package ctds_pcr.intermediateCode;
import ctds_pcr.ast.*;
public class TAInstructions{
	
	public enum Instr {
	//Instruction for define a program
	ProgramDecl,
	//Instructions for indicate a block declaration
	BlockBegin, BlockEnd,
	//Instructions for define label and methods
	MethodDecl, MethodDeclEnd,LocationDecl,	
	//Statement instructios
	Assign,
	//Arithmetical binary Instructions
	AddF, AddI, SubF, SubI,
	//Aritmetical unary instructions
	MinusI, MinusF,MultI, MultF,DivI,DivF, Mod,
	//Logical binary Instuctions
	And, Or,
	//Logical unary Instuctions
	Not,
	//relational binary instructions
	EqualI, DifI,EqualF, DifF, GEI,GEF,LEI,LEF,GrtI,GrtF,LesI,LesF,
	//Jump Instructions
	Jmp, JTrue, JFalse,
	//Instructions for call procedure
	Call,CallWithReturn, ParamPush, ParamPop, CallExtern,CallExternWithReturn ,Ret, SaveParam,
	//Conversion instruction
	ToFloat,
	//Array drive instructions
	ReadArray, WriteArray,
	//Gerate label
	PutLabel,
	//Generate a String Literal
	PutStringLiteral
	};

	public Expression op1,op2;
	public RefLocation destination;
	public Instr inst;

	public TAInstructions(Instr myInst, Expression my_op1, RefLocation my_destination){
		inst=myInst;
		op1=my_op1;
		destination=my_destination;
	}

	public TAInstructions(Instr myInst){
		inst=myInst;
	}
	public TAInstructions(Instr myInst, Expression my_op1, Expression my_op2, RefLocation my_destination){
		inst=myInst;
		op1=my_op1;
		op2=my_op2;
		destination=my_destination;
	}

	public TAInstructions(Instr myInst, Expression my_op1){
		inst=myInst;
		op1=my_op1;
	}

	public TAInstructions(Instr myInst, Expression my_op1, Expression my_op2){
		inst=myInst;
		op1=my_op1;
		op2= my_op2;
	}

	public TAInstructions(Instr myInst, Location my_op1, IntLiteral my_op2){
		inst=myInst;
		op1=my_op1;
		op2= my_op2;
	}



	public TAInstructions.Instr getInstruction(){
		return this.inst;
	}

	public Expression getOp1(){
		return op1;
	}

	public Expression getOp2(){
		return op2;
	}

	public RefLocation getDestination(){
		return destination;
	}

	public String toString(){
		String labelOp;
		switch (inst){
			case ProgramDecl: labelOp= "ProgramDecl"; break;
			case BlockBegin: labelOp= "BlockBegin";break;
			case BlockEnd: labelOp="BlockEnd";break;
			case MethodDecl: labelOp= "MethodDecl";break;
			case MethodDeclEnd:labelOp= "MethodDeclEnd";break;
			case LocationDecl: labelOp= "LocationDecl " + op1.getType();break;
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
			case DivI: labelOp="DivI";break;
			case Mod: labelOp= "Resto";break;
			case And: labelOp= "And";break;
			case Or: labelOp="Or";break;
			case Not: labelOp="Not";break;
			case EqualI: labelOp="EqualI";break;
			case DifI: labelOp= "DifI";break;
			case EqualF: labelOp="EqualF";break;
			case DifF: labelOp= "DifF";break;
			case GEI: labelOp= "GEI";break;
			case GEF: labelOp= "GEF";break;
			case LEF: labelOp="LEF";break;
			case LEI: labelOp="LEI";break;
			case GrtF: labelOp= "GrtF"; break;
			case GrtI: labelOp= "GrtI"; break;
			case LesI: labelOp= "LesI";break;
			case LesF: labelOp= "LesF";break;
			case Jmp: labelOp= "Jmp";break;
			case JTrue: labelOp= "JTrue";break;
			case JFalse: labelOp= "JFalse";break;
			case Call: labelOp= "Call";break;
			case ParamPush: labelOp= "ParamPush";break;
			case ParamPop: labelOp= "ParamPop";break;
			case CallExtern: labelOp= "CallExtern";break;
			case CallExternWithReturn: labelOp= "CallExternWithReturn";break;
			case Ret: labelOp= "Ret";break;
			case SaveParam: labelOp="SaveParam";break;
			case ReadArray: labelOp= "ReadArray";break;
			case WriteArray: labelOp="WriteArray";break;
			case ToFloat: labelOp= "ToFloat";break;
			case PutLabel: labelOp= "PutLabel";break;
			case PutStringLiteral: labelOp="PutStringLiteral";break;
			case CallWithReturn: labelOp= "CallWithReturn";break;
			default: labelOp="toString error"; break;	
		}
		String aux= labelOp+" ";
		if (op1!=null){aux=aux+ "Op1: "+ op1.toString()+" ";}
		if (op2!=null){aux=aux+ "Op2: "+ op2.toString()+" ";}
		if (destination!=null){aux=aux+ "Destination: "+ destination.toString()+" ";}
		return aux;
	}
}
/*
//Instructions for define label and methods
	//Instruction for define a program
	ProgramDecl Label
	MethodDecl MethodLocation 
	MethodDeclEnd MethodLocation 
	LocationDecl Location	
	//Statement instructios
	Assign expr location (location:=expr)
	//Arithmetical binary Instructions
		AddI|AddF|...|Divf| Expr Expr Location
	//Aritmetical unary instructions
		MinusF|...|MinusI Expr, Location (location is a place temporal)
	//Logical binary Instuctions
	Or|And Expr Expr Location
	//Logical unary Instuctions
	Not Expr, Location (location is a place temporal)
	//relational binary instructions
	Equal|Dif| GE|LE|Grt|Les Expr, Location (location is a place temporal)
	//Jump Instructions
	|JTrue|JFalse Expression Label
	jmp Label
	//Instructions for call procedure
	Call|CallExtern Label
	CallWithReturn|CallExternWithReturn label location
	ParamPush Expression Location(register or ebp+12...)
	ParamPop IntLiteral
	//Conversion instruction
	ToFloat Expresion Location (location is a place temporal)
	//Array drive instructions
		ReadArray ArrayLocation dir Location 
		WriteArray Expr dir ArrayLocation -- WriteArrat valor destino
	//Gerate label
		PutLabel Label 
	//generate a StringLiteral
	PutStringLiteral StringLiteral
*/
