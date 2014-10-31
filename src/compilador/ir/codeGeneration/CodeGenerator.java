package ir.codeGeneration;

import java.util.*;
import java.io.*;
import ir.ast.*;
import ir.intermediateCode.TAInstructions;

public class CodeGenerator{
	private static FileWriter f=null;
    private static PrintWriter pw=null;
    private static int numberLabel=0;
		
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
			case CallWithReturn: genCallWithReturnAsmCode(instr);break;
			case CallExtern: genCallExternCode(instr);break;
			case Assign: genAssignAsmCode(instr);break;
			case AddI: genAddIAsmCode(instr);break;
			case SubI: genSubIAsmCode(instr);break;
			case MinusI: genMinusIAsmCode(instr); break;
			case Ret: genRetAsmCode(instr);break;
			case ParamPush: genPushAsmCode(instr);break;
			case ParamPop: genPopAsmCode(instr);break;
			case SaveParam: genSaveParamAsmCode(instr);break;
			case LoadParam: genLoadParamAsmCode(instr);break;
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
			case AddF: genAddFAsmCode(instr); break;
			case SubF: genSubFAsmCode(instr); break;
			case MultF: genMultFAsmCode(instr); break;
			case DivF: genDivFAsmCode(instr); break;
			case LesF: genLesFAsmCode(instr); break;
			case GrtF: genGrtFAsmCode(instr); break;
			case LEF: genLEFAsmCode(instr); break;
			case GEF: genGEFAsmCode(instr); break;
			case ToFloat: genToFloatAsmCode(instr); break;
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

	private static void genCallWithReturnAsmCode(TAInstructions instr){
		LabelExpr m= (LabelExpr)instr.getOp1();
		pw.println("call "+ m.toString());
		RefLocation l= instr.getDestination(); 
		//code for return in location
		switch(l.getType()){//TODO defined the other cases
			case INT: pw.println("movl %eax, "+l.toAsmCode()); 
		}
	}
	
	private static void genAssignAsmCode(TAInstructions instr){
		Expression expr= instr.getOp1();
		RefLocation l= instr.getDestination();
		if (expr instanceof IntLiteral){
			pw.println("movl "+expr.toAsmCode()+", "+ l.toAsmCode());
		}else{
			pw.println("movl "+expr.toAsmCode()+", "+ "%ecx");
			pw.println("movl "+"%ecx"+", "+ l.toAsmCode());
		}
	}

	private static void genMinusIAsmCode(TAInstructions instr){
		Expression expr= instr.getOp1();
		RefLocation l= instr.getDestination();
			pw.println("movl "+expr.toAsmCode()+", %eax"); //mov op1 to eax to do an arithmetic operation
			pw.println("negl %eax"); //denies the number
			pw.println("movl %eax, "+l.toAsmCode()); //mov the result to destination
	}

