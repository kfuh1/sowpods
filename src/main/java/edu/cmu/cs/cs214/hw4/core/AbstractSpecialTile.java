package edu.cmu.cs.cs214.hw4.core;
/**
 * Abstract class representing special tiles.
 * @author Kathleen
 *
 */
public abstract class AbstractSpecialTile implements SpecialTile {
    private Player owner;
    private boolean isActive;
    private int cost;
    /**
     * Constructor to instantiate abstract special tile object.
     * @param pCost Integer representing cost of purchasing special tile.
     */
    public AbstractSpecialTile(int pCost){
        isActive = true;
        cost = pCost;
    }
    @Override
    public abstract String getName();
    @Override 
    public void setOwner(Player pOwner){
        owner = pOwner;
    }
    @Override
    public Player getOwner(){
        return owner;
    }
    @Override
    public abstract void performAction(ScrabbleWithStuff g, Location loc);
    @Override
    public int getCost(){
        return cost;
    }
    @Override
    public boolean getIsActive(){
        return isActive;
    }
    /**
     * Method to update whether or not special tile is active.
     * @param pIsActive True if tile is active, false if not.
     */
    protected void setIsActive(boolean pIsActive){
        isActive = pIsActive;
    }
    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();
    
}
