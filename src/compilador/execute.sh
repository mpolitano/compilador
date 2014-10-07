#!/bin/bash
jflex lexer.flex;
cup analizadorSintactico.cup;
export CLASSPATH=.:/usr/share/java/JFlex.jar:/usr/share/java/cup.jar;
javac -d . sym.java parser.java AnalizadorLexico.java
