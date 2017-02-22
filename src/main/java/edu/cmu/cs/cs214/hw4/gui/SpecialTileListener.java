package edu.cmu.cs.cs214.hw4.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import edu.cmu.cs.cs214.hw4.core.SpecialTile;
/**
 * Class to handle special tile events.
 * @author Kathleen
 *
 */
public class SpecialTileListener implements ActionListener{
    private ScrabblePanel panel;
    private SpecialTile tile;
    private JButton button;
    /**
     * Create new special tile listener to get click events on a special tile.
     * @param pPanel Display panel of game that will interact with core
     * implementation once special tile placement is submitted.
     * @param st Special tile to be played.
     * @param b Button representing special tile that was clicked.
     */
    public SpecialTileListener(ScrabblePanel pPanel, SpecialTile st,JButton b){
        panel = pPanel;
        tile = st;
        button = b;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        button.setBackground(Color.yellow);
        panel.addSpecialTileForMove(tile);
    }
}
