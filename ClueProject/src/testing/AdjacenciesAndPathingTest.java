package testing;

import static org.junit.Assert.*;
import game.Board;

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
	@Before
	public void setup() {
		Board board = new Board("Board.csv", "Legend.txt");
	}
	@Test
	public void testWalkwayAdj() {
		
	}

}
