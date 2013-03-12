
public class RoomCell extends BoardCell{

	private char room;
	public enum DoorDirection {UP,DOWN,LEFT,RIGHT,NONE};
	public DoorDirection doorDirection = DoorDirection.NONE;

	public RoomCell(int row, int col, char room){
		super(row, col);
		this.room=room;
	}
	public void setRoomDirection(char d){
		d=Character.toUpperCase(d);
		if(d=='U'){
			this.doorDirection=DoorDirection.UP;
		} else if(d=='R') {
			this.doorDirection=DoorDirection.RIGHT;
		} else if(d=='L') {
			this.doorDirection=DoorDirection.LEFT;
		} else if(d=='D') {
			this.doorDirection=DoorDirection.DOWN;
		}
	}
	@Override
	public boolean isDoorway(){
		return doorDirection != DoorDirection.NONE;
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
	
	public char getInitial() {
		return getRoom();
	}
	public String getDoorDirection(){
		switch(this.doorDirection){
		case UP:
			return "U";
		case DOWN:
			return "D";
		case LEFT:
			return "L";
		case RIGHT:
			return "R";
		default:
			return "";
		}

	}
	public DoorDirection getDoorDirection0(){
		return doorDirection;

	}
	public void setRoom(char room) {
		this.room = room;
	}





}
