


public abstract class BoardCell {
	private int row;
	private int column;
	public BoardCell(int row, int column){
		this.row=row;
		this.setColumn(column);
	}
	public boolean isWalkway(){
		return false;
	}
	public boolean isRoom(){
		return false;
	}
	public boolean isDoorway(){
		return false;
	}
	abstract void draw();
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}

}
