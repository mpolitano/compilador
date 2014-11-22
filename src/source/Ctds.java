import java_cup.runtime.*;
import java.io.FileReader;
import ir.ast.Program;
import ir.ASTVisitor;
import ir.semcheck.TypeCheckVisitor;
import ir.semcheck.BreakContinueCheckVisitor;
import ir.intermediateCode.TACVisitor;
import ir.codeGeneration.*;
import ir.optimization.*;
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
