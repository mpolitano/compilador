#!/bin/bash
export CLASSPATH=.:/usr/share/java/JFlex.jar:/usr/share/java/cup.jar;
jflex lexer.flex;
cup analizadorSintactico.cup;
javac -d . sym.java parser.java AnalizadorLexico.java
