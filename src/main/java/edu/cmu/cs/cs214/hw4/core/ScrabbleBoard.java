package edu.cmu.cs.cs214.hw4.core;
import java.util.List;

/**
 * Interface to represent Scrabble with stuff board.
 * @author Kathleen
 *
 */
public interface ScrabbleBoard {
    /**
     * Method to get Square object given location.
     * @param loc Location of square.
     * @return Square object at location. Returns null if the location 
     * is not on the board.
     */
    Square getSquare(Location loc);
    /**
     * Method to determine if all words on board are valid.
     * @param placements List of letter tiles and their intended locations
     * of placement.
     * @return True if all words on board are valid, false otherwise.
     */
    boolean isValidBoard(List<TilePlacement> placements);
    /**
     * Method to place letter tiles on the board.
     * @param letterPlacements List of letter tiles and their locations.
     * @return True if letters were successfully added to board,
     * false otherwise.
     */
    boolean addLetters(List<TilePlacement> letterPlacements);
    /**
     * Method to remove letter tiles from the board.
     * @param placements List of letter tiles and their locations.
     */
    void removeLetters(List<TilePlacement> placements);
    /**
     * Method to place special til
     * @param st Special tile to be placed on board.
     * @param loc Location where special tile wants to be placed.
     * @return True if special tile is successfully added to board,
     * false otherwise.
     */
    boolean addSpecialTile(SpecialTile st, Location loc);
    /**
     * Method to get words formed on current turn.
     * @return List of words (represented as list of squares) that were 
     * formed on current turn.
     */
    List<List<Square>> getNewWords();
    /**
     * Method to check if there are any tiles placed on the board.
     * @return True if there are no tiles on the board. False otherwise.
     */
    boolean isEmpty();
    /**
     * Method to get the number of rows in scrabble board.
     * @return number of rows.
     */
    int getRows();
    /**
     * Method to get the number of columns in scrabble board.
     * @return number of columns.
     */
    int getColumns();
    /**
     * Method to remove letter tile and special tile from the board.
     * @param loc Location of square to be cleared.
     */
    void clearSquare(Location loc);
    /**
     * Method to get center location of board.
     * @return Center location of board.
     */
    Location getCenterLocation();
}
