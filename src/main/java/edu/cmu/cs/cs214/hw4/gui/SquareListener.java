package edu.cmu.cs.cs214.hw4.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.cmu.cs.cs214.hw4.core.Location;
/**
 * Class to handle square events.
 * @author Kathleen
 *
 */
public class SquareListener implements ActionListener{
    private final int row;
    private final int col;
    private ScrabblePanel panel;
    /**
     * Create new square listener to get click events at a certain location
     * on the scrabble board.
     * @param sp Panel that displays the game and that will communicate with
     * the core implementation.
     * @param pRow Row of square.
     * @param pCol Column of square.
     */
    public SquareListener(ScrabblePanel sp, int pRow, int pCol){
        row = pRow;
        col = pCol;
        panel = sp;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        panel.addLocationForMove(new Location(row, col));
    }
}
