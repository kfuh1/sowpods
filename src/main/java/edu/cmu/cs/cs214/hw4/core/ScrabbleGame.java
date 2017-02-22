package edu.cmu.cs.cs214.hw4.core;

import java.util.List;
/**
 * Interface for Scrabble.
 * @author Kathleen
 *
 */
public interface ScrabbleGame {
    /**
     * Method to register game change listener to know when events change game.
     * @param g Listener to be notified.
     */
    void addGameChangeListener(GameChangeListener g);
    /**
     * Method called when player passes on turn.
     */
    void pass();
    /**
     * Method called when player wants to exchange a subset of his tiles.
     * @param lettersToExchange Tile to be exchanged.
     * @return True if successfully exchanged tiles, false otherwise.
     */
    boolean exchangeTiles(List<LetterTile> lettersToExchange);
    /**
     * Method called when player wants to put letter tiles on board.
     * @param letterPlacements List of letter tiles and the intended locations.
     * @return True if successfully placed and scored tiles, false otherwise.
     */
    boolean playLetterTiles(List<TilePlacement> letterPlacements);
    /**
     * Method to let user buy special tile with points.
     * @param type String indicating type of special tile.
     * @return True if successfully purchased special tile, false otherwise.
     */
    boolean purchaseSpecialTile(String type);
    /**
     * Method to let user place a special tile on the board.
     * @param st Special tile to be used.
     * @param loc Location that will be used.
     * @return True if special tile is successfully placed on the board,
     * false otherwise (i.e. square already contains a letter tile or
     * special tile - this game is designed so one square can only contain
     * one special tile).
     */
    boolean playSpecialTile(SpecialTile st, Location loc);
    /**
     * Method to get a player's score.
     * @param p Player.
     * @return Player's score.
     */
    int getPlayerScore(Player p);
    /**
     * Method to get current player.
     * @return current player.
     */
    Player getCurrentPlayer();
    /**
     * Method to get players in the game.
     * @return List of players.
     */
    List<Player> getPlayers();
    /**
     * Method to be used by GUI to get current board to be drawn.
     * @return Scrabbleboard representation of current board state.
     */
    ScrabbleBoard getBoard();
    /**
     * Method to get whether or not game is over.
     * A game is over if there are no more tiles in the tilebag and at least
     * one player doesn't have anymore tiles left.
     */
    void checkGameOver();
}
