package edu.cmu.cs.cs214.hw4.core;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
/**
 * Class to implement a standard scrabble board configuration.
 * @author Kathleen
 *
 */
public class StandardBoard implements ScrabbleBoard {
    private static final int STANDARD_ROWS = 15;
    private static final int STANDARD_COLS = 15;
    private static final Location CENTER = 
            new Location(STANDARD_ROWS/2,STANDARD_COLS/2);
    
    /* Placement of special squares. Hardcoded under the assumption that
     * the standard Scrabble board is very unlikely to change. */
    private static final Location[] TRIPLE_WORD = {new Location(0,0),
        new Location(0,7), new Location(0,14), new Location(7,0),
        new Location(7,14), new Location(14,0), new Location(14,7),
        new Location(14,14)};
    private static final Location[] DOUBLE_WORD = {new Location(1,1),
        new Location(2,2), new Location(3,3), new Location(4,4),
        new Location(1,13), new Location(2,12), new Location(3,11),
        new Location(4,10), new Location(13,1), new Location(12,2),
        new Location(11,3), new Location(10,4), new Location(10,10),
        new Location(11,11), new Location(12,12), new Location(13,13)};
    private static final Location[] DOUBLE_LETTER = {new Location(0,3),
        new Location(0,11), new Location(2,6), new Location(2,8),
        new Location(6,2), new Location(6,6), new Location(6,8),
        new Location(6,12), new Location(7,3), new Location(7,11),
        new Location(8,2), new Location(8,6), new Location(8,8), 
        new Location(8,12), new Location(11,0), new Location(11,7),
        new Location(11,14), new Location(12,6), new Location(12,8),
        new Location(14,3), new Location(14,11), new Location(3,0),
        new Location(3,14)};
    private static final Location[] TRIPLE_LETTER = {new Location(1,5),
        new Location(1,9), new Location(5,1), new Location(5,5),
        new Location(5,9), new Location(5,13), new Location(9,1),
        new Location(9,5), new Location(9,9), new Location(9,13),
        new Location(13,5), new Location(13,9)};
    
    private List<List<Square>> board;
    private Dictionary dict;
    private List<List<Square>> currentWords;
    private List<List<Square>> newWords;
    private boolean usedCenter;
    
    /**
     * Constructor to set up standard board.
     * @param d Dictionary used by game.
     */
    public StandardBoard(Dictionary d){
        dict = d;
        currentWords = new LinkedList<List<Square>>();
        newWords = new LinkedList<List<Square>>();
        usedCenter = false; //this is here to make sure first play uses center
        
        board = new ArrayList<List<Square>>();
        List<Location> tripleWordList = new ArrayList<Location>();
        List<Location> doubleWordList = new ArrayList<Location>();
        List<Location> tripleLetterList = new ArrayList<Location>();
        List<Location> doubleLetterList = new ArrayList<Location>();
        
        for(int i = 0; i < TRIPLE_WORD.length; i++){
            tripleWordList.add(TRIPLE_WORD[i]);
        }
        for(int i = 0; i < DOUBLE_WORD.length; i++){
            doubleWordList.add(DOUBLE_WORD[i]);
        }
        for(int i = 0; i < TRIPLE_LETTER.length; i++){
            tripleLetterList.add(TRIPLE_LETTER[i]);
        }
        for(int i = 0; i < DOUBLE_LETTER.length; i++){
            doubleLetterList.add(DOUBLE_LETTER[i]);
        }
        for(int i = 0; i < STANDARD_ROWS; i++){
            List<Square> row = new ArrayList<Square>();
            for(int j = 0; j < STANDARD_COLS; j++){
                Square s;
                Location loc = new Location(i,j);
                if(tripleWordList.contains(loc)){
                    s = new TripleWordSquare(loc);
                }
                else if(doubleWordList.contains(loc)){
                    s = new DoubleWordSquare(loc);
                }
                else if(tripleLetterList.contains(loc)){
                    s = new TripleLetterSquare(loc);
                }
                else if(doubleLetterList.contains(loc)){
                    s = new DoubleLetterSquare(loc);
                }
                else{
                    s = new StandardSquare(loc);
                }
                row.add(s);
            }
            board.add(row);
        }
    }
    @Override
    public Square getSquare(Location loc){
        int row = loc.getRow();
        int col = loc.getCol();
        /* check indicated location is in-bounds of board */
        if(row < 0 || row >= STANDARD_ROWS || col < 0 || col >= STANDARD_COLS){
            return null;
        }
        return board.get(row).get(col);
    }

