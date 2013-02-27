package utilities;

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
		this.cells = new ArrayList<BoardCell>();
		this.rooms = new HashMap<Character, String>();
		this.numRows = 0;
		this.numColumns = 0;
	}
	//todo: add member strings for board and legend csv files?
	//not needed for failing tests.
	public Board(String boardCsv, String legendCsv) {
		
	}
	public void loadConfigFiles() {
		
	}
	public int calcIndex(int row, int column) {
		return 0;
	}
	
	//getters.
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
