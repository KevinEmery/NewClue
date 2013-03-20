package testing;

import game.Card;
import game.ClueGame;
import game.Player;

import java.awt.Color;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

public class GameSetupTests {

	public static ClueGame myGame;
	public static Card colonelMustardCard;
	public static Card ropeCard;
	public static Card ballroomCard;
	
	@BeforeClass
	// Creates a new game from the default constructor and loads the appropriate config files
	public static void setup() {
		myGame = new ClueGame();
		myGame.loadConfigFiles();
		colonelMustardCard = new Card("Colonel Mustard", Card.CardType.PERSON);
		ropeCard = new Card("Rope", Card.CardType.WEAPON);
		ballroomCard = new Card("Ballroom", Card.CardType.ROOM);		
	}
	
	// Tests that the players ere loaded out of the file correctly
	@Test
	public void testPlayerLoading() {
		// There should be six players
		Assert.assertEquals(6, myGame.getPlayers().size());
		
		// Checks the first player to see if they are human and if their info is correct.
		Assert.assertTrue(myGame.getPlayers().get(0).isHumanPlayer());
		Assert.assertEquals("Mrs. Scarlet", myGame.getPlayers().get(0).getName());
		Assert.assertEquals(INSERT_STARTING_LOCATION, myGame.getPlayers().get(0).getStartingLocation());
		Assert.assertEquals(Color.red, myGame.getPlayers().get(0).getColor());
		
		// Checks the name, starting location, and color of the first computer in the file. Also checks to make sure it is a computer player
		Assert.assertTrue(myGame.getPlayers().get(1).isComputerPlayer());
		Assert.assertEquals("Colonel Mustard", myGame.getPlayers().get(1).getName());
		Assert.assertEquals(INSERT_STARTING_LOCATION, myGame.getPlayers().get(1).getStartingLocation());
		Assert.assertEquals(Color.yellow, myGame.getPlayers().get(1).getColor());
		
		// Checks the name, starting location, and color of the last computer in the file. Also checks to make sure it is a computer player
		Assert.assertTrue(myGame.getPlayers().get(5).isComputerPlayer());
		Assert.assertEquals("Mrs. Peacock", myGame.getPlayers().get(5).getName());
		Assert.assertEquals(INSERT_STARTING_LOCATION, myGame.getPlayers().get(5).getStartingLocation());
		Assert.assertEquals(Color.blue, myGame.getPlayers().get(5).getColor());
	}

	@Test
	public void testCardLoading() {
		// Asserts that the size of the deck is correct
		Assert.assertEquals(21, myGame.getCards().size());

		// Checks that there is the correct number of each card.
		int weaponCount = 0;
		int personCount = 0;
		int roomCount = 0;
		for (Card card : myGame.getCards()) {
			if (card.getCardType().equals(Card.CardType.WEAPON))
				weaponCount++;
			else if (card.getCardType().equals(Card.CardType.PERSON))
				personCount++;
			else if (card.getCardType().equals(Card.CardType.ROOM))
				roomCount++;
		}
		Assert.assertEquals(6, weaponCount);
		Assert.assertEquals(6, personCount);
		Assert.assertEquals(9, roomCount);
		
		// Assert that a few of the cards are in the deck, one of each type
		Assert.assertTrue(myGame.getCards().contains(colonelMustardCard));
		Assert.assertTrue(myGame.getCards().contains(ropeCard));
		Assert.assertTrue(myGame.getCards().contains(ballroomCard));
	}
	
	@Test
	public void testDeal() {
		// Deals out all of the cards
		myGame.selectAnswer();
		myGame.deal();
		
		// Initializes information to be checked
		int total = 0;
		int minCount = 10;
		int maxCount = 0;
		int colonelCount = 0;
		int ropeCount = 0;
		int ballroomCount = 0;
		boolean goodDistribution = false;
		boolean colonelCardBool = false;
		boolean ropeCardBool = false;
		boolean ballroomCardBool = false;
		
		for (Player p : myGame.getPlayers()) {
			int temp = p.getMyCards().size();
			total += temp;
			if (temp > maxCount)
				maxCount = temp;
			if (temp < minCount)
				minCount = temp;
			if (p.getMyCards().contains(colonelMustardCard)) {
				colonelCardBool = true;
				colonelCount++;
			} if (p.getMyCards().contains(ropeCard)) {
				ropeCardBool = true;
				ropeCount++;
			} if (p.getMyCards().contains(ballroomCard) {
				ballroomCardBool = true;
				ballroomCount++;
			}
		}
		if (maxCount - minCount <= 1)
			goodDistribution = true;
			
		// Checks to make sure all the cards were dealt out, except for the three in the answer
		Assert.assertEquals(18, total);
		
		// Checks to make sure that the maximum difference between the minimum and maximum number of cards is less than or equal to 1
		Assert.assertTrue(goodDistribution);
		
		// Assert that some cards have been distributed, and that they're only in one hand.
		Assert.assertTrue(colonelCardBool);
		Assert.assertTrue(ropeCardBool);
		Assert.assertTrue(ballroomCardBool);
		Assert.assertEquals(1, colonelCount);
		Assert.assertEquals(1, ropeCount);
		Assert.assertEquals(1, ballroomCount);
	}
	
}
