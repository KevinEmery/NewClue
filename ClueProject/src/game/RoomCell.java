package game;

public class RoomCell extends BoardCell {
	

	public enum DoorDirection {
		UP,
		DOWN,
		LEFT,
		RIGHT,
		NONE;
	};
	
	// Used to store the direction of a door
	private DoorDirection doorDirection;
	
	// Used to restore the character that maps to a room.
	private char roomInitial;
	
	@Override
	public boolean isRoom() {
		return true;
	}
	
	// Draws a roomCell according to our specifications
	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}
	
	// Getter for the roomInitial variable
	public char getRoomInitial() {
		return roomInitial;
	}

}
