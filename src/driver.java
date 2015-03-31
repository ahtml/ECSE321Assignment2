import java.util.LinkedList;
import java.util.Random;


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
		
		// Setting up the path
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
		
		// Adding towers on the Map
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
		
		// Setting up all the critters
		Random randomGenerator = new Random();
		LinkedList<Critter> crittersToAdd = new LinkedList<Critter>();
		Critter c1 = new Critter(myMap.temp, critterID);
		Critter c2 = new Critter(myMap.temp, 1);
		Critter c3 = new Critter(myMap.temp, 2);
		Critter c4 = new Critter(myMap.temp, 3);
		Critter c5 = new Critter(myMap.temp, 4);
		crittersToAdd.add(c2);
		crittersToAdd.add(c3);
		crittersToAdd.add(c4);
		crittersToAdd.add(c5);
		myMap.addCritter(c1);
		
		while(!crittersToAdd.isEmpty()||!myMap.critters.isEmpty()){
		//for(int i=0;i<pathList.length;i++){
			try {
				// Moving all the critters
				Thread.sleep(1000);
				myMap.moveCritters();
				// Randomly adding critters to start moving
				if(randomGenerator.nextBoolean()&&!crittersToAdd.isEmpty()){
					myMap.addCritter(crittersToAdd.getFirst());
					crittersToAdd.removeFirst();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("DONE!");
	}
}
