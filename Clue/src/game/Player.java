package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
public class Player {
	private String name;
	private ArrayList<Card> myCards;
	private int startLocation;
	private Color color;
	private BoardCell currentCell;

	
	// Default constructor, should never be used other than in testing 
	public Player() {
		this("Mrs. Scarlet", 0, Color.red);
	}
	
	// Creates a new player with the given parameters, and initializes their hand as a new, empty array list.
	public Player(String name, int startLocation, Color color) {
		this.name = name;
		this.startLocation = startLocation;
		this.color = color;
		this.myCards = new ArrayList<Card>();
	}
	
	// Allows the computer player to disprove a suggestion, if possible.
	public Card disproveSuggestion(String person, String room, String weapon) {
		ArrayList<Card> validCards = new ArrayList<Card>();
		for(Card card: myCards) {
			if(card.getName().equals(person) || card.getName().equals(room) || card.getName().equals(weapon)) {
				validCards.add(card);
			}
		}
		
		if(validCards.size() > 0) 
			return validCards.get((new Random()).nextInt(validCards.size()));
		return null;
	}
	
	// Used as a part of dealing cards, this will add cards into a players hand. Should also update cardsSeen if a computer player
	public void addCardToHand(Card newCard) {
		this.myCards.add(newCard);
	}
	
	// These methods are overridden in the child classes
	public boolean isHumanPlayer() {
		return false;
	}
	public boolean isComputerPlayer() {
		return false;
	}
	
	// Getters for instance variables
	public String getName() {
		return name;
	}
	public ArrayList<Card> getMyCards() {
		return myCards;
	}
	public Color getColor() {
		return color;
	}
	public int getStartingLocation() {
		return startLocation;
	}

	public BoardCell getCurrentCell() {
		return currentCell;
	}
	
	public void setCurrentCell(BoardCell cell) {
		this.currentCell = cell;
	}

}
