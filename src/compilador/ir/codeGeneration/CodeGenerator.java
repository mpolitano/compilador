package ir.codeGeneration;

import java.util.*;
import java.io.*;
import ir.ast.*;
import ir.intermediateCode.TAInstructions;

public class CodeGenerator{
	private static FileWriter f=null;
    private static PrintWriter pw=null;
		
	public static void generateCode(List<TAInstructions> program, String path){
    	try
    	{
        	f = new FileWriter(path);
        	pw = new PrintWriter(f);
        	for(TAInstructions instr: program){
        		generateAsmCode(instr);
        	}

    	}catch (Exception e){ e.printStackTrace();}
		finally {
       			try {      
       				if (f != null)
          					f.close();
       			} catch (Exception e2) {e2.printStackTrace();}
			}
	}

	private static void generateAsmCode(TAInstructions instr){
		switch(instr.getInstruction()){
			case ProgramDecl: genProgramAsmCode(instr); break;
			case LocationDecl: genLocationDeclAsmCode(instr); break;
			case MethodDecl: genMethodDeclAsmCode(instr); break;
			case MethodDeclEnd: genMethodDeclEndAsmCode(instr); break;
			case Call: genCallAsmCode(instr);break;
			case CallExtern: genCallExternCode(instr);break;
			case Assign: genAssignAsmCode(instr);break;
			case AddI: genAddIAsmCode(instr);break;
			case SubI: genSubIAsmCode(instr);break;
			case Ret: genRetAsmCode(instr);break;
			case ParamPush: genPushAsmCode(instr);break;
			case ParamPop: genPopAsmCode(instr);break;
			case MultI: genMultIAsmCode(instr);break;
			case DivI: genDivIAsmCode(instr); break;
			case Mod: genModAsmCode(instr); break;
			case LesI: genLesIAsmCode(instr); break;
			case GrtI: genGrtIAsmCode(instr); break;
			case Equal: genEqualAsmCode(instr); break;
			case Dif: genDifAsmCode(instr); break;
			case GEI: genGEIAsmCode(instr); break;
			case LEI: genLEIAsmCode(instr); break;
			case And: genAndAsmCode(instr); break;
			case Or: genOrAsmCode(instr); break;
			case Not: genNotAsmCode(instr); break;
			case JTrue: genJTrueAsmCode(instr);break;
			case JFalse: genJFalseAsmCode(instr);break;
			case Jmp: genJmpAsmCode(instr);break;
			case PutLabel: genPutLabelAsmCode(instr);break; 
			case PutStringLiteral: genPutStringLiteralCode(instr);break;
			default: pw.println("Asssembler code for instruction: "+ instr.getInstruction().toString() +" not defined");		
		}
	}

	private static void genProgramAsmCode(TAInstructions instr){
		pw.println(".file \""+instr.getOp1().toString()+ "\"");
	}

	private static void genLocationDeclAsmCode(TAInstructions instr){
		Location l= (Location) instr.getOp1();
		switch (l.getOffset()){
			case 0: pw.println(".comm " + l.getId() +" ,4,4");break;	
		}	
	}


	private static void genMethodDeclAsmCode(TAInstructions instr){
		MethodLocation m= (MethodLocation) instr.getOp1();
		pw.println(".text");
		pw.println(".globl "+ m.getId());
		pw.println(m.getId() + ":");
		pw.println("enter $"+ m.amoutLocalLocation()*4+",$0"); //Maybe has a problem with array
	}

	private static void genMethodDeclEndAsmCode(TAInstructions instr){
		MethodLocation m= (MethodLocation) instr.getOp1();
		if (m.amoutLocalLocation()>0)pw.println("mov %rbp, %rsp");//pop local location
		pw.println("leave");
		pw.println("ret");

	}

	private static void genCallAsmCode(TAInstructions instr){
		LabelExpr m= (LabelExpr)instr.getOp1();
		pw.println("call "+ m.toString());
	}

	
	private static void genAssignAsmCode(TAInstructions instr){
		Expression expr= instr.getOp1();
		RefLocation l= instr.getDestination();
		if (expr instanceof IntLiteral){
			pw.println("movl "+expr.toAsmCode()+", "+ l.toAsmCode());
		}
	}

