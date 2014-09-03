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
%state COMENTARIO
%%

<YYINITIAL> {
    /* Regresa que el token la palabra reservada. */

    "break" {   return symbol(sym.BREAK);}
    "class" {  return  symbol(sym.CLASS);   }
    "continue" {  return  symbol(sym.CONTINUE);   }
    "else" {  return  symbol(sym.ELSE);   }
    "false" {  return  symbol(sym.FALSE);   }
    "for" {  return  symbol(sym.FOR);   }
    "if" {  return  symbol(sym.IF);   }
    "return" {  return  symbol(sym.RETURN);   }
    "true" {  return  symbol(sym.TRUE);   }
    "void" {  return  symbol(sym.VOID);   }
    "while" {  return  symbol(sym.WHILE);   }
    "externinvk" {  return  symbol(sym.EXTERNINVK);   }

    // Comentarios 
    {ComentarioLinea} { }

    //Ignoro espacios
    {Espacio} { }

    //Op_Aritemetico. 
    "+" {  return  symbol(sym.PLUS);   }
    "-" {  return  symbol(sym.SUB);   }
    "*" {  return  symbol(sym.MULT);   }
    "/" {  return  symbol(sym.DIVI);   }
    "%" {  return  symbol(sym.MOD);   }

    //Operador Relacional
    "<" {  return  symbol(sym.LOWER);   }
    ">" {  return  symbol(sym.HIGHER);   }
    ">=" {  return  symbol(sym.HIGHEREQUAL);   }
    "<=" {  return  symbol(sym.LOWEREQUAL);   }
    
    //Operador relacional
    "==" {  return  symbol(sym.EQUAL);   }  
    "!=" {  return  symbol(sym.DIFFERENT);   }

    //Asignaciones
    "=" {  return  symbol(sym.ASSIG);   }  
    "+=" {  return  symbol(sym.ASSIGPLUS);   }
    "-=" {  return  symbol(sym.ASSIGSUB);   }

    //Condicionales
    "&&" {  return  symbol(sym.AND);   }
    "||" {  return  symbol(sym.OR);   }
     "!" {  return  symbol(sym.NEG);   }

    //Delimitadores
    "(" {  return  symbol(sym.PARENIZQ);   }
    ")" {  return  symbol(sym.PARENDER);   }
    ";" {  return  symbol(sym.PUNTOCOMA);   }
    "," {  return  symbol(sym.COMA);   }

    //Tipos
    "int" {  return  symbol(sym.RESERV_INT);   }
    "float" {  return  symbol(sym.RESERV_FLOAT);   }
    "boolean" {  return  symbol(sym.RESERV_BOOLEAN);   }

    //Llaves.
    "{" {  return  symbol(sym.LLAB);   }
    "}" {  return  symbol(sym.LLCER);   }
    "[" {  return  symbol(sym.CORAB);   }
    "]" {  return  symbol(sym.CORCER);   }

    //Identificador  
    {Alpha} ({Alpha}|{Digit})* {  return  symbol(sym.ID);   }

    //Literales
    {Digit}{Digit}* {  return symbol(sym.INT);   }
    {Digit} {Digit}* "." {Digit} {Digit}* {  return symbol(sym.FLOAT);   }
    "\""{ASCII}*"\"" {  return symbol(sym.STRING);   }
    "/*"        {yybegin(COMENTARIO);     }

      .   {   System.out.println ("Caracter ilegal!!!   " + yytext() + " en linea " + yyline + " columna " + yycolumn);
      throw new Error();}    
}

<COMENTARIO> {
     {Espacio}  {}
    "*/"       {yybegin(YYINITIAL); }
    .           {}

 }
                     
                         
