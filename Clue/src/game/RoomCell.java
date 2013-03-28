package game;

import java.awt.Color;
import java.awt.Graphics;

public class RoomCell extends BoardCell{

	private char room;
	private DoorDirection doorDirection;
	public enum DoorDirection {UP,DOWN,LEFT,RIGHT,NONE, NAME};
	
	public RoomCell(int row, int col, char room){
		super(row, col);
		this.room=room;
		doorDirection = DoorDirection.NONE;
	}
	
	public void setRoomDirection(char direction){
		direction=Character.toUpperCase(direction);
		if(direction=='U'){
			this.doorDirection=DoorDirection.UP;
		} else if(direction=='R') {
			this.doorDirection=DoorDirection.RIGHT;
		} else if(direction=='L') {
			this.doorDirection=DoorDirection.LEFT;
		} else if(direction=='D') {
			this.doorDirection=DoorDirection.DOWN;
		} else if(direction=='N') {
			this.doorDirection=DoorDirection.NAME;
		}
	}
	
	public boolean isRoom(){
		return true;
	}


	public char getRoom() {
		return room;
	}
	
	public char getInitial() {
		return getRoom();
	}
	
	public DoorDirection getDoorDirection(){
		return doorDirection;

	}
	
	@Override
	public boolean isDoorway(){
		return doorDirection != DoorDirection.NONE && doorDirection != DoorDirection.NAME;
	}
	
	@Override
	void draw(Graphics g, Board board) {
		g.setColor(Color.gray);
		g.fillRect(column * ClueGame.CELL_WIDTH, row * ClueGame.CELL_HEIGHT, ClueGame.CELL_WIDTH, ClueGame.CELL_HEIGHT);
		
		if (isDoorway()) {
			g.setColor(Color.blue);
			if (this.doorDirection == DoorDirection.LEFT) {
				g.fillRect(column * ClueGame.CELL_WIDTH, row * ClueGame.CELL_HEIGHT, ClueGame.CELL_WIDTH/5, ClueGame.CELL_HEIGHT);
			} else if (this.doorDirection == DoorDirection.UP) {
				g.fillRect(column * ClueGame.CELL_WIDTH, row * ClueGame.CELL_HEIGHT, ClueGame.CELL_WIDTH, ClueGame.CELL_HEIGHT/5);
			} else if (this.doorDirection == DoorDirection.RIGHT) {
				g.fillRect((int) (column * ClueGame.CELL_WIDTH + ClueGame.CELL_WIDTH * 0.8), row * ClueGame.CELL_HEIGHT, ClueGame.CELL_WIDTH/5, ClueGame.CELL_HEIGHT);
			} else if (this.doorDirection == DoorDirection.DOWN) {
				g.fillRect(column * ClueGame.CELL_WIDTH, (int) (row * ClueGame.CELL_HEIGHT + ClueGame.CELL_HEIGHT * 0.8), ClueGame.CELL_WIDTH, ClueGame.CELL_HEIGHT/5);
			} 
		} else if (this.doorDirection == DoorDirection.NAME) {
			g.setColor(Color.blue);
			g.drawString(board.getRooms().get(room), column * ClueGame.CELL_WIDTH, row * ClueGame.CELL_HEIGHT - ClueGame.CELL_HEIGHT/2);
		}

	}
}
