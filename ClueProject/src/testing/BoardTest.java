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
<<<<<<< HEAD
	//set up by initializing the board with a default constructor and telling it to
	//load the proper config files
=======
	
	//Set up by initializing the board with a default constructor and telling it to load some files.
>>>>>>> 453a11f6c168a86c3803a7fb3d1664e5a240f2c8
	@Before
	public void setup() {
		testBoard = new Board();
		testBoard.loadConfigFiles();
	}
<<<<<<< HEAD
	//Test to make sure that we have the right legend, 
	//which should include the 9 rooms, a closet, and a walkway.
=======
	
	// Tests to ensure that 11 rooms were read in from the legend
>>>>>>> 453a11f6c168a86c3803a7fb3d1664e5a240f2c8
	@Test
	public void testCorrectNumberRooms() {
		Assert.assertEquals(11, testBoard.getRooms().size());
	}
<<<<<<< HEAD
	//Second test for the legend - make sure that the legend
	//mapped the right keys to the correct strings.
=======
	
	// Checks each room to ensure that they are all read in correctly
>>>>>>> 453a11f6c168a86c3803a7fb3d1664e5a240f2c8
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
	
<<<<<<< HEAD
	//make sure that the Board file was read correctly, i.e. it had the correct
	//number of rows and columns.
=======
	// Ensures that the board has the correct number of rows and columns
>>>>>>> 453a11f6c168a86c3803a7fb3d1664e5a240f2c8
	@Test
	public void testRowsAndColumns() {
		Assert.assertEquals(25, testBoard.getNumRows());
		Assert.assertEquals(25, testBoard.getNumColumns());
	}
<<<<<<< HEAD
	//make sure that the doors appeared in the correct locations and open
	//in the correct direction after loading.
=======
	
	// Comprehensive test suite for the doors
>>>>>>> 453a11f6c168a86c3803a7fb3d1664e5a240f2c8
	@Test
	public void testDoors() {
		// Tests one door in each direction to ensure that direction was read in correctly
		Assert.assertEquals(RoomCell.DoorDirection.DOWN, testBoard.getRoomCellAt(6, 3));
		Assert.assertEquals(RoomCell.DoorDirection.UP, testBoard.getRoomCellAt(20,18));
		Assert.assertEquals(RoomCell.DoorDirection.RIGHT, testBoard.getRoomCellAt(19, 4));
		Assert.assertEquals(RoomCell.DoorDirection.LEFT, testBoard.getRoomCellAt(11, 19));
<<<<<<< HEAD
		//also make sure that things that _aren't_ supposed to be doors aren't.
		//room
=======
		
		// Tests a room cell to ensure that it knows it is not a doorway
>>>>>>> 453a11f6c168a86c3803a7fb3d1664e5a240f2c8
		Assert.assertEquals(false, testBoard.getRoomCellAt(0, 0).isDoorway());
		// Tests a walkway cell to ensure that it knows it is not a doorway
		Assert.assertEquals(false, testBoard.getRoomCellAt(8, 6).isDoorway());
<<<<<<< HEAD
		//count up the doors.
=======
		
		// Counts all of the doors on the board, and makes sure we have the right count
>>>>>>> 453a11f6c168a86c3803a7fb3d1664e5a240f2c8
		int counter=0;
		for(BoardCell b: testBoard.getCells()) {
			if(b.isDoorway()) {
				++counter;
			}
		}
		Assert.assertEquals(19, counter);
	}
<<<<<<< HEAD
	//Select some random spots from rooms and ensure that they mapped correctly.
=======
	
	// Tests a few rooms on the board to ensure that they have the correct character associated with them.
>>>>>>> 453a11f6c168a86c3803a7fb3d1664e5a240f2c8
	@Test
	public void testRooms() {
		Assert.assertEquals('S', testBoard.getRoomCellAt(0, 0).getRoomInitial());
		Assert.assertEquals('K', testBoard.getRoomCellAt(24, 24).getRoomInitial());
		Assert.assertEquals('H', testBoard.getRoomCellAt(13, 5).getRoomInitial());
		Assert.assertEquals('D', testBoard.getRoomCellAt(12, 22).getRoomInitial());
		
	}
<<<<<<< HEAD
	//test calcIndex, pretty straightforward, make sure it's outputting no_rows*row + column.
=======
	
	// Test the calc index function, ensuring that it is still working properly
>>>>>>> 453a11f6c168a86c3803a7fb3d1664e5a240f2c8
	@Test
	public void testCalcIndex() {
		Assert.assertEquals(0, testBoard.calcIndex(0, 0));
		Assert.assertEquals(624,  testBoard.calcIndex(24, 24));
		Assert.assertEquals(312, testBoard.calcIndex(12, 12));
	}
<<<<<<< HEAD
	//test for exception throwing, given a bad legend .csv file.
=======
	
	// Tests that the appropriate errors are throw at the correct times.
	// File not found
	@Test (expected = FileNotFoundException.class)
	public void testFileNotFoundException() throws BadConfigFormatException, FileNotFoundException {
		Board b = new Board("Board.csv", "LegendNotThere.csv");
		b.loadConfigFiles();
	}
	
	// Bad config - incorrect legend format
>>>>>>> 453a11f6c168a86c3803a7fb3d1664e5a240f2c8
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
