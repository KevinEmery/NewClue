import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;



	public class Board {
		private ArrayList<BoardCell> cells = new ArrayList<BoardCell>();
		private ArrayList<String> list=new ArrayList<String>();
		private Map<Character,String> rooms= new HashMap<Character, String>();
		int[] boardDim;
		int doorways=0;
		HashSet<Integer> targetList=new HashSet<Integer>();
		public Board(String string, String string2) throws BadConfigFormatException {
			boardDim=new int[2];
			String line = null;
			boardDim[0]=boardDim[1] = 0;
			int linelength=-1;
			int yaxis=0;
			try {
				Scanner input=new Scanner(new BufferedReader(new FileReader(string)));
				while( input.hasNext()){
					int xaxis=0;	
					line = input.next();
					line.split(",\\n");

					for(int i=0; i<line.length();i++){
						if(line.charAt(i)!=','){
							BoardCell e=null;
							if(line.charAt(i)=='W'){ e=new WalkwayCell(yaxis,xaxis );}
							else {e=new RoomCell(yaxis,xaxis, line.charAt(i));
							((RoomCell) e).setRoom(line.charAt(i));
							if(i<line.length()-1){
								if(line.charAt(i+1)!=','){
									((RoomCell) e).setRoomDirection(line.charAt(i+1));
									doorways++;
								}else{((RoomCell) e).setRoomDirection('n');}
							}
							}
							if (e!=null){cells.add(e);}

							xaxis++;

						}

						if (linelength==-1){linelength=line.length();}
						if(linelength!=line.length()){throw new BadConfigFormatException();}
						list.add(line);
						boardDim[0]++;// just read a row


					}

					for (int i=0; i < line.length(); i++){
						if (line.charAt(i) == ',')//counts the commas that are in between the data
						{boardDim[1]++;}	
					}
					boardDim[1]++;//adds one more so it is a count of the things on either side of the commas

				}

			} catch (FileNotFoundException e) {
				System.out.println("File could not be read");
				System.exit(0);
			}




		}
	
		public Board()	{

			boardDim=new int[2];
			String line = null;
			boardDim[0]=boardDim[1] = 0;

			try {
				Scanner input=new Scanner(new BufferedReader(new FileReader("etc/Clue_map.csv")));
				int yaxis=0;
				while( input.hasNext()){
					int xaxis=0;
					line = input.next();
					line.split(",\\n");
					for(int i=0; i<line.length();i++){
						if(line.charAt(i)!=','){
							BoardCell e=null;
							if(line.charAt(i)=='W'){ e=new WalkwayCell(yaxis,xaxis );}
							else {e=new RoomCell(yaxis,xaxis, line.charAt(i));
							((RoomCell) e).setRoom(line.charAt(i));
							if(i<line.length()-1){
								if(line.charAt(i+1)!=','){
									if(line.charAt(i+1)=='d'){
										((RoomCell) e).setRoomDirection('D');
										//doorways++;
									}else if(line.charAt(i+1)=='u'){
										((RoomCell) e).setRoomDirection('U');
									//	doorways++;

									}else if(line.charAt(i+1)=='l'){
										((RoomCell) e).setRoomDirection('L');
										//doorways++;
									}else if(line.charAt(i+1)=='r'){
										((RoomCell) e).setRoomDirection('R');
										//doorways++;
									}
									i++;	



								}else{((RoomCell) e).setRoomDirection('n');}
							}
							}
							if (e!=null){cells.add(e);}

							xaxis++;

						}

					}

					boardDim[0]++;// just read a row

				}

				for (int i=0; i < line.length(); i++){
					if (line.charAt(i) == ',')//counts the commas that are in between the data
					{boardDim[1]++;}	
				}
				boardDim[1]++;//adds one more so it is a count of the things on either side of the commas


			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void loadConfigFiles(){

		}
		public int calcIndex(int row, int column){	// turns a 2d board into a 1d list
			System.out.println(getNumColumns());		
			return column+row*getNumColumns();
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
				int i=0;
				Scanner input=new Scanner(new BufferedReader(new FileReader("etc/ClueLegend.txt")));
				while(  input.hasNext()){
					String cha= input.next();
					char c=cha.charAt(0);
					String r=input.nextLine();

					rooms.put(c, r);
				}
			}catch (FileNotFoundException e){
				System.out.println("Could not open Key");
				System.exit(0);
			}
			return rooms;
		}
		public void setRooms(Map<Character, String> rooms) {
			this.rooms = rooms;
		}

		public BoardCell getCellAt(int calcIndex) {
			return cells.get(calcIndex);
		}

		public HashSet<Integer> calcAdjacencies(int loc,int steps,HashSet<Integer> adjlist){
			//This function needs to do a recursive loop that counts down the steps to check if the space to the right, left, top and bottom
			//are valid places, if so it adds to adjlist, we need not worry about copies since this is a hashset.

			// System.out.println(steps+" "+loc+ " "+(getCellAt(loc).isDoorway()+" "+getCellAt(loc).isWalkway()));

			//		System.out.println(steps+" "+loc+ " "+(getCellAt(loc).isDoorway()+" "+getCellAt(loc).isWalkway()));

			//System.out.println(adjlist.size());
			if(steps>0 && (getCellAt(loc).isDoorway()||getCellAt(loc).isWalkway())){
				if(getCellAt(loc).isDoorway()){
					if((loc-boardDim[1])>=0 && getCellAt(loc-boardDim[0]).isWalkway()){
						//Go up?
						adjlist.addAll(calcAdjacencies(loc-boardDim[0],steps-1,adjlist));
					}
					if(loc+boardDim[1]<(cells.size()) && getCellAt(loc+boardDim[0]).isWalkway()){
						//Go down?
						adjlist.addAll(calcAdjacencies(loc+boardDim[0],steps-1,adjlist));
					}

					if((getCellAt(loc).getColumn()-1)>=0 && (loc-1)>=0 && getCellAt(loc-1).isWalkway()){
						//Go left?
						adjlist.addAll(calcAdjacencies(loc-1,steps-1,adjlist));
					}
					if((getCellAt(loc+1).getColumn())!=0 && (loc+1)<=(boardDim[0]*boardDim[1]) && getCellAt(loc+1).isWalkway()){

						if((getCellAt(loc).column-1)>=0 && (loc-1)>=0 && getCellAt(loc-1).isWalkway()){
							//Go left?
							adjlist.addAll(calcAdjacencies(loc-1,steps-1,adjlist));
						}
						if((getCellAt(loc+1).column)!=0 && (loc+1)<=(boardDim[0]*boardDim[1]) && getCellAt(loc+1).isWalkway()){
							//Go right?
							adjlist.addAll(calcAdjacencies(loc+1,steps-1,adjlist));
						}
					} else {
						if((loc-boardDim[1])>=0 && (getCellAt(loc-boardDim[0]).isDoorway()||getCellAt(loc-boardDim[0]).isWalkway())){
							//Go up?
							if(getCellAt(loc-boardDim[0]).isDoorway() && ((RoomCell)getCellAt(loc-boardDim[0])).getDoorDirection().equals("D")){
								adjlist.addAll(calcAdjacencies(loc-boardDim[0],steps-1,adjlist));
							} else if(!getCellAt(loc-boardDim[0]).isDoorway()){
								adjlist.addAll(calcAdjacencies(loc-boardDim[0],steps-1,adjlist));
							}
						}
						if((loc+boardDim[1])<(cells.size()) && (getCellAt(loc+boardDim[0]).isDoorway()||getCellAt(loc+boardDim[0]).isWalkway())){
							//Go down?
							if(getCellAt(loc+boardDim[0]).isDoorway() && ((RoomCell)getCellAt(loc+boardDim[0])).getDoorDirection().equals("U")){
								adjlist.addAll(calcAdjacencies(loc+boardDim[0],steps-1,adjlist));
							} else if(!getCellAt(loc+boardDim[0]).isDoorway()){
								adjlist.addAll(calcAdjacencies(loc+boardDim[0],steps-1,adjlist));
							}
						}
						if((getCellAt(loc).getColumn()-1)>=0 && (loc-1)>=0 && (getCellAt(loc-1).isDoorway()||getCellAt(loc-1).isWalkway())){
							if((getCellAt(loc).column-1)>=0 && (loc-1)>=0 && (getCellAt(loc-1).isDoorway()||getCellAt(loc-1).isWalkway())){

								//Go left?
								if(getCellAt(loc-1).isDoorway() && ((RoomCell)getCellAt(loc-1)).getDoorDirection().equals("R")){
									adjlist.addAll(calcAdjacencies(loc-1,steps-1,adjlist));
								} else if(!getCellAt(loc-1).isDoorway()){
									adjlist.addAll(calcAdjacencies(loc-1,steps-1,adjlist));
								}
							}

							if((getCellAt(loc+1).getColumn())!=0 && (loc+1)<=(boardDim[0]*boardDim[1]) && (getCellAt(loc+1).isDoorway()||getCellAt(loc+1).isWalkway())){

								if(getCellAt(loc+1).isDoorway() && ((RoomCell)getCellAt(loc+1)).getDoorDirection().equals("L")){
									adjlist.addAll(calcAdjacencies(loc+1,steps-1,adjlist));
								} else if(!getCellAt(loc+1).isDoorway()){
									adjlist.addAll(calcAdjacencies(loc+1,steps-1,adjlist));
								}
							}
						}
						return adjlist;
					}
					if(steps==0 && (getCellAt(loc).isDoorway()||getCellAt(loc).isWalkway())){
						if(!adjlist.contains(loc)){
							adjlist.add(loc);
						}
						return adjlist;
					} else {
						return adjlist;
					}
				}
			}
			return adjlist;
		}

		public HashSet<Integer> getAdjList(int calcIndex) {
			HashSet<Integer> adjlist = new HashSet<Integer>();

			return calcAdjacencies(calcIndex,1,adjlist); 


		}
		public void calcTargets(int i, int j, int k) {
			targetList=calcAdjacencies(calcIndex(i,j),k,new HashSet<Integer>());
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
			//return boardDim[1];
			return 23;
		}
		public int getNumRows() {
			return boardDim[0];
		}





		public void loadRoomConfig() {
			// TODO Auto-generated method stub

		}






	}
