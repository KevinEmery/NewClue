package game;

import game.Card.CardType;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.NumberFormat.Field;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class ClueGame {
	private ArrayList<Card> cards;
	private ArrayList<Player> players;
	private String playersFile;
	private String weaponsFile;
	private Board board;
	private Solution solution;
	private int noWeapons;
	// Be sure to trim the color, we don't want spaces around the name
	public Color convertColor(String strColor) {
		Color color;
		try {
			// We can use reflection to convert the string to a color
			color = (Color) Class.forName("java.awt.Color").getField(strColor.trim()).get(null);
		} catch (Exception e) {
			color = null; // Not defined }
		}
		return color;
	}
	// Default constructor which calls with some base values
	public ClueGame() {
		this("etc/dummyPlayersFile.csv", "etc/dummyCardFile.csv", "etc/Board.csv", "etc/Legend.csv");
	}
	
	// Parameterized constructor allowing us to set the files
	public ClueGame(String playersFile, String weaponsFile, String boardFile, String boardConfigFile) {
		this.playersFile = playersFile;
		this.weaponsFile = weaponsFile;
		this.board = new Board(boardFile, boardConfigFile);		
	}
	
	// Deals all of the cards in the deck to the players.
	public void deal() {
		Random randInt = new Random();
		int cardsToGive = cards.size()/players.size();
		int extraCards = cards.size()%players.size();
		int randomIndex = randInt.nextInt(players.size());
		int randomCardNo=randInt.nextInt(cards.size());
		for(int i=0; i< players.size(); ++i) {
			for(int j=0; j<(i >= extraCards ? cardsToGive : cardsToGive+1); j++) {
				System.out.println("Cards.size: " + cards.size());
				randomCardNo = randInt.nextInt(cards.size());
				players.get(i).addCardToHand(cards.get(randomCardNo));
				cards.remove(randomCardNo);
			}
		}
	}
	
	// Loads all of the files associated with playing the game, including the board files
	public void loadConfigFiles() {
		board.loadConfigFiles();
		board.calcAdjacencies();
		cards = new ArrayList<Card>();
		players = new ArrayList<Player>();
		for(String str: board.getRooms().values()) {
			if(str.equalsIgnoreCase("Walkway")) continue ;
			if(str.equalsIgnoreCase("Closet")) continue;
			cards.add(new Card(str, CardType.ROOM));
		}
		try {
			loadPlayerFile();
			loadCardFile();
		} catch (FileNotFoundException e) {
			// WILL FIX LATER
			e.printStackTrace();
		}
		
	}
	
	// Loads the player files
	//player file format:
	//player_name, color, (row, column)
	public void loadPlayerFile() throws FileNotFoundException {
		FileReader playerFile = new FileReader(playersFile);
		Scanner playerIn = new Scanner(playerFile);
		String[] lineParts;
		lineParts = playerIn.nextLine().split(",\\s+");
		players.add(new HumanPlayer(lineParts[0],  
					board.calcIndex(Integer.parseInt(lineParts[2].substring(1)), Integer.parseInt(lineParts[3].substring(0, lineParts[3].length()-1))), 
					convertColor(lineParts[1])));
		cards.add(new Card(lineParts[0], CardType.PERSON));
		while(playerIn.hasNext()) {
			lineParts = playerIn.nextLine().split(",\\s+");
			players.add(new ComputerPlayer(lineParts[0],  
						board.calcIndex(Integer.parseInt(lineParts[2].substring(1)), Integer.parseInt(lineParts[3].substring(0, lineParts[3].length()-1))), 
						convertColor(lineParts[1])));
			cards.add(new Card(lineParts[0], CardType.PERSON));
		}
		playerIn.close();
		
	}
	
	// Loads the card file
	//just a bunch of text lines, 
	//with weapon names.
	public void loadCardFile() throws FileNotFoundException {
		int weaponCount=0;
		FileReader cardFile = new FileReader(weaponsFile);
		Scanner cardIn = new Scanner(cardFile);
		while(cardIn.hasNext()) {
			++weaponCount;
			cards.add(new Card(cardIn.nextLine(), CardType.WEAPON));
		}
		noWeapons = weaponCount;
		cardIn.close();
	}
	
	
	// Sets the "answer" for the game to the parameters passed in, and removes these cards from the deck.
	public void selectAnswer(String person, String room, String weapon) {
		this.solution = new Solution(person, room, weapon);
		cards.remove(new Card(person, Card.CardType.PERSON));
		cards.remove(new Card(weapon, Card.CardType.WEAPON));
		cards.remove(new Card(room, Card.CardType.ROOM));
		// Removes the cards from the deck.
	}
	
	// This is called at the start of every game, and will just be used to call the selectAnswer function above
	public void selectAnswer() {
		Random randInt = new Random();
		int roomNumber = randInt.nextInt(9);
		int weaponNumber = randInt.nextInt(noWeapons);
		int currentWeapon=0, currentRoom=0;
		String weaponName="", roomName="", player="";
		for(Card card: cards) {
			if(card.getCardType().equals(Card.CardType.WEAPON)) {
				++currentWeapon;
				if(currentWeapon == weaponNumber) {
					weaponName = card.getName();
					cards.remove(card);
				}
				
			}
			if(card.getCardType().equals(Card.CardType.ROOM)) {
				if(currentRoom == roomNumber) {
					roomName = card.getName();
					cards.remove(card);	
				}
				++currentRoom;
			}
			
		}
		player = players.get(randInt.nextInt(players.size())).getName();
		cards.remove(new Card(player, Card.CardType.PERSON));
		solution = new Solution(player, weaponName, roomName);
		
	}
	
	// When a player mkes a suggestion, this function makes calls to different players and sees if they can disprove it.
	// If they can, a card is returned.
	public Card handleSuggestion(String person, String room, String weapon, Player accusingPerson) {
		ArrayList<Card> disprovingCards = new ArrayList<Card>();
		for(Player player: players) {
			Card result = player.disproveSuggestion(person, room, weapon);
			if(result != null && !(accusingPerson ==  player)) {
				disprovingCards.add(result);
			}
		}
		if(disprovingCards.size() > 0) return disprovingCards.get((new Random()).nextInt(disprovingCards.size()));
		// If accusing person is a computer, draw the info from their getters. Otherwise, it comes from the GUI
		return null;
	}
	
	// Checks to see if an accusation is correct or not
	public boolean checkAccusation(Solution solution) {
		if(this.solution.equals(solution))
			return true;
		return false;
	}
	
	// Getters for various instance variables
	public Board getBoard() {
		return board;
	}
	public ArrayList<Card> getCards() {
		return cards;
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public String getPlayersFile() {
		return playersFile;
	}
	public String getWeaponsFile() {
		return weaponsFile;
	}
	
	// EVERYTHING BELOW HERE IS FOR TESTING ONLY
	// THESE SHOULD NEVER BE USED IN PRACTICE
	public void setPlayers(ArrayList<Player> newPlayers) {
		this.players = newPlayers;
	}
}
