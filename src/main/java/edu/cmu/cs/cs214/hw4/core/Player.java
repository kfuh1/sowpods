package edu.cmu.cs.cs214.hw4.core;

import java.util.List;
import java.util.LinkedList;
/**
 * Class to represent a player in Scrabble.
 * @author Kathleen
 *
 */
public class Player {
    private static final int INIT_SCORE = 0;
    private int score;
    private String name;
    private List<LetterTile> letters;
    private List<SpecialTile> specials;
    /**
     * Constructor to instantiate player object.
     * @param pName Player name.
     * @param pLetters Starting set of seven letter tiles.
     */
    public Player (String pName, List<LetterTile> pLetters){
        score = INIT_SCORE;
        name = pName;
        letters = pLetters;
        specials = new LinkedList<SpecialTile>();
    }
    /**
     * Method to get private variable name's value.
     * @return Player name.
     */
    public String getName(){
        return name;
    }
    /**
     * Method to get private variable's score value.
     * @return Player's score.
     */
    public int getScore(){
        return score;
    }
    /**
     * Method to update player's score after a turn.
     * @param adjustment Additional points to add to player's score.
     */
    public void updateScore(int adjustment){
        score += adjustment;
    }
    /**
     * Method to set player's score to zero. Needed as intermediate change
     * when applying the switch tile effect.
     */
    public void clearScore(){
        score = 0;
    }
    /**
     * Method to get player's set of seven letter tiles.
     * @return Player's tiles.
     */
    public List<LetterTile> getLetters(){
        return letters;
    }
    /**
     * Method to update Player's set of seven letters.
     * @param ls List of letter tiles to be added to Player's set.
     */
    public void updateLetters(List<LetterTile> ls){
        letters.addAll(ls);
    }
    /**
     * Method to remove a set of tiles from a player's list after a move.
     * @param ls List of letter tiles to be removed.
     */
    public void removeLetters(List<LetterTile> ls){
        for(LetterTile tile : ls){
            letters.remove(tile);
        }
    }
    /**
     * Method to add special tile to Player's set of special tiles.
     * @param tile Special tile to be added.
     */
    public void addSpecialTile(SpecialTile tile){
        specials.add(tile);
    }
    /**
     * Method to get all special tiles belonging to player.
     * @return List of special tiles.
     */
    public List<SpecialTile> getSpecialTiles(){
        return specials;
    }
    /**
     * Method to remove a specified special tile. Most likely called 
     * after special tile has been played.
     * @param tile Special tile to be removed.
     */
    public void removeSpecialTile(SpecialTile tile){
        specials.remove(tile);
    }
}
