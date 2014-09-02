import java_cup.runtime.*;
import java.io.Reader;
      
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
ComentarioBloque= "/*"(.|\n)*"*/"
%%

<YYINITIAL> {
    /* Regresa que el token la palabra reservada. */

    "break" {   return symbol(sym.BREAK);}
    "class" {System.out.println(yytext()); return  symbol(sym.CLASS);   }
    "continue" {System.out.println(yytext()); return  symbol(sym.CONTINUE);   }
    "else" {System.out.println(yytext()); return  symbol(sym.ELSE);   }
    "false" {System.out.println(yytext()); return  symbol(sym.FALSE);   }
    "for" {System.out.println(yytext()); return  symbol(sym.FOR);   }
    "if" {System.out.println(yytext()); return  symbol(sym.IF);   }
    "return" {System.out.println(yytext()); return  symbol(sym.RETURN);   }
    "true" {System.out.println(yytext()); return  symbol(sym.TRUE);   }
    "void" {System.out.println(yytext()); return  symbol(sym.VOID);   }
    "while" {System.out.println(yytext()); return  symbol(sym.WHILE);   }
    "externinvk" {System.out.println(yytext()); return  symbol(sym.EXTERNINVK);   }

    // Comentarios 
    {ComentarioLinea} { }
    {Espacio} { }
    {ComentarioBloque} {}

    //Op_Aritemetico. 
    "+" {System.out.println(yytext()); return  symbol(sym.PLUS);   }
    "-" {System.out.println(yytext()); return  symbol(sym.SUB);   }
    "*" {System.out.println(yytext()); return  symbol(sym.MULT);   }
    "/" {System.out.println(yytext()); return  symbol(sym.DIVI);   }
    "%" {System.out.println(yytext()); return  symbol(sym.MOD);   }

    //Operador Relacional
    "<" {System.out.println(yytext()); return  symbol(sym.LOWER);   }
    ">" {System.out.println(yytext()); return  symbol(sym.HIGHER);   }
    ">=" {System.out.println(yytext()); return  symbol(sym.HIGHEREQUAL);   }
    "<=" {System.out.println(yytext()); return  symbol(sym.LOWEREQUAL);   }
    
    //Operador relacional
    "==" {System.out.println(yytext()); return  symbol(sym.EQUAL);   }  
    "!=" {System.out.println(yytext()); return  symbol(sym.DIFFERENT);   }

    //Asignaciones
    "=" {System.out.println(yytext()); return  symbol(sym.ASSIG);   }  
    "+=" {System.out.println(yytext()); return  symbol(sym.ASSIGPLUS);   }
    "-=" {System.out.println(yytext()); return  symbol(sym.ASSIGSUB);   }

    //Condicionales
    "&&" {System.out.println(yytext()); return  symbol(sym.AND);   }
    "||" {System.out.println(yytext()); return  symbol(sym.OR);   }
     "!" {System.out.println(yytext()); return  symbol(sym.NEG);   }

    //Delimitadores
    "(" {System.out.println(yytext()); return  symbol(sym.PARENIZQ);   }
    ")" {System.out.println(yytext()); return  symbol(sym.PARENDER);   }
    ";" {System.out.println(yytext()); return  symbol(sym.PUNTOCOMA);   }
    "," {System.out.println(yytext()); return  symbol(sym.COMA);   }

    //Tipos
    "int" {System.out.println(yytext()); return  symbol(sym.RESERV_INT);   }
    "float" {System.out.println(yytext()); return  symbol(sym.RESERV_FLOAT);   }
    "boolean" {System.out.println(yytext()); return  symbol(sym.RESERV_BOOLEAN);   }

    //Llaves.
    "{" {System.out.println(yytext()); return  symbol(sym.LLAB);   }
    "}" {System.out.println(yytext()); return  symbol(sym.LLCER);   }
    "[" {System.out.println(yytext()); return  symbol(sym.CORAB);   }
    "]" {System.out.println(yytext()); return  symbol(sym.CORCER);   }

    //Identificador  
    {Alpha} ({Alpha}|{Digit})* {System.out.println(yytext()); return  symbol(sym.ID);   }

    //Literales
    {Digit}{Digit}* {System.out.println(yytext()); return symbol(sym.INT);   }
    {Digit} {Digit}* "." {Digit} {Digit}* {System.out.println(yytext()); return symbol(sym.FLOAT);   }
    "\""{ASCII}*"\"" {System.out.println(yytext()); return symbol(sym.STRING);   }
  
    //Otro es Error
    .   { System.out.println ("ERROR");}                   
}
                          

