package testing;

import static org.junit.Assert.*;

import java.util.LinkedList;

import junit.framework.Assert;

import game.Board;
import game.BoardCell;

import org.junit.Before;
import org.junit.Test;

public class AdjacenciesAndPathingTest {
/* Testing verticies:
 * walkway: 
 * 	16, 17 (adj - 392 416, 418, 442)
 * Edge of board:
 * 	24 7 ( 606 608 582)
 *  17 0 ( 400 450 426)
 *  0 5 (4 6 30)
 *  7 24 (198 174 224)
 * Beside room cell that isn't a doorway:
 *  22, 17 ( 542 556 592)
 *  9 3 ( 227 229 203)
 * Locations next to a doorway w/ a needed direction
 * 12 18 ( 293 319 317 343)
 * 7 8 (182 158 184 208)
 *  19 18 ( 468 492 518)
 * Doorway that isn't in the right direction:
 *  17 20 (444 420 446)
 * Locations that are doorways
 *  17 12 (412)
 *  6 3 (178)
 *  ----Target tests----
 * Targets along walkways, at various distances (1, 2, 3, and 4)
 *   16 7 length 4: (411 385 383 433 333 307 331 381 403 429 431 455 481 507 483)
 *   7 18 length 3: (190 216 242 268 196 220 170 144 118 192 168 194 218)
 *   8 6 length 2: (204 180 156 182 208 232 256 230)
 *   13 17 length 1: (317 341 343 367)
 * Targets that allow a user to enter a room
 *   18 18 length 2: (470 444 418 442 466 492 518)
 *   3 12 length 3: (12 63 136 162)
 * Targets calculated when leaving a room
 *   20 4 length 3: (455 481 507 531 479)
 *   2 13 length 6: (136 187 163)   
 */ 
	private Board board;
	@Before
	public void setup() {
		board = new Board("Board.csv", "Legend.csv");
		board.calcAdjacencies();
	}
	public boolean contentsEqual(int[] expected, LinkedList<Integer> actual) {
		if(actual.size() != expected.length) return false; 
		for(int i: expected) {
			if(!actual.contains(i))
				return false;
		}
		return true;
	}
	@Test
	public void testWalkwayAdj() {
		//16, 17 (adj - 392 416, 418, 442)
		int[] adjacencies = new int[] {392, 416, 418, 442};
		LinkedList<Integer> walkCellAdjs = board.getAdjList(16, 17);
		Assert.assertEquals(true, contentsEqual(adjacencies, walkCellAdjs));
			
	}
	@Test
	public void testEdgeAdj() {
		//24 7
		int[] adjacencies0 = new int[] {606, 608, 582};
		LinkedList<Integer> walkCellAdjs0 = board.getAdjList(24, 7);
		Assert.assertEquals(true, contentsEqual(adjacencies0, walkCellAdjs0));
		//17 0
		int[] adjacencies1 = new int[] {400, 450, 426};
		LinkedList<Integer> walkCellAdjs1 = board.getAdjList(17, 0);
		Assert.assertEquals(true, contentsEqual(adjacencies1, walkCellAdjs1));
		
		//  0 5 (4 6 30)
		int[] adjacencies2 = new int[] {4, 6, 30};
		LinkedList<Integer> walkCellAdjs2 = board.getAdjList(0, 5);
		Assert.assertTrue(contentsEqual(adjacencies2, walkCellAdjs2));
		
		//7 24 (198 174 224)
		int[] adjacencies3 = new int[] {198, 174, 224};
		LinkedList<Integer> walkCellAdjs3 = board.getAdjList(7, 24);
		Assert.assertTrue(contentsEqual(adjacencies3, walkCellAdjs3));
		
	}
	@Test
	public void testByRoomCell() {
		//22, 17 ( 542 556 592)
		int[] adjacencies0 = new int[] {542, 556, 592};
		LinkedList<Integer> walkCellAdjs0 = board.getAdjList(22, 17);
		Assert.assertTrue(contentsEqual(adjacencies0, walkCellAdjs0));
		
		//9 3 ( 227 229 203)
		int[] adjacencies1 = new int[] {227, 229, 203};
		LinkedList<Integer> walkCellAdjs1 = board.getAdjList(9, 3);
		Assert.assertTrue(contentsEqual(adjacencies1, walkCellAdjs1));
	}
	@Test
	public void testByDoorway() {
		//12 18 ( 293 319 317 343)
		int[] adjacencies0 = new int[] {293, 319, 317, 343 };
		LinkedList<Integer> walkCellAdjs0 = board.getAdjList(12, 18);
		Assert.assertEquals(true, contentsEqual(adjacencies0, walkCellAdjs0));
		
		//7 8 (182 158 184 208)
		int[] adjacencies1 = new int[] {182, 158, 184, 208};
		LinkedList<Integer> walkCellAdjs1 = board.getAdjList(7, 8);
		Assert.assertEquals(true, contentsEqual(adjacencies1, walkCellAdjs1));
		
		// 19 18 ( 468 492 518)
		int[] adjacencies2 = new int[] {468, 492, 518};
		LinkedList<Integer> walkCellAdjs2 = board.getAdjList(19, 18);
		Assert.assertEquals(true, contentsEqual(adjacencies2, walkCellAdjs2));
		
		
	}
	@Test
	public void testByWrongDirectionDoor() {
		//  17 20 (444 420 446)
		int[] adjacencies0 = new int[] {444, 420, 446};
		LinkedList<Integer> walkCellAdjs0 = board.getAdjList(17, 20);
		Assert.assertEquals(true, contentsEqual(adjacencies0, walkCellAdjs0));
	}
	@Test
	public void testDoors() {
		// 17 12 (412)
		int[] adjacencies0 = new int[] {412};
		LinkedList<Integer> walkCellAdjs0 = board.getAdjList(17, 12);
		Assert.assertEquals(true, contentsEqual(adjacencies0, walkCellAdjs0));
		//6 3 (178)
		int[] adjacencies1 = new int[] {178};
		LinkedList<Integer> walkCellAdjs1 = board.getAdjList(6, 3);
		Assert.assertEquals(true, contentsEqual(adjacencies1, walkCellAdjs1));
	}
	
}
