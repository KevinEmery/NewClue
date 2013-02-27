
public class RoomCell extends BoardCell{
	public RoomCell(int row, int col){
		super(row, col);

	}
	private char room;
	public enum DoorDirection {UP,DOWN,LEFT,RIGHT,NONE};
	public DoorDirection doorDirection;
	@Override
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
	public String getDoorDirection(){
		switch(this.doorDirection){
			case UP:
				return "u";
			case DOWN:
				return "d";
			case LEFT:
				return "l";
			case RIGHT:
				return "r";
			default:
				return "";
		}
				
	}
	public void setRoom(char room) {
		this.room = room;
	}
	
	
}
