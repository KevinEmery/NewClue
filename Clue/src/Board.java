import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private ArrayList<BoardCell> cells = new ArrayList<BoardCell>();
	private Map<Character,String> rooms;
	private Set<BoardCell> targetList;
	private Map<Integer, LinkedList<Integer>> adjList;
	int rowCount;
	int colCount;
	int doorways;
	private String boardConfigFile;
	private String roomConfigFile;
	
	public Board(String boardConfigFile, String roomConfigFile) {
		this.boardConfigFile = boardConfigFile;
		this.roomConfigFile = roomConfigFile;
		this.rowCount = 0;
		this.colCount = 0;
	}
	
	public Board()	 {
		this("etc/ClueLayout.csv", "etc/ClueLegend.txt");
		/*boardDim=new int[2];
		rowCount=0;
		colCount=0;
		roomConfigFile="etc/ClueLegend.txt";
		try {
			Scanner lines=new Scanner(new BufferedReader(new FileReader("etc/ClueLayout.csv")));
			lines.useDelimiter("[\\n]");
			String iter=new String();
			String doorString = "UDLR"; //fastest way to search
			rowCount=0;
			while(lines.hasNext()){
				String line=lines.next();
				colCount=0;
				//System.out.println(rowCount+","+colCount);
				//System.out.print("\n");
				Scanner cell = new Scanner(line).useDelimiter(",");
				while(cell.hasNext()){
					iter=cell.next();
					BoardCell e=null;
					iter=iter.replaceAll("[\\n\\r]", ""); //regex to remove returns and newlines (yes, they're different)
					//System.out.println(iter.charAt(0)+" at "+ rowCount+" ,"+colCount+"\n");
					if(iter.equals("W")){
						//System.out.println(iter+ " is a walkway at "+ colCount+" ,"+rowCount);
						e=new WalkwayCell(rowCount,colCount);
					} else {
						
						e=new RoomCell(rowCount,colCount,iter.charAt(0));
						if(iter.length()>1){
							doorways++;
							//System.out.print(iter.charAt(1));
							if(doorString.contains(String.valueOf(iter.charAt(1)))){ //see if the key character for the door is in the string "udlr"
								//Creates the door using that character
								((RoomCell) e).setRoomDirection(iter.charAt(1));
							}
						}
					}
					//System.out.println(e.row+" ,"+e.column);
					cells.add(e);
					colCount+=1;
				}
				rowCount+=1;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		rowCount++;
		//System.out.println(rowCount+" "+colCount);*/
	}
	
	// Loads the provided configuration files for the board and legend
	public void loadConfigFiles(){
		try {
			this.loadRoomConfig();
			this.loadBoardConfig();
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());	
		} catch (BadConfigFormatException e) {
			System.err.println("While trying to load config files, encountered a file not found: " + e.getMessage());
		}		
	}
	
	public int calcIndex(int row, int column){	// turns a 2d board into a 1d list
		return column+row*(colCount);
	}
	
	public void AddRoomCell(BoardCell b){
		cells.add(b);
	}
	
	public RoomCell getRoomCellAt(int row, int column){
		return ((RoomCell)cells.get(calcIndex(row,column))) ; 
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
	
	public BoardCell getCellAt(int calcIndex) {
		return cells.get(calcIndex);
	}

	/*public HashSet<Integer> calcAdjacencies(int loc,int steps,HashSet<Integer> adjlist,HashSet<Integer> visited){
		//This function needs to do a recursive loop that counts down the steps to check if the space to the right, left, top and bottom
		//are valid places, if so it adds to adjlist, we need not worry about copies since this is a hashset.
		//		System.out.println(steps+" "+loc+ " "+(getCellAt(loc).isDoorway()+" "+getCellAt(loc).isWalkway()));
		//System.out.println(adjlist.size());
		if(visited.contains(loc)){return adjlist;}
		visited.add(loc);
		
		if(steps>0 && (getCellAt(loc).isDoorway()||getCellAt(loc).isWalkway())){
			
			if(getCellAt(loc).isDoorway()){
				if((loc-colCount)>=0  && getCellAt(loc-rowCount).isWalkway()){
					//Go up?
					adjlist.addAll(calcAdjacencies(loc-rowCount,steps-1,adjlist,visited));
					
				}
				if(loc+colCount<(cells.size()) && getCellAt(loc+rowCount).isWalkway()){
					//Go down?
					adjlist.addAll(calcAdjacencies(loc+rowCount,steps-1,adjlist,visited));
				}
				if((getCellAt(loc).column-1)>=0 && (loc-1)>=0 && getCellAt(loc-1).isWalkway()){
					//Go left?
					adjlist.addAll(calcAdjacencies(loc-1,steps-1,adjlist,visited));
				}
				if((getCellAt(loc+1).column)!=0 && (loc+1)<=(rowCount*colCount) && getCellAt(loc+1).isWalkway()){
					//Go right?
					adjlist.addAll(calcAdjacencies(loc+1,steps-1,adjlist,visited));
				}
				
			} else {
				if((loc-colCount)>=0 && (getCellAt(loc-rowCount).isDoorway()||getCellAt(loc-rowCount).isWalkway())){
					//Go up?
					if(getCellAt(loc-rowCount).isDoorway() && ((RoomCell)getCellAt(loc-rowCount)).getDoorDirection().equals("D")){
						adjlist.addAll(calcAdjacencies(loc-rowCount,steps-1,adjlist,visited));
						adjlist.add(loc-rowCount);
					} else if(!getCellAt(loc-rowCount).isDoorway()){
						adjlist.addAll(calcAdjacencies(loc-rowCount,steps-1,adjlist,visited));
					}
				}
				if((loc+colCount)<(cells.size()) && (getCellAt(loc+rowCount).isDoorway()||getCellAt(loc+rowCount).isWalkway())){
					//Go down?
					if(getCellAt(loc+rowCount).isDoorway() && ((RoomCell)getCellAt(loc+rowCount)).getDoorDirection().equals("U")){
						adjlist.addAll(calcAdjacencies(loc+rowCount,steps-1,adjlist,visited));
						adjlist.add(loc+rowCount);
					} else if(!getCellAt(loc+rowCount).isDoorway()){
						adjlist.addAll(calcAdjacencies(loc+rowCount,steps-1,adjlist,visited));
					}
				}
				if((getCellAt(loc).column-1)>=0 && (loc-1)>=0 && (getCellAt(loc-1).isDoorway()||getCellAt(loc-1).isWalkway())){
					//Go left?
					if(getCellAt(loc-1).isDoorway() && ((RoomCell)getCellAt(loc-1)).getDoorDirection().equals("R")){
						adjlist.addAll(calcAdjacencies(loc-1,steps-1,adjlist,visited));
						adjlist.add(loc-1);
					} else if(!getCellAt(loc-1).isDoorway()){
						adjlist.addAll(calcAdjacencies(loc-1,steps-1,adjlist,visited));
					}
				}
				if((getCellAt(loc+1).column)!=0 && (loc+1)<=(rowCount*colCount) && (getCellAt(loc+1).isDoorway()||getCellAt(loc+1).isWalkway())){
					if(getCellAt(loc+1).isDoorway() && ((RoomCell)getCellAt(loc+1)).getDoorDirection().equals("L")){
						adjlist.addAll(calcAdjacencies(loc+1,steps-1,adjlist,visited));
						adjlist.add(loc+1);
					} else if(!getCellAt(loc+1).isDoorway()){
						adjlist.addAll(calcAdjacencies(loc+1,steps-1,adjlist,visited));
					}
				}
			}
			visited.remove(loc);
			return adjlist;
		}
		if(steps==0 && (getCellAt(loc).isDoorway()||getCellAt(loc).isWalkway())){
			if(!adjlist.contains(loc)){
				adjlist.add(loc);
			}
			visited.remove(loc);

			return adjlist;
			
		} else {
			visited.remove(loc);

			return adjlist;
		}
	}*/
	
	// Determines the neighbors of every cell on the board
	public void calcAdjacencies() {
		adjList = new HashMap<Integer, LinkedList<Integer>>();
		// Initializes an empty adjacency list for each cell on the board
		for (int i = 0; i < rowCount * colCount ; i++) {
			this.adjList.put(i, new LinkedList<Integer>());
		}
		
		// Iterates through every row and column, checking the validity of the cells on all four sides of a given cell. 
		// If it is valid, it adds it to the adjList
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				if (isValidCell(i + 1, j) && isAdjacent(calcIndex(i, j), calcIndex(i + 1, j), RoomCell.DoorDirection.DOWN, RoomCell.DoorDirection.UP))
					adjList.get(calcIndex(i,j)).add(calcIndex(i + 1, j));
				if (isValidCell(i - 1, j) && isAdjacent(calcIndex(i, j), calcIndex(i - 1, j), RoomCell.DoorDirection.UP, RoomCell.DoorDirection.DOWN))
					adjList.get(calcIndex(i,j)).add(calcIndex(i - 1, j));
				if (isValidCell(i, j + 1) && isAdjacent(calcIndex(i, j), calcIndex(i, j + 1), RoomCell.DoorDirection.RIGHT, RoomCell.DoorDirection.LEFT))
					adjList.get(calcIndex(i,j)).add(calcIndex(i, j + 1));
				if (isValidCell(i, j - 1) && isAdjacent(calcIndex(i, j), calcIndex(i, j - 1), RoomCell.DoorDirection.LEFT, RoomCell.DoorDirection.RIGHT))
					adjList.get(calcIndex(i,j)).add(calcIndex(i, j - 1));
			}
		}
	}
	
	// Determines if a given cell is valid on the game board
	private boolean isValidCell(int row, int column) {
		return row >= 0 && row < rowCount && column >= 0 && column < colCount;	
	}
	
	// Determines if a cell is adjacent based on the current index and the index of the new cell, and the directions of doors in the area
	private boolean isAdjacent(int currentIndex, int newIndex, RoomCell.DoorDirection directionOut, RoomCell.DoorDirection directionIn) {

		// If your new cell is a walkway and you're not in a room, return true
		if (cells.get(newIndex).isWalkway() && !cells.get(currentIndex).isRoom())
			return true;
		
		// If the current cell is a room, and not a doorway, then there are no adjacencies
		else if (cells.get(currentIndex).isRoom() && !cells.get(currentIndex).isDoorway())
			return false;
		
		// If you're in a doorway, and the door is facing the direction required to get out as specified by the call, it's adjacent.
		else if (cells.get(currentIndex).isDoorway() && ((RoomCell) cells.get(currentIndex)).getDoorDirection() == directionOut)
			return true;
		
		// If you're on a walkway and trying to get into a door, and it's facing the correct way to go in, it's adjacent
		else if (!cells.get(currentIndex).isDoorway() && ((RoomCell) cells.get(newIndex)).getDoorDirection() == directionIn)
			return true;
		
		// Otherwise, it's not adjacent.
		return false;
			
	}

	// Initalies the visted array, targets as empty, and then calls the recursive function to calculate targets
	public void startTargets(int location, int numSteps) {
		boolean[] visited = new boolean[rowCount * colCount];
		targetList = new HashSet<BoardCell>();
		visited[location] = true;
		calcTargets(location, numSteps, visited);
	}
	
	// Overloaded form of startTargets
	public void startTargets(int row, int column, int numSteps) {
		startTargets(calcIndex(row, column), numSteps);
	}
	
	// Calculates the spaces we can get to from a certain cell
	private void calcTargets(int location, int numSteps, boolean[] visited) {
		
		// Initialized a new LinkedList list of adjacents cells for this location
		LinkedList<Integer> adjacentCells = new LinkedList<Integer>();
		
		// Adds unvisited cells to a new adjacency list
		for (Integer i : getAdjList(location)) {
			if (visited[i] == false)
					adjacentCells.add(i);
		}
		
		// Recursively finds the targets
		for (Integer i : adjacentCells) {
			visited[i] = true;
			if (numSteps == 1 || cells.get(i).isDoorway()) 
				targetList.add(cells.get(i));
			else {
				calcTargets(i, numSteps - 1, visited);
			}
			visited[i] = false;	
		}
	}
	
	public void calcTargets(int row, int column, int steps) {
		startTargets(calcIndex(row, column), steps);
	}
	
		
	
	public LinkedList<Integer> getAdjList(int calcIndex) {
		return adjList.get(calcIndex);
	}
	
	public Set<BoardCell> getTargets() {
		return targetList;
	}
	public int getNumColumns() {

		return colCount;
	}
	public int getNumRows() {

		return rowCount;
	}
	
	
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		rooms = new HashMap<Character, String>();
		Scanner input = new Scanner(new FileReader(roomConfigFile));
		int lineCount = 0;
		while(input.hasNext()) {
			lineCount++;
			Character key;
			
			// Reads in the first part of the line, up to the space. This should contain one character and then a comma.
			String temp = input.next();
			if (!Character.isLetter(temp.charAt(0)) || temp.charAt(1) != ',')
				throw new BadConfigFormatException("Invalid key provided");
		
			key = temp.charAt(0);
			
			// Reads in the rest of the line
			String rest = input.nextLine();
			
			// If there is a comma here, then the config file is bad
			if(rest.contains(",")) {
				throw new BadConfigFormatException("Too many commas in line " + lineCount + "of the room config file");
			}
			
			// If there is a leading space, remove it
			if(rest.charAt(0) == ' ')
				rest = rest.substring(1);
			
			// Put the room in the HashMap
			rooms.put(key, rest);
		}
	}
	
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		doorways = 0;
		Scanner lines = new Scanner(new FileReader(boardConfigFile));
		String next;
		String doorString = "UDLR"; //fastest way to search
		int currentRow = -1;
		
		while(lines.hasNext()){
			// Reads in the next line, and increments the row counter
			String line=lines.nextLine();
			this.rowCount++;
			currentRow++;
			
			// Defines a new scanner, which will iterate through comma by comma
			Scanner cell = new Scanner(line).useDelimiter(",");
			
			// Start the currentColumn at -1, it is incremented at the start of each loop
			int currentColumn = -1;
			
			while(cell.hasNext()) {
				currentColumn++;
				next = cell.next();
				BoardCell newCell;
				
				// If this is the first row we're going through, we want to count the columns
				if (currentRow == 0) {
					this.colCount++;
				}
				
				// If it's a W, add a walkwayCell
				if(next.equals("W")){
					newCell = new WalkwayCell(currentRow, currentColumn);
				} else {
					
					char firstChar = next.charAt(0);
					
					// If the part being read is not a character that's in the rooms map, throw a bad config format exception
					if(!this.rooms.keySet().contains(firstChar)){
						throw new BadConfigFormatException("Room character at location (" + currentRow + "," + currentColumn + ") is not in the rooms map");
					}
					
					// Create a new room cell at that location
					newCell = new RoomCell(currentRow, currentColumn, firstChar);
					
					// If it has a length equal to 2, then it's a doorway. Set the dor direction appropriately
					if(next.length() == 2){
						if(doorString.contains(String.valueOf(next.charAt(1)))){ //see if the key character for the door is in the string "udlr"
							((RoomCell) newCell).setRoomDirection(next.charAt(1));
							doorways++;
						}
					}
				}
				
				// Add this new boardCell to your board.
				cells.add(newCell);
				
			}
			
			// If this is not true, the number of columns is not constistent for all rows
			if(colCount != currentColumn + 1){
				throw new BadConfigFormatException("The number of columns is not consistent for all rows in the file");
			}
		
		}
	}
}


