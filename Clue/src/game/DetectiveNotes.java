package game;
import game.Card.CardType;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class DetectiveNotes extends JFrame {
	
	private ArrayList<String> playerList;
	private ArrayList<String> weaponList;
	private ArrayList<String> roomList;
	
	public DetectiveNotes(ArrayList<Card> cards) {
		// Sets the standard window information		
		this.setLayout(new GridLayout(0,2));
		setTitle("Detective Notes");
		setSize(600, 700);
		
		// Initialize the lists
		playerList = new ArrayList<String>();
		weaponList = new ArrayList<String>();
		roomList = new ArrayList<String>();
		
		// Adds an "unsure" option to each list
		playerList.add("Unsure");
		weaponList.add("Unsure");
		roomList.add("Unsure");
	
		// Reads the card names out of the file, storing it in the appropriate list
		for (Card card : cards) {
			if (card.getCardType() == CardType.PERSON) 
				playerList.add(card.getName());
			else if (card.getCardType() == CardType.WEAPON)
				weaponList.add(card.getName());
			else if (card.getCardType() == CardType.ROOM)
				roomList.add(card.getName());
		}
		
		// Sets up the selection boxes for the people, weapons, and rooms
		JPanel people = new JPanel();
		people.setBorder( new TitledBorder(new EtchedBorder(), "People"));
		JComboBox PeopleCombo = new JComboBox(playerList.toArray());
		PeopleCombo.setBorder( new TitledBorder(new EtchedBorder(),"People Guess"));
		
		JPanel weapons = new JPanel();
		weapons.setBorder( new TitledBorder(new EtchedBorder(), "Weapons"));
		JComboBox WeaponsCombo=new JComboBox(weaponList.toArray());
		WeaponsCombo.setBorder( new TitledBorder(new EtchedBorder(),"Weapons Guess"));
		
		JPanel rooms=new JPanel();
		rooms.setBorder( new TitledBorder(new EtchedBorder(),"Rooms"));
		JComboBox RoomsCombo=new JComboBox(roomList.toArray());
		RoomsCombo.setBorder( new TitledBorder(new EtchedBorder(),"Rooms Guess"));

		// Adds the player information to the JFrame
		addPlayerCheckboxes(people);
		add(people);
		add(PeopleCombo);
		
		// Adds the weapon information to the JFrame
		addWeaponCheckboxes(weapons);
		add(weapons);
		add(WeaponsCombo);
		
		// Adds the room information to the JFrame
		addRoomCheckboxes(rooms);
		add(rooms);
		add(RoomsCombo);
	}
	
	// Adds the rooms from the arrayList to the JPanel with checkboxes
	private void addRoomCheckboxes(JPanel rooms) {
		rooms.setLayout(new GridLayout(0,2));
		
		for (String room : roomList) {
			if (room != "Unsure") {
				JCheckBox newRoom = new JCheckBox(room);
				rooms.add(newRoom);
			}
		}
	}
	
	// Adds the weapons from the arrayList to the JPanel with checkboxes
	private void addWeaponCheckboxes(JPanel weapons) {
		weapons.setLayout(new GridLayout(0,2));
		for (String weapon : weaponList) {
			if (weapon != "Unsure") {
				JCheckBox newWeapon = new JCheckBox(weapon);
				weapons.add(newWeapon);
			}
		}
	}
	
	// Adds the players from the arrayList to the JPanel with checkboxes
	private void addPlayerCheckboxes(JPanel people){
		people.setLayout(new GridLayout(0,2));
		for (String player : playerList) {
			if (player != "Unsure") {
				JCheckBox newPlayer = new JCheckBox(player);
				people.add(newPlayer);
			}
		}
	}
}
