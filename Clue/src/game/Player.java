package game;

import java.awt.Color;
import java.util.ArrayList;

public class Player {
	private String name;
	private ArrayList<Card> myCards;
	private int startLocation;
	private Color color;
	private int location;
	
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
		return new Card();
	}
	
	// Used as a part of dealing cards, this will add cards into a players hand
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

	public int getLocation() {
		return location;
	}
}
