package game;

//Board class, contains the legend (cells) and the actual rooms.
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

	private String roomConfigFilename;
	private String boardConfigFilename;
	private ArrayList<BoardCell> cells;
	private Map<Character, String> rooms;
	private int numRows;
	private int numColumns;

	//default = empty lists/sets + no columns and rows.
	public Board() {
		this ("ClueLayout.csv", "ClueLegend.txt");
	}
	
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
		for (int i = 0; i < numRows * numColumns ; i++) {
			this.adjList.put(i, new LinkedList<Integer>());
		}
		// Iterates through every row and column, checking the validity of the cells on all four sides of a given cell. 
		// If it is valid, it adds it to the adjList
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (isValidCell(i + 1, j))
					adjList.get(calcIndex(i,j)).add(calcIndex(i + 1, j));
				if (isValidCell(i - 1, j))
					adjList.get(calcIndex(i,j)).add(calcIndex(i - 1, j));
				if (isValidCell(i, j + 1))
					adjList.get(calcIndex(i,j)).add(calcIndex(i, j + 1));
				if (isValidCell(i, j - 1))
					adjList.get(calcIndex(i,j)).add(calcIndex(i, j - 1));
			}
		}
	}
	
	// Determines if a given cell is valid on the game board
	private boolean isValidCell(int row, int column) {
		return row >= 0 && row < numRows && column >= 0 && column < numColumns;
		
	}

	// Initalies the visted array, targets as empty, and then calls the recursive function to calculate targets
	public void startTargets(int location, int numSteps) {
		boolean[] visited = new boolean[numRows * numColumns];
		targets = new HashSet<BoardCell>();
		visited[location] = true;
		calcTargets(location, numSteps, visited);
	}
	
	public void calcTargets(int row, int column, int steps) {
		
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
			if (numSteps == 1) 
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
	
	public void loadConfigFiles() {
		try {
			loadRoomConfig();
			loadBoardConfig();
		} catch(BadConfigFormatException e) {
			System.err.println(e.getMessage());
			
		} catch(FileNotFoundException e) {
			System.err.println("While trying to load config files, encountered a file not found: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public int calcIndex(int row, int column) {
		return numColumns * row + column;
	}
	
	public RoomCell getRoomCellAt(int row, int column) {
		return  (RoomCell) cells.get(calcIndex(row, column));
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

	public void loadRoomConfig() throws  BadConfigFormatException, FileNotFoundException{
		FileReader roomConfigFile = new FileReader(roomConfigFilename);
		Scanner fileScanner = new Scanner(roomConfigFile);
		String nextLine;
		Character key;
		while(fileScanner.hasNext()) {
			nextLine = fileScanner.nextLine();
			if(!nextLine.contains(",") || (nextLine.indexOf(',') != nextLine.lastIndexOf(',')))
				throw new BadConfigFormatException(roomConfigFilename, "Incorrect number of comma seperators - less than one or more than one.");
			key = nextLine.charAt(0);
			if(!(Character.isLetter(key))) 
				throw new BadConfigFormatException(roomConfigFilename, "Key was not a valid letter.");
			rooms.put(key, nextLine.substring((nextLine.indexOf(",")+1)).trim());
		}
	}

	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader boardConfigFile = new FileReader(boardConfigFilename);
		Scanner fileScanner = new Scanner(boardConfigFile);
		String[] cellInitials;
		int noColumns=0;
		int rowCounter=0;
		int currentColumn=0;
		boolean firstIteration = true;
		String tmp;
		while(fileScanner.hasNext()) {
			currentColumn=0;
			tmp = fileScanner.nextLine();
			//split up the line, greedily splitting around whitespace and commas..
			cellInitials = tmp.split("[\\,\\s]+");
			//if the first iteration, initialize noColumns.
			if(firstIteration) {
				noColumns = cellInitials.length;
				firstIteration = false;
			}  else if(noColumns != cellInitials.length) { 
				throw new BadConfigFormatException(boardConfigFilename, "The number of columns is not consistent across rows.");
			}
			//go through the tokenized string and add new cells based on the character.
			
			for(String i : cellInitials)  {
				if(!rooms.containsKey(i.charAt(0))) {
					throw new BadConfigFormatException(boardConfigFilename, "The configuration file contains invalid room types.");
				}
				cells.add((i.equals("W") ? new WalkwayCell() : new RoomCell(i, rowCounter, currentColumn)));
				++currentColumn;
			}
			++rowCounter;
		}
		numColumns = noColumns;
		numRows = rowCounter;
	}

	public BoardCell getCellAt(int calcIndex) {
		return cells.get(calcIndex);
	}
	
}
