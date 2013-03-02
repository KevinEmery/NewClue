
public class RoomCell extends BoardCell{
	public RoomCell(int row, int col){
		super(row, col);

	}
	private char room;
	public enum DoorDirection {UP,DOWN,LEFT,RIGHT,NONE};
	public DoorDirection doorDirection=DoorDirection.NONE;
	
	public void setRoomDirection(char d){
		if(d=='U'){this.doorDirection=DoorDirection.UP;}
		else if(d=='R'){this.doorDirection=DoorDirection.RIGHT;}
		else if(d=='L'){this.doorDirection=DoorDirection.LEFT;}
		else if(d=='D'){this.doorDirection=DoorDirection.DOWN;}
		else{this.doorDirection=DoorDirection.NONE;}
	}
	@Override
	public boolean isDoorway(){
		if(doorDirection!=DoorDirection.NONE){return true;}
		return false;
	}
	public boolean isRoom(){
		return true;
	}
	
	@Override
	void draw() {
		// TODO Auto-generated method stub
		
	}

	public char getRoom() {
		return room;
	}

	public void setRoom(char room) {
		this.room = room;
	}

	public Object getDoorDirection() {
		
		return doorDirection;
	}

	
	
}
