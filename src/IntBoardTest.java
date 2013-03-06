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
		Assert.assertEquals('C', board.getRoomCellAt(4, 4).getRoom());
		Assert.assertEquals('R', board.getRoomCellAt(4, 8).getRoom());
		Assert.assertEquals('S', board.getRoomCellAt(3, 19).getRoom());
		Assert.assertEquals(board.getNumRows(), boardSize[0]);//board Dimension
		Assert.assertEquals(board.getNumColumns(), boardSize[0]);//boardDimesnsion
		//door direction
		//room legend-part2

		//failing tests-part 2

	}
	@Test
	public void doorTest(){
		//assert.assertEquals()

	}
}