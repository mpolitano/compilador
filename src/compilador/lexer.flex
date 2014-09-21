import java_cup.runtime.*;
import java.io.Reader;
import ir.ast.*;      
%% //inicio de opciones
      
/* 
    Nombre de la clase .java que crea
*/
%class AnalizadorLexico

/*
    Activar el contador de lineas, variable yyline
    Activar el contador de columna, variable yycolumn
*/
%line
%column
    
/* 
   Activamos la compatibilidad con Java CUP
*/
%cup
   
/*
    Declaraciones

    El codigo entre %{  y %} sera copiado integramente en el 
    analizador generado.
*/
%{    
    /*  Generamos un java_cup.Symbol para guardar el tipo de token 
        encontrado */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    /* Generamos un Symbol para el tipo de token encontrado 
       junto con su valor */
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}
    
Espacio= [ \t\r\n ] 
Digit = [0-9]
Alpha = [a-zA-Z_]
ASCII = [^"\""] //Todos los caracteres del codigo ASCII que consideramos validos.
ComentarioLinea= "//".*\n 
%state COMENTARIO
%%

<YYINITIAL> {
    /* Regresa que el token la palabra reservada. */

    "break" {   return symbol(sym.BREAK, new BreakStmt(yyline,yycolumn));}
    "class" {  return  symbol(sym.CLASS);   }
    "continue" {  return  symbol(sym.CONTINUE,new ContinueStmt(yyline,yycolumn));   }
    "else" {  return  symbol(sym.ELSE);   }
    "false" {  return  symbol(sym.FALSE, new BooleanLiteral(false,yyline,yycolumn));   }
    "for" {  return  symbol(sym.FOR); }
    "if" {  return  symbol(sym.IF);   }
    "return" {  return  symbol(sym.RETURN);   }
    "true" {  return  symbol(sym.TRUE,new BooleanLiteral(true,yyline,yycolumn));   }
    "while" {  return  symbol(sym.WHILE);   }
    "externinvk" {  return  symbol(sym.EXTERNINVK);   }

    // Comentarios 
    {ComentarioLinea} { }

    //Ignoro espacios
    {Espacio} { }


    //Op_Aritemetico. 
    "+" {  return  symbol(sym.PLUS, BinOpType.PLUS);   }
    "-" {  return  symbol(sym.SUB, BinOpType.MINUS);   }
    "*" {  return  symbol(sym.MULT, BinOpType.MULTIPLY);   }
    "/" {  return  symbol(sym.DIVI,BinOpType.DIVIDE);   }
    "%" {  return  symbol(sym.MOD,BinOpType.MOD);   }

    //Operador Relacional
    "<" {  return  symbol(sym.LOWER,BinOpType.LE);   }
    ">" {  return  symbol(sym.HIGHER,BinOpType.GE);   }
    ">=" {  return  symbol(sym.HIGHEREQUAL,BinOpType.GEQ);   }
    "<=" {  return  symbol(sym.LOWEREQUAL,BinOpType.LEQ);   }
    
    //Operador relacional
    "==" {  return  symbol(sym.EQUAL,BinOpType.CEQ);   }  
    "!=" {  return  symbol(sym.DIFFERENT,BinOpType.NEQ);   }

    //Asignaciones
    "=" {  return  symbol(sym.ASSIG, AssignOpType.ASSIGN);}  
    "+=" {  return  symbol(sym.ASSIGPLUS,AssignOpType.INCREMENT);   }
    "-=" {  return  symbol(sym.ASSIGSUB,AssignOpType.DECREMENT);   }

    //Condicionales
    "&&" {  return  symbol(sym.AND,BinOpType.AND);   }
    "||" {  return  symbol(sym.OR,BinOpType.OR);   }
     "!" {  return  symbol(sym.NEG,UnaryOpType.NOT);   }

    //Delimitadores
    "(" {  return  symbol(sym.PARENIZQ);   }
    ")" {  return  symbol(sym.PARENDER);   }
    ";" {  return  symbol(sym.PUNTOCOMA, new SecStmt(yyline,yycolumn));}
    "," {  return  symbol(sym.COMA);   }

    //Tipos
    "int" {Type t=Type.INT; return symbol(sym.RESERV_INT,t);}
    "float" {Type t=Type.FLOAT; return symbol(sym.RESERV_FLOAT,t);}
    "boolean" {Type t=Type.BOOLEAN; return symbol(sym.RESERV_BOOLEAN,t);}
    "void" {  Type t= Type.VOID; return  symbol(sym.VOID,t); }
    
    //Llaves.
    "{" {  return  symbol(sym.LLAB);   }
    "}" {  return  symbol(sym.LLCER);   }
    "[" {  return  symbol(sym.CORAB);   }
    "]" {  return  symbol(sym.CORCER);   }

    //Identificador  
    {Alpha} ({Alpha}|{Digit})* {VarLocation value= new VarLocation(yytext(),yyline,yycolumn,-1);
                                return symbol(sym.ID,value);   
                                }

    //Literales
    {Digit}{Digit}* {  return symbol(sym.INT,new IntLiteral(yytext(),yyline,yycolumn));   }
    {Digit} {Digit}* "." {Digit} {Digit}* {  return symbol(sym.FLOAT, new FloatLiteral(yytext(),yyline,yycolumn));   }
    "\""{ASCII}*"\"" {  return symbol(sym.STRING, new StringLiteral(yytext(),yyline,yycolumn));  }
    "/*"        {yybegin(COMENTARIO);     }

      .   {   System.out.println ("Caracter ilegal!!!   " + yytext() + " en linea " + yyline + " columna " + yycolumn);
      }    
}

    <COMENTARIO> {
         {Espacio}  {}
        "*/"       {yybegin(YYINITIAL); }
        .           {}

     }
                     
                         
