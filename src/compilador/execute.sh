#!/bin/bash
jflex lexer.flex;
cup analizadorSintactico.cup;
export CLASSPATH=.:/usr/share/java/JFlex.jar:/usr/share/java/cup.jar;
javac ir/ast/*.java
javac ir/*.java
javac ir/semcheck/*.java
javac ir/intermediateCode/*.java
javac ir/error/*.java
javac ir/codeGeneration/*.java
javac -d . sym.java parser.java AnalizadorLexico.java
