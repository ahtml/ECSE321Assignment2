
public class Gui extends Observer {
	
	int [][] gridView;
	Map myMap;
	Scenery tempScenery;
	public Gui(Map myMap) {
		super(myMap);
		this.myMap = myMap.getInstance();
		gridView = new int[myMap.getHeight()][myMap.getWidth()];
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		printGrid(myMap);
	}
	
	/**
	 * This method is modified from the one from the Map class
	 */
	public void printGrid(Map m){
		/*
		 * This is the legend for the printed grid:
		 * Path: 1
		 * Nothing: -
		 * Scenery: 0
		 * Tower: 2
		 */
		for(int i=0;i<m.getHeight();i++){
			for(int j=0;j<m.getWidth();j++){
				if(m.getGrid(i,j)==null){
					System.out.print("- ");
				}
				else if(m.getGrid(i,j).isPath()){
					System.out.print("1 ");
				}
				else if(m.getGrid(i,j).isScenery()){
					tempScenery = (Scenery)m.getGrid(i, j);
					if(tempScenery.isTowerPresent()){
						System.out.print("2 ");
					}
					else{
						System.out.print("0 ");
					}
				}
				else{
					System.out.print("? ");
				}
			}
			System.out.println("");
		}
		System.out.println();
	}
	
}
