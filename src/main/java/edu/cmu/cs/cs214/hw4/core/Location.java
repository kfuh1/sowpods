package edu.cmu.cs.cs214.hw4.core;
/**
 * Class to represent location coordinates on Scrabble board.
 * @author Kathleen
 *
 */
public class Location {
    private int row;
    private int col;
    /**
     * Constructor to instantiate Location object.
     * @param pRow Board row.
     * @param pCol Board column.
     */
    public Location(int pRow, int pCol){
        row = pRow;
        col = pCol;
    }
    /**
     * Method to get row number.
     * @return Board row.
     */
    public int getRow(){
        return row;
    }
    /**
     * Method to get column number.
     * @return Column number.
     */
    public int getCol(){
        return col;
    }
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Location)) return false;
        Location l = (Location) obj;
        return (row == l.row && col == l.col);
    }
    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result;
        result += (row + col);
        return result;
    }
}
