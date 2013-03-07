import java.util.HashSet;
public class IntBoard {
	static final int dim=23;
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
	public void startTargets(int loc, int steps){
		this.targetList = new HashSet<Integer>();
		this.targetList=calcAdjacencies(loc, steps, targetList);
	}
	public HashSet<Integer> getTargets(){	// just used to return the target
		return this.targetList;
	}
	public HashSet<Integer> getAdjList(int i){	// just used to return the adjlist
		HashSet<Integer> adjlist = new HashSet<Integer>();
		return calcAdjacencies(i,1,adjlist);
	}
	public int calcIndex(int row, int column){	// turns a 2d board into a 1d list
		return column+row*boardDim[1];
	}
	public int[] calcRowCol(int cellIndex){
		int [] cellArray = {cellIndex/boardDim[0],cellIndex%boardDim[0]};
		return cellArray;
	}
}
