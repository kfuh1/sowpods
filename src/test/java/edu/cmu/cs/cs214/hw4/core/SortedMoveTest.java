package edu.cmu.cs.cs214.hw4.core;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.LinkedList;
/**
 * Class to test the SortedMove object.
 * @author Kathleen
 *
 */
public class SortedMoveTest {
    private List<TilePlacement> unordered1, unordered2, ordered;
    private List<Location> orderedLocs1, orderedLocs2;
    private SortedMove sm;
    private Location loc1, loc2, loc3, loc4, loc5;
    private LetterTile lt1, lt2, lt3, lt4, lt5;
    /**
     * Method to instantiate objects needed to test sortedMove object.
     */
    @Before
    public void setUp(){
       unordered1 = new LinkedList<TilePlacement>();
       unordered2 = new LinkedList<TilePlacement>();
       orderedLocs1 = new LinkedList<Location>();
       orderedLocs2 = new LinkedList<Location>();
       loc1 = new Location(4,4);
       loc2 = new Location(5,4);
       loc3 = new Location(6,4);
       loc4 = new Location(4,5);
       loc5 = new Location(4,6);
       
       lt1 = new LetterTile("a", 1);
       lt2 = new LetterTile("b", 1);
       lt3 = new LetterTile("c", 1);
       lt4 = new LetterTile("d", 1);
       lt5 = new LetterTile("e", 1);
       
       unordered1.add(new TilePlacement(lt4, loc4));
       unordered1.add(new TilePlacement(lt5, loc5));
       unordered1.add(new TilePlacement(lt1, loc1));
       
       unordered2.add(new TilePlacement(lt1, loc1));
       unordered2.add(new TilePlacement(lt3, loc3));
       unordered2.add(new TilePlacement(lt2, loc2));
       
       orderedLocs1.add(loc1);
       orderedLocs1.add(loc4);
       orderedLocs1.add(loc5);
       
       orderedLocs2.add(loc1);
       orderedLocs2.add(loc2);
       orderedLocs2.add(loc3);
    }
    /**
     * Method to test creating sortedMove object.
     */
    @Test
    public void testSortedMove(){
        int listIndex = 0;
        sm = new SortedMove(unordered1, true);
        ordered = sm.getOrderedPlacements();
        for(Location l : orderedLocs1){
            assertTrue(l.equals(ordered.get(listIndex).getLocation()));
            listIndex++;
        }
        listIndex = 0;
        sm = new SortedMove(unordered2, false);
        ordered = sm.getOrderedPlacements();
        for(Location l : orderedLocs2){
            assertTrue(l.equals(ordered.get(listIndex).getLocation()));
            listIndex++;
        }
    }
    /**
     * Method to test getting start location of sorted move object.
     */
    @Test
    public void testGetStartLoc(){
        Location start = new Location(4,4);
        sm = new SortedMove(unordered1, true);
        assertTrue(start.equals(sm.getStartLoc()));
        sm = new SortedMove(unordered2, false);
        assertTrue(start.equals(sm.getStartLoc()));
    }
    /**
     * Method to test getting end location of sorted move object.
     */
    @Test
    public void testGetEndLoc(){
        Location end = new Location(4,6);
        sm = new SortedMove(unordered1, true);
        assertTrue(end.equals(sm.getEndLoc()));
        end = new Location(6,4);
        sm = new SortedMove(unordered2, false);
        assertTrue(end.equals(sm.getEndLoc()));
    }
}
