package edu.cmu.cs.cs214.hw4.core;

import java.util.List;
import java.util.LinkedList;
import java.util.Random;
/**
 * Class to implement functionality of a Scrabble tilebag.
 * @author Kathleen
 *
 */
public class Tilebag {
    /* Letter distributions and worths as defined by standard Scrabble rules.
     * The distribution here goes in order from A to Z.
     * I'm hardcoding it under the assumption that standard Scrabble
     * distributions won't change anytime soon.
     */
    private static final int[] LETTER_DISTR = 
        {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1};
    private static final int[] LETTER_WORTH = 
        {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
    private static final int ASCII_A = 65;
    private static final int ASCII_Z = 90;
    
    /* Special tile distribution.
     * The distribution is given in the order 
     * Negative, Boom, Reverse, Freebie (mine), Switch. 
     * I'm hardcoding it because it seems just as in the letter tile
     * case, the standard for SrabbleWithStuff shouldn't be changed
     * very often once the game has been defined.
     */
    private static final int[] SPECIAL_DISTR = {5,5,5,5,5};
    private static final int NEG_INDEX = 0;
    private static final int BOOM_INDEX = 1;
    private static final int REV_INDEX = 2;
    private static final int FREEBIE_INDEX = 3;
    private static final int SWITCH_INDEX = 4;
    
    private Random rand;
    private List<LetterTile> letterTiles;
    private List<SpecialTile> specialTiles;

    /**
     * Constructor to create bag of tiles.
     */
    public Tilebag(){
        rand = new Random();
        letterTiles = new LinkedList<LetterTile>();
        specialTiles = new LinkedList<SpecialTile>();
        
        createLetterTiles();
        createSpecialTiles();
    }
    
    /**
     * Method to trade in a set of letter tiles and get that many tiles back.
     * @param tilesToExchange List of new tiles. Will be same number as the
     * player traded in iff there are at least that many tiles in the bag,
     * otherwise less.
     * @return List of new tiles picked randomly.
     */
    public List<LetterTile> exchangeLetters(List<LetterTile> tilesToExchange){
        List<LetterTile> newTiles = null;
        int num = tilesToExchange.size();
        if(num <= letterTiles.size()){
            newTiles = getLetterTiles(num);
            letterTiles.addAll(tilesToExchange);
        }
        return newTiles;
    }
    /**
     * Method to randomly pick a certain number of tiles from the bag.
     * @param num Number of tiles to get. Should be >= 0 and <= 7.
     * @return List of the tile selected from the bag.
     */
    public List<LetterTile> getLetterTiles(int num){
        List<LetterTile> tiles = new LinkedList<LetterTile>();
        /* Randomly pick however many tiles are needed */
        while(num > 0 && letterTiles.size() > 0){
            int randIndex = rand.nextInt(letterTiles.size());
            LetterTile l = letterTiles.remove(randIndex);
            tiles.add(l);
            num--;
        }
        return tiles;
    }
    /**
     * Method to create letter tiles based on distributions.
     */
    private void createLetterTiles(){
        int letterNum = ASCII_A;
        int worth;
        int number = 0;
        /* using ASCII values, create tiles with correct letters */
        while(letterNum <= ASCII_Z){
            number = LETTER_DISTR[letterNum - ASCII_A];
            worth = LETTER_WORTH[letterNum - ASCII_A];
            while(number > 0){
                char c = (char) letterNum; /* get letter based on ASCII val */
                String letter = Character.toString(c);
                LetterTile l = new LetterTile(letter, worth);
                letterTiles.add(l);
                number--;
            }
            letterNum++;
        }
    }
    /**
     * Method to create special tiles based on distribution.
     */
    private void createSpecialTiles(){
        int number;
        for(int i = 0; i < SPECIAL_DISTR.length; i++){
            number = SPECIAL_DISTR[i];
            while(number > 0){
                /* Negative tile creation */
                if(i == NEG_INDEX){
                    specialTiles.add(new NegativeTile());
                }
                /* Boom tile creation */
                else if(i == BOOM_INDEX){
                    specialTiles.add(new BoomTile());
                }
                /* Reverse tile creation */
                else if(i == REV_INDEX){
                    specialTiles.add(new ReverseTile());
                }
                /* FreebieSkip tile creation */
                else if(i == FREEBIE_INDEX){
                    specialTiles.add(new FreebieSkipTile());
                }
                else if(i == SWITCH_INDEX){
                    specialTiles.add(new SwitchTile());
                }
                number--;
            }
        }
    }
    /**
     * Method to return specified type of special tile from Tilebag. This 
     * method assumes that Game has already validated that the player has
     * enough points to purchase the tile.
     * @param type String representing type of tile desired.
     * @return SpecialTile object if that type of tile still exists in the
     * tilebag; null otherwise.
     */
    public SpecialTile purchaseSpecialTile(String type){
        boolean isPresent = false;
        SpecialTile tile = null;
        if(type.equals("Negative")){
            tile = new NegativeTile();
        }
        else if(type.equals("Boom")){
            tile = new BoomTile();
        }
        else if(type.equals("Reverse")){
            tile = new ReverseTile();
        }
        else if(type.equals("FreebieSkip")){
            tile = new FreebieSkipTile();
        }
        else if(type.equals("Switch")){
            tile = new SwitchTile();
        }
        isPresent = specialTiles.remove(tile);
        if(isPresent){
            return tile;
        }
        else{
            return null;
        }
    }
    /**
     * Method to randomly pick special tile to give current player
     * after activating the FreebieSkip tile.
     * @return randomly selected Special tile.
     */
    public SpecialTile getFreeSpecialTile(){
        SpecialTile st = null;
        if(!specialTiles.isEmpty()){
            int randIndex = rand.nextInt(specialTiles.size());
            st = specialTiles.remove(randIndex);
        }
        return st;
    }
    /**
     * Method to put tile back into bag.
     * @param tile Tile to be returned.
     */
    public void returnTile(LetterTile tile){
        letterTiles.add(tile);
    }
    /**
     * Method that indicates if there are still special tiles available.
     * @return true if there are still special tiles, false otherwise.
     */
    public boolean hasSpecialTiles(){
        return !(specialTiles.isEmpty());
    }
    /**
     * Method that indicates if there are tiles available.
     * Emptiness is determined only by letter tiles since the game can
     * continue without special tiles, but it ends once there are no more
     * letter tiles.
     * @return true if there are still letter tiles in bag; false otherwise.
     */
    public boolean getIsEmpty(){
        return letterTiles.isEmpty();
    }
    
    /* FOR TESTING PURPOSES ONLY */
    /**
     * Method to get number of letter tiles.
     * @return Number of letter tiles in the tilebag.
     */
    public int numLetterTiles(){
        return letterTiles.size();
    }
    /**
     * Method to get number of special tiles.
     * @return Number of special tiles in the tilebag.
     */
    public int numSpecialTiles(){
        return specialTiles.size();
    }
}
