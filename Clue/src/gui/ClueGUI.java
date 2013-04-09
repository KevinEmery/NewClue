package gui;
import game.ClueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class ClueGUI extends JPanel{
	
	//constructor
	public ClueGUI() {

		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new CluePanel(), BorderLayout.CENTER);
		setSize(1000, 200);
		//setTitle("Clue");
	}
	//overall panel that contains everything. We add this in the constructor. 
	//It uses a t-row 3-column grid layout.
	public class CluePanel extends JPanel {
		private JButton nextPlayer;
		private JButton makeAccusation;
		
		public CluePanel() {
			
			nextPlayer = new JButton("Next player");
			makeAccusation = new JButton("Make an accusation");
			nextPlayer.addActionListener(new nextPlayerListener());
			makeAccusation.addActionListener(new AccusationListener());
			setLayout(new GridLayout(2, 3));
			add(new TextInputFrame("Whose turn?"));
			add(nextPlayer);
			add(makeAccusation);
			add(new NumberDisplayPanel("Die", "Roll"));
	
			add(new GuessPanel("Guess", "Guess"));
			add(new ResultPanel("Guess Result", "Response"));
			
		}
	}
	//Text input frame - this is the class for the generic text-input box that has
	//a label above it and the text box below.
	public static class TextInputFrame extends JPanel {
		private static JTextField textField;	//for getting out the text later.
		public TextInputFrame(String textLabel) {
			setLayout(new GridLayout(0, 1));
			add(new JLabel(textLabel));
			textField = new JTextField(10);
			textField.setEditable(false);
			add(textField);
		}
		public static void updatePlayer(String name){
			textField.setText(name);
		}
	}
	public static class GuessPanel extends JPanel {
		private static JTextField guess;
		public GuessPanel(String panelLabel, String textLabel) {
			setBorder(new TitledBorder (new EtchedBorder(), panelLabel));
			setLayout(new GridLayout(0, 2));
			JLabel label = new JLabel(textLabel);
			guess = new JTextField(2);
			guess.setEditable(false);
			add(label);
			add(guess);
		}
		public static void updateGuess(String guessed){
			guess.setText(guessed);
		}
	}
	public static class ResultPanel extends JPanel {
		private static JTextField result;
		public ResultPanel(String panelLabel, String textLabel) {
			setBorder(new TitledBorder (new EtchedBorder(), panelLabel));
			setLayout(new GridLayout(0, 2));
			JLabel label = new JLabel(textLabel);
			result = new JTextField(2);
			result.setEditable(false);
			add(label);
			add(result);
		}
		public static void updateGuess(String results){
			result.setText(results);
		}
	}
	//numberdisplayPanel is a class where you have a bordered grid with a label 
	//and below it a label and an output box that can't be written to. 
	public static class NumberDisplayPanel extends JPanel {
		private static JTextField number; //For changing it during runtime we made the number a member var.
		public NumberDisplayPanel(String panelLabel, String textLabel) {
			setBorder(new TitledBorder (new EtchedBorder(), panelLabel));
			setLayout(new GridLayout(0, 2));
			JLabel label = new JLabel(textLabel);
			number = new JTextField(2);
			number.setEditable(false);
			add(label);
			add(number);
		}
		
		public static void updateRoll(int dieRoll){
			number.setText(Integer.toString(dieRoll));
		}

	}
	private class nextPlayerListener implements ActionListener	{
		public void actionPerformed(ActionEvent e){
			ClueGame.nextPlayer();
		}
	}

	private class AccusationListener implements ActionListener	{

		public void actionPerformed(ActionEvent e){
			//to finish later
			System.out.println("Accuation");
		}
	}

	public static void main(String[] args) {
		ClueGUI gui = new ClueGUI();
		gui.setVisible(true);
	}
}
