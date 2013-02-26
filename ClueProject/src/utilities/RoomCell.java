package utilities;

public class RoomCell extends BoardCell {
	public enum DoorDirection {
		UP,
		DOWN,
		LEFT,
		RIGHT,
		NONE;
	};
	private DoorDirection doorDirection;
	private char roomInitial;
	@Override 
	public boolean isRoom() {
		return true;
	}
	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}
	public char getRoomInitial() {
		return roomInitial;
	}

}
