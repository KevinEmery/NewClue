package testing;


import game.BadConfigFormatException;
import game.Board;
import game.BoardCell;
import game.RoomCell;
import java.io.FileNotFoundException;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	private Board testBoard;

	//set up by initializing the board with a default constructor and telling it to
	//load the proper config files
	@Before
	public void setup() {
		testBoard = new Board("Board.csv", "Legend.csv");
		testBoard.loadConfigFiles();
		testBoard.calcAdjacencies();
	}

	//Test to make sure that we have the right legend, 
	//which should include the 9 rooms, a closet, and a walkway.
	@Test
	public void testCorrectNumberRooms() {
		Assert.assertEquals(11, testBoard.getRooms().size());
	}

	//Second test for the legend - make sure that the legend
	//mapped the right keys to the correct strings.
	@Test
	public void testRoomMapping() {
		Assert.assertEquals("Conservatory", testBoard.getRooms().get('C'));
		Assert.assertEquals("Kitchen", testBoard.getRooms().get('K'));
		Assert.assertEquals("Ballroom", testBoard.getRooms().get('B'));
		Assert.assertEquals("Billiard Room", testBoard.getRooms().get('R'));
		Assert.assertEquals("Walkway", testBoard.getRooms().get('W'));
		Assert.assertEquals("Library", testBoard.getRooms().get('L'));
		Assert.assertEquals("Study", testBoard.getRooms().get('S'));
		Assert.assertEquals("Dining Room", testBoard.getRooms().get('D'));
		Assert.assertEquals("Lounge", testBoard.getRooms().get('O'));
		Assert.assertEquals("Hall", testBoard.getRooms().get('H'));
		Assert.assertEquals("Closet", testBoard.getRooms().get('X'));
	}
	

	//Make sure that the Board file was read correctly, i.e. it had the correct
	//number of rows and columns.
	@Test
	public void testRowsAndColumns() {
		Assert.assertEquals(25, testBoard.getNumRows());
		Assert.assertEquals(25, testBoard.getNumColumns());
	}

	
	//Make sure that the doors appeared in the correct locations and open
	//in the correct direction after loading.
	@Test
	public void testDoors() {
		// Tests one door in each direction to ensure that direction was read in correctly
		Assert.assertEquals(RoomCell.DoorDirection.DOWN, testBoard.getRoomCellAt(testBoard.calcIndex(6, 3)).getDoorDirection());
		Assert.assertEquals(RoomCell.DoorDirection.UP, testBoard.getRoomCellAt(testBoard.calcIndex(20,18)).getDoorDirection());
		Assert.assertEquals(RoomCell.DoorDirection.RIGHT, testBoard.getRoomCellAt(testBoard.calcIndex(19, 4)).getDoorDirection());
		Assert.assertEquals(RoomCell.DoorDirection.LEFT, testBoard.getRoomCellAt(testBoard.calcIndex(11, 19)).getDoorDirection());

		// Tests a room cell to ensure that it knows it is not a doorway
		Assert.assertEquals(false, testBoard.getCellAt(testBoard.calcIndex(0, 0)).isDoorway());
		// Tests a walkway cell to ensure that it knows it is not a doorway
		Assert.assertEquals(false, testBoard.getCellAt(testBoard.calcIndex(8, 6)).isDoorway());

		// Counts all of the doors on the board, and makes sure we have the right count
		int counter=0;
		for(BoardCell b: testBoard.getCells()) {
			if(b.isDoorway()) {
				++counter;
			}
		}
		Assert.assertEquals(19, counter);
	}

	// Tests a few rooms on the board to ensure that they have the correct character associated with them.
	@Test
	public void testRooms() {
		Assert.assertEquals('S', testBoard.getRoomCellAt(testBoard.calcIndex(0, 0)).getInitial());
		Assert.assertEquals('K', testBoard.getRoomCellAt(testBoard.calcIndex(24, 24)).getInitial());
		Assert.assertEquals('H', testBoard.getRoomCellAt(testBoard.calcIndex(13, 5)).getInitial());
		Assert.assertEquals('D', testBoard.getRoomCellAt(testBoard.calcIndex(12, 22)).getInitial());
		
	}

	// Test the calc index function, ensuring that it is still working properly
	@Test
	public void testCalcIndex() {
		Assert.assertEquals(0, testBoard.calcIndex(0, 0));
		Assert.assertEquals(624,  testBoard.calcIndex(24, 24));
		Assert.assertEquals(312, testBoard.calcIndex(12, 12));
	}

	// Tests that the appropriate errors are throw at the correct times.
	// File not found
	@Test (expected = FileNotFoundException.class)
	public void testFileNotFoundException() throws BadConfigFormatException, FileNotFoundException {
		Board b = new Board("Board.csv", "LegendNotThere.csv");
		b.loadRoomConfig();
		b.loadBoardConfig();
	}
	
	// Bad config - incorrect legend format
	@Test (expected = BadConfigFormatException.class)
	public void testBadConfigLegndExceptions() throws BadConfigFormatException, FileNotFoundException {
		Board b = new Board("Board.csv", "BadLegend.csv");
		b.loadRoomConfig();
		b.loadBoardConfig();
	}
	
	// Bad config - incorrect board format
	@Test (expected = BadConfigFormatException.class)
	public void testBadConfigBoardException() throws BadConfigFormatException, FileNotFoundException {
		Board b = new Board("BadBoard.csv", "Legend.csv");
		b.loadRoomConfig();
		b.loadBoardConfig();
	}
}
