/**
 * Java Class worked on by:
 * Anand Patel: 260583812
 * Aimee Ascencio: 260493880
 * Unless otherwise indicated, all methods in this class have been equally contributed
 * by both individuals above
 */
import java.util.LinkedList;
import java.util.ListIterator;


public class Map extends Subject {
	
	private static Tile grid [][]; // hold the Map path and scenery
	private int height; // height of map
	private static int width; // Width of Map
	
	//private
	static LinkedList <Path> temp = new LinkedList<Path>(); // List of the Path
	public LinkedList <Critter> critters = new LinkedList<Critter>(); // List of critters
	private Path entryPoint; // Entry point
	private Path exitPoint; // Exit point
	
	//Path variables
	private static boolean completePath=false;
	int currentPos=-1;
	Path currentPath;
	
	private static Map instanceMap=null;
	
	
	private Map(){
	}
	
	public static Map getInstance(){
		if(instanceMap==null)
			instanceMap=new Map();
		return instanceMap;
	}
	//SETTERS 
	public void setMap(int h, int w){
		height=h;
		width=w;
		grid=new Tile[height][width];
	}
	public void setEntryPoint(Path p){
		entryPoint=p;
	}
	public void setExitPoint(Path p){
		exitPoint=p;
	}
	
	public void setCompletePath(boolean n){
		completePath=n;
	}
	
	//GETTERS
	public int getHeight(){
		return height;
	}
	
	public static int getWidth(){
		return width;
	}	
	public Path getEntryPoint(){
		return entryPoint;
	}
	public Path getExitPoint(){
		return exitPoint;
	}
	public boolean getCompletePath(){
		return completePath;
	}
	
	
	//GRID
	public static void setGrid(int y, int x, Tile k){
		grid[y][x]=k;
	}
	
	public Tile getGrid(int y, int x){
		return grid[y][x];
	}
	
	
	//When setting the path tile to a specific type (if it is an edge, it is type 4)
	private static int caseEdge=4;
	
	/**
	 * This method sets a tile to a specific path type (given title that came before and the one that comes after).
	 * There is a currentPos variable that points one position ahead (This tile hasn't been given a type yet). 
	 * It will check if the new position (pos) is a valid one.
	 * It will push the need tile into the linked list (that contains the path)
	 * It will update the grid, with Path and scenery tiles 
	 * 
	 * @param pos
	 */
	public void setCellToPath(int pos){//how to check for the end???
		
		if(pos<0||pos>width*height-1||getCompletePath())
			return;// the position entered is not valid or the path has already been completed
		
		else if(temp.isEmpty()){//no path tile have been defined
			if(currentPos<0){//start hasn't been placed		
				currentPos=pos;
				currentPath=new Path(currentPos);
				setEntryPoint(currentPath);
				setGrid(currentPath.getRow(),currentPath.getCol(),currentPath);
			}
			else{
				int d = currentPath.getDirection(pos);
				if (d<0||d>=4)
					return; //the two tiles are not connected
				PathType type =Map.createPathTileOfType(d, caseEdge);
				Path p =PathFactory.makePath(type, currentPos);
				
				//fix direction of tile
				if(p.getEntry()==pos)
					p.rotate();
				//validate spot (for entry/exit)
				
				//set as Path entry point 
				p.setStart();
				setEntryPoint(p);
				
				//add to grid and list
				p.storePathTile();

				//update current
				currentPos=pos;
				currentPath=new Path(currentPos);		
				setGrid(currentPath.getRow(),currentPath.getCol(),currentPath);
			}		
		}
		else{
			
			if (getGrid(pos/width, pos%width)!=null)
				if(getGrid(pos/width, pos%width).isPath())
					return;//causes intersection
			
			int dExit = currentPath.getDirection(pos);
			int lastPos= temp.peekLast().getPos();
			int dEntry= currentPath.getDirection(lastPos);

			
			if (dExit<0||dExit>3||dEntry<0||dEntry>3)
				return; //the two tiles are not connected
			
			
			PathType type =Map.createPathTileOfType(dExit,dEntry);
			Path p =PathFactory.makePath(type, currentPos);
			
			//fix direction of tile
			if(p.getEntry()==pos)
				p.rotate();
			
			if(!inValidSpot(p))
				return; //
			
			//add to grid and list
			p.storePathTile();
			
			//update current
			currentPos=pos;
			currentPath=new Path(currentPos);	
			setGrid(currentPath.getRow(),currentPath.getCol(),currentPath);
		}
	}
	
