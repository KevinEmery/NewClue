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
	
	// Draws the walkway cell as yellow, with a black border
	@Override
	public void draw(Graphics g, Board board){
		g.setColor(Color.black);
		g.drawRect(column * ClueGame.CELL_WIDTH, row * ClueGame.CELL_HEIGHT, ClueGame.CELL_WIDTH, ClueGame.CELL_HEIGHT);
		if (drawingPossible) {
			g.setColor(Color.cyan);
		} else {
			g.setColor(Color.yellow);
		}
		g.fillRect(column * ClueGame.CELL_WIDTH + 1, row * ClueGame.CELL_HEIGHT + 1, ClueGame.CELL_WIDTH - 2, ClueGame.CELL_HEIGHT - 2);
	}

}
