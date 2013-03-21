package game;

import java.util.ArrayList;

public class ClueGame {
	private ArrayList<Card> cards;
	private ArrayList<Player> players;
	private String playersFile;
	private String weaponsFile;
	private Board board;
	private Solution solution;
	
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
		
	}
	
	// Loads all of the files associated with playing the game, including the board files
	public void loadConfigFiles() {
		board.loadConfigFiles();
		board.calcAdjacencies();
		loadPlayerFile();
		loadCardFile();
	}
	
	// Loads the player files
	private void loadPlayerFile() {
		cards = new ArrayList<Card>();
	}
	
	// Loads the card files
	private void loadCardFile() {
		players = new ArrayList<Player>();
	}
	
	
	// Sets the "answer" for the game to the parameters passed in, and removes these cards from the deck.
	public void selectAnswer(String person, String room, String weapon) {
		
	}
	
	// This is called at the start of every game, and will just be used to call the selectAnswer function above
	public void selectAnswer() {
		
	}
	
	// When a player mkes a suggestion, this function makes calls to different players and sees if they can disprove it
	public void handleSuggestion(String person, String room, String weapon, Player accusingPerson) {
		
	}
	
	// Checks to see if an accusation is correct or not
	public boolean checkAccusation(Solution solution) {
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
	public Solution getSolution() {
		return solution;
	}
}
