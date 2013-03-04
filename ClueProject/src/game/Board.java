package game;

//Board class, contains the legend (cells) and the actual rooms.
import game.RoomCell.DoorDirection;
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
	
	// Map to store the adjacency list
	private Map<Integer, LinkedList<Integer>> adjList;
	
	// Set to store the targets for travel
	private Set<BoardCell> targets;

	// Stores the entire board
	private ArrayList<BoardCell> cells;
	
	// Stores the mapping from a character to the name of a room
	private Map<Character, String> rooms;
	
	// Contains the dimensions of the board
	private int numRows;
	private int numColumns;
	
	private String roomConfigFilename;
	private String boardConfigFilename;


	// Default = empty lists/sets + no columns and rows.
	// Loads the files required to pass the CR test suite
	public Board() {
		this ("ClueLayout.csv", "ClueLegend.txt");
	}
	
	// Sets the empty lists/sets and the names of the config files
	public Board(String boardConfigFilename, String roomConfigFilename) {
		this.cells = new ArrayList <BoardCell>();
		this.rooms = new HashMap <Character, String>();
		this.adjList = new HashMap <Integer, LinkedList<Integer>>();
		this.numRows = 0;
		this.numColumns = 0;
		this.boardConfigFilename = boardConfigFilename;
		this.roomConfigFilename = roomConfigFilename;
	}
	
	// Determines the neighbors of every cell on the board
	public void calcAdjacencies() {
		// Initializes an empty adjacency list for each cell on the board
		for (int i = 0; i < numRows * numColumns ; i++) {
			this.adjList.put(i, new LinkedList<Integer>());
		}
		
		// Iterates through every row and column, checking the validity of the cells on all four sides of a given cell. 
		// If it is valid, it adds it to the adjList
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (isValidCell(i + 1, j) && isAdjacent(calcIndex(i, j), calcIndex(i + 1, j), DoorDirection.DOWN, DoorDirection.UP))
					adjList.get(calcIndex(i,j)).add(calcIndex(i + 1, j));
				if (isValidCell(i - 1, j) && isAdjacent(calcIndex(i, j), calcIndex(i - 1, j), DoorDirection.UP, DoorDirection.DOWN))
					adjList.get(calcIndex(i,j)).add(calcIndex(i - 1, j));
				if (isValidCell(i, j + 1) && isAdjacent(calcIndex(i, j), calcIndex(i, j + 1), DoorDirection.RIGHT, DoorDirection.LEFT))
					adjList.get(calcIndex(i,j)).add(calcIndex(i, j + 1));
				if (isValidCell(i, j - 1) && isAdjacent(calcIndex(i, j), calcIndex(i, j - 1), DoorDirection.LEFT, DoorDirection.RIGHT))
					adjList.get(calcIndex(i,j)).add(calcIndex(i, j - 1));
			}
		}
	}
	
	// Determines if a given cell is valid on the game board
	private boolean isValidCell(int row, int column) {
		return row >= 0 && row < numRows && column >= 0 && column < numColumns;	
	}
	
	// Determines if a cell is adjacent based on if it's a door or room or next to one.
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
		boolean[] visited = new boolean[numRows * numColumns];
		targets = new HashSet<BoardCell>();
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
				targets.add(cells.get(i));
			else {
				calcTargets(i, numSteps - 1, visited);
			}
			visited[i] = false;	
		}
	}
	
	// Returns the set of targets
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	// Returns the adjacency list of a certain cell
	public LinkedList<Integer> getAdjList(int location) {
		return adjList.get(location);
	}
	
	public LinkedList<Integer> getAdjList(int row, int column) {
		return getAdjList(calcIndex(row, column));
	}
	
	// Loads the appropriate config files, and handles any errors associated with that process
	// Writes any errors out to the file log.txt
	public void loadConfigFiles() {
		try {
			loadRoomConfig();
			loadBoardConfig();
		} catch(BadConfigFormatException e) {
			System.err.println(e.getMessage());	
			
		} catch(FileNotFoundException e) {
			System.err.println("While trying to load config files, encountered a file not found: " + e.getMessage());
		}
	}
	
	// Calculates the index of a cell given the row and column
	public int calcIndex(int row, int column) {
		return numColumns * row + column;
	}
	
	// Returns the RoomCell at a given row and column
	public RoomCell getRoomCellAt(int index) {
		return  (RoomCell) cells.get(index);
	}

	// Returns a room cell at a given index
	public BoardCell getCellAt(int index) {
		return cells.get(index);
	}
	
	//Overload getCellAt for 2D
	public BoardCell getCellAt(int row, int column) {
		return cells.get(calcIndex(row, column));
	}
	
	// Returns the ArrayList containing all of the cells on the board
	public ArrayList<BoardCell> getCells() {
		return cells;
	}
	
	// Returns the rooms map
	public Map<Character, String> getRooms() {
		return rooms;
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}

	// Load the room configs from the provided legend file
	public void loadRoomConfig() throws  BadConfigFormatException, FileNotFoundException {
		// Creates a FileReader and then wraps it in a scanner to read the file
		FileReader roomConfigFile = new FileReader(roomConfigFilename);
		Scanner fileScanner = new Scanner(roomConfigFile);
		
		String nextLine;
		Character key;
		
		// While there is still something in the file, process it
		while(fileScanner.hasNext()) {
			// Read in the next line
			nextLine = fileScanner.nextLine();
			
			// If there is either no comma or more than one comma, throw an exception
			if(!nextLine.contains(",") || (nextLine.indexOf(',') != nextLine.lastIndexOf(',')))
				throw new BadConfigFormatException(roomConfigFilename, "Incorrect number of comma seperators - less than one or more than one.");
			key = nextLine.charAt(0);
			
			// If the first character is not a letter, throw an exception
			if(!(Character.isLetter(key))) 
				throw new BadConfigFormatException(roomConfigFilename, "Key was not a valid letter.");
			
			// If it's passed the exception checks, write this to the room map
			rooms.put(key, nextLine.substring((nextLine.indexOf(",")+1)).trim());
		}
	}

	// Load the board config from the provided board file
	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {
		// Create a file reader and wrap it in a scanner to read the file
		FileReader boardConfigFile = new FileReader(boardConfigFilename);
		Scanner fileScanner = new Scanner(boardConfigFile);
		
		// Initialize variables
		String[] cellInitials;
		int noColumns=0;
		int rowCounter=0;
		int currentColumn=0;
		boolean firstIteration = true;
		String tmp;
		
		// While there is something in the file, process it.
		while(fileScanner.hasNext()) {
			currentColumn=0;
			tmp = fileScanner.nextLine();
			
			// Split up the line, greedily splitting around whitespace and commas, and place it into an array
			cellInitials = tmp.split("[\\,\\s]+");
			
			// If this is the first iteration, initialize noColumns.
			if(firstIteration) {
				noColumns = cellInitials.length;
				firstIteration = false;
				
				// Otherwise, check to make sure that each row has the same number of columns as the first
			}  else if(noColumns != cellInitials.length) { 
				throw new BadConfigFormatException(boardConfigFilename, "The number of columns is not consistent across rows.");
			}
			
			
			// Go through the tokenized string and add new cells based on the character.
			for(String i : cellInitials)  {
				
				// Check to make sure that the room exists
				if(!rooms.containsKey(i.charAt(0))) {
					throw new BadConfigFormatException(boardConfigFilename, "The configuration file contains invalid room types.");
				}
				
				// Add the cell onto the board in sequential order as either a walkway or a room.
				cells.add((i.equals("W") ? new WalkwayCell() : new RoomCell(i, rowCounter, currentColumn)));
				++currentColumn;
			}
			++rowCounter;
		}
		
		// Set the board dimensions
		numColumns = noColumns;
		numRows = rowCounter;
	}

	// The functions below were written for the purpose of matching function calls made in the CR test suite
	// Translates this call of calcTargets into a call to startTargets
	public void calcTargets(int row, int column, int steps) {
		startTargets(calcIndex(row, column), steps);
	}
	
	// Translates this into our call of getRoomCellAt
	public RoomCell getRoomCellAt(int row, int column) {
		return  getRoomCellAt(calcIndex(row, column));
	}
	
}
