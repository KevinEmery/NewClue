package game;

import java.util.ArrayList;

public class ClueGame {
	private ArrayList<Card> cards;
	private ArrayList<Player> players;
	private String playersFile;
	private String weaponsFile;
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
	public void selectAnswers() {
		
	}
	
	public void handleSuggestion(String person, String room, String weapon, Player accusingPerson) {
		
	}
	public boolean checkAccusation(Solution solution) {
		return false;
	}
}
