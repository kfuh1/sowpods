package edu.cmu.cs.cs214.hw4.core;
/**
 * Class to implement Negative special tile.
 * @author Kathleen
 *
 */
public class NegativeTile extends AbstractSpecialTile {
    private static final int COST = 6;
    /**
     * Constructor to instantiate NegativeTile object.
     */
    public NegativeTile(){
        super(COST);
    }
    @Override
    public void performAction(ScrabbleWithStuff g, Location loc){
        if(super.getIsActive()){
            g.changeMultFactor();
        }
        super.setIsActive(false);
    }
    @Override
    public String getName(){
        return "NEG";
    }
    @Override
    public boolean equals(Object obj){
        Player owner = super.getOwner();
        if(!(obj instanceof NegativeTile)) return false;
        NegativeTile tile = (NegativeTile) obj;
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
