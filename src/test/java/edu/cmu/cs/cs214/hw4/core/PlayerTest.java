package edu.cmu.cs.cs214.hw4.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.LinkedList;
/**
 * Class to test player object functionality.
 * @author Kathleen
 *
 */
public class PlayerTest {
    private Player p;
    private LetterTile lt1, lt2, lt3;
    private SpecialTile st1;
    private List<LetterTile> letterTiles, removedTiles;
    /**
     * Method to instantiate Person object for testing.
     */
    @Before
    public void setUp(){
        letterTiles = new LinkedList<LetterTile>();
        removedTiles = new LinkedList<LetterTile>();
        lt1 = new LetterTile("A", 1);
        lt2 = new LetterTile("E", 1);
        lt3 = new LetterTile("F", 1);
        st1 = new NegativeTile();
        removedTiles.add(lt1);
        removedTiles.add(lt2);
        for(int i = 0; i < 7; i++){
            char c = (char) (65 + i);
            letterTiles.add(new LetterTile(Character.toString(c),1));
        }
        p = new Player("Player 1", letterTiles);
    }
    /**
     * Method to test updating score.
     */
    @Test
    public void testUpdateScore(){
        p.updateScore(5);
        assertTrue(p.getScore() == 5);
        p.updateScore(-10);
        assertTrue(p.getScore() == -5);
    }
    /**
     * Method to test changes to a player's rack of letter tiles.
     */
    @Test
    public void testRemoveUpdateLetters(){
        assertTrue(letterTiles.equals(p.getLetters()));
        p.removeLetters(removedTiles);
        assertTrue(p.getLetters().size() == 5);
        assertFalse(p.getLetters().contains(lt1));
        assertTrue(p.getLetters().contains(lt3));
        p.updateLetters(removedTiles);
        assertTrue(p.getLetters().size() == 7);
    }
    /**
     * Method to test changes to a player's rack of special tiles.
     */
    @Test
    public void testRemoveUpdateSpecial(){
        assertTrue(p.getSpecialTiles().size() == 0);
        p.addSpecialTile(st1);
        assertTrue(p.getSpecialTiles().size() == 1);
    }
}
