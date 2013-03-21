package game;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player {
	private char lastRoomVisited;
	public ComputerPlayer(String name, int startLocation, Color color) {
		super(name, startLocation, color);
	}

	public void pickLocation(Set<BoardCell> targets) {
		
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
}
