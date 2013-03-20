package game;

public class Card {
	public enum CardType {
		ROOM,
		WEAPON,
		PERSON;
	}
	private String name;
		
	private CardType cardType;
	public Card() {
		this("Temp", CardType.ROOM);
	}
	public Card(String name, CardType cardType) {
		
		this.name = name;
		this.cardType = cardType;
	}
	public String getName() {
		return name;
	}
	public CardType getCardType() {
		return cardType;
	}
}
