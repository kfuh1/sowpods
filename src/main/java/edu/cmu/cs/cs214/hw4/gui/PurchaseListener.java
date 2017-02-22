package edu.cmu.cs.cs214.hw4.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.cmu.cs.cs214.hw4.core.ScrabbleGame;
/**
 * Class to deal with purchasing special tile event.
 * @author Kathleen
 *
 */
public class PurchaseListener implements ActionListener {
    private ScrabbleGame game;
    /**
     * Create new purchase special tile listener.
     * @param pGame Current scrabble game that is being played.
     */
    public PurchaseListener(ScrabbleGame pGame){
        game = pGame;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        /* Create popup dialog that asks player for special tile choice */
        JPanel stChoicePanel = new JPanel();
        String stChoice = "";
        stChoicePanel.add(new JLabel("Choose special tile type: "));
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
        model.addElement("Negative");
        model.addElement("Boom");
        model.addElement("FreebieSkip");
        model.addElement("Reverse");
        model.addElement("Switch");
        JComboBox<String> comboBox = new JComboBox<String>(model);
        stChoicePanel.add(comboBox);
        int result = JOptionPane.showConfirmDialog(null, stChoicePanel, "Special Tile Type:", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (result) {
        case JOptionPane.OK_OPTION:
            stChoice = (String) comboBox.getSelectedItem();
            boolean success = game.purchaseSpecialTile(stChoice);
            if(!success){
                JOptionPane.showMessageDialog(null,
                        "Either you don't have enough points to purchase,"
                        + " or there are no more of those tiles left.");
            }
            break;
        default:
            break;
        }
        
    }
}
