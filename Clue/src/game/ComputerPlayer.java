package game;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private char lastRoomVisited;
	

	
	// Creates a default computer player. In practice this should never be used.
	public ComputerPlayer() {
		this("Mrs. Peacock", 0, Color.blue);
	}
	
	// Creates a computer player with the given parameters. These will all be read out of a file
	public ComputerPlayer(String name, int startLocation, Color color) {
		super(name, startLocation, color);
	}

	// Used for testing, could also be used in determining the flow of the game
	@Override
	public boolean isComputerPlayer() {
		return true;
	}
	
	// Out of a list of possible locations, picks a location to travel to.
	public BoardCell pickLocation(Set<BoardCell> targets) {
		return new WalkwayCell(0, 0);
	}
	
	// Creates a suggestion that the player is asking.
	public void createSuggestion() {
		
	}
	
	// Updates the cards they have seen with this new piece of information
	public void updateSeen(Card seen) {
		
	}
	
	// Returns the character of the last room visited
	public char getLastRoomVisited() {
		return lastRoomVisited;
	}

	
	// EVERYTHING BELOW HERE IS FOR TESTING ONLY
	// THESE SHOULD NEVER BE USED IN PRACTICE
	public void setLastRoomVisited(char lastRoomVisited) {
		this.lastRoomVisited = lastRoomVisited;
	}

}
