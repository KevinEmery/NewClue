package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
public class Player {
	private String name;
	private ArrayList<Card> myCards;
	protected int location;
	private Color color;
	protected BoardCell currentCell;
	public boolean endturn;
	protected boolean canMakeAccusation = true;
	
	// Default constructor, should never be used other than in testing 
	public Player() {
		this("Mrs. Scarlet", 0, Color.red);
	}

	
	// Creates a new player with the given parameters, and initializes their hand as a new, empty array list.
	public Player(String name, int startLocation, Color color) {
		this.name = name;
		this.location = startLocation;
		this.currentCell = new WalkwayCell(location%25, location/25);
		this.color = color;
		this.myCards = new ArrayList<Card>();
	}
	
	// Allows the player to disprove a suggestion, if possible.
	public Card disproveSuggestion(String person, String room, String weapon) {
		ArrayList<Card> validCards = new ArrayList<Card>();
		
		// Searchs through the players cards and sees if it matches one of the suggested cards
		for(Card card: myCards) {
			if(card.getName().equals(person) || card.getName().equals(room) || card.getName().equals(weapon)) {
				validCards.add(card);
			}
		}
		
		// If one/more of those cards is there, return one of them
		if(validCards.size() > 0) 
			return validCards.get((new Random()).nextInt(validCards.size()));
		
		// Otherwise, return null
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
		return location;
	}
	public int getLocation() {
		return location;
	}
	

	public BoardCell getCurrentCell() {
		return currentCell;
	}
	
	
	// Draws a player using their given color with a black outline
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(location % 25 * ClueGame.CELL_WIDTH + 3, location / 25 * ClueGame.CELL_HEIGHT + 3, 24, 24);
		g.setColor(Color.black);
		g.drawOval(location % 25 * ClueGame.CELL_WIDTH + 3, location / 25 * ClueGame.CELL_HEIGHT + 3, 24, 24);
	}
	
	public void makeMove(Board board, int dieRoll, ClueGame game) {
		endturn=true;
	}

	public boolean isCanMakeAccusation() {
		return canMakeAccusation;
	}

	public void setCanMakeAccusation(boolean b) {
		canMakeAccusation=b;
		
	}

	public void forceMove(Board board, BoardCell currentCell) {
		
		this.currentCell = currentCell;
		this.location = board.calcIndex(currentCell.row, currentCell.column);
		
		board.repaint();
	}
	
	
	// EVERYTHING BELOW HERE IS FOR TESTING ONLY
	// THESE SHOULD NEVER BE USED IN PRACTICE
	public void setCurrentCell(BoardCell cell) {
		this.currentCell = cell;
	}


	

	

}
