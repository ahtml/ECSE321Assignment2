
public class driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		observing();
	}
	
	public static void observing(){
		int critterID = 0;
		Map myMap = Map.getInstance();
		myMap.setMap(5, 5);
		System.out.println("Made subject");
		Gui myGui = new Gui(myMap);
		System.out.println("Made Observer");
		
		int pathList [] = {6, 7, 12, 17, 18, 19};
		
		for(int i=0;i<pathList.length;i++){
			try {
				Thread.sleep(1000);
				myMap.setCellToPath(pathList[i]);
				myMap.notifyObservers();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		myMap.finalizePath();
		myMap.notifyObservers();
		
		int towerList [] = {10, 20, 15, 9};
		
		for(int i=0;i<towerList.length;i++){
			try {
				Thread.sleep(1000);
				myMap.placeTower(towerList[i]);
				myMap.notifyObservers();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Critter c1 = new Critter(myMap.temp, critterID);
		myMap.addCritter(c1);
		
		for(int i=0;i<pathList.length;i++){
			try {
				Thread.sleep(1000);
				myMap.moveCritters();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("DONE!");
	}
}
