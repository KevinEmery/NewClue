package testing;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import utilities.BadConfigFormatException;
import utilities.Board;
import utilities.BoardCell;
import utilities.BoardCell;
import utilities.Board;
import utilities.RoomCell;

public class BoardTest {
	private Board testBoard;
	//set up by initializing the board with a default constructor and telling it to
	//load the proper config files
	@Before
	public void setup() {
		testBoard = new Board("Board.csv", "Legend.csv");
		testBoard.loadConfigFiles();
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
	
	//make sure that the Board file was read correctly, i.e. it had the correct
	//number of rows and columns.
	@Test
	public void testRowsAndColumns() {
		Assert.assertEquals(25, testBoard.getNumRows());
		Assert.assertEquals(25, testBoard.getNumColumns());
	}
	//make sure that the doors appeared in the correct locations and open
	//in the correct direction after loading.
	@Test
	public void testDoors() {
		Assert.assertEquals(RoomCell.DoorDirection.DOWN, testBoard.getRoomCellAt(6, 3));
		Assert.assertEquals(RoomCell.DoorDirection.UP, testBoard.getRoomCellAt(20,18));
		Assert.assertEquals(RoomCell.DoorDirection.RIGHT, testBoard.getRoomCellAt(19, 4));
		Assert.assertEquals(RoomCell.DoorDirection.LEFT, testBoard.getRoomCellAt(11, 19));
		//also make sure that things that _aren't_ supposed to be doors aren't.
		//room
		Assert.assertEquals(false, testBoard.getRoomCellAt(0, 0).isDoorway());
		//walkway
		Assert.assertEquals(false, testBoard.getRoomCellAt(8, 6).isDoorway());
		//count up the doors.
		int counter=0;
		for(BoardCell b: testBoard.getCells()) {
			if(b.isDoorway()) {
				++counter;
			}
		}
		Assert.assertEquals(19, counter);
	}
	//Select some random spots from rooms and ensure that they mapped correctly.
	@Test
	public void testRooms() {
		Assert.assertEquals('S', testBoard.getRoomCellAt(0, 0).getRoomInitial());
		Assert.assertEquals('K', testBoard.getRoomCellAt(24, 24).getRoomInitial());
		Assert.assertEquals('H', testBoard.getRoomCellAt(13, 5).getRoomInitial());
		Assert.assertEquals('D', testBoard.getRoomCellAt(12, 22).getRoomInitial());
		
	}
	//test calcIndex, pretty straightforward, make sure it's outputting no_rows*row + column.
	@Test
	public void testCalcIndex() {
		Assert.assertEquals(0, testBoard.calcIndex(0, 0));
		Assert.assertEquals(624,  testBoard.calcIndex(24, 24));
		Assert.assertEquals(312, testBoard.calcIndex(12, 12));
	}
	//test for exception throwing, given a bad legend .csv file.
	@Test (expected = BadConfigFormatException.class)
	public void testExceptions() throws BadConfigFormatException, FileNotFoundException {
		Board b = new Board("Board.csv", "BadLegend.csv");
		b.loadConfigFiles();
	}
}
