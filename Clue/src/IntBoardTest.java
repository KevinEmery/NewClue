import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
/**Failing tests related to Room legend
(3) Spreadsheet document (or similar) with tests identified
Adjacency tests for (note that point value = minimum number of tests):

    (1) Locations with only walkways as adjacent locations
    (4) Locations that are at each edge of the board
    (2) Locations that are beside a room cell that is not a doorway
    (2) Locations that are adjacent to a doorway with needed direction (i.e., the adjacency list will include the doorway)
    (2) Locations that are doorways

Target tests for (note that point value = minimum number of tests):

    (4) Targets along walkways, at various distances
    (2) Targets that allow the user to enter a room
    (2) Targets calculated when leaving a room




**/
public class IntBoardTest {
	int [] boardSize = {23,23};							//setting board dimenstions to be 23x23
	static Board board = null;
	
// Creates a new board and loads the config files
@BeforeClass
public static void setup() {
	board = new Board("etc/Clue_Map.csv","etc/ClueLegend.txt");
	board.loadConfigFiles();
	
}
	@Test
	public void testAdjacency0()
	{
		IntBoard board=new IntBoard(4,4);
		HashSet<Integer> testList = board.getAdjList(0);
		Assert.assertTrue(testList.contains(4));
		Assert.assertTrue(testList.contains(1));
		Assert.assertEquals(2, testList.size());
	}
	@Test
	public void doorDirections() {
		RoomCell room = board.getRoomCellAt(4, 4);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection0());
		room = board.getRoomCellAt(4, 8);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection0());
		room = board.getRoomCellAt(14, 17);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection0());
		room = board.getRoomCellAt(15, 11);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection0());
		// Test that room pieces that aren't doors know it
		room = board.getRoomCellAt(0,0);
		assertFalse(room.isDoorway());	
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(board.calcIndex(0, 6));
		assertFalse(cell.isDoorway());
		Assert.assertEquals(17,board.doorways);

	}
	@Test
	public void testTargets0_3()
	{
		IntBoard board=new IntBoard(4,4);
		board.startTargets(0, 3);
		HashSet<Integer> targets= board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(12));
		Assert.assertTrue(targets.contains(9));
		Assert.assertTrue(targets.contains(1));
		Assert.assertTrue(targets.contains(6));
		Assert.assertTrue(targets.contains(3));
		Assert.assertTrue(targets.contains(4));
	}
	@Test
	public void testRoomsandcalcIndex() throws FileNotFoundException{
		Assert.assertEquals(11, board.getRooms().size());	//tell the number of rooms. walkway is a room
		Assert.assertEquals('C', board.getRoomCellAt(3, 0).getRoom());	//this inadvertantly tests calcIndex
		Assert.assertEquals('C', board.getRoomCellAt(4, 4).getRoom());
		Assert.assertEquals('R', board.getRoomCellAt(4, 8).getRoom());
		Assert.assertEquals('S', board.getRoomCellAt(3, 19).getRoom());
		Assert.assertEquals(11, board.getRooms().size());				//tests number of rooms
		Assert.assertEquals(board.getNumRows(), boardSize[0]);			//tests dimensions
		Assert.assertEquals(board.getNumColumns(), boardSize[1]);		//tests dimensions
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
		Board b = new Board("etc/ClueLayoutBadColumns.csv", "etc/ClueLegend.txt");
		b.loadRoomConfig();
		b.loadBoardConfig();
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
		Board b = new Board("etc/ClueLayoutBadRoom.csv", "etc/ClueLegend.txt");
		b.loadRoomConfig();
		b.loadBoardConfig();
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
		Board b = new Board("etc/ClueLayout.csv", "etc/ClueLegendBadFormat.txt");
		b.loadRoomConfig();
		b.loadBoardConfig();
	}
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		HashSet<Integer> testList = board.getAdjList(board.calcIndex(0, 0));
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(board.calcIndex(4, 0));
		Assert.assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(board.calcIndex(15, 20));
		Assert.assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(board.calcIndex(18, 11));
		Assert.assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(board.calcIndex(9, 15 ));
		Assert.assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(board.calcIndex(6, 18));
		Assert.assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		HashSet<Integer> testList = board.getAdjList(board.calcIndex(11, 6));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(11, 7)));

		// TEST DOORWAY LEFT 
		testList = board.getAdjList(board.calcIndex(3, 19));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(3, 18)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(board.calcIndex(4, 8));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(5, 8)));
		//TEST DOORWAY UP
		testList = board.getAdjList(board.calcIndex(15, 11));
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(14, 11)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		HashSet<Integer> testList = board.getAdjList(board.calcIndex(4, 5));
		Assert.assertEquals(4, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(4, 4)));
		Assert.assertTrue(testList.contains(board.calcIndex(4, 6)));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 5)));
		Assert.assertTrue(testList.contains(board.calcIndex(3, 5)));

	
		// Test beside a door direction DOWN
		testList = board.getAdjList(board.calcIndex(6, 15));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 15)));
		Assert.assertTrue(testList.contains(board.calcIndex(6, 14)));
		Assert.assertTrue(testList.contains(board.calcIndex(6, 16)));
		Assert.assertEquals(3, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(board.calcIndex(14, 16));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 16)));
		Assert.assertTrue(testList.contains(board.calcIndex(13, 16)));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 17)));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 15)));
		Assert.assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(board.calcIndex(13, 11));
		//Assert.assertTrue(testList.contains(board.calcIndex(13, 10)));
		Assert.assertTrue(testList.contains(board.calcIndex(13, 12)));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 11)));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 11)));
		Assert.assertEquals(4, testList.size());//3?
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, just one walkway piece
		HashSet<Integer> testList = board.getAdjList(board.calcIndex(0, 4));
		Assert.assertTrue(testList.contains(5));
		Assert.assertEquals(2, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(board.calcIndex(6, 0));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 0)));
		Assert.assertTrue(testList.contains(board.calcIndex(6, 1)));
		Assert.assertEquals(2, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(board.calcIndex(5, 20));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 19)));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 21)));
		Assert.assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(board.calcIndex(15,7));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 8)));
		Assert.assertTrue(testList.contains(board.calcIndex(15, 6)));
		Assert.assertTrue(testList.contains(board.calcIndex(14, 7)));
		Assert.assertTrue(testList.contains(board.calcIndex(16, 7)));
		Assert.assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(board.calcIndex(22, 15));		
		Assert.assertTrue(testList.contains(board.calcIndex(21, 15)));
		Assert.assertTrue(testList.contains(board.calcIndex(22, 16)));
		Assert.assertEquals(2, testList.size());
		
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(board.calcIndex(13, 21));
		Assert.assertEquals(2, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(13, 20)));
		Assert.assertTrue(testList.contains(board.calcIndex(12, 21)));
		
		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(board.calcIndex(5, 3));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 2)));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 4)));
		Assert.assertTrue(testList.contains(board.calcIndex(6, 3)));
		Assert.assertEquals(3, testList.size());
	}
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(1, 4, 1);
		Set<BoardCell> targets= board.getTargets();		
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(1, 5))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(0, 4))));	
		
		board.calcTargets(0, 5, 1);
		targets= board.getTargets();		
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(0, 4))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(0, 6))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(1, 5))));			
	}
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		
		Set<BoardCell> targets= board.getTargets();
				
		board.calcTargets(1, 4, 2);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(0, 5))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(1, 6))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(2, 5))));			
	}
	
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(21, 7, 4);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(5, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 7))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 7))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(20, 6))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(18, 6))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(22, 6))));
	}
	

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(21, 7, 6);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 5))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 7))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 7))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 7))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(22, 6))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(20, 6))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(18, 6))));	
	}	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(3, 5, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(5, targets.size());

		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 4))));
		// directly up and down
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 5))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(1, 5))));
		// one up/down, one left/right
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 6))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(2, 6))));
		
	}
	
	// Test getting into room, doesn't require all steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(12, 7, 3);
		Set<BoardCell> targets= board.getTargets();
		//Assert.assertEquals(12, targets.size());
		// directly up and down
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 7))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 7))));
		// directly right (can't go left)
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 10))));
		// right then down
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 9))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 7))));
		// down then left/right
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 6))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 8))));
		// right then up
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 8))));
		// into the rooms
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 6))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 6))));		
		// 
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 7))));		
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 8))));		
		
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(3, 19, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(3, 18))));
		// Take two steps
		board.calcTargets(3, 19, 2);
		targets= board.getTargets();		
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(3, 17))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(2, 18))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(4, 18))));
	}

}
