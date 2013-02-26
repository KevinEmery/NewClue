import java.util.HashSet;
import java.util.LinkedList;



public class IntBoard {
	static final int dim=10;
	int[] boardDim;
	HashSet<Integer> targetList=new HashSet<Integer>();
	//HashSet<Integer> adjlist=new HashSet<Integer>();	// using a hashset so we don not have to worry about duplicates so we
	// we need not worry about making duplicates
	public IntBoard(){	// sets the board up as a 10x10 board if there are no dimensions
		boardDim=new int[2];
		boardDim[0]=dim;
		boardDim[1]=dim;
	}
	public IntBoard(int dimx, int dimy){	// sets dimensions if they are given
		boardDim=new int[2];
		boardDim[0]=dimx;
		boardDim[1]=dimy;
	}
	public HashSet<Integer> calcAdjacencies(int loc,int steps,HashSet<Integer> adjlist){
		//This function needs to do a recursive loop that counts down the steps to check if the space to the right, left, top and bottom
		//are valid places, if so it adds to adjlist, we need not worry about copies since this is a hashset.
		if(steps>0){
			if((loc-boardDim[0])>=0 && !adjlist.contains(loc-boardDim[0])){
				//Go up?
				adjlist.add(loc-boardDim[0]);
				return calcAdjacencies(loc-boardDim[0],steps-1,adjlist);
			} else if(loc+boardDim[0]<(boardDim[0]*boardDim[1]) && (!adjlist.contains(loc+boardDim[0]))){
				//Go down?
				adjlist.add(loc+boardDim[0]);
				return calcAdjacencies(loc+boardDim[0],steps-1,adjlist);
			} else if((loc-1)%boardDim[0]!=0 && !adjlist.contains(loc-1)){
				//Go left?
				adjlist.add(loc-1);
				return calcAdjacencies(loc-1,steps-1,adjlist);
			} else if((loc+1)%boardDim[0]!=0 && !adjlist.contains(loc+1)){
				//Go right?
				adjlist.add(loc+1);
				return calcAdjacencies(loc+1,steps-1,adjlist);
			} else {
				return adjlist;
			}
		} else {
			return adjlist;
		}
	}
	public void startTargets(int loc, int steps){
		HashSet<Integer> targetList = new HashSet<Integer>();
		targetList.add(loc);
		targetList=calcAdjacencies(loc, steps, targetList);
	}
	public HashSet<Integer> getTargets(){	// just used to return the target
		return targetList;
	}
	public HashSet<Integer> getAdjList(int i){	// just used to return the adjlist
		HashSet<Integer> adjlist = new HashSet<Integer>();
		//adjlist.add(i);
		return calcAdjacencies(i,1,adjlist);
	}
	private int calcIndex(int row, int column){	// turns a 2d board into a 1d list
		return column+row*boardDim[0];
	}
}
