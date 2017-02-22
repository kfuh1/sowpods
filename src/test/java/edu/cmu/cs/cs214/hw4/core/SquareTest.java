package edu.cmu.cs.cs214.hw4.core;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
/**
 * Class for testing all Square objects.
 * @author Kathleen
 *
 */
public class SquareTest {
    private int score;
    private int multFactor;
    private Location loc;
    private LetterTile tile;
    /**
     * Method to instantiate Square object for testing.
     */
    @Before
    public void setUp(){
        score = 100;
        multFactor = 1;
        loc = new Location(0,0);
        tile = new LetterTile("A", 5);
    }
    /**
     * Test specialized TripleLetterSquare functionalities.
     */
    @Test
    public void testTripleLetterSquare(){
        Square s = new TripleLetterSquare(loc);
        s.placeLetterTile(tile);
        assertNotNull(s.getLetterTile());
        assertTrue(s.getMultFactor() == 1);
        assertTrue(s.computeScore(score, multFactor) == 115);
        /* test that square has been deactivated */
        assertTrue(s.computeScore(score, multFactor) == 105);
    }
    /**
     * Test specialized DoubleLetterSquare functionalities.
     */
    @Test
    public void testDoubleLetterSquare(){
        Square s = new DoubleLetterSquare(loc);
        s.placeLetterTile(tile);
        assertNotNull(s.getLetterTile());
        assertTrue(s.getMultFactor() == 1);
        assertTrue(s.computeScore(score, multFactor) == 110);
        assertTrue(s.computeScore(score, multFactor) == 105);
    }
    /**
     * Test specialized TripleWordSquare functionalities.
     */
    @Test
    public void testTripleWordSquare(){
        Square s = new TripleWordSquare(loc);
        s.placeLetterTile(tile);
        assertNotNull(s.getLetterTile());
        assertTrue(s.getMultFactor() == 3);
        assertTrue(s.computeScore(score, multFactor*s.getMultFactor()) == 315);
        assertTrue(s.computeScore(score, multFactor*s.getMultFactor()) == 105);
    }
    /**
     * Test specialized DoubleWordSquare functionalities.
     */
    @Test
    public void testDoubleWordSquare(){
        Square s = new DoubleWordSquare(loc);
        s.placeLetterTile(tile);
        assertNotNull(s.getLetterTile());
        assertTrue(s.getMultFactor() == 2);
        assertTrue(s.computeScore(score, multFactor*s.getMultFactor()) == 210);
        assertTrue(s.computeScore(score, multFactor*s.getMultFactor()) == 105);
    }
    /**
     * Test specialized StandardSquare functionalities.
     */
    @Test
    public void testStandardSquare(){
        Square s = new StandardSquare(loc);
        s.placeLetterTile(tile);
        assertNotNull(s.getLetterTile());
        assertTrue(s.getMultFactor() == 1);
        assertTrue(s.computeScore(score, multFactor) == 105);
    }
    
}
