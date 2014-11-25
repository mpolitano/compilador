#!/bin/bash
echo /-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-/
echo Se inicia la compilacion...
echo /-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-/
export CLASSPATH=.:/usr/share/java/JFlex.jar:/usr/share/java/cup.jar
jflex parser/lexer.flex
cup -destdir parser parser/analizadorSintactico.cup;
javac parser/*.java
javac ctds_pcr/ast/*.java
javac ctds_pcr/*.java
javac ctds_pcr/semcheck/*.java
javac ctds_pcr/intermediateCode/*.java
javac ctds_pcr/error/*.java
javac ctds_pcr/codeGeneration/*.java
javac Ctds.java

echo /-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-/
echo La compilacion ha finalizado
echo /-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-/



