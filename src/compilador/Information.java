import java_cup.runtime.*;

public class Information{
	
	public String name;
	public int intValue;
	public float floatValue;
	public int line;
	public int col;
	
	public Information(String my_name, int my_int, float my_float, int my_line, int my_col){
		name= my_name;
		intValue= my_int;
		floatValue= my_float;
		line= my_line;
		col= my_col;	
	}

}