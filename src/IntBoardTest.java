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
		int [] boardSize = {23,23};							//setting board dimenstions to be 23x23
		Board board = new Board();		//creating a board
		IntBoard intBoard= new IntBoard();						//creating a new IntBoard
		Assert.assertEquals(11, board.getRooms().size());				//tell the number of rooms. walkway is a room
		Scanner s=new Scanner(new BufferedReader(new FileReader("etc/Clue_map.csv")));
		s.useDelimiter("[,\\n]");
		int i=0;
		BoardCell b;
		String holder;
		while(s.hasNext()){
			int[] cellArray = intBoard.calcRowCol(i);//turns index into x and y coordinates
			holder=s.next().toString();//fanciness to turn the string input into a char
			char c=holder.charAt(0);//because that is what getRoom returns
			
			System.out.println("holder "+holder+" "+i);//appears to be doing every other one
			System.out.println(board.GetRoomCellAt(cellArray[0],cellArray[1]).getRoom());
			
			//Seems that s is pulling in every other number:0,2,4 instead of 0,1,2
			//don't know why, tested thoroughly, 
			
			
			Assert.assertEquals(board.GetRoomCellAt(cellArray[0],cellArray[1]).getRoom(), c);
			if(s.next().toString().length()==2){
				Assert.assertEquals(board.GetRoomCellAt(cellArray[0],cellArray[1]).getDoorDirection(), s.next().toString().substring(1, 1));
			}
			
			
			//s.next();
			Assert.assertEquals(i, intBoard.calcIndex(cellArray[0], cellArray[1]));
			i++;//moved this
		}
		s.close();
		Assert.assertEquals(board.getNumRows(), boardSize[1]);
		Assert.assertEquals(board.getNumColumns(), boardSize[1]);
	}
}
