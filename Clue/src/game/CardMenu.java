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
		JPanel people=new JPanel();
		people.setBorder( new TitledBorder(new EtchedBorder(), "People"));
		JPanel weapons=new JPanel();
		weapons.setBorder( new TitledBorder(new EtchedBorder(), "Weapons"));
		JPanel rooms=new JPanel();
		rooms.setBorder( new TitledBorder(new EtchedBorder(), "Rooms"));
		
		for(Card c:p.getMyCards()){
			if (c.getCardType()==CardType.PERSON){
				people.add(new JTextField(c.getName()));
			}else if(c.getCardType()==CardType.WEAPON){
				weapons.add(new JTextField(c.getName()));
			}else{rooms.add(new JTextField(c.getName()));}
			add(people);
			add(weapons);
			add(rooms);
		}
	}
		
}