	private static void genAddIAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %eax") ; //mov op1 to eax to do an arithmetic operation
			pw.println("addl "+expr2.toAsmCode()+", %eax");	//op2 plus eax
			pw.println("movl %eax, "+ l.toAsmCode());	//move the result to destination
	}

	private static void genSubIAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %eax"); //mov op1 to eax to do an arithmetic operation
			pw.println("subl "+expr2.toAsmCode()+", %eax");	//op2 - eax
			pw.println("movl %eax, "+ l.toAsmCode()) ; //move the result to destination
	}

	private static void genMultIAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %eax");	//mov op1 to eax to do an arithmetic operation
			pw.println("imull "+expr2.toAsmCode()+", %eax"); //op2 * eax
			pw.println("movl %eax, "+l.toAsmCode());	//move the result to destination
	}

	private static void genDivIAsmCode(TAInstructions instr){//TODO problem with intLiteral
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %eax");	//mov op1 to eax to do an arithmetic operation
			pw.println("movl $0,%edx");
			pw.println("idivl "+expr2.toAsmCode());	//edx:eax div op2
			pw.println("movl %eax, "+l.toAsmCode());	//move the result to destination
	}

	private static void genModAsmCode(TAInstructions instr){//TODO problem with intLiteral
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
		pw.println("movl "+expr1.toAsmCode()+", %eax");	//mov op1 to eax to do an arithmetic operation
		pw.println("movl $0,%edx");
		pw.println("idivl "+expr2.toAsmCode());	//edx:eax mod op2
		pw.println("movl %edx, "+l.toAsmCode()); //move the result to destination
	}

	private static void genLesIAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %eax");	//mov op1 to edx to compare
			pw.println("movl "+expr2.toAsmCode()+", %edx"); //mov op2 to eax to compare
			pw.println("cmpl %edx, %eax");	//compare eax and edx
			pw.println("jl .L"+numberLabel); //check if edx is less than eax, and move the result to eax (1 true, 0 false)
			pw.println("movl $0, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
			pw.println("movl $1, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode()); //move the result to destination
			numberLabel++;
	}

	private static void genGrtIAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %eax"); //mov op1 to edx to compare
			pw.println("movl "+expr2.toAsmCode()+", %edx"); //mov op2 to eax to compare
			pw.println("cmpl %edx, %eax"); //compare eax and edx
			pw.println("jg .L"+numberLabel); //check if edx is greater than eax and move result to eax (1 true, 0 false)
			pw.println("movl $0, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
			pw.println("movl $1, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode()); //move the result to destination
			numberLabel++;
	}

	private static void genEqualAsmCode(TAInstructions instr){
		Expression expr1=instr.getOp1();
		Expression expr2=instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %edx"); //mov op1 to edx to compare
			pw.println("movl "+expr2.toAsmCode()+", %eax"); //mov op2 to eax to compare
			pw.println("cmpl %eax, %edx"); //compare eax and edx
			pw.println("je .L"+numberLabel);	//check if edx is equal to eax and move result to eax (1 true, 0 false)
			pw.println("movl $0, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
			pw.println("movl $1, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode()); //move the result to destination
			numberLabel++;
	}

	private static void genDifAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l= instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %edx"); //mov op1 to edx to compare
			pw.println("movl "+expr2.toAsmCode()+", %eax"); //mov op2 to eax to compare
			pw.println("cmpl %eax, %edx"); //compare eax and edx
			pw.println("jne .L"+numberLabel); //check if edx is different to eax and move result to eax (1 true, 0 false)
			pw.println("movl $0, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
			pw.println("movl $1, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode()); //move the result to destination
			numberLabel++;
	}

	private static void genGEIAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l= instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %eax"); //mov op1 to edx to compare
			pw.println("movl "+expr2.toAsmCode()+", %edx"); //mov op2 to eax to compare
			pw.println("cmpl %edx, %eax"); //compare eax and edx
			pw.println("jge .L"+numberLabel); //check if edx is greater or equal than eax and move result to eax (1 true, 0 false)
			pw.println("movl $0, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
			pw.println("movl $1, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode()); //move the result to destination
			numberLabel++;
	}

	//l= op1<=op2
	private static void genLEIAsmCode(TAInstructions instr){
		Expression expr1 = instr.getOp1();
		Expression expr2 = instr.getOp2();
		RefLocation l= instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %eax"); //mov op1 to edx to compare
			pw.println("movl "+expr2.toAsmCode()+", %edx"); //mov op2 to eax to compare
			pw.println("cmpl %edx, %eax"); //compare eax and edx
			pw.println("jle .L"+numberLabel); //check if edx is less or equal than eax and move result to eax (1 true, 0 false)
			pw.println("movl $0, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
			pw.println("movl $1, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode()); //move the result to destination
			numberLabel++;
	}

	private static void genAndAsmCode(TAInstructions instr){
		Expression expr1 = instr.getOp1();
		Expression expr2 = instr.getOp2();
		RefLocation l= instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %edx"); //mov op1 to edx to compare
			pw.println("movl "+expr2.toAsmCode()+", %eax"); //mov op2 to eax to compare
			pw.println("andl %edx, %eax"); //chek the add condition between edx and eax, the result will be in eax
			pw.println("movl %eax, "+l.toAsmCode()); //move the result to destination
	}

	private static void genOrAsmCode(TAInstructions instr){
		Expression expr1 = instr.getOp1();
		Expression expr2 = instr.getOp2();
		RefLocation l= instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %edx"); //mov op1 to edx to compare
			pw.println("movl "+expr2.toAsmCode()+", %eax"); //mov op2 to eax to compare
			pw.println("orl %edx, %eax"); //chek the or condition between edx and eax, the result will be in eax
			pw.println("movl %eax, "+l.toAsmCode()); //move the result to destination
	}

	private static void genNotAsmCode(TAInstructions instr){
		Expression expr = instr.getOp1();
		RefLocation l= instr.getDestination();
			pw.println("movl "+expr.toAsmCode()+", %eax"); //mov op1 to eax 
			pw.println("xorl $1, %eax");
			pw.println("movl %eax, "+l.toAsmCode()); //move the result to destination
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
		else{
				pw.println("sub $4, %rsp");//save place for push param
				pw.println("movl "+ value.toAsmCode()+", "+destination.toAsmCode());//push to stack
			}
	}

	private static void genSaveParamAsmCode(TAInstructions instr){			
			pw.println("movl "+ instr.getOp1().toAsmCode()+" , "+ instr.getDestination().toAsmCode());
	}

	private static void genLoadParamAsmCode(TAInstructions instr){
			pw.println("movl (%rsp) , " +instr.getOp1().toAsmCode());
			pw.println("add  $4 , %rsp");
	}


	private static void genPopAsmCode(TAInstructions instr){
		String value= ((IntLiteral) instr.getOp1()).toAsmCode();
		pw.println("sub "+value+" , %rsp");
	}

	public static void genJTrueAsmCode(TAInstructions instr){
		pw.println("cmpl $1, " +instr.getOp1().toAsmCode()); //and with 1 for check if expression=true.
		pw.println("je "+ instr.getOp2().toString());//jump for not zero.
	}

	public static void genJFalseAsmCode(TAInstructions instr){
		pw.println("cmpl $0, " +instr.getOp1().toAsmCode()); //and with 1 for check if expression=true.
		pw.println("je "+ instr.getOp2().toString());//jump for zero.
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
		pw.println("movl $0, %eax");//C convention
		pw.println("call "+ m.toString());	
	}

	public static void genPutStringLiteralCode(TAInstructions instr){
		pw.println(instr.getOp1().toString());
	}

	public static void genAddFAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movss "+expr1.toAsmCode()+", %xmm0") ; 
			pw.println("addss "+expr2.toAsmCode()+", %xmm0");
			pw.println("movss %xmm0, "+ l.toAsmCode());	
	}

	public static void genSubFAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movss "+expr1.toAsmCode()+", %xmm0") ; 
			pw.println("subss "+expr2.toAsmCode()+", %xmm0");
			pw.println("movss %xmm0, "+ l.toAsmCode());	
	}

	public static void genMultFAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movss "+expr1.toAsmCode()+", %xmm0") ; 
			pw.println("mulss "+expr2.toAsmCode()+", %xmm0");
			pw.println("movss %xmm0, "+ l.toAsmCode());	
	}

	public static void genDivFAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movss "+expr1.toAsmCode()+", %xmm0") ; 
			pw.println("divss "+expr2.toAsmCode()+", %xmm0");
			pw.println("movss %xmm0, "+ l.toAsmCode());	
	}

	

    public static void genLesFAsmCode(TAInstructions instr){
    	Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movss "+expr1.toAsmCode()+", %xmm1");
			pw.println("movss "+expr2.toAsmCode()+", %xmm0");
			pw.println("ucomiss %xmm1, %xmm0");
			pw.println("jbe .L"+numberLabel);
			pw.println("movl $1, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
			pw.println("movl $0, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode());// %xmm1<%xmm0
			numberLabel++;
    }

    public static void genGrtFAsmCode(TAInstructions instr){
    	Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movss "+expr1.toAsmCode()+", %xmm1");
			pw.println("movss "+expr2.toAsmCode()+", %xmm0");
			pw.println("ucomiss %xmm0, %xmm1");
			pw.println("jbe .L"+numberLabel);
			pw.println("movl $1, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
			pw.println("movl $0, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode());// %xmm1>%xmm0
			numberLabel++;
    }

    public static void genLEFAsmCode(TAInstructions instr){
    	Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movss "+expr1.toAsmCode()+", %xmm1");
			pw.println("movss "+expr2.toAsmCode()+", %xmm0");
			pw.println("ucomiss %xmm1, %xmm0");
			pw.println("jb .L"+numberLabel);
			pw.println("movl $1, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
			pw.println("movl $0, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode());// %xmm1<%xmm0
			numberLabel++;
    }

    public static void genGEFAsmCode(TAInstructions instr){
    	Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movss "+expr1.toAsmCode()+", %xmm1");
			pw.println("movss "+expr2.toAsmCode()+", %xmm0");
			pw.println("ucomiss %xmm0, %xmm1");
			pw.println("jb .L"+numberLabel);
			pw.println("movl $1, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
		
			pw.println("movl $0, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode());// %xmm1<%xmm0
			numberLabel++;
    }

//No funciona.
    public static void genToFloatAsmCode(TAInstructions instr){
    	Expression expr1= instr.getOp1();
		RefLocation l=instr.getDestination();
		//pw.println("movss "+expr1.toAsmCode()+", %xmm0");
		pw.println("movss "+expr1.toAsmCode()+", %xmm1");
		pw.println("addss xmm3, xmm3");
		pw.println("cvtss2si "+"eax"+", %xmm0");
	//	pw.println("cvtsi2sd "+"%xmm0"+", %rax");
		//pw.println("cvtss2si "+"%xmm0"+", %xmm0");





    }

}
