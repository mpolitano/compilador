Para ejectuar el programa
Necesitamos tener instalado Jflex y Cup.

Se ejecuta:
1º= jflex lexer.flex
2º= cup AnalizadorSintactico.cup
3º= export CLASSPATH:./usr/share/java/JFlex.jar:/usr/share/java/cup.jar
4º= javac -d . sym.java parser.java AnalizadorLexico.java
5º= java parserº

En la carpeta Prueba, hay algunas pruebas que hicimos para probar que andaba bien. La prueba if_error.ctds, devuelve error ya que el if no empieza con llave.


