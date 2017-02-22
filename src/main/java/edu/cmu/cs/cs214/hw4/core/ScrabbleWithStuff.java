package edu.cmu.cs.cs214.hw4.core;

import java.util.List;
import java.util.LinkedList;

/**
 * Class to create Scrabble with stuff game.
 * @author Kathleen
 *
 */
public class ScrabbleWithStuff implements ScrabbleGame {
    private static final int NUM_TILES = 7;
    private static final int BINGO_BONUS = 50;
    private static final int BOOM_RADIUS = 3;
    
    private int numPlayers, currPlayerIndex, multFactor;
    private Player currPlayer;
    private List<GameChangeListener> listeners;
    private List<String> playerNames;
    private List<Player> players;
    private List<Player> scoreSwitchPlayers;
    private Tilebag tilebag;
    private ScrabbleBoard board;
    private Dictionary dict;
    private String filename;
    private int incrementer;
    private boolean isSwitchScore;
    /**
     * Class used exclusively by ScrabbleWithStuff implementation to store
     * a special tile placement move.
     * @author Kathleen
     *
     */
    private final class SpecialTilePlacement{
        private SpecialTile tile;
        private Location loc;
        /**
         * Constructor to initialize special tile placement fields.
         * @param t Special tile to be placed.
         * @param l Location of placement.
         */
        private SpecialTilePlacement(SpecialTile t, Location l){
            tile = t;
            loc = l;
        }
        /**
         * Method to get special tile for placement.
         * @return Special tile.
         */
        private SpecialTile getSpecialTile(){
            return tile;
        }
        /**
         * Method to get location of placement.
         * @return Location.
         */
        private Location getLocation(){
            return loc;
        }
    } 
    /**
     * Constructor to instantiate game objects.
     * @param names List of player names in game.
     * @param pFilename String representing filename used for dictionary.
     */
    public ScrabbleWithStuff(List<String> names, String pFilename){
        playerNames = names;
        numPlayers = playerNames.size();
        filename = pFilename;
        dict = new Dictionary(filename);
        currPlayerIndex = 0;
        multFactor = 1;
        players = new LinkedList<Player>();
        scoreSwitchPlayers = new LinkedList<Player>();
        listeners = new LinkedList<GameChangeListener>();
        tilebag = new Tilebag();
        incrementer = 1;
        board = new StandardBoard(dict);
        setUpGame();
        currPlayer = players.get(currPlayerIndex);
        isSwitchScore = false;
    }
    /**
     * Method to set up game with needed players, values and objects.
     */
    private void setUpGame(){
        /* Create players and give them starting set of tiles */
        for(int i = 0; i < numPlayers; i++){
            String name = playerNames.get(i);
            List<LetterTile> letters = tilebag.getLetterTiles(NUM_TILES);
            Player p = new Player(name, letters);
            players.add(p);
        }
    }
    /**
     * Method to get next player based on the incrementer.
     */
    private void stepPlayer(){
        currPlayerIndex = (currPlayerIndex + incrementer) % numPlayers;
        if(currPlayerIndex < 0){
            currPlayerIndex += numPlayers;
        }
        currPlayer = players.get(currPlayerIndex);
        checkGameOver();
        notifyPlayerChanged();
    }
    
    /* The following are methods to notify the GUI once something has changed.
     * They were based off of the sample solutiosn from Recitation 8 */
    
    @Override
    public void addGameChangeListener(GameChangeListener g){
        listeners.add(g);
    }
    /**
     * Method to notify GUI when the state of the board has changed.
     */
    private void notifyBoardChanged(){
        for(GameChangeListener g : listeners){
            g.boardChanged(board);
        }
    }
    /**
     * Method to notify GUI when current player has changed.
     */
    private void notifyPlayerChanged(){
        for(GameChangeListener g : listeners){
            g.playerChanged(currPlayer);
        }
    }
    /**
     * Method to notify GUI when game has ended.
     * @param winner Player with highest number of points.
     */
    private void notifyGameEnd(Player winner){
        for(GameChangeListener g : listeners){
            g.gameEnded(winner);
        }
    }
    
    /* The following are methods regarding the different moves a player makes*/
    
