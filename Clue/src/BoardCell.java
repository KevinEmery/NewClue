


public abstract class BoardCell {
	public int row;
	public int column;
	public BoardCell(int row, int column){
		this.row=row;
		this.column=column;
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

}
