package game;

public class RoomCell extends BoardCell {
	
	// Custom enum created to easily handle door direction
	public enum DoorDirection {
		UP,
		DOWN,
		LEFT,
		RIGHT,
		NONE;
		public static DoorDirection makeFromChar(char initial) {
			
			switch(initial) {
			case 'U':
				return UP;
			case 'D':
				return DOWN;
			case 'L':
				return LEFT;
			case 'R':
				return RIGHT;
			default:
				return NONE;
			}
		}
	};
	
	// Used to store the direction of a door
	private DoorDirection doorDirection;
	
	// Used to restore the character that maps to a room.
	private char roomInitial;
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return roomInitial;
	}
	
	public RoomCell() {
		this.roomInitial = 'Z';
		this.doorDirection = DoorDirection.NONE;
	}
	
	// Creates a room cell using the given initial at the location (row, column)
	public RoomCell(String roomInitial, int row, int column) {
		super(row, column);
		this.roomInitial = roomInitial.charAt(0);
		
		// Sets a door direction to any of the four directions if it has a door, otherwise sets it to none
		if(roomInitial.length() > 1) {
				this.doorDirection = DoorDirection.makeFromChar(roomInitial.charAt(1));
		} else {
				this.doorDirection = DoorDirection.NONE;
		}
	}
	
	// If this is a door, and matches one of the four standard directions, return true
	@Override
	public boolean isDoorway() {
		return (this.doorDirection == DoorDirection.NONE ? false : true);
	}
	
	// Overrides the class method of isRoom to return true.
	@Override
	public boolean isRoom() {
		return true;
	}
	
	// Draws a roomCell according to our specifications
	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}
	

}
