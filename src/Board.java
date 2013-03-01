import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Board {
	private ArrayList<BoardCell> cells = new ArrayList<BoardCell>();
	private Map<Character,String> rooms= new HashMap<Character, String>();
	private int numRows;
	private int numColumns;
	
	public Board(String string, String string2) {
		// TODO Auto-generated constructor stub
	}
	public Board() {
		// TODO Auto-generated constructor stub
	}
	public void loadConfigFiles(){

	}
	public int calcIndex(int row, int column){
		return 0;
	}
	
	
	
	public RoomCell GetRoomCellAt(int row, int column){
		
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
	public BoardCell getCellAt(int calcIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	public void loadRoomConfig() {
		// TODO Auto-generated method stub
		
	}
	public void calcAdjacencies() {
		// TODO Auto-generated method stub
		
	}
	public void loadBoardConfig() {
		// TODO Auto-generated method stub
		
	}
	public LinkedList<Integer> getAdjList(int calcIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	public void calcTargets(int i, int j, int k) {
		// TODO Auto-generated method stub
		
	}
	public Set<BoardCell> getTargets() {
		// TODO Auto-generated method stub
		return null;
	}

}


