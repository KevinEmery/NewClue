import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Board {
	private ArrayList<BoardCell> cells = new ArrayList<BoardCell>();
	private Map<Character,String> rooms= new HashMap<Character, String>();
	
	static final int dim=10;
	int[] boardDim;
	HashSet<Integer> targetList=new HashSet<Integer>();
	public Board(String string, String string2) {
		// TODO Auto-generated constructor stub
	}
	public Board()	 {
		boardDim=new int[2];
		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader("ClueLayout.csv"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		    
		       
		    
		
	}
	
	
	public void loadConfigFiles(){
		
	}
	public int calcIndex(int row, int column){	// turns a 2d board into a 1d list
		return column+row*boardDim[0];
	}
	public RoomCell GetRoomCellAt(int row, int column){
		
		return null; 
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
	
	public BoardCell getCellAt(int calcIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	public void loadRoomConfig() {
		// TODO Auto-generated method stub
		
	}
	
		public HashSet<Integer> calcAdjacencies(int loc,int steps,HashSet<Integer> adjlist){
			//This function needs to do a recursive loop that counts down the steps to check if the space to the right, left, top and bottom
			//are valid places, if so it adds to adjlist, we need not worry about copies since this is a hashset.
			if(steps!=0){
				if((loc-boardDim[0])>=0){
					//Go up?
					adjlist.addAll(calcAdjacencies(loc-boardDim[0],steps-1,adjlist));
				}
				if(loc+boardDim[0]<(boardDim[0]*boardDim[1])){
					//Go down?
					adjlist.addAll(calcAdjacencies(loc+boardDim[0],steps-1,adjlist));
				}
				if((loc)%boardDim[0]!=0 && (loc-1)>=0){
					//Go left?
					adjlist.addAll(calcAdjacencies(loc-1,steps-1,adjlist));
				}
				if((loc+1)%boardDim[0]!=0 && (loc+1)<=(boardDim[0]*boardDim[1])){
					//Go right?
					adjlist.addAll(calcAdjacencies(loc+1,steps-1,adjlist));
				}
				return adjlist;
			} else if(steps==0){
				if(!adjlist.contains(loc)){
					adjlist.add(loc);
				}
				return adjlist;
			} else {
				return adjlist;
			}
		}
	
	public void loadBoardConfig() {
		// TODO Auto-generated method stub
		
	}
	public HashSet<Integer> getAdjList(int calcIndex) {
		HashSet<Integer> adjlist = new HashSet<Integer>();
		//adjlist.add(i);
		return calcAdjacencies(calcIndex,1,adjlist);

	}
	public void calcTargets(int i, int j, int k) {
		// TODO Auto-generated method stub
		
	}
	public Set<BoardCell> getTargets() {
		// TODO Auto-generated method stub
		return null;
	}
	public int getNumColumns() {
		
		return boardDim[1];
	}
	public int getNumRows() {
		
		return boardDim[0];
	}
	
		
}