    /**
     * Method to test validity in the horizontal direction of a single tile.
     * This is also used when a word is formed by placing tiles parallel to an
     * existing word, in order to get the new words that will be formed in the
     * perpendicular direction.
     * @param tileLoc Location of place tile.
     * @return True if tile is placed in a valid way, false otherwise.
     */
    private boolean checkOneTileHorizontal(Location tileLoc){
        List<Square> word = new LinkedList<Square>();
        boolean seenTile = false, isIntersect = false;
        int row = tileLoc.getRow(), tempRow = row, tempCol = 0;
        while(tempCol < STANDARD_COLS){
            Location loc = new Location(tempRow, tempCol);
            Square s = getSquare(loc);
            if(loc.equals(tileLoc)){
                seenTile = true;
            }
            if(s.getLetterTile() != null){
                word.add(s);
            }
            if(s.getLetterTile() == null && !seenTile){
                word.clear();
            }
            else if(s.getLetterTile() == null && seenTile){
                break;
            }
            tempCol++;
        }
        if(word.size() > 1){
            isIntersect = true;
            newWords.add(word);
        }
        return isIntersect;
    }
    /**
     * Method to test validity in the vertical direction of a single tile.
     * This is also used when parallel words are formed to get the new words
     * spanning the two parallel words.
     * @param tileLoc Location of place tile.
     * @return True if tile is placed in a valid way, false otherwise.
     */
    private boolean checkOneTileVertical(Location tileLoc){
        List<Square> word = new LinkedList<Square>();
        boolean seenTile = false, isIntersect = false;
        int col = tileLoc.getCol(), tempRow = 0, tempCol = col;
        while(tempRow < STANDARD_ROWS){
            Location loc = new Location(tempRow, tempCol);
            Square s = getSquare(loc);
            if(loc.equals(tileLoc)){
                seenTile = true;
            }
            if(s.getLetterTile() == null && !seenTile){
                word.clear();
            } 
            else if(s.getLetterTile() == null && seenTile){
                break;
            }
            else if(s.getLetterTile() != null){
                word.add(s);
            }
            tempRow++;
        }
        if(word.size() > 1){
            isIntersect = true;
            newWords.add(word);
        }
        return isIntersect;
    }
    /**
     * Method to check validity of one tile placement.
     * @param tileLoc Location of placed tile.
     * @return True if valid placement of tile, false otherwise.
     */
    private boolean checkOneTilePlacement(Location tileLoc){
        return checkOneTileHorizontal(tileLoc) || 
                checkOneTileVertical(tileLoc); 
    }
    /**
     * Method to check validity of a horizontal word.
     * @param placements Map of letter tile to location of wanted placements.
     * @param startLoc Location where word starts.
     * @param endLoc Location where word ends.
     * @return True if valid placement of tiles, false otherwise.
     */
    private boolean checkHorizontalWord(List<TilePlacement> placements,
            Location startLoc, Location endLoc){
        List<Location> locs = getPlacementLocs(placements);
        List<Square> word = new LinkedList<Square>();
        int parWords = 0,  row = startLoc.getRow();
        int tempRow = row, tempCol = 0;
        boolean isIntersect = false, isPastEnd = false, isBeforeStart = true;
        while(tempCol < STANDARD_COLS){
            Location l = new Location(tempRow, tempCol);
            Square s = getSquare(l);
            if(l.equals(startLoc)){
                isBeforeStart = false;
            }
            else if(l.equals(endLoc)){
                isPastEnd = true;
            }
            if(s.getLetterTile() == null && !locs.contains(l)){
                if(isBeforeStart) word.clear();
                else if(isPastEnd) break;
                else if(!isPastEnd) return false;
            }
            else{
                word.add(s);
                if(!locs.contains(l)) isIntersect = true;
                else{
                    if(checkOneTileVertical(l)) parWords++;
                }
            }
            tempCol++;
        }
        /* main word formed by the placed tiles must be first to ensure
         * correct scoring with special squares */
        newWords.add(0,word);
        return isIntersect || (parWords <= placements.size() && parWords > 0);
    }
    /**
     * Method to check validity of a vertical word.
     * @param placements Map of letter tile to location of wanted placements.
     * @param startLoc Location where word starts.
     * @param endLoc Location where word ends.
     * @return True if valid placement of tiles, false otherwise.
     */
    private boolean checkVerticalWord(List<TilePlacement> placements,
            Location startLoc, Location endLoc){
        List<Location> locs = getPlacementLocs(placements);
        List<Square> word = new LinkedList<Square>();
        int parWords = 0, col = startLoc.getCol();
        int tempRow = 0, tempCol = col;
        boolean isIntersect = false, isPastEnd = false, isBeforeStart = true;
        while(tempRow < STANDARD_ROWS){ 
            Location l = new Location(tempRow, tempCol);
            Square s = getSquare(l);
            if(l.equals(startLoc)){
                isBeforeStart = false;
            }
            else if(l.equals(endLoc)){ 
                isPastEnd = true;
            }
            if(s.getLetterTile() == null && !locs.contains(l)){
                if(isBeforeStart) word.clear();
                else if(isPastEnd) break;
                else if(!isPastEnd) return false; 
            }
            else{
                word.add(s);
                if(!locs.contains(l)) isIntersect = true;
                else{
                    if(checkOneTileHorizontal(l)) parWords++;
                }
            }
            tempRow++;
        }
        /* main word formed by the placed tiles must be first to ensure
         * correct scoring with special squares */
        newWords.add(0,word);
        return isIntersect || (parWords <= placements.size() && parWords > 0);
    }
    
