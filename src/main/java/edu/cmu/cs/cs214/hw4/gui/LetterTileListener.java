package edu.cmu.cs.cs214.hw4.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import edu.cmu.cs.cs214.hw4.core.LetterTile;
/**
 * Class to handle letter tile click events.
 * @author Kathleen
 *
 */
public class LetterTileListener implements ActionListener{
    private ScrabblePanel panel;
    private LetterTile tile;
    private JButton button;
    /**
     * Create new letter tile listener to get click events on letter tiles.
     * @param pPanel Display panel for the game.
     * @param lt Letter tile to be played.
     * @param b Button representing letter tile in GUI.
     */
    public LetterTileListener(ScrabblePanel pPanel, LetterTile lt, JButton b){
        panel = pPanel;
        tile = lt;
        button = b;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        button.setBackground(Color.yellow);
        panel.addTileForMove(tile);
    }
}
