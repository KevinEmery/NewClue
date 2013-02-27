package testing;

import java.io.FileNotFoundException;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import utilities.BadConfigFormatException;
import utilities.Board;
import utilities.BoardCell;
import utilities.RoomCell;

public class BoardTest {
	private Board testBoard;
	
	//Set up by initializing the board with a default constructor and telling it to load some files.
	@Before
	public void setup() {
		testBoard = new Board();
		testBoard.loadConfigFiles();
	}
	
	// Tests to ensure that 11 rooms were read in from the legend
	@Test
	public void testCorrectNumberRooms() {
		Assert.assertEquals(11, testBoard.getRooms().size());
	}
	
	// Checks each room to ensure that they are all read in correctly
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
	
	// Ensures that the board has the correct number of rows and columns
	@Test
	public void testRowsAndColumns() {
		Assert.assertEquals(25, testBoard.getNumRows());
		Assert.assertEquals(25, testBoard.getNumColumns());
	}
	
	// Comprehensive test suite for the doors
	@Test
	public void testDoors() {
		// Tests one door in each direction to ensure that direction was read in correctly
		Assert.assertEquals(RoomCell.DoorDirection.DOWN, testBoard.getRoomCellAt(6, 3));
		Assert.assertEquals(RoomCell.DoorDirection.UP, testBoard.getRoomCellAt(20,18));
		Assert.assertEquals(RoomCell.DoorDirection.RIGHT, testBoard.getRoomCellAt(19, 4));
		Assert.assertEquals(RoomCell.DoorDirection.LEFT, testBoard.getRoomCellAt(11, 19));
		
		// Tests a room cell to ensure that it knows it is not a doorway
		Assert.assertEquals(false, testBoard.getRoomCellAt(0, 0).isDoorway());
		// Tests a walkway cell to ensure that it knows it is not a doorway
		Assert.assertEquals(false, testBoard.getRoomCellAt(8, 6).isDoorway());
		
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
		Assert.assertEquals('S', testBoard.getRoomCellAt(0, 0).getRoomInitial());
		Assert.assertEquals('K', testBoard.getRoomCellAt(24, 24).getRoomInitial());
		Assert.assertEquals('H', testBoard.getRoomCellAt(13, 5).getRoomInitial());
		Assert.assertEquals('D', testBoard.getRoomCellAt(12, 22).getRoomInitial());
		
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
		b.loadConfigFiles();
	}
	
	// Bad config - incorrect legend format
	@Test (expected = BadConfigFormatException.class)
	public void testBadConfigLegndExceptions() throws BadConfigFormatException, FileNotFoundException {
		Board b = new Board("Board.csv", "BadLegend.csv");
		b.loadConfigFiles();
	}
	
	// Bad config - incorrect board format
	@Test (expected = BadConfigFormatException.class)
	public void testBadConfigBoardException() throws BadConfigFormatException, FileNotFoundException {
		Board b = new Board("BadBoard.csv", "Legend.csv");
		b.loadConfigFiles();
	}
}
