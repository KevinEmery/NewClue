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
import java.util.Scanner;
import java.util.Set;

public class Board {
	private ArrayList<BoardCell> cells = new ArrayList<BoardCell>();
	private ArrayList<String> list=new ArrayList<String>();
	private Map<Character,String> rooms= new HashMap<Character, String>();
	int[] boardDim;
	HashSet<Integer> targetList=new HashSet<Integer>();
	public Board(String string, String string2) {
		boardDim=new int[2];
		String line = null;
		boardDim[0]=boardDim[1] = 0;
		try {
			Scanner input=new Scanner(new BufferedReader(new FileReader(string)));
			while(  input.hasNext()){
				line = input.next();
				line.split(",\\n");
				list.add(line);
				for(String s:list) {
		            System.out.println(s);
		           
		        }
				boardDim[0]++;// just read a row
			}
			for (int i=0; i < line.length(); i++){
				if (line.charAt(i) == ',')//counts the commas that are in between the data
				{boardDim[1]++;}				
			}
			boardDim[1]++;//adds one more so it is a count of the things on either side of the commas
			
			

		} catch (IOException e) {
			System.out.println("File could not be read");
			System.exit(0);
		}



	
	}
	public Board()	 {
	
		boardDim=new int[2];
		String line = null;
		boardDim[0]=boardDim[1] = 0;
		try {
			Scanner input=new Scanner(new BufferedReader(new FileReader("etc/ClueLayout.csv")));
			while(  input.hasNext()){
				line = input.next();
				line.split(",");
				boardDim[0]++;// just read a row
				
			}
			for (int i=0; i < line.length(); i++){
				if (line.charAt(i) == ',')//counts the commas that are in between the data
				{boardDim[1]++;}				
			}
			boardDim[1]++;//adds one more so it is a count of the things on either side of the commas
			
			//if (boardDim[0]!= row&& boardDim[1]!= column){throw BadConfigFormatException;} Trying to make sure board is consistant, still thinking.

		} catch (IOException e) {
			e.printStackTrace();
		}



	}


	
	public void loadConfigFiles(){

	}
	public int calcIndex(int row, int column){	// turns a 2d board into a 1d list
		return column+row*boardDim[1];
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
		try{
			Scanner input=new Scanner(new BufferedReader(new FileReader("ClueLegend.txt")));
			while(  input.hasNext()){
				String c=input.next();
				String r=input.next();
				rooms.put( null, c);
			}
		}catch (FileNotFoundException e){System.out.println("Could not open Key");}
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


