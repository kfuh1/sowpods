package edu.cmu.cs.cs214.hw4.core;

import java.awt.Color;

/**
 * Class to represent double word square object.
 * @author Kathleen
 *
 */
public class DoubleWordSquare extends AbstractSquare {
    private static final int MULT_FACTOR = 2;
    private boolean isActive;
    /**
     * Constructor to instantiate double word square object.
     * @param l Location where double word square will be.
     */
    public DoubleWordSquare(Location l){
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
            score *= MULT_FACTOR;
            isActive = false;
        }
        worth *= multFactor;
        return score + worth;
        
    }
    @Override
    public int getMultFactor(){
        if(isActive) return MULT_FACTOR;
        else return 1;
    }
    @Override
    public Color getColor(){
        return Color.pink;
    }
    @Override
    public String getText(){
        return "DW";
    }
}
