package game;

import java.awt.Color;
import java.awt.Graphics;

public class WalkwayCell extends BoardCell {
	
	public WalkwayCell(int row, int col) {
		super(row, col);
	}

	@Override
	public boolean isWalkway(){
		return true;
	}
	
	@Override
	public void draw(Graphics g, Board board){
		g.setColor(Color.black);
		g.drawRect(column * cellWidth, row * cellHeight, cellWidth, cellHeight);
		g.setColor(Color.yellow);
		g.fillRect(column * cellWidth + 1,	row * cellHeight + 1, cellWidth - 2, cellHeight - 2);
	}

}
