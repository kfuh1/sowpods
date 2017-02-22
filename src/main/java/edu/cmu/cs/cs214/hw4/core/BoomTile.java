package edu.cmu.cs.cs214.hw4.core;
/**
 * Class to implement special tile with Boom effect.
 * @author Kathleen
 *
 */
public class BoomTile extends AbstractSpecialTile{
    private static final int COST = 6;
    /**
     * Constructor to create BoomTile object.
     */
    public BoomTile(){
        super(COST); 
    }
    @Override
    public void performAction(ScrabbleWithStuff g, Location loc){
        if(super.getIsActive()){
            g.boomEffect(loc);
        }
        super.setIsActive(false);
    }
    @Override
    public String getName(){
        return "BM";
    }
    @Override
    public boolean equals(Object obj){
        Player owner = super.getOwner();
        if(!(obj instanceof BoomTile)) return false;
        BoomTile tile = (BoomTile) obj;
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
