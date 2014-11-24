#!/bin/bash
echo /-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-/
echo Se inicia la compilacion...
echo /-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-/
export CLASSPATH=.:/usr/share/java/JFlex.jar:/usr/share/java/cup.jar
jflex lexer.flex
cup analizadorSintactico.cup;
javac ir/ast/*.java
javac ir/*.java
javac ir/semcheck/*.java
javac ir/intermediateCode/*.java
javac ir/error/*.java
javac ir/codeGeneration/*.java
javac Ctds.java

echo /-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-/
echo La compilacion ha finalizado
echo /-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-/



