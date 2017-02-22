package edu.cmu.cs.cs214.hw4.core;

import java.awt.Color;

/**
 * Class to represent triple word square.
 * @author Kathleen
 *
 */
public class TripleWordSquare extends AbstractSquare{
    private static final int MULT_FACTOR = 3;
    private boolean isActive;
    /**
     * Constructor to instantiate triple word square object.
     * @param l Location of triple word square on board.
     */
    public TripleWordSquare(Location l){
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
        return Color.red;
    }
    @Override
    public String getText(){
        return "TW";
    }
}
