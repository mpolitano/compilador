/**
*	Class for report Error
* 	@author:Cornejo-Politano-Raverta.
*/

package ctds_pcr.error;

public class Error{
	private int line;/**line where the error*/
	private int column;/**column where the error*/
	private String description; /** description the error.*/

	/**
	* Constructor of a Error object
	*
	* @param my_line - line where the error
	* @param my_column - column where the error
	* @param my_description - description the error.
	*
	*/
	public Error(int my_line, int my_column, String my_description){
		line=my_line+1; //For fix wrong line number
		column=my_column+1;//For fix wrong line number
		description= my_description;
	}

	public String toString(){
		return "Line: "+ line + " Column: "+ column +" "+description;
	}
	

}