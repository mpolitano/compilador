#!/bin/bash

#
#                           ******************                           
#**************************** test semanticos **************************************
#**                         ******************                                    **
#**                                                                               **
#**Descripcion : Script que corre los casos de prueba definidos en la carpeta     **
#**              test_semantica. cada test tiene su propio main.                  **
#**                                                                               **
#***********************************************************************************   
echo "*********************************************************************************** "
echo "**                                                                               ** " 
echo "**                            Corriendo casos de prueba . . .                    ** "                
echo "**                                                                               ** "
echo "**                                                                               ** "
echo "*********************************************************************************** "
echo "     " 
echo "     " 

comp=$1


echo "///////////////////////// TESTS SEMANTICO CORRECTOS ////////////////////////////////////////" 
files=`ls ../test/testSemantic/test_correctos/*.ctds`


for file in $files ; do 
	echo "---------------- Test $file ... -------------------" 
        java  $comp $file ../test/a.s
	echo "---------------------------------------------------" 
        echo "  "
        echo "  "
done

echo "///////////////////////// TESTS SEMANTICO CON FALLAS ////////////////////////////////////////" 

files=`ls ../../test/testSemantic/test_errores/*.ctds`

for file in $files ; do 
	echo "---------------- Test $file ... -------------------" 
        java $comp $file ../test/a.s
	echo "---------------------------------------------------" 
        echo "  "
        echo "  "
done

exit 0

