package edu.cmu.cs.cs214.hw4.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.LinkedList;

/**
 * Class to test StandardBoard implementation.
 * @author Kathleen
 *
 */
public class StandardBoardTest {
    private static final String FILENAME = 
            "src/main/resources/testDictionary.txt";
    private Dictionary d;
    private StandardBoard sb;

    private LetterTile lt1, lt2, lt3, lt4, lt5, lt6, lt7, lt8, lt9, lt10, lt11;
    private Location loc1, loc2, loc3, loc4, loc5, loc6, loc7, loc8, loc9,
    loc10, loc11, loc12, loc13;
    private List<TilePlacement> placements1, placements2, 
                placements3, placements4, placements5, placements6, 
                placements7;
    /**
     * Method to set up objects needed to test StandardBoard.
     */
    @Before
    public void setUp(){
        d = new Dictionary(FILENAME);
        sb = new StandardBoard(d);
        lt1 = new LetterTile("M", 3);
        lt2 = new LetterTile("E", 1);
        lt3 = new LetterTile("T", 1);
        lt4 = new LetterTile("A", 1);
        lt5 = new LetterTile("O", 1);
        lt6 = new LetterTile("T", 1);
        lt7 = new LetterTile("T", 1);
        lt8 = new LetterTile("S", 1);
        lt9 = new LetterTile("E", 1);
        lt10 = new LetterTile("B", 3);
        lt11 = new LetterTile("B", 3);
        loc1 = new Location(0,0);
        loc4 = new Location(1,0);
        loc2 = new Location(7,6);
        loc3 = new Location(7,7);     
        loc5 = new Location(6,7);
        loc6 = new Location(8,7);
        loc7 = new Location(6,8);
        loc8 = new Location(7,8);
        loc9 = new Location(8,8);
        loc10 = new Location(9,8);
        loc11 = new Location(8,9);
        loc12 = new Location(9,9);
        loc13 = new Location(10,9);
        
        placements1 = new LinkedList<TilePlacement>();
        placements2 = new LinkedList<TilePlacement>();
        placements3 = new LinkedList<TilePlacement>();
        placements4 = new LinkedList<TilePlacement>();
        placements5 = new LinkedList<TilePlacement>();
        placements6 = new LinkedList<TilePlacement>();
        placements7 = new LinkedList<TilePlacement>();
        
        placements1.add(new TilePlacement(lt10, loc12));
        placements1.add(new TilePlacement(lt11, loc13));
        
        /* valid word (but not for start - doesnt use center) reading down */
        placements2.add(new TilePlacement(lt1, loc1));
        placements2.add(new TilePlacement(lt2, loc4));
        
        /* valid word but invalid position (reads up) */
        placements3.add(new TilePlacement(lt9, loc11));
        
        /* valid word for start (start at center) reading across */
        placements4.add(new TilePlacement(lt1, loc2));
        placements4.add(new TilePlacement(lt2, loc3));
        
        placements5.add(new TilePlacement(lt5, loc7));
        placements5.add(new TilePlacement(lt6, loc8));
        placements5.add(new TilePlacement(lt7, loc9));
        placements5.add(new TilePlacement(lt8, loc10));
        
        /* valid intersecting word */
        placements6.add(new TilePlacement(lt3, loc5));
        placements6.add(new TilePlacement(lt4, loc6));
        
        /* invalid intersecting word (reads up) */
        placements7.add(new TilePlacement(lt4, loc5));
        placements7.add(new TilePlacement(lt3, loc6));       
    }
    /**
     * Method to test getSquare().
     */
    @Test
    public void testGetSquare(){
        Square s = sb.getSquare(loc1);
        assertTrue(s instanceof TripleWordSquare);
        s = sb.getSquare(loc2);
        assertTrue(s instanceof StandardSquare);
        assertNull(s.getLetterTile());
        assertNull(s.getSpecialTile());
    }
    /**
     * Method to test isValidBoard after different types of tile additions.
     */
    @Test
    public void testIsValidBoard(){
        /* Test adding valid word at invalid start location */        
        assertFalse(sb.isValidBoard(placements2));

        /* Test adding valid word at valid start location */
        
        assertTrue(sb.isValidBoard(placements4));
        
        /* Test adding valid intersecting word */
        assertTrue(sb.isValidBoard(placements6));
        
        /* Test adding word to occupied squares */
        assertFalse(sb.isValidBoard(placements6));
        
        /* Test adding invalid intersecting word */
        assertFalse(sb.isValidBoard(placements7));
        sb.addLetters(placements6);
        
        /* Test adding valid word at invalid location (not touching existing)*/
        assertFalse(sb.isValidBoard(placements2));
        
        /* Test adding valid parallel word */
        assertTrue(sb.isValidBoard(placements5));
        
        /* Test adding one letter to a word */
        assertTrue(sb.isValidBoard(placements3));
        
        /* Test adding word perpendicular to existing */
        assertTrue(sb.isValidBoard(placements1));     
    }
    /**
     * Method to test getNewWords().
     */
    @Test
    public void testGetNewWords(){
        /* Only testing valid letter placements since new words can only be
         * formed with valid placements */ 
        sb.isValidBoard(placements4);
        List<List<Square>> newWords = sb.getNewWords();
        assertTrue(newWords.size() == 1);
        assertTrue(newWords.get(0).size() == 2);
        assertTrue(sb.isValidBoard(placements6));
        newWords = sb.getNewWords();
        assertTrue(newWords.size() == 1);
        assertTrue(newWords.get(0).size() == 3);
        assertTrue(sb.isValidBoard(placements5));
        newWords = sb.getNewWords();
        assertTrue(newWords.size() == 4);
        
    }
    /**
     * Method to test isEmpty().
     */
    @Test
    public void testIsEmpty(){
        assertTrue(sb.isEmpty());
        assertFalse(sb.isValidBoard(placements2));
        assertTrue(sb.isEmpty());
        sb.isValidBoard(placements4);
        assertFalse(sb.isEmpty());
    }
    
}