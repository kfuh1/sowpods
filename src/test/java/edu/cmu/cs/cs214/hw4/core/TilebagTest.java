package edu.cmu.cs.cs214.hw4.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.LinkedList;

/**
 * Class for testing Tilebag functionality. 
 * @author Kathleen
 *
 */
public class TilebagTest {
    private Tilebag tilebag;
    private LetterTile l1;
    private LetterTile l2;
    private List<LetterTile> letters;
    /**
     * Method to instantiate Tilebag object for testing.
     */
    @Before
    public void setUp(){
        tilebag = new Tilebag();
        l1 = new LetterTile("A", 1);
        l2 = new LetterTile("X", 10);
        letters = new LinkedList<LetterTile>();
    }
    
    /**
     * Method to test initial creation of letter and special tiles.
     */
    @Test
    public void testTileCreation(){
        assertFalse(tilebag.getIsEmpty());
        assertTrue(tilebag.hasSpecialTiles());
        assertTrue(tilebag.numLetterTiles() == 98);
        assertTrue(tilebag.numSpecialTiles() == 25);
    }
    /**
     * Method to test exchanging a list of letters.
     */
    @Test
    public void testExchangeLetters(){
        List<LetterTile> newLetters;
        letters.add(l1);
        letters.add(l2);
        newLetters = tilebag.exchangeLetters(letters);
        assertNotNull(newLetters);
        assertTrue(newLetters.size() == 2);
        assertTrue(tilebag.numLetterTiles() == 98);
        
    }
    /**
     * Method to test getting a certain number of letters.
     */
    @Test
    public void testGetLetterTiles(){
        List<LetterTile> tiles = tilebag.getLetterTiles(7);
        assertTrue(tiles.size() == 7);
        tiles = tilebag.getLetterTiles(95);
        assertTrue(tiles.size() == 91);
    }
    /**
     * Method to test purchasing a special tile.
     */
    @Test
    public void testPurchaseSpecialTile(){
        SpecialTile s = tilebag.purchaseSpecialTile("Negative");
        assertTrue(s instanceof NegativeTile);
        assertTrue(tilebag.numSpecialTiles() == 24);
        /* Test removing all of a type of tile */
        s = tilebag.purchaseSpecialTile("Boom");
        s = tilebag.purchaseSpecialTile("Boom");
        assertTrue(tilebag.numSpecialTiles() == 22);
        s = tilebag.purchaseSpecialTile("Boom");
        assertTrue(tilebag.numSpecialTiles() == 21);
    }
}
