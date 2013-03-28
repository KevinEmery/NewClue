package game;

import java.awt.Graphics;



public abstract class BoardCell {
	
	public int row;
	public int column;
	protected int cellWidth = 30;
	protected int cellHeight = 30;
	
	public BoardCell(int row, int column){
		this.row = row;
		this.column = column;
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
	
	abstract void draw(Graphics g, Board board);

}
