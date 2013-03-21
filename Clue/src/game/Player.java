package game;

import java.awt.Color;
import java.util.ArrayList;

public class Player {
	private String name;
	private ArrayList<Card> myCards;
	private int startLocation;
	private Color color;
	
	public Player(String name, int startLocation, Color color) {
		this.name = name;
		this.startLocation = startLocation;
		this.color = color;
		this.myCards = new ArrayList<Card>();
	}
	public Card disproveSuggestion(String person, String room, String weapon) {
		return new Card();
	}
	public String getName() {
		return name;
	}
	public ArrayList<Card> getMyCards() {
		return myCards;
	}
	public boolean isHumanPlayer() {
		return false;
	}
	public boolean isComputerPlayer() {
		return false;
	}
}
