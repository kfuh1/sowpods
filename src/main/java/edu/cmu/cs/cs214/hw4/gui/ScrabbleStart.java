package edu.cmu.cs.cs214.hw4.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import edu.cmu.cs.cs214.hw4.core.ScrabbleWithStuff;

import java.util.List;
import java.util.LinkedList;
/**
 * Class to implement panel for initial start screen prompting for player 
 * names.
 * The idea to have this class came from Recitation 8's chat GUI.
 * @author Kathleen
 *
 */
public class ScrabbleStart extends JPanel{
    /* generated serial version id */
    private static final long serialVersionUID = -3038676968678948939L;
    private static final String FILENAME = "src/main/resources/words.txt";
    private static final int MAX_PLAYERS = 4;
    
    private final List<String> playerNames;
    private final JFrame parentFrame;
    /**
     * Create new start screen for Scrabble game
     * @param frame Frame to be used as parent frame.
     */
    public ScrabbleStart(JFrame frame){
        parentFrame = frame;
        playerNames = new LinkedList<String>();
        
        JLabel playerLabel = new JLabel("Player Name: ");
        final JTextField playerText = new JTextField(30);
        
        JButton playerButton = new JButton("Add Player");
        JPanel playerPanel = new JPanel();
        
        playerPanel.setLayout(new BorderLayout());
        playerPanel.add(playerLabel, BorderLayout.WEST);
        playerPanel.add(playerText, BorderLayout.CENTER);
        playerPanel.add(playerButton, BorderLayout.EAST);
        
        ActionListener newPlayerListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String name = playerText.getText();
                if(name.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Please enter a name");
                }
                else if(playerNames.contains(name)){
                    JOptionPane.showMessageDialog(null,"This name has already been used");
                }
                else if(playerNames.size() >= MAX_PLAYERS){
                    JOptionPane.showMessageDialog(null,"Only up to 4 people allowed in Scrabble");
                }
                else{
                    playerNames.add(name);
                }
                playerText.setText("");
                playerText.requestFocus();
            }
        };
        
        playerButton.addActionListener(newPlayerListener);
        playerText.addActionListener(newPlayerListener);
        
        JButton startGameButton = new JButton("Start Scrabble!");
        startGameButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(playerNames.size() < 2){
                    JOptionPane.showMessageDialog(null,"Not enough players");
                }
                else{
                    startScrabbleGame();
                }
            }
        });
        setLayout(new BorderLayout());
        add(playerPanel, BorderLayout.NORTH);
        add(startGameButton, BorderLayout.SOUTH);
        setVisible(true);
        
    }
    /**
     * Method to bring up scrabble start screen and start game.
     * @param args command line arguments.
     */
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                createAndShowScrabbleStart();
            }
        });
    }
    /**
     * Method to set up the start window.
     */
    private static void createAndShowScrabbleStart(){
        JFrame frame = new JFrame("Start a Scrabble Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.add(new ScrabbleStart(frame));
        
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
    }
    /**
     * Method to create new window once Start Scrabble is clicked.
     */
    private void startScrabbleGame(){
        final ScrabbleWithStuff scrabble = 
                new ScrabbleWithStuff(playerNames, FILENAME);
        
        parentFrame.remove(this);
        
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                JFrame frame = new JFrame("Scrabble With Stuff");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(new ScrabblePanel(scrabble));
                frame.pack();
                frame.setResizable(true);
                frame.setVisible(true);
            }
        });
    }
}