    @Override
    public void pass(){
        stepPlayer();
        notifyBoardChanged();
    }
    @Override
    public boolean exchangeTiles(List<LetterTile> lettersToExchange){
        List<LetterTile> newLetters = tilebag.exchangeLetters(lettersToExchange);
        if(newLetters != null){
            currPlayer.removeLetters(lettersToExchange);
            currPlayer.updateLetters(newLetters);
            stepPlayer();
            notifyBoardChanged();
            return true;
        }
        /* Not enough tiles in tilebag to exchange that many */
        else{
            return false;
        }
    }
    @Override
    public boolean playLetterTiles(List<TilePlacement> letterPlacements){  
        List<LetterTile> letters = new LinkedList<LetterTile>();
        boolean isValid = board.isValidBoard(letterPlacements);
        /* when player makes move, those letter tiles are removed from rack,
         * if invalid, give those letters back. */
        if(isValid){
            for(TilePlacement tp : letterPlacements){
                letters.add(tp.getLetterTile());
            }
            currPlayer.removeLetters(letters);
            move(letterPlacements.size());
            return true;
        }
//        else{
//            board.removeLetters(letterPlacements);
//        }
        return false;
    }
    @Override
    public boolean purchaseSpecialTile(String type){
        int cost;
        int score = currPlayer.getScore();
        SpecialTile st;
        if(type.equals("Negative")){
            st = new NegativeTile();
        }
        else if(type.equals("Boom")){
            st = new BoomTile();
        }
        else if(type.equals("Reverse")){
            st = new ReverseTile();
        }
        else if(type.equals("FreebieSkip")){
            st = new FreebieSkipTile();
        }
        else if(type.equals("Switch")){
            st = new SwitchTile();
        }
        else{
            /* When the GUI is implemented, should never get to this case 
             * because GUI will provide special tile options that can be
             * selected, which must be tiles defined in the game. */
            System.out.println("Invalid tile choice");
            return false;
        }
        cost = st.getCost();
        /* Not enough points to purchase tile */
        if(cost > score){
            return false;
        }
        st = tilebag.purchaseSpecialTile(type);
        /* no more of those tiles left */
        if(st == null){
            return false;
        }
        else{
            st.setOwner(currPlayer);
            currPlayer.updateScore(cost * -1);
            currPlayer.addSpecialTile(st);
            stepPlayer();
        }
        return true;
    }
    @Override
    public boolean playSpecialTile(SpecialTile st, Location loc){
        boolean addSuccess = board.addSpecialTile(st, loc);
        if(addSuccess){
            currPlayer.removeSpecialTile(st);
            stepPlayer();
            notifyBoardChanged();
        }
        return addSuccess;
    }
    
