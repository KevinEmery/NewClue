import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.HashSet;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;


public class IntBoardTest {


	int [] boardSize = {23,24};	//setting board dimenstions to be 23x24
	Board board = new Board();	//creating a board
	IntBoard intBoard= new IntBoard();	//creating a new IntBoard

	//are these next two adjacency tests?



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


		Assert.assertEquals(11, board.getRooms().size());	//tell the number of rooms. walkway is a room

		Assert.assertEquals('C', board.getRoomCellAt(3, 0).getRoom());//room initials and CalcIndex
		Assert.assertEquals('C', board.getRoomCellAt(4, 4).getRoom());//r?
		Assert.assertEquals('R', board.getRoomCellAt(4, 8).getRoom());
		Assert.assertEquals('S', board.getRoomCellAt(3, 19).getRoom()); //c?
		Assert.assertEquals(board.getNumRows(), boardSize[0]);//board Dimension {507
		Assert.assertEquals(board.getNumColumns(), boardSize[0]);//boardDimesnsion {0
		//door direction
		//room legend-part2

		//failing tests-part 2

	}
	@Test
	public void doorTest(){
		//tests door Direction
		RoomCell room = board.getRoomCellAt(4, 3);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getRoomCellAt(4, 8);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
		room = board.getRoomCellAt(15, 18);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());
		room = board.getRoomCellAt(14, 11);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());
		// Test that room pieces that aren't doors know it
		room = board.getRoomCellAt(14, 14);
		assertFalse(room.isDoorway());	
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(board.calcIndex(0, 6));
		assertFalse(cell.isDoorway());	
		//tests correct number of doors
		int numDoors = 0;
		int totalCells = board.getNumColumns() * board.getNumRows();
		Assert.assertEquals(506, totalCells);
		for (int i=0; i<totalCells; i++)
		{
			BoardCell cell1 = board.getCellAt(i);
			if (cell1.isDoorway())
				numDoors++;
		}
		Assert.assertEquals(16, numDoors);
	

	}
}