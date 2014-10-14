package ir.codeGeneration;

import java.util.*;
import java.io.*;
import ir.ast.*;
import ir.intermediateCode.TAInstructions;

public class CodeGenerator{
	private static FileWriter f=null;
    private static PrintWriter pw=null;
		
	public static void generateCode(List<TAInstructions> program){
    	try
    	{
        	f = new FileWriter("/home/nando/Desktop/example1.s");
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
			case Assign: genAssignAsmCode(instr);break;
			case AddI: genAddIAsmCode(instr);break;
			case Ret: genRetAsmCode(instr);break;
			case ParamPush: genPushAsmCode(instr);break;
			case ParamPop: genPopAsmCode(instr);break;
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
}