    /**
     * Method called to update game after a valid word placement move is made.
     * @param tilesUsed Number of tiles used.
     */
    private void move(int tilesUsed){
        int score = 0;
        List<List<Square>> wordsFormed = board.getNewWords();
        List<SpecialTilePlacement> specialTiles = 
                new LinkedList<SpecialTilePlacement>();
        /* BINGO - using all tiles gives premium of 50 points */
        if(tilesUsed == NUM_TILES){
            score = BINGO_BONUS;    
        }
        /* get special tiles on squares */
        for(List<Square> word : wordsFormed){
            for(Square s : word){
                SpecialTile tile = s.getSpecialTile();
                if(tile != null){
                    specialTiles.add(new 
                            SpecialTilePlacement(tile, s.getLocation()));
                    s.removeSpecialTile();   
                }
            }
        }
        /* special tile effect taken into account before scoring */
        for(SpecialTilePlacement placement : specialTiles){
            SpecialTile st = placement.getSpecialTile();
            st.performAction(this, placement.getLocation());
        }
        /* score the words formed on this turn */
        for(List<Square> word : wordsFormed){
            for(Square s : word){
                multFactor *= s.getMultFactor();
                score = s.computeScore(score, multFactor);     
            }
        }
        multFactor = 1;
        currPlayer.updateScore(score);
        /* score switching done after score for this turn is computed */
        if(isSwitchScore){
            int tempScore;
            for(Player p : scoreSwitchPlayers){
                tempScore = p.getScore();
                p.clearScore();
                p.updateScore(currPlayer.getScore());
                currPlayer.clearScore();
                currPlayer.updateScore(tempScore);
            }
            isSwitchScore = false;
            scoreSwitchPlayers.clear();
        }
        List<LetterTile> newTiles = tilebag.getLetterTiles(tilesUsed);
        if(newTiles != null){
            currPlayer.updateLetters(newTiles);
        }
        stepPlayer();
        notifyBoardChanged();
        /* this is to fix the case where the incrementer has been changed by 
         * the skip mechanism in my FreebieSkip special tile because we don't
         * want the skip to be carried through the rest of the game */
        if(incrementer < 0){
            incrementer = -1;
        }
        else{
            incrementer = 1;
        }   
    }   
    @Override
    public int getPlayerScore(Player p){
        return p.getScore();
    }
    @Override
    public Player getCurrentPlayer(){
        return currPlayer;
    }
    @Override
    public List<Player> getPlayers(){
        return players;
    }
    @Override
    public ScrabbleBoard getBoard(){
        return board;
    }
    /**
     * Method to calculate final scores and determine game winner
     * once game is over. For players who still have tiles on their rack,
     * their score is reduced by sum of unplayed letters. If player has used
     * all letters, sum of other players' unplayed letters is added to his.
     * @return Player that has highest final score.
     */
    private Player calcFinalScores(){
        Player winner = null;
        List<Integer> leftoverScore = new LinkedList<Integer>();
        int totalScore = 0, index = 0, maxScore = 0;
        /* get score adjustments */
        for(Player p : players){
            int score = 0;
            for(LetterTile lt : p.getLetters()){
                score += lt.getWorth();
            }
            totalScore += score;
            leftoverScore.add(score);
        }
        /* apply score adjustments */
        for(Player p : players){
            int extraScore = leftoverScore.get(index);
            if(extraScore == 0){
                p.updateScore(totalScore);
            }
            else{
                p.updateScore(extraScore * -1);
            }
            index++;
        }
        /* find player with max score */
        for(Player p : players){
            if(p.getScore() > maxScore){
                maxScore = p.getScore();
                winner = p;
            }
        }
        return winner;
    }
    @Override
    public void checkGameOver(){
        boolean isGameOver = false;
        /* Game is over when all tiles in tilebag are used and
         * at least one player doesn't have any more tiles. */
        if(tilebag.getIsEmpty()){
            for(Player p : players){
                if(p.getLetters().size() == 0){
                    isGameOver = true;
                }
            }
        }
        if(isGameOver){
            Player winner = calcFinalScores();
            notifyGameEnd(winner);
        }
    }
    
    /* Special methods related to Scrabble with stuff special tile functions */
    
    /**
     * Method to reverse order of play.
     */
    public void reverseOrder(){
        incrementer = -1;
    }
    /**
     * Method to skip the next player.This is for the special tile I defined.
     */
    public void skipPlayer(){
        /* This deals with the case of activating multiple skips in one turn.
         * Because once one skip has been applied we should only increment by
         * 1 to skip the next player in line.
         * The abs(incrementer) should only be greater than or equal to 2 
         * if at least one skip has been used */
        if(Math.abs(incrementer) >= 2){
            /* preserve direction of play */
            if(incrementer < 0){
                incrementer += -1;
            }
            else{
                incrementer += 1;
            }
        }
        else{
            incrementer *= 2;
        }
    }
    /**
     * Method to remove all tiles within a 3-square radius.
     * @param loc Center location from where boom starts.
     */
    public void boomEffect(Location loc){
        int row = loc.getRow();
        int col = loc.getCol();
        int tempRow = row - BOOM_RADIUS;
        if(tempRow < 0){
            tempRow = 0;
        }
        int tempCol = 0;
        while(tempRow <= row + BOOM_RADIUS && tempRow < board.getRows()){
            while(tempCol < board.getColumns()){
                if(tempCol > col + BOOM_RADIUS) break;
                if(tempCol >= col - BOOM_RADIUS && tempCol <= col + BOOM_RADIUS
                        && tempCol < board.getColumns()){
                    board.clearSquare(new Location(tempRow, tempCol));
                }
                tempCol++;
            }
            tempRow++;
            tempCol = 0;
        }
    }
    /**
     * Method to give current player random free special tile if available.
     */
    public void giveFreebie(){
        SpecialTile st = tilebag.getFreeSpecialTile();
        if(st != null){
            currPlayer.addSpecialTile(st);
        }
        else{
            System.out.println("Out of special tiles");
        }
    }
    /**
     * Method to switch current player's score with tile owner's score.
     * @param tileOwner Owner of switch tile that was just activated.
     */
    public void switchScores(Player tileOwner){
        isSwitchScore = true;
        scoreSwitchPlayers.add(tileOwner);
    }
    /**
     * Method to make multiplicative factor of score calcuation negative,
     * after negative tile is played and activated.
     */
    public void changeMultFactor(){
        multFactor = -1;
    }
}
