package game;

import java.awt.Color;
import java.awt.Graphics;



public abstract class BoardCell {
	
	public int row;
	public int column;
	protected boolean drawingPossible = false;

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
	
	public void setDrawingPossible(boolean drawingPossible) {
		this.drawingPossible = drawingPossible;
	}
	
	abstract void draw(Graphics g, Board board);
		

	
	
	
}
