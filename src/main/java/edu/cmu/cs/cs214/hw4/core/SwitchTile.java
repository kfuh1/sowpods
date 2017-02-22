package edu.cmu.cs.cs214.hw4.core;
/**
 * Class to implement Switch special tile.
 * @author Kathleen
 *
 */
public class SwitchTile extends AbstractSpecialTile{
    private static final int COST = 6;
    /**
     * Create new Switch tile for Scrabble with stuff.
     */
    public SwitchTile(){
        super(COST);
    }
    @Override
    public void performAction(ScrabbleWithStuff g, Location loc){
        if(super.getIsActive()){
            g.switchScores(getOwner());
        }
        super.setIsActive(false);
    }
    @Override
    public String getName(){
        return "SWI";
    }
    @Override
    public boolean equals(Object obj){
        Player owner = super.getOwner();
        if(!(obj instanceof SwitchTile)) return false;
        SwitchTile tile = (SwitchTile) obj;
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
