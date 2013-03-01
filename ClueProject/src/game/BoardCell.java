package game;

public abstract class BoardCell {
	@SuppressWarnings("unused")
	private int row, column;
	
	// Sets the default row and column to 0,0
	public BoardCell() {
		this.row = this.column = 0;
	}
	
	// Sets the row and column to  specified location
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	// These methods here are overridden as needed
	public boolean isWalkway() {
		return false;
	}
	public boolean isRoom() {
		return false;
	}	
	public boolean isDoorway() {
		return false;
	}
	
	// Abstract function for our game to call for each child class.
	public abstract void draw();
	
}
