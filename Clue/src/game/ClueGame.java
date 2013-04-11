package game;

import game.Card.CardType;
import game.AccusationPanel;
import gui.ClueGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class ClueGame extends JFrame {

	private ArrayList<Card> cards;
	private ArrayList<Card> originalDeck;
	private ArrayList<Player> players;
	private String playersFile;
	private String weaponsFile;
	private Board board;
	private Solution solution;
	private int noWeapons;
	private DetectiveNotes detectiveNotes;
	private AccusationPanel ac;
	private ClueGUI controlPanel;
	private boolean firstTurn = true;
	private int playerIndex;

	public final static int CELL_WIDTH = 30;
	public final static int CELL_HEIGHT = 30;

	// Sets up a listener that opens the detective notes when activated
	private class detectiveNotesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			detectiveNotes.setVisible(true);
		}
	}
	private class exitListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}

	}


	// Default constructor which calls with some base values
	public ClueGame() {
		this("etc/personFile.dat", "etc/weaponsFile.dat", "etc/Board.csv", "etc/Legend.csv");
	}

	// Parameterized constructor allowing us to set the files, and builds the GUI
	public ClueGame(String playersFile, String weaponsFile, String boardFile, String boardConfigFile) {
		// Loads the board using the given config files
		board = new Board(boardFile, boardConfigFile);

		// Sets the player and weapon file names, and loads those config files
		this.playersFile = playersFile;
		this.weaponsFile = weaponsFile;
		loadConfigFiles();
		board.setPlayers(players);
		selectAnswer();
		deal();

		// Sets up a new instance of detective notes
		detectiveNotes = new DetectiveNotes(originalDeck);

		// Sets the standard window information
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue");
		setSize(910, 1050);

		// Instantiates a new file menu
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");

		// Adds menu options to the file menu
		JMenuItem notesAction = new JMenuItem("Show Detective Notes");
		notesAction.addActionListener(new detectiveNotesListener());
		fileMenu.add(notesAction);
		JMenuItem exitAction = new JMenuItem("Exit");
		exitAction.addActionListener(new exitListener());
		fileMenu.add(exitAction);	

		//adds an accusation pop-up
		ac = new AccusationPanel(this);

		// Instantiates and adds the board
		add(board, BorderLayout.CENTER);		
		
		// Creates a card Menu
		CardMenu cardmenu=new CardMenu(players.get(0));
		add(cardmenu, BorderLayout.EAST);
		
		// Adds the file menu to the menu bar
		menuBar.add(fileMenu);

		// Adds the menu bar
		add(menuBar, BorderLayout.NORTH);	

		//creates the action menu
		controlPanel = new ClueGUI(this);
		add(controlPanel, BorderLayout.SOUTH);
	}

	// Deals all of the cards in the deck to the players.
	public void deal() {
		Random randInt = new Random();

		// Performs integer division to determine how many cards to deal out to each player
		int cardsToGive = cards.size()/players.size();

		// In case of an uneven distribution, this will determine the number of players who get one extra card.
		int extraCards = cards.size()%players.size();

		// This is the index of the card to be dealt
		int randomCardNo;

		// Iterates through every player
		for(int i=0; i< players.size(); ++i) {

			// Iterates through the number of cards to be given to each player
			for(int j=0; j<(i >= extraCards ? cardsToGive : cardsToGive+1); j++) {
				randomCardNo = randInt.nextInt(cards.size());
				players.get(i).addCardToHand(cards.get(randomCardNo));

				// Once the card is dealt, it is removed so that it is not dealt again
				cards.remove(randomCardNo);
			}
		}
	}

	// Loads all of the files associated with playing the game, including the board files
	public void loadConfigFiles() {

		// Initializes new arraylists
		cards = new ArrayList<Card>();
		players = new ArrayList<Player>();

		// Creates cards for the rooms, as specified by the Legend, ignoring the Walkway and Closet
		for(String str: board.getRooms().values()) {
			if(str.equalsIgnoreCase("Walkway")) continue;
			if(str.equalsIgnoreCase("Closet")) continue;
			cards.add(new Card(str, CardType.ROOM));
		}

		// Loads the player and card files
		try {
			loadPlayerFile();
			loadCardFile();
		} catch (FileNotFoundException e) {
			System.err.println("While trying to load config files, encountered a file not found: " + e.getMessage());
		}

		// Copies the deck as configured into a separate arraylist.
		originalDeck = new ArrayList<Card>(cards);

	}

	// Loads the player files
	// Player file format: player_name, color, (row, column)
	public void loadPlayerFile() throws FileNotFoundException {

		// Creates the file scanner
		FileReader playerFile = new FileReader(playersFile);
		Scanner playerIn = new Scanner(playerFile);

		// Splits the file around commas
		String[] lineParts;
		lineParts = playerIn.nextLine().split(",\\s+");

		// Adds a human player to the players list and cards list as specified in the file
		players.add(new HumanPlayer(lineParts[0],
				board.calcIndex(Integer.parseInt(lineParts[2].substring(1)), Integer.parseInt(lineParts[3].substring(0, lineParts[3].length()-1))),
				convertColor(lineParts[1])));
		cards.add(new Card(lineParts[0], CardType.PERSON));

		// Adds all of the computer players to the game and card list as specified
		while(playerIn.hasNext()) {
			lineParts = playerIn.nextLine().split(",\\s+");
			players.add(new ComputerPlayer(lineParts[0],
					board.calcIndex(Integer.parseInt(lineParts[2].substring(1)), Integer.parseInt(lineParts[3].substring(0, lineParts[3].length()-1))),
					convertColor(lineParts[1])));
			cards.add(new Card(lineParts[0], CardType.PERSON));
		}

		// Closes the players file
		playerIn.close();

	}

	// Loads the card file
	// Just a bunch of text lines with weapon names.
	public void loadCardFile() throws FileNotFoundException {


		// Opens a scanner for the file
		FileReader cardFile = new FileReader(weaponsFile);
		Scanner cardIn = new Scanner(cardFile);
		int weaponCount=0;

		// Reads in the weapons and adds them to the card list
		while(cardIn.hasNext()) {
			++weaponCount;
			cards.add(new Card(cardIn.nextLine(), CardType.WEAPON));
		}
		noWeapons = weaponCount;
		cardIn.close();
	}

	// This is called at the start of every game, and will just be used to call the selectAnswer function above
	public void selectAnswer() {
		Random randInt = new Random();

		ArrayList<Card> weapons = new ArrayList<Card>();
		ArrayList<Card> rooms = new ArrayList<Card>();

		// Sets the random numbers for the room and weapon
		String weapon="", room="", player="";

		// Iterates through sorts the weapons and rooms into their own lists
		for(Card card: cards) {
			if(card.getCardType().equals(Card.CardType.WEAPON)) {
				weapons.add(card);	
			}
			else if(card.getCardType().equals(Card.CardType.ROOM)) {
				rooms.add(card);
			}

		}

		// Selects a random weapon and removes it
		room = rooms.get(randInt.nextInt(rooms.size())).getName();
		cards.remove(new Card(room, Card.CardType.ROOM));	

		// Selects a random weapon and removes it
		weapon = weapons.get(randInt.nextInt(weapons.size())).getName();
		cards.remove(new Card(weapon, Card.CardType.WEAPON));	

		// Selects a random player, and removes them
		player = players.get(randInt.nextInt(players.size())).getName();
		cards.remove(new Card(player, Card.CardType.PERSON));

		// Sets the solution
		solution = new Solution(player, weapon, room);
	}

	// When a player mkes a suggestion, this function makes calls to different players and sees if they can disprove it.
	// If they can, a card is returned.
	public Card handleSuggestion(String person, String room, String weapon, Player accusingPerson, BoardCell currentCell) {
		System.out.println("Suggestion Made");
		ArrayList<Card> disprovingCards = new ArrayList<Card>();
		for(Player player: players) {
			// Moves the accused player to that cell
			if (person.equals(player.getName())) {
				player.forceMove(board, currentCell);
			}
			Card result = player.disproveSuggestion(person, room, weapon);
			if(result != null && !(accusingPerson == player)) {
				disprovingCards.add(result);
			}
		}
		
		// Updates the display results, and returns the appropriate variable
		controlPanel.cluePanel.guessPanel.updateGuess(person + " in the " + room + " with the " + weapon);
	
		if(disprovingCards.size() > 0) {
			Card result = disprovingCards.get((new Random()).nextInt(disprovingCards.size()));
			controlPanel.cluePanel.resultPanel.updateResult(result.getName());
			return result;
		} 
		
		controlPanel.cluePanel.resultPanel.updateResult("No new clue");
		return null;
	}

	// Checks to see if an accusation is correct or not
	public boolean checkAccusation(Solution solution) {
		if(this.solution.equals(solution)){
			return true;}
		return false;
	}

	public void checkAccusationHandler(Solution solution) {
		String message;
		if(!players.get(playerIndex).isCanMakeAccusation()) {
			ac.setVisible(false);
			message="You can only make one Accusation per turn";
			JOptionPane.showMessageDialog(null, message);
		} else {	
			players.get(playerIndex).setCanMakeAccusation(false);
			if(playerIndex==0){
				// Need to find a way to make this non-static
				if(checkAccusation(solution)){
					message="That is correct! You win!";
					JOptionPane.showMessageDialog(null, message);
					System.exit(0);
				}else{
					message="That is not correct";
					JOptionPane.showMessageDialog(null, message);
				}
			} else {

				if(checkAccusation(solution)){
					message ="Computer Player "+ players.get(playerIndex).getName() +
							" Accused "+solution.getPerson()+" of using "+solution.getWeapon()+
							" in the "+solution.getRoom()+" and was correct!";
					JOptionPane.showMessageDialog(null, message);
					System.exit(0);
				} else {
					message ="Computer Player "+ players.get(playerIndex).getName()+
							" Accused "+solution.getPerson()+" of using "+solution.getWeapon()+
							" in the "+solution.getRoom()+" and was incorrect";
					JOptionPane.showMessageDialog(null, message);
				}
				
			}
		}
	}
	
	// Used to convert color from a string to a java class
	public Color convertColor(String strColor) {
		Color color;
		try {
			// We can use reflection to convert the string to a color
			color = (Color) Class.forName("java.awt.Color").getField(strColor.trim()).get(null);
		} catch (Exception e) {
			color = null; // Color not defined
		}
		return color;
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

	public ArrayList<Card> getOriginalDeck() {
		return originalDeck;
	}

	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);
		// Adds the starting message
		String message="You are " + game.getPlayers().get(0).getName() + ", press Next Player to begin play";
		JOptionPane.showMessageDialog(null, message);

	}

	public void nextPlayer(){
		Random r=new Random();
		if(firstTurn){				//first turn done by Mrs. Scarlet
			playerIndex=0; 
			int dieRoll = r.nextInt(6) + 1;	//makes a dieroll from 1 to 6
			ClueGUI.TextInputFrame.updatePlayer(players.get(playerIndex).getName());
			ClueGUI.NumberDisplayPanel.updateRoll(dieRoll);
			players.get(playerIndex).makeMove(board,dieRoll, this);
			firstTurn=false;
		} else {										//all other turns following
			if(players.get(playerIndex).endturn) {	//if a players turn is ended
				playerIndex++;						//it moves to the next player
				if(playerIndex==6){playerIndex=0;}	//loops back around
				ClueGUI.TextInputFrame.updatePlayer(players.get(playerIndex).getName());
				int dieRoll=r.nextInt(6)+1;			//makes a dieroll from 1 to 6
				ClueGUI.NumberDisplayPanel.updateRoll(dieRoll);
				players.get(playerIndex).makeMove(board,dieRoll, this);

			} else {
				String message="You are not done with your turn";
				JOptionPane.showMessageDialog(null, message);
			}
		}
	}
	
	public void makeAccusation() {
		if(playerIndex==0 && players.get(0).isCanMakeAccusation()){
			ac.setVisible(true);
		} else if (playerIndex == 0) {
			String message="You can only make one accusation per turn";
			JOptionPane.showMessageDialog(null, message);
		}
	}

	
	// EVERYTHING BELOW HERE IS FOR TESTING ONLY
	// THESE SHOULD NEVER BE USED IN PRACTICE
	public void setPlayers(ArrayList<Player> newPlayers) {
		this.players = newPlayers;
	}

	// Sets the "answer" for the game to the parameters passed in, and removes these cards from the deck.
	public void selectAnswer(String person, String room, String weapon) {
		this.solution = new Solution(person, room, weapon);

		// Removes the cards from the deck.
		cards.remove(new Card(person, Card.CardType.PERSON));
		cards.remove(new Card(weapon, Card.CardType.WEAPON));
		cards.remove(new Card(room, Card.CardType.ROOM));

	}



}