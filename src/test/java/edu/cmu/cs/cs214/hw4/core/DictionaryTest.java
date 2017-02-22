package edu.cmu.cs.cs214.hw4.core;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Class to test Dictionary functionality.
 * @author Kathleen
 *
 */
public class DictionaryTest {
    private static final String FILENAME = "src/main/resources/testDictionary.txt";
    private Dictionary d;
    
    /**
     * Method to instantiate Dictionary object for testing.
     */
    @Before
    public void setUp(){
        d = new Dictionary(FILENAME);
    }
    /**
     * Test dictionary has correctly read in file and test isValidWord works.
     */
    @Test
    public void testIsValidWord(){
        assertTrue(d.isValidWord("hello"));
        assertFalse(d.isValidWord("there"));
        assertTrue(d.isValidWord("HElLO"));
    }
}