	/**
	 * Once the path has been completed, this method will initialize the currentPos to a type of path.
	 * It will store it in the linked list and the grid. 
	 */
	public void finalizePath(){
		if(getCompletePath()||temp.isEmpty())
			return;//path has already been finalized or there is no path defined
		else{
			int LastPos=temp.peekLast().getPos();
			int d = currentPath.getDirection(LastPos);
			PathType type = Map.createPathTileOfType(d,caseEdge);
			Path p = PathFactory.makePath(type,currentPos);
			
			if(p.getEntry()!= LastPos)
				p.rotate();
			p.setEnd();
			setExitPoint(p);
			p.storePathTile();
			
			setCompletePath(true);
			setRemainingToScenery();
		}
		
		
	}	

	/**
	 * It will delete the last path tile introduced to the linked list. 
	 * It will update the position of current to the end of the path.
	 * If an element was deleted it will return an incomplete Path.
	 * The necessary changes will be made in the grid as well. 
	 */
	public void deleteLastPathTile(){
		if(temp.isEmpty()){
			if(currentPos!=-1){
				currentPos=-1;
				setEntryPoint(null);//start has been deleted
				grid[currentPath.getRow()][currentPath.getCol()]= null;
				currentPath=null;
			}
			return;//the list is empty
		}
		else {//
			if(getCompletePath()){
					setCompletePath(false);
					setExitPoint(null);
					removeScenery();
					
			}//since the last node was removed
			else//if the path wasn't complete (should it be scenery?)
				grid[currentPath.getRow()][currentPath.getCol()]= null;
				currentPath=null;
			
			Path removedPath=temp.removeLast();
			currentPos=removedPath.getPos();
			currentPath=new Path(currentPos);
		}
	}
	
	/**
	 * Set cells on the grid that haven't been initialized to Path to Scenery
	 */
	public void setRemainingToScenery(){
		for(int i=0;i<grid.length;i++){
			for(int j=0;j<grid[0].length;j++){
				if(grid[i][j]==null)
					grid[i][j]=new Scenery(i*grid[0].length+j);
			}
		}
	}
	public void removeScenery(){
		for(int i=0;i<grid.length;i++){
			for(int j=0;j<grid[0].length;j++){
				if(grid[i][j].isScenery())
					grid[i][j]=null;
			}
		}
	}
		
	//TESTS
	/**
	 * Tests whether a spot is Valid for a specific type of Path Tile
	 * @param p1: Path Tile being tested
	 * @return
	 */
	public boolean inValidSpot(Path p1){
		// Checks to see it the path piece placed expects an entrance or exit piece out of bounds
		
		int largestRow = (height*width-1)/width;
		
		// if entrance or exit pos expected is negative, know it is expecting a path outside the top bound
		if(p1.getEntry()<0 || p1.getExit()<0){
			return false;
		}
		// if entrance or exit's row greater than the maximum row, know it is expecting a path outside bottom bound
		if(p1.getEntryRow()>largestRow || p1.getExitRow()>largestRow){
			return false;
		}
		int currentRow = p1.getRow();
		int nextRow=0;
		if(Math.abs(p1.getPos()-p1.getEntry())==1){
			// If the absolute value of the difference between the current position and its entrance is 1 ...
			// Checking to see if the entrance is expected to be outside of the left or right edges of Map
			nextRow = p1.getEntryRow();
			if(currentRow!=nextRow){
				// This means that the entrance expected is not to the left or to the right
				// In other words, the entrance is expected in another row
				return false;
			}
		}
		if(Math.abs(p1.getPos()-p1.getExit())==1){
			// If the absolute value of the difference between the current position and its exit is 1 ...
			// Checking to see if the exit is expected to be outside of the left or right edges of Map
			nextRow = p1.getExitRow();
			if(currentRow!=nextRow){
				// This means that the exit expected is not to the left or to the right
				// In other words, the exit is expected in another row
				return false;
			}
		}
		/*
		if(Math.abs(p1.getPos()-p1.getEntry())==width){
			nextRow = p1.getEntryRow();
			if(p1.getEntry()<0 || nextRow>(height-1/width)){//it used to be calculaterow(height*width-1
				//Less than zero would make it on top of the top edge
				//First term calculated in this manner because any negative entrance between -1 and 
				// -width+1 will have an integer rounded to zero. The double casting will give an 
				// accurate number without losing the sign
				
				//Greater than height*width-1 would make in lower than the bottom edge
				return false;
			}
		}
		if(Math.abs(p1.getPos()-p1.getExit())==width){
			nextRow = p1.getExitRow();
			if(p1.getEntry()<0 || nextRow>(height-1/width)){//it used to be calculaterow(height*width-1
				return false;
			}
		}
		*/
		return true;
		
	}
	
	
	/*
	 * THESE TWO METHODS ARE USED FOR TESTING PURPOSES 
	 * ARE NOT NEEDED IN THE CODE
	 */
	/**
	 * Tests if two path tiles are connected properly
	 * @param p1: Path Tile 1
	 * @param p2: Path Tile 2
	 * @return
	 */
	public boolean connected(Path p1,Path p2){
		// Checks if p1 and p2 are connected
		if(p1.getExit()==p2.getPos()&&p1.getPos()==p2.getEntry())
			// If the exit of the first tile is equal to the position of the second tile
			// AND if the entrance of the second tile is equal to the position of the first tile
			return true;
		else
			return false;	
	}

