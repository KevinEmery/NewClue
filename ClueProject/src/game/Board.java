package game;

//Board class, contains the legend (cells) and the actual rooms.
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {	
	
	private ArrayList<BoardCell> cells;
	private Map<Character, String> rooms;
	private int numRows;
	private int numColumns;
	
	//default = empty lists/sets + no columns and rows.
	public Board() {
		this ("Board.csv", "Legend.csv");
	}
	
	public Board(String string, String string2) {
		this.cells = new ArrayList<BoardCell>();
		this.rooms = new HashMap<Character, String>();
		this.numRows = 0;
		this.numColumns = 0;
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
