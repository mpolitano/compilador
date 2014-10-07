#!/bin/bash
export CLASSPATH=.:/usr/share/java/JFlex.jar:/usr/share/java/cup.jar
jflex lexer.flex;
cup analizadorSintactico.cup;
javac ir/ast/*.java;
javac ir/semcheck/*.java;
javac ir/*.java;
javac ir/error/*.java;
javac ir/intermediateCode/*.java;
javac -d . sym.java parser.java AnalizadorLexico.java;