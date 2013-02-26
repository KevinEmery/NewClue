
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

	public void setRoom(char room) {
		this.room = room;
	}
	
	
}
