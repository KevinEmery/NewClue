package game;

import game.Card.CardType;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SuggestionPane extends JDialog {
	private ArrayList<String> playerList;
	private ArrayList<String> weaponList;
	private String personChoice;
	private String weaponChoice;
	private final String roomChoice;
	JComboBox PersonCombo;
	JComboBox WeaponCombo;
	ClueGame game;
	
	SuggestionPane(JFrame jf, Boolean b, ClueGame game){
		super(jf, b);
		this.game = game;

		String roomName = game.getBoard().getRooms().get(((RoomCell)game.getPlayers().get(0).currentCell).getRoom());
		
		setSize(300, 250);
		// Initialize the lists
		playerList = new ArrayList<String>();
		weaponList = new ArrayList<String>();
		//sets up layout
		this.setLayout(new GridLayout(0,2));
		JButton submitbutton=new JButton("Submit");
		JButton cancelbutton=new JButton("Cancel");
		submitbutton.addActionListener(new submitListener());
		cancelbutton.addActionListener(new cancelListener());
		for (Card card : game.getOriginalDeck()) {
			if (card.getCardType() == CardType.PERSON) 
				playerList.add(card.getName());
			else if (card.getCardType() == CardType.WEAPON)
				weaponList.add(card.getName());
		}
		ComboListener listener=new ComboListener();
		
		JLabel person=new JLabel();
		person.setText("Person");
		PersonCombo = new JComboBox(playerList.toArray());
		PersonCombo.addActionListener(listener);

		JLabel weapon=new JLabel();
		weapon.setText("Weapon");
		WeaponCombo = new JComboBox(weaponList.toArray());
		WeaponCombo.addActionListener(listener);


		JLabel room=new JLabel();
		room.setText("Room");
		JLabel roomLabel = new JLabel();
		roomLabel.setText(roomName);

		add(person);
		add(PersonCombo);
		add(weapon);
		add(WeaponCombo);
		add(room);
		add(roomLabel);
		add(submitbutton);
		add(cancelbutton);
		personChoice = playerList.get(0);
		weaponChoice = weaponList.get(0);
		roomChoice = roomName;
	}

	private class ComboListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getSource()==PersonCombo){
				personChoice= PersonCombo.getSelectedItem().toString();
			}else if(arg0.getSource()==WeaponCombo){
				weaponChoice=WeaponCombo.getSelectedItem().toString();
			}			
		}
	}
	private class submitListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0 ) {
			game.handleSuggestion(personChoice, roomChoice, weaponChoice, game.getPlayers().get(0), game.getPlayers().get(0).currentCell);
			setVisible(false);
		}
	}
	private class cancelListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			setVisible(false);
		}
	}
}
