package edu.cmu.cs.cs214.hw4.core;

/**
 * Class to represent a letter tile and it's intended location in a move.
 * @author Kathleen
 *
 */
public class TilePlacement {
    private LetterTile tile;
    private Location loc;
    
    /**
     * Constructor to create TilePlacement object with initialized
     * letter tile and location fields.
     * @param pTile Tile to be placed.
     * @param pLoc Location of placement.
     */
    public TilePlacement(LetterTile pTile, Location pLoc){
        tile = pTile;
        loc = pLoc;
    }
    /**
     * Method to get letter tile in placement.
     * @return Letter tile.
     */
    public LetterTile getLetterTile(){
        return tile;
    }
    /**
     * Method to get location of placement.
     * @return Location.
     */
    public Location getLocation(){
        return loc;
    }
}
