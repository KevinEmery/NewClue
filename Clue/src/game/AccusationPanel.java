package game;

import game.Card;
import game.Card.CardType;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class AccusationPanel extends JFrame{
	private ArrayList<String> playerList;
	private ArrayList<String> weaponList;
	private ArrayList<String> roomList;
	private String personChoice="Mrs. Scarlet";
	private String weaponChoice="Rope";
	private String roomChoice="Conservatory";
	JComboBox PersonCombo;
	JComboBox WeaponCombo;
	JComboBox RoomCombo;
	AccusationPanel(ArrayList<Card> cards){
		setSize(600, 350);
		// Initialize the lists
		playerList = new ArrayList<String>();
		weaponList = new ArrayList<String>();
		roomList = new ArrayList<String>();
		//sets up layout
		this.setLayout(new GridLayout(0,2));
		JButton submitbutton=new JButton("Submit");
		JButton cancelbutton=new JButton("Cancel");
		submitbutton.addActionListener(new submitListener());
		cancelbutton.addActionListener(new cancelListener());
		for (Card card : cards) {
			if (card.getCardType() == CardType.PERSON) 
				playerList.add(card.getName());
			else if (card.getCardType() == CardType.WEAPON)
				weaponList.add(card.getName());
			else if (card.getCardType() == CardType.ROOM)
				roomList.add(card.getName());
		}
		ComboListener listener=new ComboListener();
		JPanel person=new JPanel();
		person.setBorder( new TitledBorder(new EtchedBorder(), "Person"));
		PersonCombo = new JComboBox(playerList.toArray());
		PersonCombo.addActionListener(listener);
		PersonCombo.setBorder( new TitledBorder(new EtchedBorder(),"Person Guess"));

		JPanel weapon=new JPanel();
		weapon.setBorder( new TitledBorder(new EtchedBorder(), "Weapon"));
		WeaponCombo = new JComboBox(weaponList.toArray());
		WeaponCombo.addActionListener(listener);
		WeaponCombo.setBorder( new TitledBorder(new EtchedBorder(),"Weapon Guess"));

		JPanel room=new JPanel();
		room.setBorder( new TitledBorder(new EtchedBorder(), "Room"));
		RoomCombo = new JComboBox(roomList.toArray());
		RoomCombo.addActionListener(listener);
		RoomCombo.setBorder( new TitledBorder(new EtchedBorder(),"Room Guess"));

		add(person);
		add(PersonCombo);
		add(weapon);
		add(WeaponCombo);
		add(room);
		add(RoomCombo);
		add(submitbutton);
		add(cancelbutton);
	}
	private class ComboListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getSource()==PersonCombo){
				personChoice= PersonCombo.getSelectedItem().toString();
			}else if(arg0.getSource()==WeaponCombo){
				weaponChoice=WeaponCombo.getSelectedItem().toString();
			}else if(arg0.getSource()==RoomCombo){
				roomChoice=RoomCombo.getSelectedItem().toString();
			}
			
		}

	}
	private class submitListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			ClueGame.checkAccusationHandler(new Solution(personChoice, weaponChoice, roomChoice));
		}
	}
	private class cancelListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			setVisible(false);
		}
	}
}