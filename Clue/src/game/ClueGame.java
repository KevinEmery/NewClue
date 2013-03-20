package game;

import java.util.ArrayList;

public class ClueGame {
	private ArrayList<Card> cards;
	private ArrayList<Player> players;
	private String playersFile;
	private String weaponsFile;
	private Board board;
	private Solution solution;
	public ClueGame() {
		//add call to this (string, string)
	}
	public ClueGame(String playersFile, String weaponsFile) {
		this.playersFile = playersFile;
		this.weaponsFile = weaponsFile;
	}
	public void deal() {
		
	}
	public void loadConfigFiles() {
		
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
	public void selectAnswers() {
		
	}
	
	public void handleSuggestion(String person, String room, String weapon, Player accusingPerson) {
		
	}
	public boolean checkAccusation(Solution solution) {
		return false;
	}
}
