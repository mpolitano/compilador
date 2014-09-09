import java_cup.runtime.*;
import java.util.*;

public class Level{
	
	private LinkedList<Information> definition;

	public Level(){
		definition= new LinkedList<Information>();
	}

	//This methods returns true if sym could be inserted to level and false if exist other symbol thas has the same name. 
	public boolean addSymbol(Information newSymbol){
		for(Information s: definition){
			if (s.name.equals(newSymbol.name)){
				return false;		
			}
		}
		definition.add(newSymbol);
		return true;
	}

	public int size(){
		return definition.size();
	}

	public String toString(){
		String aux= new String();
		aux= "\n" + "----Level----"+ "\n";
		for(Information s: definition){
			aux= aux + s.name + "\n";
		}	
		return aux;		
	}
}