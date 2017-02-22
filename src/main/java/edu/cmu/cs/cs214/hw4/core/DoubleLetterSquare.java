package edu.cmu.cs.cs214.hw4.core;

import java.awt.Color;

/**
 * Class to represent double letter square.
 * @author Kathleen
 *
 */
public class DoubleLetterSquare extends AbstractSquare {
    private static final int MULT_FACTOR = 1;
    private static final int ADJUSTMENT = 2;
    private boolean isActive;
    /**
     * Constructor to instantiate double letter square object.
     * @param l Location where double letter square will be.
     */
    public DoubleLetterSquare(Location l){
        super(l);
        isActive = true;
    }
    @Override
    public int computeScore(int score, int multFactor){
        LetterTile letterTile = super.getLetterTile();
        int worth = 0;
        if(letterTile != null){
            worth = letterTile.getWorth();   
        } 
        if(isActive){
            worth *= ADJUSTMENT;
            isActive = false; 
        }
        worth *= multFactor;
        return score + worth;
    }
    @Override
    public int getMultFactor(){
        return MULT_FACTOR;
    }
    @Override
    public Color getColor(){
        return Color.cyan;
    }
    @Override
    public String getText(){
        return "DL";
    }
}
