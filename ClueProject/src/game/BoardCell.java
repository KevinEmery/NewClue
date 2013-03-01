package game;

public abstract class BoardCell {
	private int row, column;
	
	// Sets the default row and column to 0,0
	public BoardCell() {
		this.row = this.column = 0;
	}
	
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