	/**
	 * Checks if a rotation is needed to make two Path Tiles connected
	 * @param p1: PathTile 1
	 * @param p2: PathTile 2
	 * @return 
	 */
	public boolean connectedRotate(Path p1, Path p2){
		if((p1.getExit()==p2.getPos()&&p2.getExit()==p1.getPos())||(p1.getEntry()==p2.getPos()&&p2.getEntry()==p1.getPos()))
			return true;
		else 
			return false;
	}
	
	/**
	 * Method prints the position of the Path Tiles
	 */
	public void printPath(){
		ListIterator<Path> listIterator =temp.listIterator();
		while(listIterator.hasNext()){
			System.out.print(listIterator.next().getPos()+" ");
		}
	}
	
	/**
	 * This method will print the contents of the 2D array
	 * GRID. It will print 1 is the cell contains a path tile 
	 * 0 if there is a Scenery Tile and - if it is null
	 */
	public void printGrid(){
		for(int i=0;i<getHeight();i++){
			for(int j=0;j<getWidth();j++){
				if(grid[i][j]==null)
					System.out.print("- ");
				else if(getGrid(i,j).isPath())
					System.out.print("1 ");
				else if(getGrid(i,j).isScenery())
					System.out.print("0 ");
				else
					System.out.print("? ");
			}
			System.out.println("");
		}
		System.out.println();
	}
	
	
	

	/**
	 * This is a Path Factory 
	 * It initializes a Path Tile to certain position
	 * It will be given a type depending on where the entrance and the exit are placed.
	 * 
	 * @param s: entrance of the path
	 * @param e: exit of the Path
	 * @return
	 */
	public static PathType createPathTileOfType(int s, int e){
		//N=0, E=1, S=2, W=3
		//if the pathTile is an edge, exit/entry would be indicated and 
		int type; 
		if ((s==0&&e==1)||(s==1&&e==0))
			type= 0;//NE
		else if ((s==0&&e==2)||(s==2&&e==0)||(s==0&&e==4)||(s==2&&e==4))
			type=1;//NS
		else if ((s==0&&e==3)||(s==3&&e==0))
			type = 2;//NW
		else if ((s==1&&e==2)||(s==2&&e==1))
			type = 3;//SE
		else if ((s==1&&e==3)||(s==3&&e==1)||(s==3&&e==4)||(s==1&&e==4))
			type=4;//WE
		else if ((s==2&&e==3)||(s==3&&e==2))
			type= 5;//SW
		else 
			type= -1;//error 

		switch(type){
		case 0:
			return PathType.turnEN;
		case 1:
			return PathType.straightNS;
		case 2:
			return PathType.turnNW;
		case 3:
			return PathType.turnSE;
		case 4:
			return PathType.straightWE;
		case 5:
			return PathType.turnWS;
		default:
			return PathType.noDirection;//error
		}
	}
	
	// Methods below this are the new methods created particularly for assignment 2
	
	/**
	 * New method created by me to place tower on the scenery from Map
	 * @param towerPos
	 */
	public void placeTower(int towerPos){
		Scenery tempScenery;
		if(getGrid(towerPos/width, towerPos%width).isScenery()){
			tempScenery = (Scenery) grid[towerPos/width][towerPos%width];
			tempScenery.towerPlaced();
		}
		
	}
	
	public void addCritter(Critter c){
		critters.add(c);
	}
	
	public void moveCritters(){
		LinkedList <Critter> tempCritters =(LinkedList<Critter>) critters.clone();
		for(Critter c: tempCritters){
			c.move();
			if(c.getCompletion()>temp.size()-1){
				System.out.println("Critter " + c.getID() + " has been removed");
				critters.remove(c);
			}
		}
	}
}
