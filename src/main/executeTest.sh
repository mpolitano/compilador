#!/bin/bash
#Script for run all test
	
echo ----- TestCheckTest sin optimizaciones------
./ctds-PCR ../test/testCheckTest.ctds 
./../test/testCheckTest > ../test/testCheck.txt
diff ../test/testCheck.txt ../test/output_CheckTest.txt 
rm ../test/testCheck.txt ../test/testCheckTest

echo ----- TestCheckTest Con Todas optimizaciones------
./ctds-PCR ../test/testCheckTest.ctds -o
./../test/testCheckTest > ../test/testCheck.txt
diff ../test/testCheck.txt ../test/output_CheckTest.txt 
rm ../test/testCheck.txt ../test/testCheckTest

echo ----- TestCheckTest Con frame optimization------
./ctds-PCR ../test/testCheckTest.ctds -f
./../test/testCheckTest > ../test/testCheck.txt
diff ../test/testCheck.txt ../test/output_CheckTest.txt 
rm ../test/testCheck.txt ../test/testCheckTest

echo ----- TestCheckTest Con constante propagacion optimization------
./ctds-PCR ../test/testCheckTest.ctds -pc
./../test/testCheckTest > ../test/testCheck.txt
diff ../test/testCheck.txt ../test/output_CheckTest.txt 
rm ../test/testCheck.txt ../test/testCheckTest

echo ----- TestCheckTest Con codigo muerto optimization------
./ctds-PCR ../test/testCheckTest.ctds -pu
./../test/testCheckTest > ../test/testCheck.txt
diff ../test/testCheck.txt ../test/output_CheckTest.txt 
rm ../test/testCheck.txt ../test/testCheckTest

echo --------TEST MOODLE CTDS---------------
echo

echo ----- TestMoodle sin optimizaciones------
./ctds-PCR ../test/testMoodle/testCTDS.ctds 
cd ../test/testMoodle
./testCTDS> testCTDS.txt
diff output testCTDS.txt
rm testCTDS.txt testCTDS
cd ../../main

echo ----- TestMoodle con TODAS optimizaciones------
./ctds-PCR ../test/testMoodle/testCTDS.ctds -o
cd ../test/testMoodle
./testCTDS> testCTDS.txt
diff output testCTDS.txt
rm testCTDS.txt testCTDS
cd ../../main

echo ----- TestMoodle con constante progaciones optimizaciones------
./ctds-PCR ../test/testMoodle/testCTDS.ctds -pc
cd ../test/testMoodle
./testCTDS> testCTDS.txt
diff output testCTDS.txt
rm testCTDS.txt testCTDS
cd ../../main

echo ----- TestMoodle con codigo muerto optimizaciones------
./ctds-PCR ../test/testMoodle/testCTDS.ctds -pu
cd ../test/testMoodle
./testCTDS> testCTDS.txt
diff output testCTDS.txt
rm testCTDS.txt testCTDS
cd ../../main

echo ----- TestMoodle con frame optimizaciones------
./ctds-PCR ../test/testMoodle/testCTDS.ctds -f
cd ../test/testMoodle
./testCTDS> testCTDS.txt
diff output testCTDS.txt
rm testCTDS.txt testCTDS
cd ../../main

echo --------TEST MOODLE CTDS1---------------
echo

echo ----- TestMoodle sin optimizaciones------
./ctds-PCR ../test/testMoodle/testCTDS1.ctds 
cd ../test/testMoodle
./testCTDS1> testCTDS1.txt
diff output1 testCTDS1.txt
rm testCTDS1.txt testCTDS1
cd ../../main

echo ----- TestMoodle con TODAS optimizaciones------
./ctds-PCR ../test/testMoodle/testCTDS1.ctds -o
cd ../test/testMoodle
./testCTDS1> testCTDS1.txt
diff output1 testCTDS1.txt
rm testCTDS1.txt testCTDS1
cd ../../main

echo ----- TestMoodle con constante progaciones optimizaciones------
./ctds-PCR ../test/testMoodle/testCTDS1.ctds -pc
cd ../test/testMoodle
./testCTDS1> testCTDS1.txt
diff output1 testCTDS1.txt
rm testCTDS1.txt testCTDS1
cd ../../main

echo ----- TestMoodle con codigo muerto optimizaciones------
./ctds-PCR ../test/testMoodle/testCTDS1.ctds -pu
cd ../test/testMoodle
./testCTDS1> testCTDS1.txt
diff output1 testCTDS1.txt
rm testCTDS1.txt testCTDS1
cd ../../main

echo ---- TEST DE ACCESO A ARREGLOS--------
echo 

echo ----- TestExecutionTime Array sin las optimizaciones------
./ctds-PCR ../test/testExecutionTime/testError/testErrorReadArray.ctds
echo La ejecuccion debe dar:
echo Invalid array position access in line: 10
echo El resultado es:
./../test/testExecutionTime/testError/testErrorReadArray
rm  ../test/testExecutionTime/testError/testErrorReadArray

echo ----- TestExecutionTime Array con todas las optimizaciones------
./ctds-PCR ../test/testExecutionTime/testError/testErrorReadArray.ctds -o
echo La ejecuccion debe dar:
echo Invalid array position access in line: 10
echo El resultado es:
./../test/testExecutionTime/testError/testErrorReadArray
rm  ../test/testExecutionTime/testError/testErrorReadArray 

