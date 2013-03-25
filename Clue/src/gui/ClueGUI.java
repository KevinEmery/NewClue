package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class ClueGUI extends JFrame {
	//constructor
	public ClueGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new CluePanel(), BorderLayout.CENTER);
		setSize(new Dimension(900, 200));
		setTitle("Clue");
	}
	//overall panel that contains everything. We add this in the constructor. 
	//It uses a t-row 3-column grid layout.
	private class CluePanel extends JPanel {
		private JButton nextPlayer;
		private JButton makeAccusation;
		public CluePanel() {
			nextPlayer = new JButton("Next player");
			makeAccusation = new JButton("Make an accusation");
			setLayout(new GridLayout(2, 3));
			add(new TextInputFrame("Whose turn?"));
			add(nextPlayer);
			add(makeAccusation);
			add(new NumberDisplayPanel("Die", "Roll"));
			add(new NumberDisplayPanel("Guess", "Guess"));
			add(new NumberDisplayPanel("Guess Result", "Response"));
		}
	}
	//Text input frame - this is the class for the generic text-input box that has
	//a label above it and the text box below.
	private class TextInputFrame extends JPanel {
		private JTextField textField;	//for getting out the text later.
		public TextInputFrame(String textLabel) {
			setLayout(new GridLayout(0, 1));
			add(new JLabel(textLabel));
			textField = new JTextField(10);
			add(textField);
		}
	}
	//numberdisplayPanel is a class where you have a bordered grid with a label 
	//and below it a label and an output box that can't be written to. 
	private class NumberDisplayPanel extends JPanel {
		private JTextField number; //For changing it during runtime we made the number a member var.
		public NumberDisplayPanel(String panelLabel, String textLabel) {
			setBorder(new TitledBorder (new EtchedBorder(), panelLabel));
			setLayout(new GridLayout(0, 2));
			JLabel label = new JLabel(textLabel);
			number = new JTextField(2);
			number.setEditable(false);
			add(label);
			add(number);
		}
		
	}
	public static void main(String[] args) {
		ClueGUI gui = new ClueGUI();
		gui.setVisible(true);
	}
}
