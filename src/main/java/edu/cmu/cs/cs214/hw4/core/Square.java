package edu.cmu.cs.cs214.hw4.core;

import java.awt.Color;

/**
 * Interface for a square on the Scrabble board.
 * @author Kathleen
 *
 */
public interface Square {
    /**
     * Method to get location of Square on board.
     * @return Location representing x and y coordinate of square on board.
     */
    Location getLocation();
    /**
     * Method to get letter tile that is placed on square.
     * @return LetterTile object if there is one, null otherwise.
     */
    LetterTile getLetterTile();
    /**
     * Method to get special tile that is placed on square.
     * @return SpecialTile object if there is one, null otherwise.
     */
    SpecialTile getSpecialTile();
    /**
     * Method to place a letter tile on a square. 
     * @param tile LetterTile to be set on square.
     */
    void placeLetterTile(LetterTile tile);
    /**
     * Method to place a special tile on a square.
     * @param tile SpecialTile to be set on square.
     */
    void placeSpecialTile(SpecialTile tile);
    /**
     * Method to remove letter tile from square. Will probably be used
     * when there is an invalid move that needs to be undone.
     */
    void removeLetterTile();
    /**
     * Method to remove special tile from square. Will probably be used
     * when there is an invalid move that needs to be undone.
     */
    void removeSpecialTile();
    /**
     * Method to compute score based on square type.
     * @param score Current total score calculated for this play just made.
     * @param multFactor Current score adjustment factor.
     * @return New score with adjustment and new tile worth. 
     */
    int computeScore(int score, int multFactor);
    /**
     * Method to get the factor of multiplication from square.
     * @return Standard, TripleLetter, DoubleLetter squares return 1,
     * DoubleWordSquare returns 2, and TripleWordSquare returns 3.
     */
    int getMultFactor();
    /**
     * Method to get the text associated with the type of square.
     * @return a String, DW for double word, TW for triple word, 
     * DL for double letter and TL for triple letter.
     */
    String getText();
    /**
     * Method to get the color that the square should be when displayed
     * by the GUI. This should only be called by the GUI.
     * @return cyan for double letter, dark blue for triple letter,
     * pink for double word, and red for triple word.
     */
    Color getColor();
    @Override 
    boolean equals(Object obj);
    @Override
    int hashCode();   
}