echo ----- TestExecutionTime Array con constante propagacion optimizaciones------
./ctds-PCR ../test/testExecutionTime/testError/testErrorReadArray.ctds -pc
echo La ejecuccion debe dar:
echo Invalid array position access in line: 10
echo El resultado es:
./../test/testExecutionTime/testError/testErrorReadArray
rm  ../test/testExecutionTime/testError/testErrorReadArray 

echo ----- TestExecutionTime Array con frame optimizaciones------
./ctds-PCR ../test/testExecutionTime/testError/testErrorReadArray.ctds -f
echo La ejecuccion debe dar:
echo Invalid array position access in line: 10
echo El resultado es:
./../test/testExecutionTime/testError/testErrorReadArray
rm  ../test/testExecutionTime/testError/testErrorReadArray 

echo ----- TestExecutionTime1 Array con codigo muerto optimizaciones------
./ctds-PCR ../test/testExecutionTime/testError/testErrorReadArray.ctds -pu
echo La ejecuccion debe dar:
echo Invalid array position access in line: 10
echo El resultado es:
./../test/testExecutionTime/testError/testErrorReadArray
rm  ../test/testExecutionTime/testError/testErrorReadArray  

echo ----- TestExecutionTime1 Array sin las optimizaciones------
./ctds-PCR ../test/testExecutionTime/testError/testError1ReadArray.ctds
echo La ejecuccion debe dar:
echo Invalid array position access in line: 9
echo El resultado es:
./../test/testExecutionTime/testError/testError1ReadArray
rm  ../test/testExecutionTime/testError/testError1ReadArray

echo ----- TestExecutionTime1 Array con todas las optimizaciones------
./ctds-PCR ../test/testExecutionTime/testError/testError1ReadArray.ctds -o
echo La ejecuccion debe dar:
echo Invalid array position access in line: 9
echo El resultado es:
./../test/testExecutionTime/testError/testError1ReadArray
rm  ../test/testExecutionTime/testError/testError1ReadArray 

echo ----- TestExecutionTime1 Array con constante propagacion optimizaciones------
./ctds-PCR ../test/testExecutionTime/testError/testError1ReadArray.ctds -pc
echo La ejecuccion debe dar:
echo Invalid array position access in line: 9
echo El resultado es:
./../test/testExecutionTime/testError/testError1ReadArray
rm  ../test/testExecutionTime/testError/testError1ReadArray 

echo ----- TestExecutionTime1 Array con frame optimizaciones------
./ctds-PCR ../test/testExecutionTime/testError/testError1ReadArray.ctds -f
echo La ejecuccion debe dar:
echo Invalid array position access in line: 9
echo El resultado es:
./../test/testExecutionTime/testError/testError1ReadArray
rm  ../test/testExecutionTime/testError/testError1ReadArray 

echo ----- TestExecutionTime1 Array con codigo muerto optimizaciones------
./ctds-PCR ../test/testExecutionTime/testError/testError1ReadArray.ctds -pu
echo La ejecuccion debe dar:
echo Invalid array position access in line: 9
echo El resultado es:
./../test/testExecutionTime/testError/testError1ReadArray
rm  ../test/testExecutionTime/testError/testError1ReadArray  


echo ----- TestExecutionTime2 Array sin las optimizaciones------
./ctds-PCR ../test/testExecutionTime/testError/testError2ReadArray.ctds
echo La ejecuccion debe dar:
echo Invalid array position access in line: 9
echo El resultado es:
./../test/testExecutionTime/testError/testError2ReadArray
rm  ../test/testExecutionTime/testError/testError2ReadArray

echo ----- TestExecutionTime2 Array con todas las optimizaciones------
./ctds-PCR ../test/testExecutionTime/testError/testError2ReadArray.ctds -o
echo La ejecuccion debe dar:
echo Invalid array position access in line: 9
echo El resultado es:
./../test/testExecutionTime/testError/testError2ReadArray
rm  ../test/testExecutionTime/testError/testError2ReadArray 

echo ----- TestExecutionTime2 Array con constante propagacion optimizaciones------
./ctds-PCR ../test/testExecutionTime/testError/testError2ReadArray.ctds -pc
echo La ejecuccion debe dar:
echo Invalid array position access in line: 9
echo El resultado es:
./../test/testExecutionTime/testError/testError2ReadArray
rm  ../test/testExecutionTime/testError/testError2ReadArray 

echo ----- TestExecutionTime2 Array con frame optimizaciones------
./ctds-PCR ../test/testExecutionTime/testError/testError2ReadArray.ctds -f
echo La ejecuccion debe dar:
echo Invalid array position access in line: 9
echo El resultado es:
./../test/testExecutionTime/testError/testError2ReadArray
rm  ../test/testExecutionTime/testError/testError2ReadArray 

echo ----- TestExecutionTime1 Array con codigo muerto optimizaciones------
./ctds-PCR ../test/testExecutionTime/testError/testError2ReadArray.ctds -pu
echo La ejecuccion debe dar:
echo Invalid array position access in line: 9
echo El resultado es:
./../test/testExecutionTime/testError/testError2ReadArray
rm  ../test/testExecutionTime/testError/testError2ReadArray  
















