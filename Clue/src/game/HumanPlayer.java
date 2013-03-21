package game;

import java.awt.Color;

public class HumanPlayer extends Player {
	
	public HumanPlayer() {
		this("Mrs. Scarlet", 174, Color.red);
	}
	
	public HumanPlayer(String name, int startingLocation, Color color) {
		super(name, startingLocation, color);
	}
	
	@Override
	public boolean isHumanPlayer() {
		return true;
	}

}
