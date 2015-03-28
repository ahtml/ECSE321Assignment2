/**
 * Java Class worked on by:
 * Anand Patel: 260583812
 * Aimee Ascencio: 260493880
 * Unless otherwise indicated, all methods in this class have been equally contributed
 * by both individuals above
 */

public abstract class Tile {
	
	private int pos;
	
	public int getPos(){
		return pos;
	}
	public void setPos(int pos){
		this.pos=pos;
	}
	
	/*
	 * Method that returns the Row coordinate of a Tile
	 */
	public int getRow(){
		return (pos/Map.getWidth());
	}
	public int getRow(int n){
		return (n/Map.getWidth());
	}
	

	/*
	 * Method that returns the Col coordinate of the Tile 
	 */
	public int getCol(){
		return pos%(Map.getWidth());
	}
	public int getCol(int n){
		return n%(Map.getWidth());
	}
	
	
	public boolean isPath(){
		if (this instanceof Path)
			return true;
		else
			return false;
	}
	
	public boolean isScenery(){
		if (this instanceof Scenery)
			return true;
		else
			return false;
	}
}
