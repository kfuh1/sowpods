package edu.cmu.cs.cs214.hw4.core;
/**
 * Interface for listeners that handle game events.
 * @author Kathleen
 *
 */
public interface GameChangeListener {
    /**
     * Called when game ends, and announces winner.
     * @param winner Player that had highest points.
     */
    void gameEnded(Player winner);
    /**
     * Called when a tile on the board changes. This is also used
     * in the initialization stage.
     * @param board Current scrabble board.
     */
    void boardChanged(ScrabbleBoard board);
    /**
     * Called when the current player changes.
     * @param p New current player.
     */
    void playerChanged(Player p);
}
