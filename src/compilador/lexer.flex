/* --------------------------Codigo de Usuario----------------------- */
package compilador;

import java_cup.runtime.*;
import java.io.Reader;
import static compilador.Token.*;
      
%% //inicio de opciones
   
/* ------ Seccion de opciones y declaraciones de JFlex -------------- */  
   
/* 
    Cambiamos el nombre de la clase del analizador a Lexer
*/
%class AnalizadorLexico

/*
    Activar el contador de lineas, variable yyline
    Activar el contador de columna, variable yycolumn
*/
%line
%column
    
/* 
   Activamos la compatibilidad con Java CUP para analizadores
   sintacticos(parser)
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
   

   
/*  Un salto de linea es un \n  */
Salto = \n
   

Espacio= {Salto} | [ \t ]
   

Digito = [0-9]
Float= Digito
Letras = [a-zA-Z_]
ComentarioLinea= "//"

<YYINITIAL> {
    /* Regresa que el token la palabra reservada. */

    "boolean" { return symbol(sym.BOOLEAN);  System.out.println (yytext())}
    "break" { return symbol(sym.BREAK);  System.out.println (yytext())}
    "class" { return symbol(sym.CLASS);  System.out.println (yytext())}
    "continue" { return symbol(sym.CONTINUE);  System.out.println (yytext())}
    "else" { return symbol(sym.ELSE);  System.out.println (yytext())}
    "false" { return symbol(sym.FALSE);  System.out.println (yytext())}
    "float" { return symbol(sym.FLOAT);  System.out.println (yytext())}
    "for" { return symbol(sym.FOR);  System.out.println (yytext())}
    "if" { return symbol(sym.IF);  System.out.println (yytext())}
    "return" { return symbol(sym.RETURN);  System.out.println (yytext())}
    "true" { return symbol(sym.TRUE);  System.out.println (yytext())}
    "void" { return symbol(sym.VOID);  System.out.println (yytext())}
    "while" { return symbol(sym.WHILE);  System.out.println (yytext())}

      /* Comentarios, VER. */
    {ComentarioLinea} {System.out.println (yytext())}
    {Espacio} {System.out.println (yytext())}


    /* Op_Aritemetico. */

    "+" { return symbol(sym.SUMA);  System.out.println (yytext())}
    "-" { return symbol(sym.RESTA);  System.out.println (yytext())}
    "*" { return symbol(sym.MULT);  System.out.println (yytext())}
    "/" { return symbol(sym.MULT);  System.out.println (yytext())}
    "%" { return symbol(sym.MOD);  System.out.println (yytext())}

    //Operador Relacional
    "<" { return symbol(sym.MENOR);  System.out.println (yytext())}
    ">" { return symbol(sym.MAYOR);  System.out.println (yytext())}
    ">=" { return symbol(sym.MAYORIG);  System.out.println (yytext())}
    "<=" { return symbol(sym.MENORIG);  System.out.println (yytext())}
    
    //Operador relacional
    "==" { return symbol(sym.IGUALDAD);  System.out.println (yytext())}  
    "!=" { return symbol(sym.DISTINTO);  System.out.println (yytext())}


    //Asignaciones
    "=" { return symbol(sym.ASIG);  System.out.println (yytext())}  
    "=+" { return symbol(sym.ASIGSUMA);  System.out.println (yytext())}
    "=-" { return symbol(sym.ASIGSUMA);  System.out.println (yytext())}

    //Condicionales
    "&&" { return symbol(sym.AND);  System.out.println (yytext())}
    "||" { return symbol(sym.OR);  System.out.println (yytext())}
     "!" { return symbol(sym.NEG);  System.out.println (yytext())}

    //Delimitadores
    "(" { return symbol(sym.PARENIZQ);  System.out.println (yytext())}
    ")" { return symbol(sym.PARENDER);  System.out.println (yytext())}
    ";" { return symbol(sym.PUNTOCOMA);  System.out.println (yytext())}
    "," { return symbol(sym.COMA);  System.out.println (yytext())}

    //Tipos
    "int" { return symbol(sym.INT);  System.out.println (yytext())}
    "float" { return symbol(sym.FLOAT);  System.out.println (yytext())}
    "boolean" { return symbol(sym.BOOLEAN);  System.out.println (yytext())}

    //Llaves.
    "{" { return symbol(sym.LLAB);  System.out.println (yytext())}
    "}" { return symbol(sym.LLCE);  System.out.println (yytext())}
    "]" { return symbol(sym.CORAB);  System.out.println (yytext())}
    "[" { return symbol(sym.CORCER);  System.out.println (yytext())}




 //Identificador  
{Letra}({Letra}|{Digito})* { return symbol(sym.ID);  System.out.println (yytext())}

//Literales
{Digito}{Digito}* {return symbol(sym.ENTERO);  System.out.println (yytext())}
{Digito}{Digito}*"."{Digito}{Digito}* {return symbol(sym.FLOAT);  System.out.println (yytext())}
["]{Letras}*["] {return symbol(sym.STRING);  System.out.println (yytext())}
}   

.   { System.out.println ("ERROR"}                   
                           

