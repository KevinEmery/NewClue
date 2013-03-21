package game;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player {
	private char lastRoomVisited;
	
	
	public ComputerPlayer() {
		
	}
	
	public ComputerPlayer(String name, int startLocation, Color color) {
		super(name, startLocation, color);
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		return new WalkwayCell(0, 0);
	}
	public void createSuggestion() {
		
	}
	public void updateSeen(Card seen) {
		
	}
	public char getLastRoomVisited() {
		return lastRoomVisited;
	}
	@Override
	public boolean isComputerPlayer() {
		return true;
	}
	
	// For testing purposes only
	public void setLastRoomVisited(char lastRoomVisited) {
		this.lastRoomVisited = lastRoomVisited;
	}

}
