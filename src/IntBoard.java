import java.util.HashSet;
import java.util.LinkedList;



public class IntBoard {
	int boarddimx;		//setting up all the variables
	int boarddimy;
	HashSet<Integer> targetList=new HashSet<Integer>();
	HashSet<Integer> adjlist=new HashSet<Integer>();// using a hashset so we don not have to worry about duplicates so we
													// we need not worry about making duplicates
	public IntBoard(){// sets the board up as a 10x10 board if there are no dimenstions
		boarddimx=boarddimy=10;
	}
	public IntBoard(int dimx, int dimy){// sets dimensions if they are given
		boarddimx=dimx;
		boarddimy=dimy;
	}
	public HashSet<Integer> calcAdgancies(int loc,int steps){
		//This function needs to do a recursive loop that counts down the steps to check if the space to the right, left, top and bottom
		//are valid places, if so it adds to adjlist, we need not worry about copies since this is a hashset.
		
		
		return adjlist;
	}
	public void startTargets(int loc, int steps){
		targetList=calcAdgancies(loc, steps);
		
		
	}
	public HashSet<Integer> getTargets(){// just used to return the target
		return targetList;
	}
	public HashSet<Integer> getAdjList(int i){// just used to return the adjlist
		return adjlist;
	}
	public int calcIndex(int row, int column){// turns a 2d board into a 1d list
		int retrn=column+row*boarddimx;
		return retrn;
	}
	
}
