package utilities;

import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class IntBoard {
	
	// Constants to declare the board size
	private final int NUMBER_OF_ROWS;
	private final int NUMBER_OF_COLUMNS;
	
	// Map to store the adjacency list
	private Map<Integer, LinkedList<Integer>> adjList;
	
	// Set to store the targets for travel
	private Set<Integer> targets;

	// Default constructor, to be called when no arguments are given. Defaults to a 4x4 board
	public IntBoard() {
		this(4, 4);
	}

	// Creates a "board" with dimensions rows x cols. Initiates a new adjacency list and initalizes a spot for each cell and a new linked list with it
	public IntBoard(int rows, int cols) {
		NUMBER_OF_ROWS = rows;
		NUMBER_OF_COLUMNS = cols;
		adjList = new HashMap<Integer, LinkedList<Integer>>();
		for (int i = 0; i < NUMBER_OF_ROWS * NUMBER_OF_COLUMNS; i++) {
			adjList.put(i, new LinkedList<Integer>());
		}
	}
	
	// Determines the neighbors of every cell on the board
	public void calcAdjacencies() {
		// Iterates through every row and column, checking the validity of the cells on all four sides of a given cell. 
		// If it is valid, it adds it to the adjList
		for (int i = 0; i < NUMBER_OF_ROWS; i++) {
			for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
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
		return row >= 0 && row < NUMBER_OF_ROWS && column >= 0 && column < NUMBER_OF_COLUMNS;
		
	}

	// Initalies the visted array, targets as empty, and then calls the recursive function to calculate targets
	public void startTargets(int location, int numSteps) {
		boolean[] visited = new boolean[NUMBER_OF_ROWS * NUMBER_OF_COLUMNS];
		targets = new HashSet<Integer>();
		visited[location] = true;
		calcTargets(location, numSteps, visited);
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
				targets.add(i);
			else {
				calcTargets(i, numSteps - 1, visited);
			}
			visited[i] = false;	
		}
	}
	
	// Returns the set of targets
	public Set<Integer> getTargets() {
		return targets;
	}
	
	// Returns the adjacency list of a certain cell
	public LinkedList<Integer> getAdjList(int location) {
		return adjList.get(location);
	}
	
	// Calculates the index of a certain cell based on it's row and column
	public int calcIndex(int row, int column) {	
		return NUMBER_OF_COLUMNS * row + column;
	}
}
