package testing;

import static org.junit.Assert.fail;
import game.BoardCell;
import game.ClueGame;
import game.ComputerPlayer;
import game.Solution;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class GameActionTests {

	public static ClueGame myGame;
	
	@BeforeClass
	public static void setup() {
		// Creates a new board using our files, and then loads those files.
		myGame = new ClueGame("etc/dummyPlayersFile.csv", "etc/dummyCardFile.csv", "etc/Board.csv", "etc/Legend.csv");
		myGame.loadConfigFiles();
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

	@Test
	public void testSelectionIntoRoom() {
		myGame.getBoard().startTargets(13, 7, 2);
		
		for (int i = 0; i < 10; i++) {
			// The player is built here so that the last room visited is always null
			ComputerPlayer player = new ComputerPlayer();
			BoardCell selected = player.pickLocation(myGame.getBoard().getTargets());
			
			if (selected != myGame.getBoard().getCellAt(13, 5))
				fail("Incorrect target selected");
		}
	}
	
	@Test
	public void testRandomSelectionAfterVisitingRoom() {
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
	
	@Test
	public void testDisprovingSuggestion() {
		
	}
	
	@Test
	public void testMakingSuggestion() {
		
	}
}
