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

	// Used for testing, could also be used in determining the flow of the game
	@Override
	public boolean isComputerPlayer() {
		return true;
	}
	
	// Out of a list of possible locations, picks a location to travel to.
	public BoardCell pickLocation(Set<BoardCell> targets) {
		int randInt = (new Random()).nextInt(targets.size());
		int counter=0;
		BoardCell targetCell=null;
		for(BoardCell cell: targets) {
			if(counter == randInt) {
				targetCell = cell;
			}
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
		
		ArrayList<Card> peopleList = new ArrayList<Card>();
		ArrayList<Card> weaponList = new ArrayList<Card>();
		
		for (Card c : game.getOriginalDeck()) {
			if (!cardsSeen.contains(c) && !getMyCards().contains(c)) {
				if (c.getCardType().equals(CardType.PERSON)) {
					peopleList.add(c);
				} else if (c.getCardType().equals(CardType.WEAPON)) {
					weaponList.add(c);
				}
			}
		}
		
		Random randInt = new Random();
		
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
