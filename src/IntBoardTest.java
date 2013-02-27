import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;


public class IntBoardTest {

	
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
		int [] boardSize = {23,23};
		Board board = new Board(boardSize[0],boardSize[1]);
		IntBoard intBoard= new IntBoard();
		Assert.assertEquals(10, board.getNumRooms());
		Scanner s=new Scanner(new BufferedReader(new FileReader("etc/Clue_map.csv")));
		s.useDelimiter("[,\\n]");
		int i=0;
		while(s.hasNext()){
			int[] cellArray = intBoard.calcRowCol(i);
			Assert.assertEquals(board.getRoomCellAt(cellArray[0],cellArray[1]).getRoom(), s.next().toString());
			if(s.next().toString().length()==2){
				Assert.assertEquals(board.getRoomCellAt(cellArray[0],cellArray[1]).getDoorDirection(), s.next().toString().substring(1, 1));
			}
			i++;
			s.next();
			Assert.assertEquals(i, intBoard.calcIndex(cellArray[0], cellArray[1]));
		}
		s.close();
		Assert.assertEquals(board.getNumRows(), boardSize[1]);
		Assert.assertEquals(board.getNumColumns(), boardSize[1]);
	}
}
