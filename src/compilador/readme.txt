Para ejectuar el programa
Necesitamos tener instalado Jflex y Cup.

Se ejecuta:
1º= jflex lexer.flex
2º= cup AnalizadorSintactico.cup
3º=  export CLASSPATH=.:/usr/share/java/JFlex.jar:/usr/share/java/cup.jar
4º= javac -d . sym.java parser.java AnalizadorLexico.java
5º= java parser

En la carpeta Prueba, hay algunas pruebas que hicimos para probar que andaba bien. La prueba if_error.ctds, devuelve error ya que el if no empieza con llave.

---SEGUNDA ENTREGA----
Para correrlos son necesarios los mismos pasos que la entrega anterior. 
Hay un script: runSemanticTest.sh que corre un bateria de casos de test que diseñamos para probar las funcionalidades del compilador hasta el análizis semantico correspondiente.

