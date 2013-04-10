package game;

import java.awt.Color;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

public class HumanPlayer extends Player {
	
	private Set<BoardCell> possibleTargets;
	
	public HumanPlayer() {
		this("Mrs. Scarlet", 174, Color.red);
	}
	
	public HumanPlayer(String name, int startingLocation, Color color) {
		super(name, startingLocation, color);
		possibleTargets = new HashSet<BoardCell>();
	}
	
	@Override
	public boolean isHumanPlayer() {
		return true;
	}
	@Override
	public void makeMove(Board board, int dieRoll){
		canMakeAccusation=true;
		endturn=false;
		board.startTargets(location, dieRoll);
		possibleTargets = board.getTargets();
		for (BoardCell cell : possibleTargets) {
			cell.setDrawingPossible(true);
		}
		board.repaint();
	}
	public void checkValidity(int mouseX, int mouseY, Board board){
		if(board.getCellAt(mouseY/ClueGame.CELL_WIDTH, mouseX/ClueGame.CELL_HEIGHT).drawingPossible){
			currentCell= board.getCellAt(mouseY/ClueGame.CELL_WIDTH, mouseX/ClueGame.CELL_HEIGHT);
			location=board.calcIndex(currentCell.row, currentCell.column);
			board.repaint();
			endMove(board);
		}else{
			String message="That is not a valid cell";
			JOptionPane.showMessageDialog(null, message);	
		}
	}
	
	public void endMove(Board board){
		for (BoardCell cell : possibleTargets) {
			cell.setDrawingPossible(false);
		}
		endturn=true;
	}
}
