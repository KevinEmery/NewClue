import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {
	private ArrayList<BoardCell> cells = new ArrayList<BoardCell>();
	private Map<Character,String> rooms= new HashMap<Character, String>();
	private int numRows;
	private int numColumns;
	Board(int rows,int cols){
//		this.numRows=rows;
//		this.numColumns=cols;
	}
	public void loadConfigFiles(){

	}
	public int calcIndex(int row, int column){
		return 0;
	}
	
	
	
	public RoomCell getRoomCellAt(int row, int column){
		
		return new RoomCell(row,column); 
		}
	
	
	
	public ArrayList<BoardCell> getCells() {
		return cells;
	}
	public void setCells(ArrayList<BoardCell> cells) {
		this.cells = cells;
	}
	public Map<Character, String> getRooms() {
		return rooms;
	}
	public void setRooms(Map<Character, String> rooms) {
		this.rooms = rooms;
	}
	public int getNumRows() {
		return numRows;
	}
	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	public int getNumRooms(){
		return rooms.size();
	}
	public void setNumColumns(int numColumns) {
		this.numColumns = numColumns;
	}

}


