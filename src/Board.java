import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	ArrayList<BoardCell> cells = new ArrayList<BoardCell>();
	private Map<Character,String> rooms= new HashMap<Character, String>();
	private HashSet<Integer> targetList=new HashSet<Integer>();
	private HashSet<Integer> visited=new HashSet<Integer>();
	int[] boardDim;
	int room;
	int doorwaynumber;
	public Board(String string, String string2) throws BadConfigFormatException, FileNotFoundException {
		boardDim=new int[2];
		boardDim[0]=0;
		boardDim[1]=0;
		Scanner lines=new Scanner(new BufferedReader(new FileReader(string)));
		lines.useDelimiter("[\\n]");
		String iter=new String();
		String doorString = "UDLR"; //fastest way to search
		boardDim[0]=-1;
		int linelength=-1;
		while(lines.hasNext()){
			
			String line=lines.next();
			if(linelength==-1){linelength=line.length();}
			if(linelength!=line.length()){throw new BadConfigFormatException();}
			linelength=line.length();
			boardDim[0]+=1;
			
			Scanner cell = new Scanner(line).useDelimiter(",");
			while(cell.hasNext()){
				iter=cell.next();
				BoardCell e=null;
				
				if(iter.equals("W")){
					
					e=new WalkwayCell(boardDim[0],boardDim[1]);
				} else {
					e=new RoomCell(boardDim[0],boardDim[1],iter.charAt(0));
					room++;
					if(iter.length()>1){
						
						if(doorString.contains(String.valueOf(iter.charAt(1)))){ //see if the key character for the door is in the string "udlr"
							
							((RoomCell) e).setRoomDirection(iter.charAt(1));
						}
					}
				}
				
				cells.add(e);
				boardDim[1]+=1;
			}
		}
		boardDim[0]++;
	}
	public Board()	{
		boardDim=new int[2];
		boardDim[0]=0;
		boardDim[1]=0;
		try {
			Scanner lines=new Scanner(new BufferedReader(new FileReader("etc/ClueLayout.csv")));
			lines.useDelimiter("[\\n]");
			String iter=new String();
			String doorString = "UDLR"; //fastest way to search
			boardDim[0]=0;
			while(lines.hasNext()){
				String line=lines.next();
				boardDim[1]=0;
				//System.out.println(boardDim[0]+","+boardDim[1]);
				//System.out.print("\n");
				Scanner cell = new Scanner(line).useDelimiter(",");
				while(cell.hasNext()){
					iter=cell.next();
					BoardCell e=null;
					iter=iter.replaceAll("[\\n\\r]", ""); //regex to remove returns and newlines (yes, they're different)
					//System.out.println(iter.charAt(0)+" at "+ boardDim[0]+" ,"+boardDim[1]+"\n");
					if(iter.equals("W")){
						//System.out.println(iter+ " is a walkway at "+ boardDim[1]+" ,"+boardDim[0]);
						e=new WalkwayCell(boardDim[0],boardDim[1]);
					} else {
						room++;
						e=new RoomCell(boardDim[0],boardDim[1],iter.charAt(0));
						if(iter.length()>1){
							//System.out.print(iter.charAt(1));
							if(doorString.contains(String.valueOf(iter.charAt(1)))){ //see if the key character for the door is in the string "udlr"
								//Creates the door using that character
								((RoomCell) e).setRoomDirection(iter.charAt(1));
								doorwaynumber++;
							}
						}
					}
					//System.out.println(e.row+" ,"+e.column);
					cells.add(e);
					boardDim[1]+=1;
				}
				boardDim[0]+=1;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		boardDim[0]++;
		//System.out.println(boardDim[0]+" "+boardDim[1]);
	}
	public void loadConfigFiles(){

	}
	public int calcIndex(int row, int column){	// turns a 2d board into a 1d list
		return column+row*(boardDim[1]);
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
		try{
			Scanner input=new Scanner(new BufferedReader(new FileReader("etc/ClueLegend.txt")));
			while( input.hasNext()){
				String cha= input.next();
				char c=cha.charAt(0);
				String r=input.nextLine();
				rooms.put(c, r);
			}
		}catch (FileNotFoundException e){
			System.out.println("Could not open Key");
			System.exit(0);}
		return rooms;
	}
	public void setRooms(Map<Character, String> rooms) {
		this.rooms = rooms;
	}

	public BoardCell getCellAt(int calcIndex) {
		return cells.get(calcIndex);
	}

	public HashSet<Integer> calcAdjacencies(int loc,int steps,HashSet<Integer> adjlist,HashSet<Integer> visisted){
		//This function needs to do a recursive loop that counts down the steps to check if the space to the right, left, top and bottom
		//are valid places, if so it adds to adjlist, we need not worry about copies since this is a hashset.
		// System.out.println(steps+" "+loc+ " "+(getCellAt(loc).isDoorway()+" "+getCellAt(loc).isWalkway()));
		//System.out.println(adjlist.size());
		if(steps>0 && (getCellAt(loc).isDoorway()||getCellAt(loc).isWalkway())){
			if(getCellAt(loc).isDoorway()){
				if((loc-boardDim[1])>=0 && getCellAt(loc-boardDim[0]).isWalkway()&&!visited.contains(loc-boardDim[1])){
					//Go up?
					adjlist.addAll(calcAdjacencies(loc-boardDim[0],steps-1,adjlist, visited));
				}
				if(loc+boardDim[1]<(cells.size()) && getCellAt(loc+boardDim[0]).isWalkway()&&!visited.contains(loc+boardDim[1])){
					//Go down?
					adjlist.addAll(calcAdjacencies(loc+boardDim[0],steps-1,adjlist,visited));
				}
				if((getCellAt(loc).column-1)>=0 && (loc-1)>=0 && getCellAt(loc-1).isWalkway()&&!visited.contains(getCellAt(loc).column-1)){
					//Go left?
					adjlist.addAll(calcAdjacencies(loc-1,steps-1,adjlist,visited));
				}
				if((getCellAt(loc+1).column)!=0 && (loc+1)<=(boardDim[0]*boardDim[1]) && getCellAt(loc+1).isWalkway()&&!visited.contains(getCellAt(loc).column+1)){
					//Go right?
					adjlist.addAll(calcAdjacencies(loc+1,steps-1,adjlist,visited));
				}
			} else {
				if((loc-boardDim[1])>=0 && (getCellAt(loc-boardDim[0]).isDoorway()||getCellAt(loc-boardDim[0]).isWalkway())){
					//Go up?
					if(getCellAt(loc-boardDim[0]).isDoorway() && ((RoomCell)getCellAt(loc-boardDim[0])).getDoorDirection().equals("D")&&!visited.contains(loc-boardDim[0])){
						adjlist.addAll(calcAdjacencies(loc-boardDim[0],steps-1,adjlist,visited));
					} else if(!getCellAt(loc-boardDim[0]).isDoorway()){
						adjlist.addAll(calcAdjacencies(loc-boardDim[0],steps-1,adjlist,visited));
					}
				}
				if((loc+boardDim[1])<(cells.size()) && (getCellAt(loc+boardDim[0]).isDoorway()||getCellAt(loc+boardDim[0]).isWalkway())){
					//Go down?
					if(getCellAt(loc+boardDim[0]).isDoorway() && ((RoomCell)getCellAt(loc+boardDim[0])).getDoorDirection().equals("U")&&!visited.contains(loc+boardDim[0])){
						adjlist.addAll(calcAdjacencies(loc+boardDim[0],steps-1,adjlist,visited));
					} else if(!getCellAt(loc+boardDim[0]).isDoorway()){
						adjlist.addAll(calcAdjacencies(loc+boardDim[0],steps-1,adjlist,visited));
					}
				}
				if((getCellAt(loc).column-1)>=0 && (loc-1)>=0 && (getCellAt(loc-1).isDoorway()||getCellAt(loc-1).isWalkway())){
					//Go left?
					if(getCellAt(loc-1).isDoorway() && ((RoomCell)getCellAt(loc-1)).getDoorDirection().equals("R")&&!visited.contains(loc-1)){
						adjlist.addAll(calcAdjacencies(loc-1,steps-1,adjlist, visisted));
					} else if(!getCellAt(loc-1).isDoorway()){
						adjlist.addAll(calcAdjacencies(loc-1,steps-1,adjlist, visisted));
					}
				}
				if((getCellAt(loc+1).column)!=0 && (loc+1)<=(boardDim[0]*boardDim[1]) && (getCellAt(loc+1).isDoorway()||getCellAt(loc+1).isWalkway())){
					if(getCellAt(loc+1).isDoorway() && ((RoomCell)getCellAt(loc+1)).getDoorDirection().equals("L")&&!visited.contains(loc+boardDim[0])){
						adjlist.addAll(calcAdjacencies(loc+1,steps-1,adjlist,visited));
					} else if(!getCellAt(loc+1).isDoorway()){
						adjlist.addAll(calcAdjacencies(loc+1,steps-1,adjlist,visited));
					}
				}
			}
			return adjlist;
		}else if(steps>0 && getCellAt(loc).isRoom()){
		}
		if(steps==0 && (getCellAt(loc).isDoorway()||getCellAt(loc).isWalkway())){
			if(!adjlist.contains(loc)){
				adjlist.add(loc);
				//visited.add(loc);
			}
			return adjlist;
		} else {
			
			return adjlist;
		}
	}


	public HashSet<Integer> getAdjList(int calcIndex) {
		HashSet<Integer> adjlist = new HashSet<Integer>();
		return calcAdjacencies(calcIndex,1,adjlist,null);

	}
	public void calcTargets(int i, int j, int k) {
		targetList=calcAdjacencies(calcIndex(i,j),k,new HashSet<Integer>(),null);
		targetList.remove(calcIndex(i,j));
	}
	public Set<BoardCell> getTargets() {
		Set<BoardCell> targets = new HashSet<BoardCell>();
		for(int currTar:targetList){
			targets.add(getCellAt(currTar));
		}
		return targets;
	}
	public int getNumColumns() {

		return boardDim[1];
	}
	public int getNumRows() {
		//System.out.println(boardDim[0]);
		return boardDim[0];
	}
	public void loadRoomConfig() {
		
	}
	public int getRoomNum(){
		return room;
	}
	public int getNumDoorway() {
		return doorwaynumber;
	}


}