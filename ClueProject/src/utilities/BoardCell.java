package utilities;

public abstract class BoardCell {
	private int row, column;
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
	public abstract void draw();
	
}
