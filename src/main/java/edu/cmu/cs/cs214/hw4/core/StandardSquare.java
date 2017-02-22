package edu.cmu.cs.cs214.hw4.core;

import java.awt.Color;

/**
 * Class representing standard square without special effects on ScrabbleBoard.
 * @author Kathleen
 *
 */
public class StandardSquare extends AbstractSquare {
    private static final int MULT_FACTOR = 1;
    /**
     * Constructor to instantiate standard square object.
     * @param l Location where standard square will be on board.
     */
    public StandardSquare(Location l){
        super(l);
    }
    @Override
    public int computeScore(int score, int multFactor){
        LetterTile letterTile = super.getLetterTile();
        int worth = 0;
        if(letterTile != null){
            worth = letterTile.getWorth();
        } 
        //score *= multFactor;
        return score + (worth * multFactor);   
    }
    @Override
    public int getMultFactor(){
        return MULT_FACTOR;
    }
    @Override
    public Color getColor(){
        return Color.darkGray;
    }
    @Override
    public String getText(){
        return "";
    }
}
