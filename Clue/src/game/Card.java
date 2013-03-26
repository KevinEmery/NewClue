package game;

public class Card  {
	
	// Defines a card type enum
	public enum CardType {
		ROOM,
		WEAPON,
		PERSON;
	}
	
	private String name;
	private CardType cardType;
	
	// Default constructor
	public Card() {
		this("Temp", CardType.ROOM);
	}
	
	// Parameterized constructor
	public Card(String name, CardType cardType) {
		this.name = name;
		this.cardType = cardType;
	}
	
	// Getters
	public String getName() {
		return name;
	}
	public CardType getCardType() {
		return cardType;
	}
	
	// Overriding .equals method used for comparison and for contains
	@Override
	public boolean equals(Object object) {
		Card compare = (Card)object;
		if(compare.cardType.equals(cardType) && compare.name.equals(name))
			return true;
		return false;
	}
	
}
