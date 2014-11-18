/* Funciones auxiliares para realizar el test del compilador C-TDS.
 * Permite leer los datos de entrada e imprimir la salida para cada funcion a ejecutar. 
 * Asume que los datos de entrada se encuentran en el archivo "input".
 * El archivo input contiene una linea por cada funcion que se invoca (en el orden que se invocan). 
 * Cada linea es una lista de numeros (enteros y/o reales). 
 * El primer numero indica la cantidad de ejecuciones de cada funcion seguido de los parametros que se 
 * necesitan para la invocación.
 * */


#include <stdio.h>

FILE *fp;  
char buffer[100];

// abre el archivo con los datos de entrada
int init_input(char *f){
	fp = fopen ( f, "r" );
}

// cierra el archivo
int close_input(){
	fclose(fp);
}

// lee un dato entero del archivo de entrada
int get_int(){
	int x;	
   	fscanf(fp, "%s" ,buffer); 
    sscanf(buffer, "%d", &x);
    return x;
}

// imprime un entero en la salida
void print_int(int x){
       printf("%d\n",x);	
	}

// lee un dato real del archivo de entrada
float get_float(){
	float x;	
   	fscanf(fp, "%s" ,buffer);
    sscanf(buffer, "%f", &x);
    return x;
}

// imprime un real en la salida
void print_float(float x){
       printf("%f\n",x);	
	}
	
// imprime un string en la salida	
void print_string(char *s){
		printf("%s\n",s);
	}
