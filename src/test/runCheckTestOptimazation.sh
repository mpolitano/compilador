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

comp=Ctds


echo "///////////////////////// TESTS SINTANCTO CORRECTOS ////////////////////////////////////////" 
files=`ls ../test/*.ctds`


for file in $files ; do 
	echo "---------------- Test $file ... -------------------" 
        java  $comp $file ../test/a.s -o
	echo "---------------------------------------------------" 
        echo "  "
        echo "  "
done



exit 0

