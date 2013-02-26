package utilities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	
	// Constants to declare the board size
	private final int NUMBER_OF_ROWS = 4;
	private final int NUMBER_OF_COLUMNS = 4;
	
	// Map to store the adjacency list
	private Map<Integer, LinkedList<Integer>> adjList;
	
	// Set to store the targets for travel
	private Set<Integer> targets;

	// Initiates a new adjacency list and initalizes a spot for each cell and a new linked list with it
	public IntBoard() {
		adjList = new HashMap<Integer, LinkedList<Integer>>();
		for (int i = 0; i < NUMBER_OF_ROWS * NUMBER_OF_COLUMNS; i++) {
			adjList.put(i, new LinkedList<Integer>());
		}
	}
	
	// Determines what cells each cell is adjacent to.
	public void calcAdjacencies() {
		for (int i = 0; i < NUMBER_OF_ROWS; i++) {
			for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
				if (i + 1 >= 0 && i + 1 < NUMBER_OF_ROWS) {
					adjList.get(calcIndex(i,j)).add(calcIndex(i + 1, j));
				} if (i - 1 >= 0 && i - 1 < NUMBER_OF_ROWS)
					adjList.get(calcIndex(i,j)).add(calcIndex(i - 1, j));
				if (j + 1 >= 0 && j + 1 < NUMBER_OF_COLUMNS)
					adjList.get(calcIndex(i,j)).add(calcIndex(i, j + 1));
				if (j - 1 >= 0 && j - 1 < NUMBER_OF_COLUMNS)
					adjList.get(calcIndex(i,j)).add(calcIndex(i, j - 1));
			}
		}
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
