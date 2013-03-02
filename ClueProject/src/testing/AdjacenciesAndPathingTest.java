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
		board.loadConfigFiles();
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
	
	public boolean contentsEqual(int[] expected, Set<BoardCell> actual) {
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
		for(int i: adjacencies) {
			Assert.assertTrue(walkCellAdjs.contains(i));
		}
		Assert.assertEquals(true, contentsEqual(adjacencies, walkCellAdjs));
			
	}
	@Test
	public void testEdgeAdj() {
		//24 7
		int[] adjacencies0 = new int[] {606, 608, 582};
		LinkedList<Integer> walkCellAdjs = board.getAdjList(24, 7);
		Assert.assertEquals(true, contentsEqual(adjacencies0, walkCellAdjs));
		//17 0
		int[] adjacencies1 = new int[] {400, 450, 426};
		LinkedList<Integer> walkCellAdjs1 = board.getAdjList(24, 7);
		Assert.assertEquals(true, contentsEqual(adjacencies1, walkCellAdjs1));

	}

	@Test
	public void testTargetsAlongWalkways() {
		// Initialize lists of where everything should be.
		int[] targets4 = new int[] {411, 385, 383, 433, 333, 307, 331, 381, 403, 429, 431, 455, 481, 507, 483};
		int[] targets3 = new int[] {190, 216, 242, 268, 196, 220, 170, 144, 118, 192, 168, 194, 218};
		int[] targets2 = new int[] {204, 180, 156, 182, 208, 232, 256, 230};
		int[] targets1 = new int[] {317, 341, 343, 367};
		
		// Compare the actual
		board.startTargets(16, 7, 4);
		Set<BoardCell> actual4 = board.getTargets();
		Assert.assertTrue(contentsEqual(targets4, actual4));
		
		board.startTargets(7, 18, 3);
		Set<BoardCell>actual3 = board.getTargets();
		Assert.assertTrue(contentsEqual(targets3, actual3));
		
		board.startTargets(8, 6, 2);
		Set<BoardCell> actual2 = board.getTargets();
		Assert.assertTrue(contentsEqual(targets2, actual2));
		
		board.startTargets(13,  17, 1);
		Set<BoardCell> actual1 = board.getTargets();
		Assert.assertTrue(contentsEqual(targets1, actual1));
	}
	
	@Test
	public void testTargetsIntoRoom() {
		int[] targets1 = new int[] {470, 444, 418, 442, 466, 492, 518};
		int[] targets2 = new int[] {12, 63, 136, 162};
		
		board.startTargets(18, 18, 2);
		Set<BoardCell> actual1 = board.getTargets();
		Assert.assertTrue(contentsEqual(targets1, actual1));	
		
		board.startTargets(3, 12, 3);
		Set<BoardCell> actual2 = board.getTargets();
		Assert.assertTrue(contentsEqual(targets2, actual2));		
	}
	
	
	@Test
	public void testTargetsLeavingRoom() {
		int[] targets1 = new int[] {455, 481, 507, 531, 479};
		int[] targets2 = new int[] {138, 187, 163};
		
		board.startTargets(20, 4, 3);
		Set<BoardCell> actual1 = board.getTargets();
		Assert.assertTrue(contentsEqual(targets1, actual1));	
		
		board.startTargets(2,  13, 6);
		Set<BoardCell> actual2 = board.getTargets();
		Assert.assertTrue(contentsEqual(targets2, actual2));
	}
	
}
