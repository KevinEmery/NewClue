import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Map;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;


public class BoardTest {


	int [] boardSize = {23,23};	//setting board dimenstions to be 23x24
	private static Board board;
	private static IntBoard intboard;
	@BeforeClass
	public static void setUp(){
	 board = new Board();	//creating a board
	 intboard = new IntBoard();	//creating a new IntBoard
	}
	
	@Test(expected = BadConfigFormatException.class)
	public void testFiles()throws BadConfigFormatException, FileNotFoundException{//for simplicity sake, I will use the crader fail files
		Board b = new Board("etc/ClueLayout.csv", "etc/ClueLegendBadFormat.txt");
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
		
		//room legend-part2

		//failing tests-part 2

	}
	@Test
	public void doorTest(){
		//tests door Direction
		RoomCell room = board.getRoomCellAt(4, 3);//this also checks calcIndex
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection0());
		room = board.getRoomCellAt(4, 8);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection0());
		room = board.getRoomCellAt(15, 18);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection0());
		room = board.getRoomCellAt(14, 11);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection0());
		// Test that room pieces that aren't doors know it
		room = board.getRoomCellAt(14, 14);
		assertFalse(room.isDoorway());	
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(board.calcIndex(0, 6));
		assertFalse(cell.isDoorway());	
		//tests correct number of doors
		Assert.assertEquals(16, board.getNumDoorway());
		Assert.assertEquals(boardSize[0],board.getNumColumns());
		Assert.assertEquals(boardSize[1], board.getNumRows());
		int totalCells = board.getNumColumns() * board.getNumRows();
		Assert.assertEquals(529, totalCells);

	}
}