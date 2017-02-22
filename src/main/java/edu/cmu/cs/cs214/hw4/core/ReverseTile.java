package edu.cmu.cs.cs214.hw4.core;
/**
 * Class to implement Reverse special tile.
 * @author Kathleen
 *
 */
public class ReverseTile extends AbstractSpecialTile{
    private static final int COST = 5;
    /**
     * Constructor to instantiate reverse tile object.
     */
    public ReverseTile(){
        super(COST);
    }
    @Override
    public void performAction(ScrabbleWithStuff g, Location loc){
        if(super.getIsActive()){
            g.reverseOrder();
        }
        super.setIsActive(false);
    }  
    @Override
    public String getName(){
        return "REV";
    }
    @Override
    public boolean equals(Object obj){
        Player owner = super.getOwner();
        if(!(obj instanceof ReverseTile)) return false;
        ReverseTile tile = (ReverseTile) obj;
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
