/**
 * Java Class worked on by:
 * Anand Patel: 260583812
 * Aimee Ascencio: 260493880
 * Unless otherwise indicated, all methods in this class have been equally contributed
 * by both individuals above
 */
		
public class Path extends Tile{
	
	
	//privacy of the attributes might change 
	//Attributes of the tile itself
	public int entry;
	public int exit;
		
	//Validate
	public boolean isEdge;//entry or exit of the Path
	public boolean isStart;//or isExit
	//public boolean isVisited;

	/*
	 * How the subclasses of Path or named:
	 * First its the description of the Path
	 * Then, the first capital letter describes where the entrance is from
	 * The second capital letter describes where the exit is
	 * Both the entrance and exit are relative to the current position of the Path 
	 */
	
	//Constructor 
	public Path(int pos){
		this.setPos(pos);	
	}
	 	
	
	public void rotate(){
		int temp=entry;
		entry =exit;
		exit = temp;
	}
	
	
	//SETTERS
	public void setIsEdge(){//either Start or End
		isEdge=true;
	}
	public void setStart(){
		setIsEdge();
		isStart = true;
		entry = getPos();
	}
	public void setEnd(){
		setIsEdge();
		isStart=false;
		exit=getPos();
	}
//	public void setVisited(boolean visited){
//		isVisited = visited;
//	}
//	
	
	//GETTERS
	public boolean getIsEdge(){
		return isEdge;
	}
	public int getEntry(){
		return entry;
	}
	public int getExit(){
		return exit;
	}
//	public boolean getVisited(){
//		return isVisited;
//	}
	
	
	//Methods to return the col and row of the entry and exit of the Path
	public int getEntryCol(){
		return getCol(this.getEntry());
	}
	public int getEntryRow(){
		return getRow(this.getEntry());
	}
	
	public int getExitCol(){
		return getCol(this.getExit());
	}
	public int getExitRow(){
		return getRow(this.getExit());		
	}
		
	
	//Get position of the tiles connected
	public int getNorth(){
		return getPos()-Map.getWidth();
	}
	public int getSouth(){
		return getPos()+Map.getWidth();
	}
	public int getWest(){
		return getPos()-1;
	}
	public int getEast(){
		return getPos()+1;
	}
	
	
	
	//Calculate direction
	public int getDirection(int p){
		if (p==this.getNorth())
			return 0;
		else if (p==this.getEast())
			return 1;
		else if(p==this.getSouth())
			return 2;
		else if (p==this.getWest())
			return 3;
		else
			return -1;// error
		
	}
	public void storePathTile(){
		Map.setGrid(this.getRow(),this.getCol(),this);
		Map.temp.add(this);

	}
	
	
}
