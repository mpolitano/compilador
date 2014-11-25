package parser;
import java_cup.runtime.*;
import java.util.*;
import ctds_pcr.ast.Location;
public class Level{
	
	private LinkedList<Location> definition;

	public Level(){
		definition= new LinkedList<Location>();
	}

	//This methods returns true if sym could be inserted to level and false if exist other symbol thas has the same name. 
	public boolean addSymbol(Location newSymbol){
		for(Location s: definition){
			if (s.getId().equals(newSymbol.getId())){
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
		for(Location s: definition){
			aux= aux + s.toString() + "\n";
		}	
		return aux;		
	}

	public Location getByIde(String id){
		for(Location e: definition){
			if (e.getId().equals(id)){
				return e;
			}
		}
		return null;
	}		

}
