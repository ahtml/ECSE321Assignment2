/**
 * Java Class worked on by:
 * Aimee Ascencio: 260493880
 * Unless otherwise indicated, all methods in this class have been equally contributed
 * by both individuals above
 */

public class Scenery extends Tile{
	
	
	private boolean towerPresent;
	
	public Scenery(int pos){
		this.setPos(pos);	
		towerPresent=false;
	}
	
	/*
	 * Returns true if there is a tower present the Tile Path
	 */
	public boolean isTowerPresent(){
		return towerPresent;
	}
	
	
	/*
	 * If it is able to place a tower on a tile Path it returns true
	 * It is only able to place a tower, if there are no towers present in that tile
	 */
	public boolean towerPlaced(){
		if(!isTowerPresent()){//if there are no towers 
			towerPresent=true;// a tower is placed 
			return true;//tower was successfully added
		}
		else//there is already a tower there 
			return false;//can't add another tower
	}	
	
	/*
	 * 
	 */
	public boolean towerRemoved(){
		if (isTowerPresent()){//if there is a tower
			towerPresent=false;//remove tower
			return true;//tower was successfully removed 
		}
		else 
			return false;//there is no tower to remove
	}
	
	
	
	
}
