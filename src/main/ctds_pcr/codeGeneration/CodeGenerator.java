/**
*	Class that generate assembly x86-64 code from a Three Adress Code
*	@author: Cornejo-Politano-Raverta
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
			case MinusF: genMinusFAsmCode(instr); break;
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

	/** method that write name's program*/
	private static void genProgramAsmCode(TAInstructions instr){
		pw.println(".file \""+instr.getOp1().toString()+ "\"");
	}

	/** method that initial the blocks*/
	private static void genBlockBeginAsmCode(TAInstructions instr){
		if (frameOptimization){
			IntLiteral offset= (IntLiteral)instr.getOp1();
				switch(offset.getValue()%16){//for 16 bytes aling stack 
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

	/** method that initial the blocks*/
	private static void genBlockEndAsmCode(TAInstructions instr){
		if (frameOptimization){
			IntLiteral offset= (IntLiteral)instr.getOp1();
			pw.println("addq "+offset.toAsmCode()+" ,%rsp");
		}
	}

	/** method that initial the blocks*/
	private static void genLocationDeclAsmCode(TAInstructions instr){
		Location l= (Location) instr.getOp1();
		switch (l.getOffset()){
			case 0: if (l instanceof VarLocation) {pw.println(".comm " + l.getId() +" ,4,4");break;}	
					else{ int size= ((ArrayLocation) l).getSize().getValue() * 4; pw.println(".comm " + l.getId() +" ," + size +",32");break;} 
		}	
	}

	/** method that initializes a method, this declared enter a multiple of 16 if use frameOptimitazion*/
	private static void genMethodDeclAsmCode(TAInstructions instr){
		MethodLocation m= (MethodLocation) instr.getOp1();
		pw.println(".text");
		pw.println(".globl "+ m.getId());
		pw.println(m.getId() + ":");
		/*aling frame, remeber that the inovcator can desalign frame */
		int numEnter= (m.amountLocalLocation()*4) + (m.amountStackLocation()*4);
		int suma;
			if (!frameOptimization){
				switch(numEnter%16){
					case 4: suma=numEnter+12; //for align frame in 16 byte
							suma=suma -(m.amountStackLocation()*4);//sub bytes used by invocator
							pw.println("enter $"+ suma+",$0");break;
					case 8: suma=numEnter+8; //for align frame in 16 byte
							suma=suma -(m.amountStackLocation()*4);//sub bytes used by invocator
							pw.println("enter $"+ suma+",$0");break;
					case 12:suma=numEnter+4;//for align frame in 16 byte
							suma=suma -(m.amountStackLocation()*4);//sub bytes used by invocator
							pw.println("enter $"+ suma+",$0");break;
					case 0: numEnter=numEnter -(m.amountStackLocation()*4);//for align frame in 16 byte
							pw.println("enter $"+ numEnter+",$0");break; 	
				}
			} 
			else{
				numEnter= m.amountStackLocation()*4;
				switch(numEnter%16){ //for align frame when the invocator desaling frame
					case 4: suma=12; //for align frame in 16 byte
							pw.println("enter $"+ suma+",$0");break;
					case 8: suma=8; //for align frame in 16 byte
							pw.println("enter $"+ suma+",$0");break;
					case 12:suma=4;//for align frame in 16 byte
							pw.println("enter $"+ suma+",$0");break;
					case 0: pw.println("enter $0,$0");break; 
				} 
				
			}
	}

	/** method that close a method*/
	private static void genMethodDeclEndAsmCode(TAInstructions instr){
		MethodLocation m= (MethodLocation) instr.getOp1();
		int numEnter;
		if (m.amountLocalLocation()>0) 
			pw.println("mov %rbp, %rsp");//pop local location
		//make a epilogo
		pw.println("leave");
		pw.println("ret");

	}

	/** method that call one LabelExpr*/
	private static void genCallAsmCode(TAInstructions instr){
		LabelExpr m= (LabelExpr)instr.getOp1();
		pw.println("call "+ m.toString());
	}

	/** Method for calls a LabelExpr and retrieves the return of this*/
	private static void genCallWithReturnAsmCode(TAInstructions instr){
		LabelExpr m= (LabelExpr)instr.getOp1();
		pw.println("call "+ m.toString());
		RefLocation l= instr.getDestination(); 
		//code for return in location
		switch(l.getType()){
			case INT: case BOOLEAN: pw.println("movl %eax, "+l.toAsmCode()); break;
			case FLOAT: pw.println("movss %xmm0, "+l.toAsmCode()); break;

		}
		
	}
	
	/** Method for Assign*/	
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

	/** Method for change the sign the int*/	
	private static void genMinusIAsmCode(TAInstructions instr){
		Expression expr= instr.getOp1();
		RefLocation l= instr.getDestination();
			pw.println("movl "+expr.toAsmCode()+", %eax"); //mov op1 to eax to do an arithmetic operation
			pw.println("negl %eax"); //denies the number
			pw.println("movl %eax, "+l.toAsmCode()); //mov the result to destination
	}

	/** Method for change the sign the float.*/	
	private static void genMinusFAsmCode(TAInstructions instr){
		Expression expr= instr.getOp1();
		RefLocation l= instr.getDestination();
			pw.println("movl $0, %eax");
			pw.println("cvtsi2ss %eax , %xmm1 ");
			pw.println("movss "+ expr.toAsmCode()+", %xmm0");
			pw.println("subss %xmm1 , %xmm0");
			pw.println("movss %xmm0, "+l.toAsmCode()); //mov the result to destination
	}

	/** Method for add a integer*/
	private static void genAddIAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %eax") ; //mov op1 to eax to do an arithmetic operation
			pw.println("addl "+expr2.toAsmCode()+", %eax");	//op2 plus eax
			pw.println("movl %eax, "+ l.toAsmCode());	//move the result to destination
	}

	/** Method for substract a integer*/
	private static void genSubIAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %eax"); //mov op1 to eax to do an arithmetic operation
			pw.println("subl "+expr2.toAsmCode()+", %eax");	//op2 - eax
			pw.println("movl %eax, "+ l.toAsmCode()) ; //move the result to destination
	}

	/** Method for multiplication integers*/
	private static void genMultIAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %eax");	//mov op1 to eax to do an arithmetic operation
			pw.println("imull "+expr2.toAsmCode()+", %eax"); //op2 * eax
			pw.println("movl %eax, "+l.toAsmCode());	//move the result to destination
	}

	/** Method for divide integers*/	
	private static void genDivIAsmCode(TAInstructions instr){
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

	/** Method for module*/	
	private static void genModAsmCode(TAInstructions instr){
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

	/** Method to compare for less in integers. op1<op2*/	
	private static void genLesIAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %eax");	//mov op1 to eax to compare
			pw.println("movl "+expr2.toAsmCode()+", %edx"); //mov op2 to edx to compare
			pw.println("cmpl %edx, %eax");	//compare edx and eax
			pw.println("jl .L"+numberLabel); //check if edx is less than eax, and move the result to eax (1 true, 0 false)
			pw.println("movl $0, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
			pw.println("movl $1, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode()); //move the result to destination
			numberLabel++;
	}

	/** Method to compare for Greather in integers. op1>op2*/	
	private static void genGrtIAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %eax"); //mov op1 to eax to compare
			pw.println("movl "+expr2.toAsmCode()+", %edx"); //mov op2 to edx to compare
			pw.println("cmpl %edx, %eax"); //compare edx and eax
			pw.println("jg .L"+numberLabel); //check if edx is greater than eax and move result to eax (1 true, 0 false)
			pw.println("movl $0, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
			pw.println("movl $1, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode()); //move the result to destination
			numberLabel++;
	}

	/** Method to compare for equal in integers. op1==op2*/	
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

	/** Method to compare for equal in floats. op1==op2*/
	private static void genEqualFAsmCode(TAInstructions instr){
		Expression expr1=instr.getOp1();
		Expression expr2=instr.getOp2();
		RefLocation l=instr.getDestination();
		pw.println("movss "+expr1.toAsmCode()+", %xmm0"); //mov op1 to xmm0 to compare
		pw.println("movss "+expr2.toAsmCode()+", %xmm1"); //mov op2 to xmm1 to compare
		pw.println("ucomiss %xmm1, %xmm0"); //compare xmm1 and xmm0
		pw.println("je .L"+numberLabel);	//check if xmm0 is equal to xmm1 and move result to eax (1 true, 0 false)
		pw.println("movl $0, %eax");
		pw.println("jmp .Continue"+numberLabel);
		pw.println(".L"+numberLabel+":");
		pw.println("movl $1, %eax");
		pw.println(".Continue"+numberLabel+":");
		pw.println("movl %eax, "+l.toAsmCode()); //move the result to destination
		numberLabel++;
		
	}

	/** Method to compare for different in integer. op1!=op2*/
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
		
	/** Method to compare for different in floats. op1!=op2*/	
	private static void genDifFAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l= instr.getDestination();
		pw.println("movss "+expr1.toAsmCode()+", %xmm0"); //mov op1 to xmm0 to compare
		pw.println("movss "+expr2.toAsmCode()+", %xmm1"); //mov op2 to xmm1 to compare
		pw.println("ucomiss %xmm1, %xmm0"); //compare xmm1 and xmm0
		pw.println("jne .L"+numberLabel);	//check if xmm0 is different to xmm1 and move result to eax (1 true, 0 false)
		pw.println("movl $0, %eax");
		pw.println("jmp .Continue"+numberLabel);
		pw.println(".L"+numberLabel+":");
		pw.println("movl $1, %eax");
		pw.println(".Continue"+numberLabel+":");
		pw.println("movl %eax, "+l.toAsmCode()); //move the result to destination
		numberLabel++;
	}

	/** Method to compare for greather equals in integer. op1>=op2*/	
	private static void genGEIAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l= instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %eax"); //mov op1 to eax to compare
			pw.println("movl "+expr2.toAsmCode()+", %edx"); //mov op2 to edx to compare
			pw.println("cmpl %edx, %eax"); //compare edx and eax
			pw.println("jge .L"+numberLabel); //check if edx is greater or equal than eax and move result to eax (1 true, 0 false)
			pw.println("movl $0, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
			pw.println("movl $1, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode()); //move the result to destination
			numberLabel++;
	}

	/** Method to compare for less equals in integer. op1<=op2 */
	private static void genLEIAsmCode(TAInstructions instr){
		Expression expr1 = instr.getOp1();
		Expression expr2 = instr.getOp2();
		RefLocation l= instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %eax"); //mov op1 to eax to compare
			pw.println("movl "+expr2.toAsmCode()+", %edx"); //mov op2 to edx to compare
			pw.println("cmpl %edx, %eax"); //compare edx and eax
			pw.println("jle .L"+numberLabel); //check if edx is less or equal than eax and move result to eax (1 true, 0 false)
			pw.println("movl $0, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
			pw.println("movl $1, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode()); //move the result to destination
			numberLabel++;
	}

	/** Method for add a floats*/
	public static void genAddFAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movss "+expr1.toAsmCode()+", %xmm0") ; 
			pw.println("addss "+expr2.toAsmCode()+", %xmm0");
			pw.println("movss %xmm0, "+ l.toAsmCode());	
	}

	/** Method for substract a floats*/
	public static void genSubFAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movss "+expr1.toAsmCode()+", %xmm0") ; 
			pw.println("subss "+expr2.toAsmCode()+", %xmm0");
			pw.println("movss %xmm0, "+ l.toAsmCode());	
	}

	/** Method for multiplication a floats*/
	public static void genMultFAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movss "+expr1.toAsmCode()+", %xmm0") ; 
			pw.println("mulss "+expr2.toAsmCode()+", %xmm0");
			pw.println("movss %xmm0, "+ l.toAsmCode());	
	}

	/** Method for divide a floats*/
	public static void genDivFAsmCode(TAInstructions instr){
		Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movss "+expr1.toAsmCode()+", %xmm0") ; 
			pw.println("divss "+expr2.toAsmCode()+", %xmm0");
			pw.println("movss %xmm0, "+ l.toAsmCode());	
	}

	/** Method to compare for less in floats. op1<op2*/
    public static void genLesFAsmCode(TAInstructions instr){
    	Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movss "+expr1.toAsmCode()+", %xmm0");//mov op1 to xmm0 to compare
			pw.println("movss "+expr2.toAsmCode()+", %xmm1");//mov op2 to xmm1 to compare
			pw.println("comiss %xmm1, %xmm0");//compare xmm1 and xmm0
			pw.println("jb .L"+numberLabel); //check if xmm1 is less than xmm0 and move result to eax (1 true, 0 false)
			pw.println("movl $0, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
			pw.println("movl $1, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode());// %xmm1<%xmm0
			numberLabel++;
    }

    /** Method to compare for greather in floats. op1>op2*/
    public static void genGrtFAsmCode(TAInstructions instr){
    	Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movss "+expr1.toAsmCode()+", %xmm0");//mov op1 to xmm0 to compare
			pw.println("movss "+expr2.toAsmCode()+", %xmm1");//mov op2 to xmm1 to compare
			pw.println("comiss %xmm1, %xmm0");//compare xmm1 and xmm0
			pw.println("ja .L"+numberLabel);//check if xmm1 is greather than xmm0 and move result to eax (1 true, 0 false)
			pw.println("movl $0, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
			pw.println("movl $1, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode());// %xmm1>%xmm0
			numberLabel++;
    }

    /** Method to compare for less equal in floats. op1<=op2*/
    public static void genLEFAsmCode(TAInstructions instr){
    	Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movss "+expr1.toAsmCode()+", %xmm0");//mov op1 to xmm0 to compare
			pw.println("movss "+expr2.toAsmCode()+", %xmm1");//mov op2 to xmm1 to compare
			pw.println("comiss %xmm1, %xmm0");//compare xmm1 and xmm0
			pw.println("jbe .L"+numberLabel);//check if xmm1 is less or equal than xmm0 and move result to eax (1 true, 0 false)
			pw.println("movl $0, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
			pw.println("movl $1, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode());// %xmm1<%xmm0
			numberLabel++;
    }

    /** Method to compare for greather equal in floats. op1>=op2*/
    public static void genGEFAsmCode(TAInstructions instr){
    	Expression expr1= instr.getOp1();
		Expression expr2= instr.getOp2();
		RefLocation l=instr.getDestination();
			pw.println("movss "+expr1.toAsmCode()+", %xmm0");//mov op1 to xmm0 to compare
			pw.println("movss "+expr2.toAsmCode()+", %xmm1");//mov op2 to xmm1 to compare
			pw.println("comiss %xmm1, %xmm0");//compare xmm1 and xmm0
			pw.println("jae .L"+numberLabel);//check if xmm1 is greather or equal than xmm0 and move result to eax (1 true, 0 false)
			pw.println("movl $0, %eax");
			pw.println("jmp .Continue"+numberLabel);
			pw.println(".L"+numberLabel+":");
			pw.println("movl $1, %eax");
			pw.println(".Continue"+numberLabel+":");
			pw.println("movl %eax, "+l.toAsmCode());// %xmm1<%xmm0
			numberLabel++;
    }

	/**Method to the do AND between operator. op1 && op2 */
	private static void genAndAsmCode(TAInstructions instr){
		Expression expr1 = instr.getOp1();
		Expression expr2 = instr.getOp2();
		RefLocation l= instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %edx"); //mov op1 to edx to compare
			pw.println("movl "+expr2.toAsmCode()+", %eax"); //mov op2 to eax to compare
			pw.println("andl %edx, %eax"); //chek the add condition between edx and eax, the result will be in eax
			pw.println("movl %eax, "+l.toAsmCode()); //move the result to destination
	}

	/**Method to the do OR between operator. op1 || op2 */
	private static void genOrAsmCode(TAInstructions instr){
		Expression expr1 = instr.getOp1();
		Expression expr2 = instr.getOp2();
		RefLocation l= instr.getDestination();
			pw.println("movl "+expr1.toAsmCode()+", %edx"); //mov op1 to edx to compare
			pw.println("movl "+expr2.toAsmCode()+", %eax"); //mov op2 to eax to compare
			pw.println("orl %edx, %eax"); //chek the or condition between edx and eax, the result will be in eax
			pw.println("movl %eax, "+l.toAsmCode()); //move the result to destination
	}

	/**Method to the do NOT between operator. ! op1 */
	private static void genNotAsmCode(TAInstructions instr){
		Expression expr = instr.getOp1();
		RefLocation l= instr.getDestination();
			pw.println("movl "+expr.toAsmCode()+", %eax"); //mov op1 to eax 
			pw.println("xorl $1, %eax"); //changes every bit to its opposite
			pw.println("movl %eax, "+l.toAsmCode()); //move the result to destination
	}

	/**Method use for returns*/
	private static void genRetAsmCode(TAInstructions instr){
		Expression expr=instr.getOp1();
		switch(expr.getType()){
			case INT: case BOOLEAN: pw.println("movl "+ expr.toAsmCode() +", %eax" );break;//Int return in eax	
			case FLOAT:pw.println("movss "+expr.toAsmCode() + ", %xmm0"); break;	//float return in xmm0
		}
	}

	/**Method use for do push the register according the ofsset*/
	private static void genPushAsmCode(TAInstructions instr){
		Expression value= instr.getOp1();
		Location destination= (Location)instr.getOp2();
		switch (value.getType()){
			case FLOAT: if (destination.getOffset()>0 && destination.getOffset()<=8){	
							pw.println("movss "+ value.toAsmCode() +","+destination.toAsmCode()); 
						}else{
								pw.println("sub $4, %rsp");//save place for push param
								pw.println("movss "+ value.toAsmCode()+" ,%xmm8"); //use xmm8 as auxiliary register
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

	/**generate code for save param pass in register to method's frame.*/
	/*when value is pass as 1-6 param, alwalls stay in register (see convection call's x86-64 processor). 
	  but the set of register used depend of value's type. If float use xmm0, xmm1, xmm2, xmm3, xmm4,xmm5
	  otherwise(in this language int or string) use esi,edi,edx,ecx,r8,r9 .
	  So, select the correct assembly x86-64 intructions(movl or movss)
	*/
	private static void genSaveParamAsmCode(TAInstructions instr){		
		switch(instr.getOp1().getType()){
			case FLOAT:pw.println("movss "+ instr.getOp1().toAsmCode()+" , "+ instr.getDestination().toAsmCode());break;
			default:   pw.println("movl "+ instr.getOp1().toAsmCode()+" , "+ instr.getDestination().toAsmCode());
		}	
	}

	/**Method use for do pop the value*/
	private static void genPopAsmCode(TAInstructions instr){
		String value= ((IntLiteral) instr.getOp1()).toAsmCode();
		pw.println("add "+value+" , %rsp");
	}

	/**Method use for jump for true*/
	public static void genJTrueAsmCode(TAInstructions instr){
		pw.println("cmpl $1, " +instr.getOp1().toAsmCode()); //and with 1 for check if expression=true.
		pw.println("je "+ instr.getOp2().toString());//jump for not zero.
	}

	/**Method use for jump for false*/
	public static void genJFalseAsmCode(TAInstructions instr){
		pw.println("cmpl $0, " +instr.getOp1().toAsmCode()); //and with 1 for check if expression=true.
		pw.println("je "+ instr.getOp2().toString());//jump for zero.
	}

	/**Method use for jump*/
	public static void genJmpAsmCode(TAInstructions instr){
		pw.println("jmp "+ instr.getOp1().toString());	
	}

	/**Method use for put labelExpr */
	public static void genPutLabelAsmCode(TAInstructions instr){
		pw.println(instr.getOp1().toString()+":");	
	}

	/**Method use for call a extern method */
	public static void  genCallExternCode(TAInstructions instr){
		String m= instr.getOp1().toString();
		m=m.substring(1,m.length()-1);//method name without "". pre= m="nameMethod" pos= m=nameMethod
		pw.println("movl $0, %eax");//C convention
		pw.println("call "+ m.toString());	
	}

	/**Method use for call a extern method and this has return*/
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

	/**Method use for put string Literal when declared string or float*/
	public static void genPutStringLiteralCode(TAInstructions instr){
		pw.println(instr.getOp1().toString());
	}

	/** Method use for convert integer in float*/
    public static void genToFloatAsmCode(TAInstructions instr){
    	Expression expr1= instr.getOp1();
		RefLocation l=instr.getDestination();
		pw.println("movl "+ expr1.toAsmCode()+","+  "%edx");// Mov to edx, use its as auxiliary
		pw.println("cvtsi2ss %edx , % xmm3 "); //Convertion edx and mov to xmm3 
		pw.println("movss  %xmm3 , "+ l.toAsmCode()); //mov xmm3 to destination
    }


	/** ReadArray RefArrayLocation dir var */
	/** (-sizeArray*4 +posArray) %ebp
			0 
			1
			2 
			3
			Last Pos array
			... arrayOffset (ebp-...)
			ebp 
	*/
    public static void genReadArrayAsmCode(TAInstructions instr){
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

/** WriteArray expr, dir, location deja expr en location*/
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

}/**End class generate code*/
