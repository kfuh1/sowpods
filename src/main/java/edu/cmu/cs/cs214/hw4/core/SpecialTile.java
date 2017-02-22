package edu.cmu.cs.cs214.hw4.core;
/**
 * Interface to represent special tile in Scrabble with stuff.
 * @author Kathleen
 *
 */
public interface SpecialTile {
    /**
     * Method to set owner of special tile after purchase.
     * @param pOwner Player that purchased tile.
     */
    void setOwner(Player pOwner);
    /**
     * Method to get player that owns special tile.
     * @return Special tile owner.
     */
    Player getOwner();
    /**
     * Method to get cost to purchas special tile.
     * @return Number of points required to buy special tile.
     */
    int getCost();
    /**
     * Method to get whether or not a special tile is still active.
     * @return True if special tile is active, false otherwise.
     */
    boolean getIsActive();
    /**
     * Method to make special tile do its special action after activation.
     * @param g Game object this tile is used in.
     * @param loc Location of special tile on board.
     */
    void performAction(ScrabbleWithStuff g, Location loc);
    /**
     * Method to get name of special tile in the format to be displayed by GUI.
     * @return String of name.
     */
    String getName();
    @Override
    boolean equals(Object obj);
    @Override
    int hashCode();
}
