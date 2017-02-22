package edu.cmu.cs.cs214.hw4.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class ScrabbleWithStuffTest {
    private static final int NUM_PLAYERS = 4;
    private static final String FILENAME = 
            "src/main/resources/testDictionary.txt";
    private ScrabbleWithStuff game;
    private LetterTile lt1, lt2, lt3, lt4, lt5, lt6, lt7, lt8, lt9, lt10,
    lt11, lt12, lt13;
    private Location loc1, loc2, loc3, loc4, loc5, loc6, loc7, loc8, loc9,
    loc10, loc11, loc12, loc13;
    private SpecialTile st1, st2, st3;
    private List<TilePlacement> placements1, placements2, placements3,
    placements4, placements5, placements6;
    private List<String> playerNames;
    /**
     *
     */
    @Before
    public void setUp(){
        playerNames = new LinkedList<String>();
        playerNames.add("Player 1");
        playerNames.add("Player 2");
        playerNames.add("Player 3");
        playerNames.add("Player 4");
        game = new ScrabbleWithStuff(playerNames, FILENAME);
        lt1 = new LetterTile("M", 3);
        lt2 = new LetterTile("E", 1);
        lt3 = new LetterTile("T", 1);
        lt4 = new LetterTile("A", 1);
        lt5 = new LetterTile("O", 1);
        lt6 = new LetterTile("T", 1);
        lt7 = new LetterTile("T", 1);
        lt8 = new LetterTile("E", 1);
        lt9 = new LetterTile("B", 3);
        lt10 = new LetterTile("B", 3);
        lt11 = new LetterTile("E", 1);
        lt12 = new LetterTile("E", 1);
        lt13 = new LetterTile("S", 1);
        
        loc1 = new Location(7,6);
        loc2 = new Location(7,7);
        loc3 = new Location(6,7);
        loc4 = new Location(8,7);
        loc5 = new Location(6,8);
        loc6 = new Location(7,8);
        loc7 = new Location(8,8);
        loc8 = new Location(8,9);
        loc9 = new Location(9,9);
        loc10 = new Location(10,9);
        loc11 = new Location(10,10);
        loc12 = new Location(10,11);
        loc13 = new Location(10,12);
        
        st1 = new NegativeTile();
        st2 = new BoomTile();
        st3 = new FreebieSkipTile();
        
        placements1 = new LinkedList<TilePlacement>();
        placements2 = new LinkedList<TilePlacement>();
        placements3 = new LinkedList<TilePlacement>();
        placements4 = new LinkedList<TilePlacement>();
        placements5 = new LinkedList<TilePlacement>();
        placements6 = new LinkedList<TilePlacement>();
        
        placements1.add(new TilePlacement(lt1, loc1));
        placements1.add(new TilePlacement(lt2, loc2));
        placements2.add(new TilePlacement(lt3, loc3));
        placements2.add(new TilePlacement(lt4, loc4));
        placements3.add(new TilePlacement(lt5, loc5));
        placements3.add(new TilePlacement(lt6, loc6));
        placements3.add(new TilePlacement(lt7, loc7));
        placements4.add(new TilePlacement(lt8, loc8));
        placements4.add(new TilePlacement(lt9, loc9));
        placements4.add(new TilePlacement(lt10, loc10));
        placements5.add(new TilePlacement(lt11, loc11));
        placements6.add(new TilePlacement(lt12, loc12));
        placements6.add(new TilePlacement(lt13, loc13));
    }
    @Test
    public void testPlayerInit(){
        List<Player> players = game.getPlayers();
        assertTrue(players.size() == NUM_PLAYERS);
        for(Player p : players){
            assertTrue(p.getScore() == 0);
        }
        Player cp = game.getCurrentPlayer();
        assertTrue("Player 1".equals(cp.getName()));
    }
    @Test
    public void testPlayLetterTiles(){
        /* player 1 */
        Player currPlayer = game.getCurrentPlayer();
        game.playLetterTiles(placements1);
        assertTrue(currPlayer.getScore() == 4);
        /* player 2 */
        currPlayer = game.getCurrentPlayer();
        assertTrue("Player 2".equals(currPlayer.getName()));      
        game.playLetterTiles(placements2);
        assertTrue(currPlayer.getScore() == 3);
        /* player 3 */
        game.pass();
        /* player 4 */
        game.pass();
        /* player 1 */
        currPlayer = game.getCurrentPlayer();
        assertTrue("Player 1".equals(currPlayer.getName()));
        game.playLetterTiles(placements3);
        assertTrue(currPlayer.getScore() == 18);
        /* player 2 */
        game.pass();
        /* player 3 */
        currPlayer = game.getCurrentPlayer();
        game.playLetterTiles(placements4);
        assertTrue(currPlayer.getScore() == 16);
        /* player 4 */
        currPlayer = game.getCurrentPlayer();
        game.playLetterTiles(placements5);
        assertTrue(currPlayer.getScore() == 8);
        /* player 1 */
        currPlayer = game.getCurrentPlayer();
        game.playLetterTiles(placements6);
        assertTrue(currPlayer.getScore() == 24);      
    }
    @Test
    public void testNegativeTileEffect(){
        Player currPlayer = game.getCurrentPlayer();
        game.playSpecialTile(st1, loc1);
        assertTrue(currPlayer.getScore() == 0);
        currPlayer = game.getCurrentPlayer();
        assertTrue("Player 2".equals(currPlayer.getName()));
        game.playLetterTiles(placements1);
        assertTrue(currPlayer.getScore() == -4);
        assertFalse(game.playSpecialTile(st1, loc2));
    }
    @Test
    public void testBoomTileEffect(){
        Player currPlayer = game.getCurrentPlayer();
        game.playSpecialTile(st2, loc1);
        currPlayer = game.getCurrentPlayer();
        game.playLetterTiles(placements1); 
        assertTrue(currPlayer.getScore() == 0);  
    }
    @Test
    public void testFreebieSkipTileEffect(){
        Player currPlayer = game.getCurrentPlayer();
        game.playSpecialTile(st3, loc1);
        currPlayer = game.getCurrentPlayer();
        game.playLetterTiles(placements1); 
        assertTrue(currPlayer.getScore() == 4);  
        assertTrue(currPlayer.getSpecialTiles().size() == 1);
        currPlayer = game.getCurrentPlayer();
        assertTrue("Player 4".equals(currPlayer.getName()));
    }
    /**
     * Method to test reverse functionality
     */
    @Test
    public void testReverse(){
        game.pass();
        Player cp = game.getCurrentPlayer();
        assertTrue("Player 2".equals(cp.getName()));
        game.reverseOrder();
        game.pass();
        cp = game.getCurrentPlayer();
        assertTrue("Player 1".equals(cp.getName()));
        game.pass();
        cp = game.getCurrentPlayer();
        assertTrue("Player 4".equals(cp.getName()));
    }
    /**
     * Method to test effect of activating boom tile.
     */
    @Test
    public void testActivateBoom(){
        ScrabbleBoard sb = game.getBoard();
        assertTrue(sb.isValidBoard(placements1));
        game.boomEffect(loc9);
        assertTrue(sb.isEmpty());
        sb.isValidBoard(placements1);
        game.boomEffect(loc11);
        assertFalse(sb.isEmpty());
        sb.isValidBoard(placements1);
        game.boomEffect(loc12);
        assertFalse(sb.isEmpty());
    }
}
