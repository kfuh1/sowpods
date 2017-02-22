package edu.cmu.cs.cs214.hw4.core;

import java.awt.Color;

/**
 * Abstract class to implement general version of Scrabble board square.
 * @author Kathleen
 *
 */
public abstract class AbstractSquare implements Square {
    private Location loc;
    private LetterTile letterTile;
    private SpecialTile specialTile;
    /**
     * Constructor to initialize AbstractSquare object.
     * @param l Location of square on board.
     */
    public AbstractSquare(Location l){
        loc = l;
        letterTile = null;
        specialTile = null;
    }
    @Override
    public Location getLocation(){
        return loc;
    }
    @Override
    public LetterTile getLetterTile(){
        return letterTile;
    }
    @Override
    public SpecialTile getSpecialTile(){
        return specialTile;
    }
    @Override
    public void placeLetterTile(LetterTile lt){
        letterTile = lt;
    }
    @Override
    public void placeSpecialTile(SpecialTile st){
        specialTile = st;
    }
    @Override
    public void removeLetterTile(){
        letterTile = null;
    }
    @Override
    public void removeSpecialTile(){
        specialTile = null;
    }
    @Override
    public abstract int computeScore(int score, int multFactor);
    @Override
    public abstract int getMultFactor();
    @Override
    public abstract Color getColor();
    @Override
    public abstract String getText();
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Square)) return false;
        Square s = (Square) obj;
        return (loc.equals(s.getLocation()) 
                && ((letterTile == null && s.getLetterTile() == null)  
                || (letterTile.equals(s.getLetterTile()))
                && ((specialTile == null && s.getSpecialTile() == null)
                || specialTile.equals(s.getSpecialTile()))));
    }
    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result *= prime;
        
        if(loc == null) result += 0;
        else result += (loc.hashCode());
        
        if(letterTile == null) result += 0;
        else result += (letterTile.hashCode());
        
        if(specialTile == null) result += 0;
        else result += (specialTile.hashCode());
        
        return result;
    }
}
