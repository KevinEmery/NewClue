package testing;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import junit.framework.Assert;

import game.Board;
import game.BoardCell;

import org.junit.Before;
import org.junit.Test;

public class AdjacenciesAndPathingTest {
/* Description of all our tests, and their desired results.
 * Testing verticies:
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
 *   16 7 length 4: (307 331 333 357 381 383 405 403 429 431 455 481 457 507 483 433 409 385 411)
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
		board.loadConfigFiles();
		board.calcAdjacencies();
	}

	// Little contenstEqual function. Java has an assertEquals that takes these as
	// arguments but we want to ensure we compare the size and ensure all the elements
	// in the set are contained in the array, but don't care about order.
	public boolean contentsEqual(int[] expected, LinkedList<Integer> actual) {
		if(actual.size() != expected.length) return false; 
		for(int i: expected) {
			if(!actual.contains(i))
				return false;
		}
		return true;
	}

	// Test adjacency lists for just a walkway.
	@Test
	public void testWalkwayAdj() {
		//16, 17 (adj - 392 416, 418, 442)
		int[] adjacencies = new int[] {392, 416, 418, 442};
		LinkedList<Integer> walkCellAdjs = board.getAdjList(16, 17);
		for(int i: adjacencies) {
			Assert.assertTrue(walkCellAdjs.contains(i));
		}
		Assert.assertEquals(true, contentsEqual(adjacencies, walkCellAdjs));
			
	}
	
	// Test adjacencies for on the edge of the map.
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
	
	// Test adjacencies for right by the room cells - i.e. make sure
	// that we don't include room cells in our adjacencies.
	@Test
	public void testByRoomCell() {
		//22, 17 ( 542 556 592)
		int[] adjacencies0 = new int[] {542, 566, 592};
		LinkedList<Integer> walkCellAdjs0 = board.getAdjList(22, 17);
		Assert.assertTrue(contentsEqual(adjacencies0, walkCellAdjs0));
		
		//9 3 ( 227 229 203)
		int[] adjacencies1 = new int[] {227, 229, 203};
		LinkedList<Integer> walkCellAdjs1 = board.getAdjList(9, 3);
		Assert.assertTrue(contentsEqual(adjacencies1, walkCellAdjs1));
	}
	
	// Test by a doorway, making sure we can get into a door.
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
	
	// Test to make sure we can't get into doors that we are right by, but face
	// the wrong direction for us to get into.
	@Test
	public void testByWrongDirectionDoor() {
		//  17 20 (444 420 446)
		int[] adjacencies0 = new int[] {444, 420, 446};
		LinkedList<Integer> walkCellAdjs0 = board.getAdjList(17, 20);
		Assert.assertEquals(true, contentsEqual(adjacencies0, walkCellAdjs0));
	}
	
	// Test adjacencies for when we are sitting in a door and venturing out.
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

	// Tests targeting along a walkway where no doors are reached.
	@Test
	public void testTargetsAlongWalkways() {
		// Initialize lists of where everything should be.
		int[] targets4 = new int[] {307, 331, 333, 357, 381, 383, 405, 403, 429, 431, 455, 481, 457, 507, 483, 433, 409, 385, 411};
		int[] targets3 = new int[] {190, 216, 242, 268, 196, 220, 170, 144, 118, 192, 168, 194, 218};
		int[] targets2 = new int[] {204, 180, 156, 182, 208, 232, 256, 230};
		int[] targets1 = new int[] {317, 341, 343, 367};
		
		board.startTargets(13, 17, 1);
		Set<BoardCell> actual1 = board.getTargets();
		Assert.assertTrue(actual1.size() == targets1.length);
		for(int i: targets1) {
			Assert.assertTrue(actual1.contains(board.getCellAt(i)));
		}
		
		board.startTargets(16, 7, 4);
		Set<BoardCell> actual4 = board.getTargets();
		Assert.assertTrue(actual4.size() == targets4.length);
		for(int i: targets4) {
			Assert.assertTrue(actual4.contains(board.getCellAt(i)));
		}
		
		board.startTargets(7, 18, 3);
		Set<BoardCell>actual3 = board.getTargets();
		Assert.assertTrue(actual3.size() == targets3.length);
		for(int i: targets3) {
			Assert.assertTrue(actual3.contains(board.getCellAt(i)));
		}
		
		board.startTargets(8, 6, 2);
		Set<BoardCell> actual2 = board.getTargets();
		Assert.assertTrue(actual2.size() == targets2.length);
		for(int i: targets2) {
			Assert.assertTrue(actual2.contains(board.getCellAt(i)));
		}
		
	
	}
	
	// Test targeting into rooms
	@Test
	public void testTargetsIntoRoom() {
		int[] targets1 = new int[] {470, 444, 418, 442, 466, 492, 518};
		int[] targets2 = new int[] {12, 63, 136, 162};
		
		board.startTargets(18, 18, 2);
		Set<BoardCell> actual1 = board.getTargets();
		Assert.assertTrue(actual1.size() == targets1.length);
		for(int i: targets1) {
			Assert.assertTrue(actual1.contains(board.getCellAt(i)));
		}
		
		board.startTargets(3, 12, 3);
		Set<BoardCell> actual2 = board.getTargets();
		Assert.assertTrue(actual2.size() == targets2.length);
		for(int i: targets2) {
			Assert.assertTrue(actual2.contains(board.getCellAt(i)));
		}		
	}
	
	// Test targeting leaving rooms.
	@Test
	public void testTargetsLeavingRoom() {
		int[] targets1 = new int[] {455, 481, 507, 531, 479};
		int[] targets2 = new int[] {136, 187, 163};
		
		board.startTargets(20, 4, 3);
		Set<BoardCell> actual1 = board.getTargets();
		Assert.assertTrue(actual1.size() == targets1.length);
		for(int i: targets1) {
			Assert.assertTrue(actual1.contains(board.getCellAt(i)));
		}
		
		board.startTargets(2,  13, 6);
		Set<BoardCell> actual2 = board.getTargets();
		Assert.assertTrue(actual2.size() == targets2.length);
		for(int i: targets2) {
			Assert.assertTrue(actual2.contains(board.getCellAt(i)));
		}
	}
		
	
}
