package edu.cmu.cs.cs214.hw4.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Class to test Location implementation.
 * @author Kathleen
 *
 */
public class LocationTest {
    private Location loc;
    
    /**
     * Method to instantiate Location object for testing.
     */
    @Before
    public void setUp(){
        loc = new Location(4,5);
    }
    /**
     * Test getX() method.
     */
    @Test
    public void testGetRow(){
        assertTrue(loc.getRow() == 4);
        assertFalse(loc.getRow() == 5);
    }
    /**
     * Test getY() method.
     */
    @Test
    public void testGetCol(){
        assertTrue(loc.getCol() == 5);
        assertFalse(loc.getCol() == 8);
    }
}
