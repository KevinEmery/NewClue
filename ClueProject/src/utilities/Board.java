package utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class Board {	
	
	private ArrayList<BoardCell> cells;
	private Map<Character, String> rooms;
	private int numRows;
	private int numColumns;
	
	public Board() {
		this.cells = new ArrayList<BoardCell>();
		this.rooms = new HashMap<Character, String>();
		this.rooms.put('A', "Test");
		this.numRows = 0;
		this.numColumns = 0;
	}
	
	public Board(String string, String string2) {
		
	}
	
	public void loadConfigFiles() {
		
	}
	
	public int calcIndex(int row, int column) {
		return 0;
	}
	
	public RoomCell getRoomCellAt(int row, int column) {
		return new RoomCell();
	}
	
	public ArrayList<BoardCell> getCells() {
		return cells;
	}
	
	public Map<Character, String> getRooms() {
		return rooms;
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
	
}
