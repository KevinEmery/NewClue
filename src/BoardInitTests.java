

// Doing a static import allows me to write assertEquals rather than
// Assert.assertEquals
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;




public class BoardInitTests {
	// I made this static because I only want to set it up one 
	// time (using @BeforeClass), no need to do setup before each test
	private static Board board;
	public static final int NUM_ROOMS = 11;
	public static final int NUM_ROWS = 23;
	public static final int NUM_COLUMNS = 24;

	@BeforeClass
	public static void setUp() {
		board = new Board();
	
	}
	@Test
	public void testRooms() {
		Map<Character, String> rooms = board.getRooms();
		// Ensure we read the correct number of rooms
		assertEquals(NUM_ROOMS, rooms.size());
		// Test retrieving a few from the hash, including the first
		// and last in the file and a few others
		assertEquals("Conservatory", rooms.get('C'));
		assertEquals("Ballroom", rooms.get('B'));
		assertEquals("Billiard", rooms.get('R'));
		assertEquals("Dining", rooms.get('D'));
		assertEquals("Walkway", rooms.get('W'));
	}

	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}

	// Test a doorway in each direction, plus two cells that are not
	// a doorway.
	// These cells are white on the planning spreadsheet
	@Test
	public void FourDoorDirections() {
		
		// Test one each RIGHT/LEFT/UP/DOWN
		RoomCell room =board.GetRoomCellAt(4,0);
		
		assertTrue(room.isDoorway());	
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());	
		room = board.GetRoomCellAt(4,4 );	
		assertTrue(room.isDoorway());	//good
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());//good
		room = board.GetRoomCellAt(3, 16);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());
		
		room = board.GetRoomCellAt(14,20);
		System.out.println(room.getRoom());

		
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());
		
		
		// Test that room pieces that aren't doors know it
		room = board.GetRoomCellAt(0, 0); //good
		assertFalse(room.isDoorway());	//good
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(board.calcIndex(0, 0));//good
		assertFalse(cell.isDoorway());		

	}

	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() 
	{
				
		Assert.assertEquals(17, board.getDoorways());
	}


	@Test
	public void testCalcIndex() {
		// Test each corner of the board
		assertEquals(0, board.calcIndex(0, 0));
		assertEquals(NUM_COLUMNS-1, board.calcIndex(0, NUM_COLUMNS-1));
		
		assertEquals(528, board.calcIndex(NUM_ROWS-1, 0));
		assertEquals(551, board.calcIndex(NUM_ROWS-1, NUM_COLUMNS-1));
		// Test a couple others
		assertEquals(25, board.calcIndex(1, 1));
		assertEquals(68, board.calcIndex(2, 20));		
	}

	// Test a few room cells to ensure the room initial is
	// correct.
	@Test
	public void testRoomInitials() {
		assertEquals('c', board.GetRoomCellAt(0, 0).getRoom());
		assertEquals('r', board.GetRoomCellAt(0, 8).getRoom());
		
		assertEquals('x', board.GetRoomCellAt(10, 0).getRoom());
		
		assertEquals('o', board.GetRoomCellAt(21, 21).getRoom());
		assertEquals('d', board.GetRoomCellAt(21, 10).getRoom());
	}

	// Test that an exception is thrown for a bad config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
		// overloaded Board ctor takes config file names
		Board b = new Board("etc/ClueLayoutBadColumns.csv", "etc/ClueLegend.txt");
		// You may change these calls if needed to match your function names
		// My loadConfigFiles has a try/catch, so I can't call it directly to
		// see test throwing the BadConfigFormatException
		
	}
	// Test that an exception is thrown for a bad config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
		// overloaded Board ctor takes config file name
		Board b = new Board("etc/ClueLayoutBadRoom.csv", "etc/ClueLegend.txt");
		
	}
	// Test that an exception is thrown for a bad config file
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
		// overloaded Board ctor takes config file name
		Board b = new Board("etc/ClueLayout.csv", "etc/ClueLegendBadFormat.txt");
	
	}
}
