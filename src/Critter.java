import java.util.LinkedList;


public class Critter {
	
	private LinkedList<Path> myPath;
	private int completion = 0;
	private int position = 0;
	private int ID;
	public Critter(LinkedList<Path> pathList,int ID) {
		// TODO Auto-generated constructor stub
		myPath = pathList;
		this.ID = ID;
	}
	
	public void move(){
		position = myPath.get(completion).getPos();
		completion++;
		System.out.println("Critter " + ID + " is on position " + position);
		
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getCompletion() {
		return completion;
	}

	public void setCompletion(int completion) {
		this.completion = completion;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

}
