package game;

import game.Card.CardType;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private char lastRoomVisited;
	private ArrayList<Card> cardsSeen;	
	private String suggestedRoom;
	private String suggestedPerson;
	private String suggestedWeapon;
	


	// Creates a default computer player. In practice this should never be used.
	public ComputerPlayer() {
		this("Mrs. Peacock", 0, Color.blue);
	}
	
	// Creates a computer player with the given parameters. These will all be read out of a file
	public ComputerPlayer(String name, int startLocation, Color color) {
		super(name, startLocation, color);
		lastRoomVisited = ' ';
		cardsSeen = new ArrayList<Card>();
	}

	// Used for finding a computer player in the array list
	@Override
	public boolean isComputerPlayer() {
		return true;
	}
	
	// Out of a list of possible locations, picks a location to travel to.
	public BoardCell pickLocation(Set<BoardCell> targets) {
		// Sets a new random int, based on the size of the targets set
		int randInt = (new Random()).nextInt(targets.size());
		int counter=0;
		BoardCell targetCell=null;
		
		// Selects the target
		for(BoardCell cell: targets) {
			
			// If the counter equals the random int, then set the target to that cell
			if(counter == randInt) {
				targetCell = cell;
			}
			
			// However, if you find a room that is not the last visited, then you ALWAYS go there instead
			if(cell.isRoom() && ((RoomCell)cell).getRoom() != lastRoomVisited) {
				lastRoomVisited = ((RoomCell)cell).getRoom();
				targetCell = cell;
				break;
			}
			++counter;
		}
		return targetCell;
	}
	
	// Creates a suggestion that the player is asking.
	public void createSuggestion(ClueGame game) {
		
		// Creates a temporary data structure
		ArrayList<Card> peopleList = new ArrayList<Card>();
		ArrayList<Card> weaponList = new ArrayList<Card>();
		
		// Adds the card to the temp structure if it has not been seen
		for (Card c : game.getOriginalDeck()) {
			if (!cardsSeen.contains(c) && !getMyCards().contains(c)) {
				if (c.getCardType().equals(CardType.PERSON)) {
					peopleList.add(c);
				} else if (c.getCardType().equals(CardType.WEAPON)) {
					weaponList.add(c);
				}
			}
		}
		
		// Creates a new random generator
		Random randInt = new Random();
		
		// Sets the player's suggestion
		this.suggestedPerson = peopleList.get(randInt.nextInt(peopleList.size())).getName();
		this.suggestedWeapon = weaponList.get(randInt.nextInt(weaponList.size())).getName();
		this.suggestedRoom = game.getBoard().getRooms().get(((RoomCell) getCurrentCell()).getInitial());
	}
	
	// Updates the cards they have seen with this new piece of information
	public void updateSeen(Card seen) {
		this.cardsSeen.add(seen);
	}
	
	// Getters for the instance variables
	public char getLastRoomVisited() {
		return lastRoomVisited;
	}
	public ArrayList<Card> getCardsSeen() {
		return cardsSeen;
	}
	public String getSuggestedRoom() {
		return suggestedRoom;
	}
	public String getSuggestedPerson() {
		return suggestedPerson;
	}
	public String getSuggestedWeapon() {
		return suggestedWeapon;
	}

	// EVERYTHING BELOW HERE IS FOR TESTING ONLY
	// THESE SHOULD NEVER BE USED IN PRACTICE
	public void setLastRoomVisited(char lastRoomVisited) {
		this.lastRoomVisited = lastRoomVisited;
	}
}
