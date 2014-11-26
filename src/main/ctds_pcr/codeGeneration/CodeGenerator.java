/* 
	Class that generate assembly x86-64 code from a Three Adress Code
	@author: Cornejo-Politano-Raverta
*/
package ctds_pcr.codeGeneration;

import java.util.*;
import java.io.*;
import ctds_pcr.ast.*;
import ctds_pcr.intermediateCode.TAInstructions;

public class CodeGenerator{
	private static FileWriter f=null;
    private static PrintWriter pw=null;
    private static int numberLabel=0;
	private static boolean frameOptimization; //flag for make frame optimization or not


	//Method for generate x86-64 code
	public static void generateCode(List<TAInstructions> program, String path,boolean my_frameOptimization){
    	try
    	{
        	f = new FileWriter(path);
        	pw = new PrintWriter(f);
        	frameOptimization=my_frameOptimization;
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

	//Method for dispatch each intructions to correct method for generate assembly x86-64 code.
	private static void generateAsmCode(TAInstructions instr){
		switch(instr.getInstruction()){
			case ProgramDecl: genProgramAsmCode(instr); break;
			case BlockBegin: genBlockBeginAsmCode(instr);break;
			case BlockEnd: genBlockEndAsmCode(instr);break;
			case LocationDecl: genLocationDeclAsmCode(instr); break;
			case MethodDecl: genMethodDeclAsmCode(instr); break;
			case MethodDeclEnd: genMethodDeclEndAsmCode(instr); break;
			case Call: genCallAsmCode(instr);break;
			case CallWithReturn: genCallWithReturnAsmCode(instr);break;
			case CallExtern: genCallExternCode(instr);break;
			case CallExternWithReturn: genCallExternWithReturnCode(instr);break;
			case Assign: genAssignAsmCode(instr);break;
			case AddI: genAddIAsmCode(instr);break;
			case SubI: genSubIAsmCode(instr);break;
			case MinusI: genMinusIAsmCode(instr); break;
			case Ret: genRetAsmCode(instr);break;
			case ParamPush: genPushAsmCode(instr);break;
			case ParamPop: genPopAsmCode(instr);break;
			case SaveParam: genSaveParamAsmCode(instr);break;
			case MultI: genMultIAsmCode(instr);break;
			case DivI: genDivIAsmCode(instr); break;
			case Mod: genModAsmCode(instr); break;
			case LesI: genLesIAsmCode(instr); break;
			case GrtI: genGrtIAsmCode(instr); break;
			case EqualI: genEqualIAsmCode(instr); break;
			case DifI: genDifIAsmCode(instr); break;
			case EqualF: genEqualFAsmCode(instr); break;
			case DifF: genDifFAsmCode(instr); break;
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
			case ReadArray: genReadArrayAsmCode(instr);break;
			case WriteArray: genWriteArrayAsmCode(instr);break;
			default: pw.println("Asssembler code for instruction: "+ instr.getInstruction().toString() +" not defined");		
		}
	}

	/*method that write name's program*/
	private static void genProgramAsmCode(TAInstructions instr){
		pw.println(".file \""+instr.getOp1().toString()+ "\"");
	}

	/*method that initial the blocks*/
	private static void genBlockBeginAsmCode(TAInstructions instr){
		if (frameOptimization){
			IntLiteral offset= (IntLiteral)instr.getOp1();
				switch(offset.getValue()%16){
					case 4: offset.setValue(offset.getValue()+12);
							break;
					case 8: offset.setValue(offset.getValue()+8);
							break;
					case 12:offset.setValue(offset.getValue()+4);
							break;
				}	
			pw.println("subq "+offset.toAsmCode()+" ,%rsp");
		}
	}

	/*method that initial the blocks*/
	private static void genBlockEndAsmCode(TAInstructions instr){
		if (frameOptimization){
			IntLiteral offset= (IntLiteral)instr.getOp1();
			pw.println("addq "+offset.toAsmCode()+" ,%rsp");
		}
	}

	/*method that initial the blocks*/
	private static void genLocationDeclAsmCode(TAInstructions instr){
		Location l= (Location) instr.getOp1();
		switch (l.getOffset()){
			case 0: if (l instanceof VarLocation) {pw.println(".comm " + l.getId() +" ,4,4");break;}	
					else{ int size= ((ArrayLocation) l).getSize().getValue() * 4; pw.println(".comm " + l.getId() +" ," + size +",32");break;} 
		}	
	}

	/*method that initializes a method, this declared enter a multiple of 16 if use frameOptimitazion*/
	private static void genMethodDeclAsmCode(TAInstructions instr){
		MethodLocation m= (MethodLocation) instr.getOp1();
		pw.println(".text");
		pw.println(".globl "+ m.getId());
		pw.println(m.getId() + ":");
		int numEnter= m.amoutLocalLocation()*4;
		int suma;

			if (!frameOptimization){
				switch(numEnter%16){
					case 4: suma=numEnter+12;
							pw.println("enter $"+ suma+",$0");break;
					case 8: suma=numEnter+8; 
							pw.println("enter $"+ suma+",$0");break;
					case 12:suma=numEnter+4;
							pw.println("enter $"+ suma+",$0");break;
					case 0: pw.println("enter $"+ numEnter+",$0");break; 	
				}
			} 
			else
				pw.println("enter $0,$0"); 
	}

	/*method that close a method*/
	private static void genMethodDeclEndAsmCode(TAInstructions instr){
		MethodLocation m= (MethodLocation) instr.getOp1();
		if (m.amoutLocalLocation()>0)pw.println("mov %rbp, %rsp");//pop local location
		pw.println("leave");
		pw.println("ret");

	}

	/*method that call one LabelExpr*/
	private static void genCallAsmCode(TAInstructions instr){
		LabelExpr m= (LabelExpr)instr.getOp1();
		pw.println("call "+ m.toString());
	}

	/*Method for calls a LabelExpr and retrieves the return of this */
	private static void genCallWithReturnAsmCode(TAInstructions instr){
		LabelExpr m= (LabelExpr)instr.getOp1();
		pw.println("call "+ m.toString());
		RefLocation l= instr.getDestination(); 
		//code for return in location
		switch(l.getType()){//TODO defined the other cases
			case INT: case BOOLEAN: pw.println("movl %eax, "+l.toAsmCode()); break;
			case FLOAT: pw.println("movss %xmm0, "+l.toAsmCode()); break;

		}
		
	}
	
	private static void genAssignAsmCode(TAInstructions instr){
		Expression expr= instr.getOp1();
		RefLocation l= instr.getDestination();
		if (expr instanceof IntLiteral){
			pw.println("movl "+expr.toAsmCode()+", "+ l.toAsmCode());
		}else{if (expr instanceof FloatLiteral){
			pw.println("movss "+expr.toAsmCode()+", %xmm3" );//by our convention , save float in xmm3.
			pw.println("movss %xmm3 ," +l.toAsmCode());
			}else{
				pw.println("movl "+expr.toAsmCode()+", "+ "%ecx"); //problem in case x=y. Use auxiliar register for move.
				pw.println("movl "+"%ecx"+", "+ l.toAsmCode());
			}
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
		if(expr2 instanceof IntLiteral){
			pw.println("movl "+expr1.toAsmCode()+", %eax");	//mov op1 to eax to do an arithmetic operation
			pw.println("movl $0,%edx");
			pw.println("movl " + expr2.toAsmCode()+ ", %ecx ");
			pw.println("idivl %ecx");	//edx:eax div op2
			pw.println("movl %eax, "+l.toAsmCode());	//move the result to destination
		}else{
			pw.println("movl "+expr1.toAsmCode()+", %eax");	//mov op1 to eax to do an arithmetic operation
			pw.println("movl $0,%edx");
			pw.println("idivl "+expr2.toAsmCode());	//edx:eax div op2
			pw.println("movl %eax, "+l.toAsmCode());	//move the result to destination
		}
	}

	private static void genModAsmCode(TAInstructions instr){//TODO problem with intLiteral
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
		if(expr2 instanceof IntLiteral){
			pw.println("movl "+expr1.toAsmCode()+", %eax");	//mov op1 to eax to do an arithmetic operation
			pw.println("movl $0,%edx");
			pw.println("movl " + expr2.toAsmCode()+ ", %ecx ");
			pw.println("idivl %ecx");	//edx:eax mod op2
			pw.println("movl %edx, "+l.toAsmCode()); //move the result to destination
		}else{
			pw.println("movl "+expr1.toAsmCode()+", %eax");	//mov op1 to eax to do an arithmetic operation
			pw.println("movl $0,%edx");
			pw.println("idivl "+expr2.toAsmCode());	//edx:eax mod op2
			pw.println("movl %edx, "+l.toAsmCode()); //move the result to destination
		}
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

	private static void genEqualIAsmCode(TAInstructions instr){
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

	private static void genEqualFAsmCode(TAInstructions instr){
		Expression expr1=instr.getOp1();
		Expression expr2=instr.getOp2();
		RefLocation l=instr.getDestination();
		pw.println("movss "+expr1.toAsmCode()+", %xmm0"); //mov op1 to xmm0 to compare
		pw.println("movss "+expr2.toAsmCode()+", %xmm1"); //mov op2 to xmm1 to compare
		pw.println("ucomiss %xmm1, %xmm0"); //compare xmm1 and xmm0
		pw.println("je .L"+numberLabel);	
		pw.println("movl $0, %eax");
		pw.println("jmp .Continue"+numberLabel);
		pw.println(".L"+numberLabel+":");
		pw.println("movl $1, %eax");
		pw.println(".Continue"+numberLabel+":");
		pw.println("movl %eax, "+l.toAsmCode()); //move the result to destination
		numberLabel++;
		
	}

	private static void genDifIAsmCode(TAInstructions instr){
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
		
		
	private static void genDifFAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l= instr.getDestination();
		pw.println("movss "+expr1.toAsmCode()+", %xmm0"); //mov op1 to edx to compare
		pw.println("movss "+expr2.toAsmCode()+", %xmm1"); //mov op2 to eax to compare
		pw.println("ucomiss %xmm1, %xmm0"); //compare eax and edx
		pw.println("jne .L"+numberLabel);	//check if edx is equal to eax and move result to eax (1 true, 0 false)
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
			case INT: case BOOLEAN: pw.println("movl "+ expr.toAsmCode() +", %eax" );break;//Int return in eax	
			case FLOAT:pw.println("movss "+expr.toAsmCode() + ", %xmm0"); break;	
		}
	}

	private static void genPushAsmCode(TAInstructions instr){
		Expression value= instr.getOp1();
		Location destination= (Location)instr.getOp2();
		switch (value.getType()){
			case FLOAT: if (destination.getOffset()>0 && destination.getOffset()<=8){	
							pw.println("movss "+ value.toAsmCode() +","+destination.toAsmCode()); 
						}else{
								pw.println("sub $4, %rsp");//save place for push param
								pw.println("movss "+ value.toAsmCode()+" ,%xmm8"); //use xmm6 as auxiliary register
								pw.println("movss %xmm8, (%rsp)");//push to stack top
							 } break;
			default: if (destination.getOffset()>0 && destination.getOffset()<=6)	
						pw.println("movl "+ value.toAsmCode()+" , "+destination.toAsmCode());//push to register		
					else{
						pw.println("sub $4, %rsp");//save place for push param
						pw.println("movl "+ value.toAsmCode()+" ,%eax"); //use eax as auxiliary register
						pw.println("movl %eax, (%rsp)");//push to stack top
					}
					break;
		}		
	}

	//generate code for save param pass in register to method's frame.
	private static void genSaveParamAsmCode(TAInstructions instr){		
		/*when value is pass as 1-6 param, alwalls stay in register (see convection call's x86-64 processor). 
		  but the set of register used depend of value's type. If float use xmm0, xmm1, xmm2, xmm3, xmm4,xmm5
		  otherwise(in this language int or string) use esi,edi,edx,ecx,r8,r9 .
		  So, select the correct assembly x86-64 intructions(movl or movss)
		*/
		switch(instr.getOp1().getType()){
			case FLOAT:pw.println("movss "+ instr.getOp1().toAsmCode()+" , "+ instr.getDestination().toAsmCode());break;
			default:   pw.println("movl "+ instr.getOp1().toAsmCode()+" , "+ instr.getDestination().toAsmCode());

		}	
	}

	private static void genPopAsmCode(TAInstructions instr){
		String value= ((IntLiteral) instr.getOp1()).toAsmCode();
		pw.println("add "+value+" , %rsp");
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

	public static void genCallExternWithReturnCode(TAInstructions instr){
		String m= instr.getOp1().toString();//get method's name.
		m=m.substring(1,m.length()-1);//method name without "". pre= m="nameMethod" pos= m=nameMethod
		pw.println("movl $0, %eax");//C conventionf
		pw.println("call "+ m.toString());
		RefLocation l= instr.getDestination(); 
		//code for return in location
		switch(l.getType()){
			case INT: case BOOLEAN: pw.println("movl %eax, "+l.toAsmCode()); break;
			case FLOAT: pw.println("movss %xmm0, "+l.toAsmCode()); break;
		}
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
			pw.println("movss "+expr1.toAsmCode()+", %xmm0");//mov op1 to xmm0 to compare
			pw.println("movss "+expr2.toAsmCode()+", %xmm1");//mov op2 to xmm1 to compare
			pw.println("comiss %xmm1, %xmm0");
			pw.println("jb .L"+numberLabel);
			pw.println("movl $0, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
			pw.println("movl $1, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode());// %xmm1<%xmm0
			numberLabel++;
    }

    public static void genGrtFAsmCode(TAInstructions instr){
    	Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movss "+expr1.toAsmCode()+", %xmm0");//mov op1 to xmm0 to compare
			pw.println("movss "+expr2.toAsmCode()+", %xmm1");//mov op2 to xmm1 to compare
			pw.println("comiss %xmm1, %xmm0");
			pw.println("ja .L"+numberLabel);
			pw.println("movl $0, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
			pw.println("movl $1, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode());// %xmm1>%xmm0
			numberLabel++;
    }

    public static void genLEFAsmCode(TAInstructions instr){
    	Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movss "+expr1.toAsmCode()+", %xmm0");//mov op1 to xmm0 to compare
			pw.println("movss "+expr2.toAsmCode()+", %xmm1");//mov op2 to xmm1 to compare
			pw.println("comiss %xmm1, %xmm0");
			pw.println("jbe .L"+numberLabel);
			pw.println("movl $0, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
			pw.println("movl $1, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode());// %xmm1<%xmm0
			numberLabel++;
    }

    public static void genGEFAsmCode(TAInstructions instr){
    	Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movss "+expr1.toAsmCode()+", %xmm0");//mov op1 to xmm0 to compare
			pw.println("movss "+expr2.toAsmCode()+", %xmm1");//mov op2 to xmm1 to compare
			pw.println("comiss %xmm1, %xmm0");
			pw.println("jae .L"+numberLabel);
			pw.println("movl $0, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
			pw.println("movl $1, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode());// %xmm1<%xmm0
			numberLabel++;
    }

//No funciona.
    public static void genToFloatAsmCode(TAInstructions instr){
    	Expression expr1= instr.getOp1();
		RefLocation l=instr.getDestination();
		pw.println("movl "+ expr1.toAsmCode()+","+  "%edx");// Muevo al edx, Lo uso como auxiliar, por los tipos que usa el convertor
		pw.println("cvtsi2ss %edx , % xmm3 "); //Convetion xmm3 auxiliar 
		pw.println("movss  %xmm3 , "+ l.toAsmCode());
    }

//ReadArray RefArrayLocation dir var
/*
(-sizeArray*4 +posArray) %ebp
0 
1
2 
3
Last Pos array
... arrayOffset (ebp-...)
ebp 
*/
    public static void genReadArrayAsmCode(TAInstructions instr){//puedo hacerlo todo como C
    	ArrayLocation from= (ArrayLocation) ((RefArrayLocation)instr.getOp1()).getLocation();
    	int arraySize= from.getOffset()-(from.getSize().getValue()*4);//calc the end of array. a[i] is acces as end(a)+ i*4   	
    	switch(from.getOffset()){ 
    		case 0://array in static data segment		    			
	    			pw.println("movl "+ instr.getOp2().toAsmCode()+ ", %eax");	    				
					pw.println("cltq");//eax sign extend to rax
					pw.println("movl " +from.getId()+"(,%rax,4), %eax ");
					pw.println("movl %eax, "+instr.getDestination().toAsmCode());		    		 
			    	break;	
			default: //array in stack frame				    
					pw.println("movl "+ instr.getOp2().toAsmCode()+ ", %eax");
					pw.println("cltq");//eax sign extend to rax
					pw.println("movl " +Integer.toString(arraySize) + "(%rbp,%rax,4), %eax ");    						
					pw.println("movl %eax, "+instr.getDestination().toAsmCode());			    		 
    				break;	
    }
}

// WriteArray expr, dir, location deja expr en location
    public static void genWriteArrayAsmCode(TAInstructions instr){
    	ArrayLocation dest= (ArrayLocation) ((RefArrayLocation)instr.getDestination()).getLocation();
    	int arraySize= dest.getOffset()-(dest.getSize().getValue()*4);//calc the end of array. a[i] is acces as end(a)+ i*4   				
    	switch(dest.getOffset()){ 
    		case 0://array in static data segment		    			
	    			pw.println("movl "+ instr.getOp2().toAsmCode()+ ", %eax");	    				
					pw.println("cltq");//eax sign extend to rax
					pw.println("movl "+ instr.getOp1().toAsmCode()+ ", %ebx");//ebx has value for assign to array
					pw.println("movl %ebx, " +dest.getId()+"(,%rax,4)");		    		 
			    	break;	
			default: //array in stack frame				    
					pw.println("movl "+ instr.getOp2().toAsmCode()+ ", %eax");
					pw.println("cltq");//eax sign extend to rax
					pw.println("movl "+ instr.getOp1().toAsmCode()+ ", %ebx");//ebx has value for assign to array
					pw.println("movl %ebx, " +Integer.toString(arraySize) + "(%rbp,%rax,4)");    									    		 
    				break;	
    	}    		
	}

}
