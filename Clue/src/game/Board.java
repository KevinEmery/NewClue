package game;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import game.RoomCell.DoorDirection;

public class Board {
	
	// This data structures are used to store all of the board information
	private ArrayList<BoardCell> cells;
	private Map<Character,String> rooms;
	private Set<BoardCell> targetList;
	private Map<Integer, LinkedList<Integer>> adjList;
	
	// The board dimensions, as set by the loadBoardConfig() function, are placed here
	int rowCount;
	int colCount;
	
	// The name of the config files, as set by the constructors, are placed here
	private String boardConfigFile;
	private String roomConfigFile;
	
	// This variable, set by loadBoardConfig(), is only used for testing purposes
	private int doorways;
	
	// Default constructor that calls the parameterized constructor
	public Board()	 {
		this("etc/ClueLayout.csv", "etc/ClueLegend.txt");
	}
	
	// Parameterized constructor that sets a few basic variables.
	public Board(String boardConfigFile, String roomConfigFile) {
		this.boardConfigFile = boardConfigFile;
		this.roomConfigFile = roomConfigFile;
		this.rowCount = 0;
		this.colCount = 0;
	}
	
	// Calculates the index given a row and column
	public int calcIndex(int row, int column){	// turns a 2d board into a 1d list
		return column + row * colCount;
	}
	
	// Determines the neighbors of every cell on the board
	public void calcAdjacencies() {
		adjList = new HashMap<Integer, LinkedList<Integer>>();
		
		// Iterates through every row and column, checking the validity of the cells on all four sides of a given cell. 
		// If it is valid, it adds it to the adjList
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				
				// Initializes the variables to be used in computations
				LinkedList<Integer> newList = new LinkedList<Integer>();
				int currentCell = calcIndex(i, j);
				int cellAbove = calcIndex(i - 1, j);
				int cellBelow = calcIndex(i + 1, j);
				int cellLeft = calcIndex(i, j - 1);
				int cellRight = calcIndex(i, j + 1);
				
				// Checks the validity of each adjacent cell, and whether it is adjacent based on the cell type
				if (isValidCell(i + 1, j) && isAdjacent(currentCell, cellBelow, DoorDirection.DOWN, DoorDirection.UP))
					newList.add(cellBelow);
				if (isValidCell(i - 1, j) && isAdjacent(currentCell, cellAbove, DoorDirection.UP, DoorDirection.DOWN))
					newList.add(cellAbove);
				if (isValidCell(i, j + 1) && isAdjacent(currentCell, cellRight, DoorDirection.RIGHT, DoorDirection.LEFT))
					newList.add(cellRight);
				if (isValidCell(i, j - 1) && isAdjacent(currentCell, cellLeft, DoorDirection.LEFT, DoorDirection.RIGHT))
					newList.add(cellLeft);
				
				// Adds this mapping to the adjacency list
				adjList.put(currentCell, newList);
			}
		}
	}
	
	// Determines if a given cell is valid on the game board
	private boolean isValidCell(int row, int column) {
		return row >= 0 && row < rowCount && column >= 0 && column < colCount;	
	}
	
	// Determines if a cell is adjacent based on the current index and the index of the new cell, and the directions of doors in the area
	private boolean isAdjacent(int currentIndex, int newIndex, DoorDirection directionOut, DoorDirection directionIn) {

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
	
	// Call to start targets in order to pass her tests
	public void calcTargets(int row, int column, int steps) {
		startTargets(calcIndex(row, column), steps);
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
	
	// Loads the room mapping out of the key
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
	
	// Loads the board config out of the given file
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		cells = new ArrayList<BoardCell>();
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
							doorways += 1;
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

	// Returns a Room Cell at the given row and column
	public RoomCell getRoomCellAt(int row, int column){
		return ((RoomCell)cells.get(calcIndex(row,column))) ; 
	}
	
	// Returns a Board Cell at a given index
	public BoardCell getCellAt(int calcIndex) {
		return cells.get(calcIndex);
	}
	
	// Returns the mapping from a character to the rooms
	public Map<Character, String> getRooms() {
		return rooms;
	}

	// Returns the adjacency list
	public LinkedList<Integer> getAdjList(int calcIndex) {
		return adjList.get(calcIndex);
	}
	
	// Returns the list of targets
	public Set<BoardCell> getTargets() {
		return targetList;
	}
	
	public int getNumColumns() {
		return colCount;
	}
	
	public int getNumRows() {
		return rowCount;
	}

	
	public int getDoorways() {
		return doorways;
	}
}