package ir.error;

public class Error{
	private int line;
	private int column;
	private String description;

	public Error(int my_line, int my_column, String my_description){
		line=my_line;
		column=my_column;
		description= my_description;
	}

	public String toString(){
		return "Line: "+ line + " Column: "+ column+" "+description;
	}
	

}