	private static void genAddIAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %eax") ;
			pw.println("addl "+expr2.toAsmCode()+", %eax");
			pw.println("movl %eax, "+ l.toAsmCode());
	}

	private static void genSubIAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %eax");
			pw.println("subl "+expr2.toAsmCode()+", %eax");
			pw.println("movl %eax, "+ l.toAsmCode()) ;
	}

	private static void genMultIAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %eax");
			pw.println("imull "+expr2.toAsmCode()+", %eax");
			pw.println("movl %eax, "+l.toAsmCode());
	}

	private static void genDivIAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %eax");
			pw.println("cltd"); //Convert Signed Long to Signed Double Long. Sign-extend EAX -> EDX:EAX
			pw.println("divl"+expr2.toAsmCode());
			pw.println("movl %eax, "+l.toAsmCode());
	}

	private static void genModAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %eax");
			pw.println("cltd");
			pw.println("divl"+expr2.toAsmCode());
			pw.println("movl %edx, "+l.toAsmCode());
	}

	private static void genLesIAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %edx");
			pw.println("movl "+expr2.toAsmCode()+", %eax");
			pw.println("cmpl %eax, %edx");
			pw.println("setl %al");
			pw.println("movb %al, "+l.toAsmCode());
	}

	private static void genGrtIAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %edx");
			pw.println("movl "+expr2.toAsmCode()+", %eax");
			pw.println("cmpl %eax, %edx");
			pw.println("setg %al");
			pw.println("movb %al, "+l.toAsmCode());
	}

	private static void genEqualAsmCode(TAInstructions instr){
		Expression expr1=instr.getOp1();
		Expression expr2=instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %edx");
			pw.println("movl "+expr2.toAsmCode()+", %eax");
			pw.println("cmpl %eax, %edx");
			pw.println("sete %al");
			pw.println("movb %al, "+l.toAsmCode());
	}

	private static void genDifAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l= instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %edx");
			pw.println("movl "+expr2.toAsmCode()+", %eax");
			pw.println("cmpl %eax, %edx");
			pw.println("setne %al");
			pw.println("movb %al, "+l.toAsmCode());
	}

	private static void genGEIAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l= instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %edx");
			pw.println("movl "+expr2.toAsmCode()+", %eax");
			pw.println("cmpl %eax, %edx");
			pw.println("setge %al");
			pw.println("movb %al, "+l.toAsmCode());
	}

	private static void genLEIAsmCode(TAInstructions instr){
		Expression expr1 = instr.getOp1();
		Expression expr2 = instr.getOp2();
		RefLocation l= instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %edx");
			pw.println("movl "+expr2.toAsmCode()+", %eax");
			pw.println("cmpl %eax, %edx");
			pw.println("setle %al");
			pw.println("movb %al, "+l.toAsmCode());
	}

	private static void genAndAsmCode(TAInstructions instr){
		Expression expr1 = instr.getOp1();
		Expression expr2 = instr.getOp2();
		RefLocation l= instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %edx");
			pw.println("movl "+expr2.toAsmCode()+", %eax");
			pw.println("andl %edx, %eax");
			pw.println("movl %eax, "+l.toAsmCode());
	}

	private static void genOrAsmCode(TAInstructions instr){
		Expression expr1 = instr.getOp1();
		Expression expr2 = instr.getOp2();
		RefLocation l= instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %edx");
			pw.println("movl "+expr2.toAsmCode()+", %eax");
			pw.println("orl %edx, %eax");
			pw.println("movl %eax, "+l.toAsmCode());
	}

	private static void genNotAsmCode(TAInstructions instr){
		Expression expr = instr.getOp1();
		RefLocation l= instr.getDestination();
			pw.println("movl "+expr.toAsmCode()+", %eax");
			pw.println("test %eax, %eax");
			pw.println("sete %al");
			pw.println("movb %al, "+l.toAsmCode());
	}


	private static void genRetAsmCode(TAInstructions instr){
		Expression expr=instr.getOp1();
		switch(expr.getType()){
			case INT: pw.println("movl "+ expr.toAsmCode() +", %eax" );//Int return in eax		
		}
	}

	private static void genPushAsmCode(TAInstructions instr){
		Expression value= instr.getOp1();
		Location destination= (Location)instr.getOp2();
		if (destination.getOffset()<=6)	
			pw.println("movl "+ value.toAsmCode()+" , "+destination.toAsmCode());//push to register		
		else
			pw.println("pushl "+ value.toAsmCode()+", "+destination.toAsmCode());//push to stack
	}

	private static void genPopAsmCode(TAInstructions instr){
		String value= ((IntLiteral) instr.getOp1()).toAsmCode();
		pw.println("sub "+value+" , %rsp");
	}

	public static void genJTrueAsmCode(TAInstructions instr){
		pw.println("testl $1, " +instr.getOp1().toAsmCode()); //and with 1 for check if expression=true.
		pw.println("jnz "+ instr.getOp2().toString());//jump for not zero.
	}

	public static void genJFalseAsmCode(TAInstructions instr){
		pw.println("testl $1, " +instr.getOp1().toAsmCode()); //and with 1 for check if expression=true.
		pw.println("jz "+ instr.getOp2().toString());//jump for zero.
	}

	public static void genJmpAsmCode(TAInstructions instr){
		pw.println("jmp "+ instr.getOp1().toString());	
	}

	public static void genPutLabelAsmCode(TAInstructions instr){
		pw.println(instr.getOp1().toString()+":");	
	}


	public static void  genCallExternCode(TAInstructions instr){
		String m= instr.getOp1().toString();
		m=m.substring(1,m.length()-1);//method name without "". pre= m="nameMethod" pos= m=nameMethod
		pw.println("mov $0, %eax");//C convention
		pw.println("call "+ m.toString());	
	}

	public static void genPutStringLiteralCode(TAInstructions instr){
		pw.println(instr.getOp1().toString());
	}
}
