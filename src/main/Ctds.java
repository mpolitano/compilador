/*------------------------------------------
                C-TDS-PCR COMPILER
Class for execute de C-TDS-PCR compiler proccess. 
The sintax for execute this is:
java Ctds pathCtdsProgram DestinationPath ( |-o|-pc|-pu|-f)
Where -o -pc -pu -f are flags for indicate the following optimization:
  empty= anyone optimization
  -pc= constant propagation
  -pu= prune unreachable code
  -f= frame optimization
  -o= all optimizations
------------------------------------------*/
import java_cup.runtime.*;
import java.io.FileReader;
import parser.*;
import ctds_pcr.ast.Program;
import ctds_pcr.ASTVisitor;
import ctds_pcr.semcheck.TypeCheckVisitor;
import ctds_pcr.semcheck.BreakContinueCheckVisitor;
import ctds_pcr.intermediateCode.TACVisitor;
import ctds_pcr.codeGeneration.*;
import ctds_pcr.optimization.*;
import java.lang.Exception;

public class Ctds{
    //Method for execute Ctds compiler
    public static void main(String[] args)throws Exception{
        try {
						boolean makeFrameOptimization=false;
            parser a = new parser(new AnalizadorLexico( new FileReader(args[0])));
            Program result = (Program)a.parse().value;

            
						//Type-Checked
            TypeCheckVisitor typeCheckVisitor= new TypeCheckVisitor();
						result.accept(typeCheckVisitor);
						//Check break and continue statement 
            BreakContinueCheckVisitor breakCheckVisitor= new BreakContinueCheckVisitor();
            result.accept(breakCheckVisitor);
            int sizeErrorType = typeCheckVisitor.getErrors().size();
            int sizeErrorBreak= breakCheckVisitor.getErrors().size();
						TACVisitor coding= new TACVisitor();						
            if (sizeErrorType==0 && sizeErrorBreak==0) {					
								if (args.length > 2){
										switch(args[2]){
											default: throw new Exception ("Bad Ctds compile invocation");	
											case "-o":	
											case "-pc": 
																ConstPropVisitor propConst= new ConstPropVisitor();                           
																result.accept(propConst);					
																if (args[2].equals("-pc")){ result.accept(coding); break;}	
                      case "-pu":
                                PruneUnreachableCode pruneUnreachableCode= new PruneUnreachableCode();
                                result.accept(pruneUnreachableCode);
                                if (args[2].equals("-pu")){ result.accept(coding); break;}  
                      case "-f": 																	 
  														 result.accept(coding);
								               FrameOptimization frameOptimizator= new FrameOptimization();
								               result.accept(frameOptimizator);
															 makeFrameOptimization=true;

							
									}
							 		CodeGenerator.generateCode(coding.getTAC(),args[1],makeFrameOptimization);									
								}else{
										 result.accept(coding);
				 						 CodeGenerator.generateCode(coding.getTAC(),args[1],makeFrameOptimization);														
										}           
							}else{
              if (sizeErrorBreak!=0){
                System.out.println(breakCheckVisitor.getErrors().toString());
              }else{
                if (sizeErrorType!=0){
                System.out.println(typeCheckVisitor.getErrors().toString());
              }else{
                System.out.println(breakCheckVisitor.getErrors().toString());
                System.out.println(typeCheckVisitor.getErrors().toString());
              }
            }
          }
        } catch (Exception ex) {
            ex.printStackTrace();
        }										            
  }
}
