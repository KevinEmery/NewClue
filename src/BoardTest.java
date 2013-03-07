import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;


public class BoardTest {


	int [] boardSize = {23,23};	//setting board dimenstions to be 23x24
	private static Board board;
	@BeforeClass
	public static void setUp(){
		//board = new Board();//creating a board
		board = new Board("etc/Clue_map.csv");
		
	}

	@Test(expected = BadConfigFormatException.class)
	public void testFiles()throws BadConfigFormatException, FileNotFoundException{//for simplicity sake, I will use the crader fail files
		Board b = new Board("etc/Clue_map.csv", "etc/ClueLegendBadFormat.txt");
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
	public void testRooms() throws FileNotFoundException{
		Map<Character, String> rooms = board.getRooms();
		assertEquals(11, rooms.size());
		assertEquals(" Conservatory", rooms.get('C'));
		assertEquals(" Ballroom", rooms.get('B'));
		assertEquals(" Billiard room", rooms.get('R'));
		assertEquals(" Dining room", rooms.get('D'));
		assertEquals(" Walkway", rooms.get('W'));	
	}
	@Test
	public void doorTestandCalcIndex(){
		//tests door Direction
		RoomCell room = board.getRoomCellAt(4, 4);//this also checks calcIndex
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection0());
		assertEquals('C', room.getInitial());
		room = board.getRoomCellAt(4, 8);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection0());
		assertEquals('R', room.getInitial());
		room = board.getRoomCellAt(14, 17);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection0());
		assertEquals('O', room.getInitial());
		room = board.getRoomCellAt(15, 11);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection0());
		assertEquals('D', room.getInitial());
		// Test that room pieces that aren't doors know it
		room = board.getRoomCellAt(1, 14);
		assertFalse(room.isDoorway());	
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(board.calcIndex(0, 6));
		assertFalse(cell.isDoorway());	
		//tests correct number of doors
	}
	@Test
	public void doornum(){
		Assert.assertEquals(16, board.getNumDoorway());//I was lazy and just made somthing count the doors as they were entered
	}	
	@Test
	public void sizeanddim(){
		int totalCells = board.getNumColumns() * board.getNumRows();
		Assert.assertEquals(boardSize[0]*boardSize[1], totalCells);
		Assert.assertEquals(boardSize[0],board.getNumColumns());
		Assert.assertEquals(boardSize[1], board.getNumRows());

	}
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
		testList = board.getAdjList(board.calcIndex(14, 12));
		Assert.assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(board.calcIndex(5, 20));
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
		testList = board.getAdjList(board.calcIndex(14, 17));
		Assert.assertEquals(2, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(14, 16)));
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
		HashSet<Integer> testList = board.getAdjList(board.calcIndex(4, 4));
		Assert.assertTrue(testList.contains(board.calcIndex(4, 5)));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 4)));
		Assert.assertEquals(2, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(board.calcIndex(4, 8));
		
		Assert.assertEquals(RoomCell.DoorDirection.DOWN,board.getRoomCellAt(4,8).getDoorDirection0());
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(5, 8)));
		
		
		// Test beside a door direction LEFT
		testList = board.getAdjList(board.calcIndex(14, 17));
		Assert.assertEquals(2, testList.size());
		Assert.assertTrue(testList.contains(board.calcIndex(14, 16)));
		
		Assert.assertTrue(testList.contains(board.calcIndex(13, 17)));
	
		// Test beside a door direction UP
		testList = board.getAdjList(board.calcIndex(13, 11));
		Assert.assertEquals(4, testList.size());

		Assert.assertTrue(testList.contains(board.calcIndex(13, 10)));
		Assert.assertTrue(testList.contains(board.calcIndex(13, 12)));
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		
		HashSet<Integer> testList  = board.getAdjList(board.calcIndex(6, 0));
		Assert.assertEquals(1, testList.size());

		Assert.assertTrue(testList.contains(board.calcIndex(6, 1)));
		

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(board.calcIndex(5, 21));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 20)));
		Assert.assertTrue(testList.contains(board.calcIndex(5, 22)));
		Assert.assertEquals(2, testList.size());

		
	}

	@Test
	public void testTargetsSteps() {
		//1step
		board.calcTargets(14, 0, 1);
		Set<BoardCell> targets= board.getTargets();
		

		
	
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 1))));
		//2steps
		board.calcTargets(21, 7, 2);
		targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(20, 7))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(22, 7))));
		board.calcTargets(14, 0, 2);
		
		//4steps
		board.calcTargets(21, 7, 4);
		targets= board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 7))));
		
	
		//6steps		
		board.calcTargets(14, 0, 6);
		targets= board.getTargets();
		Assert.assertEquals(15, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 6))));
		
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 4))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 2))));	
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 2))));	
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
		
	}
}