package game;

public class RoomCell extends BoardCell {
	

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
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	// Used to restore the character that maps to a room.
	private char roomInitial;
	public RoomCell() {
		this.roomInitial = 'Z';
		this.doorDirection = DoorDirection.NONE;
	}
	public RoomCell(String roomInitial) {
		this.roomInitial = roomInitial.charAt(0);
		if(roomInitial.length() > 1) {
			this.doorDirection = DoorDirection.makeFromChar(roomInitial.charAt(1));
		} else {
			this.doorDirection = DoorDirection.NONE;
		}
	}
	@Override
	public boolean isDoorway() {
		return (this.doorDirection == DoorDirection.NONE ? true : false);
	}
	@Override
	public boolean isRoom() {
		return true;
	}
	
	// Draws a roomCell according to our specifications
	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}
	
	public char getInitial() {
		return roomInitial;
	}

}
