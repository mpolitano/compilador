import java.util.*;
import java_cup.runtime.*;

public class SymbolTable{

	LinkedList<Level> levels;

	public SymbolTable(){
		levels= new LinkedList<Level>();	
	}

	public void popLevel(){
		levels.pop();
	}

	public void pushLevel(){
		Level level= new Level(); 	
		levels.push(level);
	}

	//This methods returns true if sym could be inserted to level and false if exist other symbol thas has the same name. 
	public boolean addSymbolToLevel(Information sym){
		return levels.getFirst().addSymbol(sym);		
	}	

	public String toString(){
		String aux= new String();
		aux="\n" +"*********Symbol Table*******" +"\n" ;
		for(Level l: levels){
			aux= aux + l.toString() + "\n";		
		}		
		return aux;
	}

}