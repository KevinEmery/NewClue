package game;

public class RoomCell extends BoardCell{

	private char room;
	private DoorDirection doorDirection;
	public enum DoorDirection {UP,DOWN,LEFT,RIGHT,NONE};
	
	public RoomCell(int row, int col, char room){
		super(row, col);
		this.room=room;
		doorDirection = DoorDirection.NONE;
	}
	
	public void setRoomDirection(char direction){
		direction=Character.toUpperCase(direction);
		if(direction=='U'){
			this.doorDirection=DoorDirection.UP;
		} else if(direction=='R') {
			this.doorDirection=DoorDirection.RIGHT;
		} else if(direction=='L') {
			this.doorDirection=DoorDirection.LEFT;
		} else if(direction=='D') {
			this.doorDirection=DoorDirection.DOWN;
		}
	}
	
	public boolean isRoom(){
		return true;
	}


	public char getRoom() {
		return room;
	}
	
	public char getInitial() {
		return getRoom();
	}
	
	public DoorDirection getDoorDirection(){
		return doorDirection;

	}
	
	@Override
	public boolean isDoorway(){
		return doorDirection != DoorDirection.NONE;
	}
	
	@Override
	void draw() {
		// TODO Auto-generated method stub

	}
}
