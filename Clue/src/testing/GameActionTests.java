package testing;

import static org.junit.Assert.fail;

import java.awt.Color;
import java.util.ArrayList;

import game.BoardCell;
import game.Card;
import game.ClueGame;
import game.ComputerPlayer;
import game.HumanPlayer;
import game.Player;
import game.Solution;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class GameActionTests {

	public static ClueGame myGame;
	public static Card colonelMustardCard;
	public static Card scarletCard;
	public static Card ropeCard;
	public static Card knifeCard;
	public static Card ballroomCard;
	public static Card hallCard;
	
	@BeforeClass
	public static void setup() {
		// Creates a new board using our files, and then loads those files.
		myGame = new ClueGame("etc/dummyPlayersFile.csv", "etc/dummyCardFile.csv", "etc/Board.csv", "etc/Legend.csv");
		myGame.loadConfigFiles();
		
		// Creates cards to be used in testing
		colonelMustardCard = new Card("Colonel Mustard", Card.CardType.PERSON);
		scarletCard = new Card("Mrs. Scarlet", Card.CardType.PERSON);
		ropeCard = new Card("Rope", Card.CardType.WEAPON);
		knifeCard = new Card("Knife", Card.CardType.WEAPON);
		ballroomCard = new Card("Ballroom", Card.CardType.ROOM);
		hallCard = new Card("Hall", Card.CardType.ROOM);
	}
	
	@Test
	public void testAccusation() {
		myGame.selectAnswer("Mrs. Scarlet", "Kitchen", "Revolver");
		
		// Sets a correct solution
		Solution playerAccusation = new Solution("Mrs. Scarlet", "Kitchen", "Revolver");
		
		// Checks the players accusation. Since this is correct, it should return true.
		Assert.assertTrue(myGame.checkAccusation(playerAccusation));
		
		// Creates a new, false accusation
		playerAccusation = new Solution("Mrs. Scarlet", "Conservatory", "Revolver");
		
		// Tests it, it should return false
		Assert.assertFalse(myGame.checkAccusation(playerAccusation));
	}
	
	// Tests that computers randomly select a location if a room is not within range
	@Test
	public void testTargetRandomSelection() {
		ComputerPlayer player = new ComputerPlayer();
		
		// Calculating targets from a walkway cell. This should path to another walkway cell randomly.
		myGame.getBoard().startTargets(8, 12, 2);
		
		// Initialize counter variables
		int loc_8_10Tot = 0;
		int loc_7_11Tot = 0;
		int loc_6_12Tot = 0;
		int loc_7_13Tot = 0;
		int loc_8_14Tot = 0;
		
		// Run the test 100 times
		for (int i = 0; i < 100; i++) {
			BoardCell selected = player.pickLocation(myGame.getBoard().getTargets());
			if (selected == myGame.getBoard().getCellAt(8, 10))
				loc_8_10Tot++;
			else if (selected == myGame.getBoard().getCellAt(7, 11))
				loc_7_11Tot++;
			else if (selected == myGame.getBoard().getCellAt(6, 12))
				loc_6_12Tot++;
			else if (selected == myGame.getBoard().getCellAt(7, 13))
				loc_7_13Tot++;
			else if (selected == myGame.getBoard().getCellAt(8, 14))
				loc_8_14Tot++;
			else
				fail("Invalid target selected");
		}
		
		// Ensure we have 100 total selections (fail should also ensure)
		Assert.assertEquals(100, loc_8_10Tot + loc_7_11Tot + loc_6_12Tot + loc_7_13Tot + loc_8_14Tot);
		
		// Ensure each target was selected more than ten times
		Assert.assertTrue(loc_8_10Tot > 10);
		Assert.assertTrue(loc_7_11Tot > 10);
		Assert.assertTrue(loc_6_12Tot > 10);
		Assert.assertTrue(loc_7_13Tot > 10);
		Assert.assertTrue(loc_8_14Tot > 10);
	}

	// Tests that, if there is a room in range and it is not the last visited, it goes there
	@Test
	public void testSelectionIntoRoom() {
		// Finds targets
		myGame.getBoard().startTargets(13, 7, 2);
		
		for (int i = 0; i < 10; i++) {
			// The player is built here so that the last room visited is always null
			ComputerPlayer player = new ComputerPlayer();
			
			// Picks the location, this should always select the doorway
			BoardCell selected = player.pickLocation(myGame.getBoard().getTargets());
			
			// If it's not the doorway, the test fails.
			if (selected != myGame.getBoard().getCellAt(13, 5))
				fail("Incorrect target selected");
		}
	}
	
	// Tests to ensure that if the only room within range is the room last visited, then a ocation is chosen at random
	@Test
	public void testRandomSelectionAfterVisitingRoom() {
		// Creates a new player, and sets the last room visited to the Hall
		ComputerPlayer player = new ComputerPlayer();
		myGame.getBoard().startTargets(13, 6, 1);
		player.setLastRoomVisited('H');
		
		int loc_13_5Tot = 0;
		int loc_13_7Tot = 0;
		int loc_14_6Tot = 0;
		int loc_12_6Tot = 0;
		
		for (int i = 0; i < 100; i++) {
			
			BoardCell selected = player.pickLocation(myGame.getBoard().getTargets());
			
			if (selected == myGame.getBoard().getCellAt(13, 5))
				loc_13_5Tot++;
			else if (selected == myGame.getBoard().getCellAt(13, 7))
				loc_13_7Tot++;
			else if (selected == myGame.getBoard().getCellAt(14, 6))
				loc_14_6Tot++;
			else if (selected == myGame.getBoard().getCellAt(12, 6))
				loc_12_6Tot++;
			else
				fail("Invalid target selected");			
		}
		
		Assert.assertEquals(100, loc_13_5Tot + loc_13_7Tot + loc_14_6Tot + loc_12_6Tot);
		
		Assert.assertTrue(loc_13_5Tot > 10);
		Assert.assertTrue(loc_13_7Tot > 10);
		Assert.assertTrue(loc_14_6Tot > 10);
		Assert.assertTrue(loc_12_6Tot > 10);	
	}
	
	// Tests to ensure that a player returns the correct card when asked to disprove a suggestion, or null if they cannot.
	@Test
	public void testDisprovingSuggestionOneCard() {
		// Creates a new player for testing
		Player p = new Player();
		
		// Adds cards to his hand.
		p.addCardToHand(colonelMustardCard);
		p.addCardToHand(scarletCard);
		p.addCardToHand(ropeCard);
		p.addCardToHand(knifeCard);
		p.addCardToHand(ballroomCard);
		p.addCardToHand(hallCard);
		
		// Assert that the player returns the correct card in the situation
		Assert.assertEquals(scarletCard, p.disproveSuggestion("Mrs. Scarlet", "Dining Room", "Revolver"));
		Assert.assertEquals(ropeCard, p.disproveSuggestion("Mrs.Peacock", "Dining Room", "Rope"));
		Assert.assertEquals(hallCard, p.disproveSuggestion("Mrs. Peacock", "Hall", "Revolver"));
		Assert.assertNull(p.disproveSuggestion("Mrs. Peacock", "Dining Room", "Revolver"));
	}
	
	// Tests to ensure that if a player can disprove something using more than one card, he randomly selects one of them.
	@Test
	public void testDisprovingSuggestionMultipleCards() {
		// Creates a new player for testing
		Player p = new Player();
		
		// Adds cards to his hand.
		p.addCardToHand(colonelMustardCard);
		p.addCardToHand(scarletCard);
		p.addCardToHand(ropeCard);
		p.addCardToHand(knifeCard);
		p.addCardToHand(ballroomCard);
		p.addCardToHand(hallCard);
		
		// Initialize counter variables
		int scarletCount = 0;
		int ropeCount = 0;
		int hallCount = 0;
		
		// Allows the player to disprove the suggestion 100 times, and counts how many times each card is returned
		for (int i = 0; i < 100; i++) {
			Card card = p.disproveSuggestion("Mrs. Scarlet", "Hall", "Rope");
			if (card.equals(scarletCard))
				scarletCount++;
			else if (card.equals(hallCard))
				hallCount++;
			else if (card.equals(ropeCard))
				ropeCount++;
			else
				fail("Invalid card returned");
		}
		
		// Checks to make sure that each card was returned more than 10 times
		Assert.assertTrue(scarletCount > 10);
		Assert.assertTrue(hallCount > 10);
		Assert.assertTrue(ropeCount > 10);	
	}
	
	// Tests to ensure that all players and asked and that the appropriate cards are returned in all situations
	@Test
	public void testDisprovingSuggestionAllPlayers() {
		
		// Creates an array list of players, adds all of the players to the list, and sets the game list to that
		ArrayList<Player> tempList = new ArrayList<Player>();
		tempList.add(new HumanPlayer("Mrs. Scarlet", 174, Color.red));
		tempList.add(new ComputerPlayer("Colonel Mustard", 450, Color.yellow));
		tempList.add(new ComputerPlayer("Mr. Green", 20, Color.green));
		tempList.add(new ComputerPlayer("Mrs. Peacock", 4, Color.blue));
		tempList.add(new ComputerPlayer("Professor Plum", 615, Color.pink));
		tempList.add(new ComputerPlayer("Mr. White", 609, Color.white));
		myGame.setPlayers(tempList);
		
		// Gives each player one card.
		myGame.getPlayers().get(0).addCardToHand(scarletCard);
		myGame.getPlayers().get(1).addCardToHand(colonelMustardCard);
		myGame.getPlayers().get(2).addCardToHand(ropeCard);
		myGame.getPlayers().get(3).addCardToHand(knifeCard);
		myGame.getPlayers().get(4).addCardToHand(ballroomCard);
		myGame.getPlayers().get(5).addCardToHand(hallCard);
		
		// Asserts that if the human player makes a suggestion that can only be disproven using their own cards, null is returned
		Assert.assertNull(myGame.handleSuggestion("Mrs. Scarlet", "Dining Room", "Revolver", myGame.getPlayers().get(0)));
		
		// Asserts that if a player makes a suggest that no one can disprove, null is returned
		Assert.assertNull(myGame.handleSuggestion("Mrs. Peacock", "Dining Room", "Revolver", myGame.getPlayers().get(0)));
		
		// Asserts that if the suggestion is such that only the human player can disprove it, they do
		Assert.assertEquals(scarletCard, myGame.handleSuggestion("Mrs. Scarlet", "Dining Room", "Revolver", myGame.getPlayers().get(1)));
		
		// Asserts that if multiple players have cards to disprove a suggestion, they both can given multiple opportunities
		// This tests that players are asked to disprove suggestions in random order
		// Also asserts that the computer player does not disprove their own suggestion.
		int scarletCount = 0;
		int ropeCount = 0;
		int nullCount = 0;
		for (int i = 0; i < 100; i++) {
			Card card = myGame.handleSuggestion("Mrs. Scarlet", "Hall", "Rope", myGame.getPlayers().get(5));
			if (card.equals(scarletCard))
				scarletCount++;
			else if (card.equals(hallCard))
				fail("Computer disproved their own suggestion");
			else if (card.equals(ropeCard))
				ropeCount++;
			else if (card.equals(null))
				nullCount++;
			else
				fail("Invalid card returned");
		}
		Assert.assertTrue(nullCount > 10);
		Assert.assertTrue(scarletCount > 10);
		Assert.assertTrue(ropeCount > 10);
	}
	
	
	@Test
	public void testMakingSuggestion() {
		
	}
}
