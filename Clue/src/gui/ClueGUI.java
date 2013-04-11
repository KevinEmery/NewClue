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
	
	private ClueGame game;
	public CluePanel cluePanel;
	
	//constructor
	public ClueGUI(ClueGame game) {
		
		this.game = game;
		
		cluePanel = new CluePanel();
		add(cluePanel, BorderLayout.SOUTH);
		
	}
	//overall panel that contains everything. We add this in the constructor. 
	//It uses a t-row 3-column grid layout.
	public class CluePanel extends JPanel {
		private JButton nextPlayer;
		private JButton makeAccusation;
		public GuessPanel guessPanel;
		public ResultPanel resultPanel;
		private NumberDisplayPanel diePanel;
		
		public CluePanel() {
			nextPlayer = new JButton("Next player");
			makeAccusation = new JButton("Make an accusation");
			nextPlayer.addActionListener(new nextPlayerListener());
			makeAccusation.addActionListener(new AccusationListener());
			setPreferredSize(new Dimension(900, 200));
			JPanel topRow = new JPanel();
			topRow.setLayout(new GridLayout(0, 3));
			topRow.add(new TextInputFrame("Whose turn?"));
			topRow.add(nextPlayer);
			topRow.add(makeAccusation);
			topRow.setPreferredSize(new Dimension(800, 50));
			
			diePanel = new NumberDisplayPanel("Die", "Roll");
			diePanel.setPreferredSize(new Dimension(100, 100));
			guessPanel = new GuessPanel("Guess", "Guess");
			guessPanel.setPreferredSize(new Dimension(500, 100));
			resultPanel = new ResultPanel("Guess Result", "Response");
			resultPanel.setPreferredSize(new Dimension(200, 100));
			JPanel bottomRow = new JPanel();
			bottomRow.add(diePanel);
			bottomRow.add(guessPanel);
			bottomRow.add(resultPanel);
			


			
			add(topRow, BorderLayout.NORTH);
			add(bottomRow, BorderLayout.SOUTH);
			
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
	public class GuessPanel extends JPanel {
		private JTextField guess;
		public GuessPanel(String panelLabel, String textLabel) {
			setBorder(new TitledBorder (new EtchedBorder(), panelLabel));
			setLayout(new GridLayout(2, 0));
			JLabel label = new JLabel(textLabel);
			label.setPreferredSize(new Dimension(100, 50));
			guess = new JTextField(2);
			guess.setSize(new Dimension(400, 50));
			guess.setEditable(false);
			add(label);
			add(guess);
		}
		public void updateGuess(String guessed){
			guess.setText(guessed);
		}
	}
	
	public static class ResultPanel extends JPanel {
		private JTextField result;
		public ResultPanel(String panelLabel, String textLabel) {
			setBorder(new TitledBorder (new EtchedBorder(), panelLabel));
			setLayout(new GridLayout(2, 0));
			JLabel label = new JLabel(textLabel);
			result = new JTextField(2);
			result.setEditable(false);
			add(label);
			add(result);
		}
		
		public void updateResult(String results){
			result.setText(results);
		}
	}
	//numberdisplayPanel is a class where you have a bordered grid with a label 
	//and below it a label and an output box that can't be written to. 
	public static class NumberDisplayPanel extends JPanel {
		private static JTextField number; //For changing it during runtime we made the number a member var.
		public NumberDisplayPanel(String panelLabel, String textLabel) {
			setBorder(new TitledBorder (new EtchedBorder(), panelLabel));
			setLayout(new GridLayout(2, 0));
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
			game.nextPlayer();
		}
	}

	private class AccusationListener implements ActionListener	{
		public void actionPerformed(ActionEvent e){
			game.makeAccusation();
		}
	}
	
}
