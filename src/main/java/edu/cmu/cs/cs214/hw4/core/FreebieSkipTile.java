package edu.cmu.cs.cs214.hw4.core;
/**
 * Class to implement FreebieSkip special tile.
 * @author Kathleen
 *
 */
public class FreebieSkipTile extends AbstractSpecialTile{
    private static final int COST = 8;
    /**
     * Constructor to instantiate FreebieSkip tile object.
     */
    public FreebieSkipTile(){
        super(COST);
    }
    @Override
    public void performAction(ScrabbleWithStuff g, Location loc){
        if(super.getIsActive()){
            g.giveFreebie();
            g.skipPlayer(); 
        }
        super.setIsActive(false);
          
    }
    @Override
    public String getName(){
        return "F&S";
    }
    @Override
    public boolean equals(Object obj){
        Player owner = super.getOwner();
        if(!(obj instanceof FreebieSkipTile)) return false;
        FreebieSkipTile tile = (FreebieSkipTile) obj;
        return ((owner == null && tile.getOwner() == null) ||
                owner.equals(tile.getOwner()));
    }
    @Override
    public int hashCode(){
        Player owner = super.getOwner();
        final int prime = 31;
        int result = 1;
        result *= prime;
        if(owner == null) result += 0;
        else result += owner.hashCode();
        return result;
    }
}