    /**
     * Method to get a list of locations used in intended move.
     * @param placements List of letter tiles and locations.
     * @return List of locations that the letter tiles will be placed.
     */
    private List<Location> getPlacementLocs(List<TilePlacement> placements){
        List<Location> locs = new LinkedList<Location>();
        for(TilePlacement tp : placements){
            locs.add(tp.getLocation());
        }
        return locs;
    }
    /**
     * Method to check validity of word placement onto board. This checks for
     * colinearity and then depending on whether the placement is
     * horizontal, vertical, or a single tile, further checks are done.
     * @param placements List of letter tile and wanted location of placement.
     * These placements have already been verified to be in valid locations
     * (i.e. the intended squares were all empty). 
     * @return True if valid placement, false otherwise.
     */
    private boolean isValidTilePlacement(List<TilePlacement> placements){
        boolean isValid = true, isHorizontal = false, isVertical = false;
        int counter = 0, currRow, currCol, startRow = 0, startCol = 0;
        Location startLoc = null, endLoc;
        for(TilePlacement tp : placements){
            Location loc = tp.getLocation();
            currRow = loc.getRow();
            currCol = loc.getCol();
            if(counter == 0){
                startLoc = loc;
                startRow = currRow;
                startCol = currCol;
            }
            /* make an assumption about direction based on second location
             * since this direction should be preserved. */
            else if(counter == 1){
                if(startRow == currRow) isHorizontal = true;
                else if(startCol == currCol) isVertical = true;
                else return false;
            }
            /* check if all placements are colinear */
            if(counter > 1){
                if(isHorizontal && startRow != currRow){
                    return false;
                }
                else if(isVertical && startCol != currCol){
                    return false;
                }
            }
            counter++;
        }
        if(placements.size() == 1){
            isValid = checkOneTilePlacement(startLoc); 
        }
        else{
            if(isHorizontal){
                /* sort map of moves by column location because subsequent
                 * validation requires ordered map */
                SortedMove sm = new SortedMove(placements, true);
                List<TilePlacement> newPlacements = sm.getOrderedPlacements();
                startLoc = sm.getStartLoc();
                endLoc = sm.getEndLoc();
                isValid = checkHorizontalWord(newPlacements, startLoc, endLoc);
                
            }
            else if(isVertical){
                /* sort map of moves by row location because subsequent
                 * validation requires ordered map */
                SortedMove sm = new SortedMove(placements, false);
                List<TilePlacement> newPlacements = sm.getOrderedPlacements();
                startLoc = sm.getStartLoc();
                endLoc = sm.getEndLoc();
                isValid = checkVerticalWord(newPlacements, startLoc, endLoc);
            }
        }
        return isValid;
    }
    @Override
    public boolean isValidBoard(List<TilePlacement> placements){ 
        String wordStr = "";  
        SortedMove sortedMove;
        /* first word played is checked differently because there are no 
         * other words on the board at the time so we shouldn't check that
         * it uses existing tiles */
        if(isEmpty()){ 
            List<Square> firstWordSqs = new LinkedList<Square>();
            boolean isHorizontal = false, isVertical = false;
            int counter = 0, startRow = 0, startCol = 0, endRow, endCol;
            /* check locations don't already contain letter tiles */
            boolean isValidLocations = addLetters(placements);
            if(!isValidLocations) return false;
            
            /* check if all placements are colinear */
            for(TilePlacement tp : placements){
                Location loc = tp.getLocation();
                int row = loc.getRow();
                int col = loc.getCol();
                firstWordSqs.add(getSquare(loc));
                if(counter == 0){
                    startRow = row;
                    startCol = col;
                }
                else if(counter == 1){       
                    if(startRow == row) isHorizontal = true;
                    else if(startCol == col) isVertical = true;
                    else{
                        removeLetters(placements);
                        return false; 
                    }
                }
                else{
                    if(isHorizontal && startRow != row){
                        removeLetters(placements);
                        return false;
                    }
                    else if(isVertical && startCol != col){
                        removeLetters(placements);
                        return false;
                    }
                }
                /* first word starts on center */
                if(loc.equals(CENTER)) usedCenter = true;
                counter++;
            }
            if(!usedCenter){
                removeLetters(placements);
                return false;
            }
            /* check if tiles form real word */
            sortedMove = new SortedMove(placements, isHorizontal);
            for(TilePlacement tp : sortedMove.getOrderedPlacements()){
                LetterTile lt = tp.getLetterTile();
                wordStr += lt.getLetter(); 
            }
            if(!dict.isValidWord(wordStr.toLowerCase())){
                removeLetters(placements);
                return false;
            }
            /* check there are no gaps between letters */
            startRow = sortedMove.getStartLoc().getRow();
            startCol = sortedMove.getStartLoc().getCol();
            endRow = sortedMove.getEndLoc().getRow();
            endCol = sortedMove.getEndLoc().getCol();
            if(!(Math.abs(startRow - endRow) < counter && 
                    Math.abs(startCol-endCol) < counter)){
                removeLetters(placements);
                return false;
            }
            newWords.add(firstWordSqs);
        }
        /* all words played on nonempty board checked here */
        else{
            newWords.clear();
            /* check locations don't already contain letter tiles */
            boolean isValidLocations = addLetters(placements);
            if(!isValidLocations) return false;
            boolean isValidPlacement = isValidTilePlacement(placements);
            if(!isValidPlacement){
                removeLetters(placements);
                return false;
            }
            else{
                for(List<Square> word : newWords){
                    for(Square s : word){
                        LetterTile l = s.getLetterTile();
                        wordStr += l.getLetter();
                    }
                    if(!dict.isValidWord(wordStr)){
                        newWords.clear();
                        removeLetters(placements);
                        return false;
                    }
                    else{
                        wordStr = "";
                    }
                }
            } 
        }
        return true;
    }
    @Override
    public boolean addLetters(List<TilePlacement> letterPlacements){
        List<TilePlacement> placed = new LinkedList<TilePlacement>();
        for(TilePlacement tp : letterPlacements){
            Location loc = tp.getLocation();
            LetterTile tile = tp.getLetterTile();
            Square s = getSquare(loc);
            if(s == null){
                System.out.println("Invalid location");
                return false;
            }
            if(s.getLetterTile() == null){
                s.placeLetterTile(tile); 
                placed.add(tp);
            }
            else{
                removeLetters(placed);
                return false;
            }
        }
        return true;
    }
    @Override
    public void removeLetters(List<TilePlacement> badPlacements){
        for(TilePlacement tp : badPlacements){
            Location loc = tp.getLocation();
            Square s = getSquare(loc);
            s.removeLetterTile();
        }
    }
    @Override
    public boolean addSpecialTile(SpecialTile st, Location loc){
        Square s = getSquare(loc);
        /* Location not on the board */
        if(s == null) return false;
        if(s.getLetterTile() != null) return false;
        SpecialTile stOnSquare = s.getSpecialTile();
        /* valid to place a tile on a square that has a special tile that
         * has been activated already */
        if(stOnSquare != null && stOnSquare.getIsActive()){
            return false;
        }
        s.placeSpecialTile(st);
        return true;
    }
    @Override
    public List<List<Square>> getNewWords(){
        newWords.removeAll(currentWords);
        List<List<Square>> newWordsToReturn = new LinkedList<List<Square>>();
        /* deep copy newWords so we can clear newWords before returning */
        for(List<Square> ls : newWords){
            List<Square> word = new LinkedList<Square>();
            for(Square s : ls){
                word.add(s);
            }
            newWordsToReturn.add(word);
        }
        currentWords.addAll(newWordsToReturn);
        newWords.clear();
        return newWordsToReturn;
    }
    @Override
    public boolean isEmpty(){
        for(List<Square> row : board){
            for(Square s : row){
                if(s.getLetterTile() != null){
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public int getRows(){
        return STANDARD_ROWS;
    }
    @Override
    public int getColumns(){
        return STANDARD_COLS;
    }
    @Override
    public void clearSquare(Location loc){
        Square s = getSquare(loc);
        LetterTile t = s.getLetterTile();
        if(t != null){
            s.removeLetterTile();
            s.removeSpecialTile();
        }
    }
    @Override
    public Location getCenterLocation(){
        return CENTER;
    }
}