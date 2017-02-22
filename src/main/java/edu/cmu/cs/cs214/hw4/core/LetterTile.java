package edu.cmu.cs.cs214.hw4.core;
/**
 * Class to represent letter tiles in Scrabble.
 * @author Kathleen
 *
 */
public class LetterTile {
    private String letter;
    private int worth;
    /**
     * Constructor to instantiate LetterTile object.
     * @param pLetter Letter on tile.
     * @param pWorth Worth of letter.
     */
    public LetterTile(String pLetter, int pWorth){
        letter = pLetter;
        worth = pWorth;
    }
    /**
     * Method get letter on tile.
     * @return String representing letter on tile.
     */
    public String getLetter(){
        return letter;
    }
    /**
     * Method to get worth of letter.
     * @return Worth of letter.
     */
    public int getWorth(){
        return worth;
    }
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof LetterTile)) return false;
        LetterTile tile = (LetterTile) obj;
        return letter.equals(tile.getLetter()) && worth == tile.getWorth();
    }
    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result;
        if(letter != null) result += letter.hashCode();
        result += worth;
        return result;
    }
}
