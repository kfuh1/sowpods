package edu.cmu.cs.cs214.hw4.core;

import java.util.List;
import java.util.LinkedList;
import java.util.Random;
/**
 * Class that represents a move sorted by location. It's used because
 * when a player place tiles on the board it's not guaranteed that he
 * will place the tiles in order of location but the checks on validity
 * require order. This will only be needed when there is more than one
 * tile being placed.
 * @author Kathleen
 *
 */
public class SortedMove {
    private List<Location> orderedLocs;
    private List<TilePlacement> placements;
    private List<TilePlacement> orderedPlacements;
    private Location startLoc, endLoc;
    private Random rand;
    
    /**
     * Constructor to create a sorted move object.
     * @param p The (possibly) unsorted moves which is a map from a
     * letter tile to the intended location.
     * @param isHorizontal True if the words are formed across one row,
     * false if formed down a one column.
     */
    public SortedMove(List<TilePlacement> p, boolean isHorizontal){
        orderedLocs = new LinkedList<Location>();
        orderedPlacements = new LinkedList<TilePlacement>();
        placements = p;
        rand = new Random();

        List<Location> locList = new LinkedList<Location>();
        for(TilePlacement tp : placements){
            locList.add(tp.getLocation());
        }
        if(isHorizontal){
            orderedLocs = sortByColumn(locList);
            makeOrderedList();
        }
        else{
            orderedLocs = sortByRow(locList);
            makeOrderedList();
        }
    }
    /**
     * Method to sort the locations by column number.
     * @param locs (Possibly) unsorted list of locations.
     * @return List of locations sorted in increasing order by column.
     */
    private List<Location> sortByColumn(List<Location> locs){
        List<Location> lowResult = new LinkedList<Location>();
        List<Location> highResult = new LinkedList<Location>();
        List<Location> low = new LinkedList<Location>();
        List<Location> high = new LinkedList<Location>();
        /* quicksort */
        if(locs.size() < 2) return locs;
        else{
            int pivot = rand.nextInt(locs.size());
            Location pivLoc = locs.get(pivot);
            int pivCol = pivLoc.getCol();
            locs.remove(pivot);
            for(Location l : locs){
                int col = l.getCol();
                if(col < pivCol){
                    low.add(l);
                }
                else{
                    high.add(l);
                }
            }
            lowResult = sortByColumn(low);
            highResult = sortByColumn(high);
            lowResult.add(pivLoc);
            lowResult.addAll(highResult);
            return lowResult;
        }
        
    }
    /**
     * Method to sort the locations by row number.
     * @param locs (Possibly) unsorted list of locations.
     * @return List of locations sorted in increasing order by row.
     */
    private List<Location> sortByRow(List<Location> locs){
        List<Location> lowResult = new LinkedList<Location>();
        List<Location> highResult = new LinkedList<Location>();
        List<Location> low = new LinkedList<Location>();
        List<Location> high = new LinkedList<Location>();
        /* quicksort */
        if(locs.size() < 2) return locs;
        else{
            int pivot = rand.nextInt(locs.size());
            Location pivLoc = locs.get(pivot);
            int pivRow = pivLoc.getRow();
            locs.remove(pivot);
            for(Location l : locs){
                int row = l.getRow();
                if(row < pivRow){
                    low.add(l);
                }
                else{
                    high.add(l);
                }
            }
            lowResult = sortByRow(low);
            highResult = sortByRow(high);
            lowResult.add(pivLoc);
            lowResult.addAll(highResult);
            return lowResult;
        }
    }
    /**
     * Method to create a map from letter tiles to locations that is
     * inserted in order of the ordered locations. Also sets the start
     * and end locations of the tile placements.
     */
    private void makeOrderedList(){
        int counter = 0;
        for(Location loc : orderedLocs){
            for(TilePlacement tp : placements){
                if(loc.equals(tp.getLocation())){
                    orderedPlacements.add(tp);
                    if(counter == 0){
                        startLoc = loc;
                    }
                    else if(counter == orderedLocs.size()-1){
                        endLoc = loc;
                    }
                }
            }
            counter++;
        }
    }
    /**
     * Method to get the start location of the placements.
     * @return Start location.
     */
    public Location getStartLoc(){
        return startLoc;
    }
    /**
     * Method to get the end location of the placements.
     * @return End location.
     */
    public Location getEndLoc(){
        return endLoc;
    }
    /**
     * Method to get the ordered map of letter tiles to locations.
     * @return Ordered map of letter tiles to locations.
     */
    public List<TilePlacement> getOrderedPlacements(){
        return orderedPlacements;
    }
}

