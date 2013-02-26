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
	@Before
	public void setup() {
		testBoard = new Board();
		testBoard.loadConfigFiles();
	}
	@Test
	public void testCorrectNumberRooms() {
		Assert.assertEquals(11, testBoard.getRooms().size());
	}
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
	@Test
	public void testRowsAndColumns() {
		Assert.assertEquals(25, testBoard.getNumRows());
		Assert.assertEquals(25, testBoard.getNumColumns());
	}
	@Test
	public void testDoors() {
		Assert.assertEquals(RoomCell.DoorDirection.DOWN, testBoard.getRoomCellAt(6, 3));
		Assert.assertEquals(RoomCell.DoorDirection.UP, testBoard.getRoomCellAt(20,18));
		Assert.assertEquals(RoomCell.DoorDirection.RIGHT, testBoard.getRoomCellAt(19, 4));
		Assert.assertEquals(RoomCell.DoorDirection.LEFT, testBoard.getRoomCellAt(11, 19));
		
		//room
		Assert.assertEquals(false, testBoard.getRoomCellAt(0, 0).isDoorway());
		//walkway
		Assert.assertEquals(false, testBoard.getRoomCellAt(8, 6).isDoorway());
		int counter=0;
		for(BoardCell b: testBoard.getCells()) {
			if(b.isDoorway()) {
				++counter;
			}
		}
		Assert.assertEquals(19, counter);
	}
	@Test
	public void testRooms() {
		Assert.assertEquals('S', testBoard.getRoomCellAt(0, 0).getRoomInitial());
		Assert.assertEquals('K', testBoard.getRoomCellAt(24, 24).getRoomInitial());
		Assert.assertEquals('H', testBoard.getRoomCellAt(13, 5).getRoomInitial());
		Assert.assertEquals('D', testBoard.getRoomCellAt(12, 22).getRoomInitial());
		
	}
	@Test
	public void testCalcIndex() {
		Assert.assertEquals(0, testBoard.calcIndex(0, 0));
		Assert.assertEquals(624,  testBoard.calcIndex(24, 24));
		Assert.assertEquals(312, testBoard.calcIndex(12, 12));
	}
	@Test (expected = BadConfigFormatException.class)
	public void testExceptions() throws BadConfigFormatException, FileNotFoundException {
		Board b = new Board("Board.csv", "BadLegend.csv");
		b.loadConfigFiles();
	}
}
