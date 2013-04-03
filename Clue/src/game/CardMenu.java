package game;

import game.Card.CardType;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CardMenu extends JPanel {
	CardMenu(Player p){
		setBorder( new TitledBorder(new EtchedBorder(), "My Cards"));
		setLayout(new GridLayout(3,0));
		ArrayList<Card> peopleCards=new ArrayList<Card>();
		ArrayList<Card> weaponCards=new ArrayList<Card>();
		ArrayList<Card> roomCards=new ArrayList<Card>();
		for(Card c:p.getMyCards()){
			if (c.getCardType()==CardType.PERSON){
				peopleCards.add(c);
			}else if(c.getCardType()==CardType.WEAPON){
				weaponCards.add(c);
			}else{roomCards.add(c);}
		}
		String message="";
		JPanel people=new JPanel();
		people.setBorder( new TitledBorder(new EtchedBorder(), "People"));
		JTextField peopleList=new JTextField();
		peopleList.setEditable(false);
		for(Card c:peopleCards){
			message+="\n"+c.getName();
		}
		peopleList.setText(message);
		people.add(peopleList);
		add(people);
		
		
		message="";
		JPanel weapons=new JPanel();
		weapons.setBorder( new TitledBorder(new EtchedBorder(), "Weapons"));
		JTextField weaponsList=new JTextField();
		weaponsList.setEditable(false);
		for(Card c:weaponCards){

			message+="\n"+c.getName();
		}
		weaponsList.setText(message);
		weapons.add(weaponsList);
		add(weapons);
		
		//adds rooms
		message="";
		JPanel rooms=new JPanel();
		rooms.setBorder( new TitledBorder(new EtchedBorder(), "Rooms"));
		JTextField roomsList=new JTextField();
		roomsList.setEditable(false);
		for(Card c:roomCards){

			message+="\n"+c.getName();
		}
		roomsList.setText(message);
		rooms.add(roomsList);
		add(rooms);
	}
}
