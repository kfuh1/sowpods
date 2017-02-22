package edu.cmu.cs.cs214.hw4.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.util.LinkedList;
import java.util.List;

import edu.cmu.cs.cs214.hw4.core.GameChangeListener;
import edu.cmu.cs.cs214.hw4.core.LetterTile;
import edu.cmu.cs.cs214.hw4.core.Location;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.ScrabbleBoard;
import edu.cmu.cs.cs214.hw4.core.ScrabbleGame;
import edu.cmu.cs.cs214.hw4.core.SpecialTile;
import edu.cmu.cs.cs214.hw4.core.Square;
import edu.cmu.cs.cs214.hw4.core.TilePlacement;

/**
 * Class for ScrabbleWithStuff GUI.
 * @author Kathleen
 *
 */
public class ScrabblePanel extends JPanel implements GameChangeListener{
    /* generated serial version id */
    private static final long serialVersionUID = 3951022770588841507L;
    private static final int STANDARD_ROWS = 15;
    private static final int STANDARD_COLS = 15;
    private static final int MOVE_ROWS = 3;
    private static final int MOVE_COLS = 2;
    private static final int HGAP = 5;
    private static final int VGAP = 5;
    private static final int FONT_SIZE1 = 14;
    private static final int FONT_SIZE2 = 12;
    private static final int FONT_SIZE3 = 20;
    private static final int FONT_SIZE4 = 11;
    private static final int STD_SIZE = 60;
    
    private final ScrabbleGame game;
    private final JButton[][] displayBoard;
    private List<JButton> letters, specials;
    private JButton passButton, playLTButton, purchaseSTButton, playSTButton,
                    submitMoveButton, exchangeButton, cancelMoveButton;
    private JLabel currPlayerLabel, scoreLabel;
    private JPanel infoPanel, rackPanel, letterRackPanel, specialRackPanel,
                    moveChoicePanel;
    private ScrabbleBoard board;
    private List<Player> players;
    private List<LetterTile> lettersInMove;
    private SpecialTile specialInMove;
    private List<Location> moveLocs;
    private boolean isExchange, isPlayLetters, isPlaySpecial;
    /**
     * Create new object that will deal with displaying Scrabble game and
     * allow interactions between player and game.
     * @param g Scrabble game.
     */
    public ScrabblePanel(ScrabbleGame g){
        game = g;
        game.addGameChangeListener(this);
        
        specialInMove = null;
        
        isExchange = false;
        isPlayLetters = false;
        isPlaySpecial = false;
        
        moveLocs = new LinkedList<Location>();
        lettersInMove = new LinkedList<LetterTile>();
        letters = new LinkedList<JButton>();
        specials = new LinkedList<JButton>();
        
        initInfoPanel();
        initRackPanel();
        initMoveChoicePanel();
        infoPanel.setLayout(new BorderLayout());
        infoPanel.add(currPlayerLabel, BorderLayout.NORTH);
        infoPanel.add(scoreLabel, BorderLayout.SOUTH);
        
        board = game.getBoard();
        displayBoard = new JButton[STANDARD_ROWS][STANDARD_COLS];
        initGUI();
    }
    /**
     * Set up window that displays the game.
     */
    private void initGUI(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        panel.add(moveChoicePanel, BorderLayout.NORTH);
        panel.add(rackPanel, BorderLayout.SOUTH);
        
        setLayout(new BorderLayout());
        add(infoPanel, BorderLayout.NORTH);
        
        add(createBoardPanel(), BorderLayout.WEST);    
        add(panel, BorderLayout.EAST);
        boardChanged(game.getBoard());
    }
    /**
     * Set up panel that displays current player and player scores.
     */
    private void initInfoPanel(){
        String scoreText = "Player scores:   ";
        Player currPlayer = game.getCurrentPlayer();
        players = game.getPlayers();
        
        infoPanel = new JPanel();  
        currPlayerLabel = new JLabel();
        scoreLabel = new JLabel();
        
        currPlayerLabel.setText("Current Player: " + currPlayer.getName());
        
        for(Player p : players){
            scoreText += (p.getName() + ": ");
            scoreText += (p.getScore()); 
            scoreText += "  ";
        }
        scoreLabel.setText(scoreText);
    }
    /**
     * Set up panel that displays current player's letter rack and
     * special tile rack.
     */
    private void initRackPanel(){
        Player currPlayer = game.getCurrentPlayer();

        rackPanel = new JPanel(new BorderLayout());
        rackPanel.setBorder(BorderFactory.createTitledBorder("Racks"));
        List<LetterTile> tiles = currPlayer.getLetters();
        List<SpecialTile> specialTiles = currPlayer.getSpecialTiles();
        letterRackPanel = new JPanel(new GridLayout(1,tiles.size()));
        letterRackPanel.setBorder(BorderFactory.createTitledBorder("Letters"));
        letterRackPanel.setBackground(Color.WHITE);
        specialRackPanel = new JPanel(new GridLayout(1,specialTiles.size()));
        specialRackPanel.setBorder(
                BorderFactory.createTitledBorder("Specials"));
        specialRackPanel.setBackground(Color.WHITE);
        
        rackPanel.add(letterRackPanel, BorderLayout.NORTH);
        rackPanel.add(specialRackPanel, BorderLayout.SOUTH);
        rackPanel.setBackground(Color.WHITE);
        playerChanged(currPlayer);
    }
    /**
     * Toggle move choice buttons once a move has been selected or submitted.
     */
    private void adjustButtons(){
        passButton.setEnabled(!passButton.isEnabled());
        purchaseSTButton.setEnabled(!purchaseSTButton.isEnabled());
        exchangeButton.setEnabled(!exchangeButton.isEnabled());
        playLTButton.setEnabled(!playLTButton.isEnabled());
        playSTButton.setEnabled(!playSTButton.isEnabled());
        submitMoveButton.setEnabled(!submitMoveButton.isEnabled());
        cancelMoveButton.setEnabled(!cancelMoveButton.isEnabled());
    }
    /**
     * Set up panel to display move choice buttons.
     */
    private void initMoveChoicePanel(){
        moveChoicePanel = new JPanel(new GridLayout(MOVE_ROWS,MOVE_COLS,
                HGAP,VGAP));
        moveChoicePanel.setBorder(
                BorderFactory.createTitledBorder("Move Choices"));
        moveChoicePanel.setBackground(Color.WHITE);
        passButton = new JButton("Pass");
        playLTButton = new JButton("Play Letter Tiles");
        purchaseSTButton = new JButton("Buy Special Tile");
        playSTButton = new JButton("Play Special Tile");
        exchangeButton = new JButton("Exchange Tiles");
        submitMoveButton = new JButton("Submit Move");
        cancelMoveButton = new JButton("Cancel Move");
        submitMoveButton.setEnabled(false);
        cancelMoveButton.setEnabled(false);
        
        ActionListener passListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                game.pass();
            }
        };
        ActionListener purchaseListener = new PurchaseListener(game);
        
        ActionListener exchangeListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                lettersInMove.clear();
                isExchange = true;
                boardChanged(game.getBoard());
                playerChanged(game.getCurrentPlayer());
                adjustButtons();
            }
        };
        ActionListener playLTListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                isPlayLetters = true;
                moveLocs.clear();
                lettersInMove.clear();
                boardChanged(game.getBoard());
                playerChanged(game.getCurrentPlayer());
                adjustButtons();
            }
        };
        ActionListener playSTListener = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                specialInMove = null;
                moveLocs.clear();
                isPlaySpecial = true;
                boardChanged(game.getBoard());
                playerChanged(game.getCurrentPlayer());
                adjustButtons();
            }
        };
        ActionListener submitListener = new ActionListener(){
            private boolean success;
            @Override
            public void actionPerformed(ActionEvent e){
                if(isExchange){
                    success = game.exchangeTiles(lettersInMove);
                    if(success){
                        isExchange = false;
                        adjustButtons();     
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Not enough "
                                + "tiles in tilebag to exchange that many");
                    }
                    lettersInMove.clear();
                }
                else if(isPlaySpecial){
                    if(moveLocs.size() == 1 && specialInMove != null){
                        success = game.playSpecialTile(specialInMove,
                                moveLocs.get(0));
                        if(success){
                            isPlaySpecial = false;
                            adjustButtons();
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Invalid "
                                    + "location for special tile placement");
                            specialInMove = null;
                            moveLocs.clear();
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Something went"
                                + "wrong with your selection. Try again.");
                        specialInMove = null;
                        moveLocs.clear();
                    }
                }
                else if(isPlayLetters){
                    List<TilePlacement> letterPlacements =
                            new LinkedList<TilePlacement>();
                    if(moveLocs.size() == lettersInMove.size()){
                        for(int i = 0; i < moveLocs.size(); i++){
                            TilePlacement tp = new TilePlacement(
                                    lettersInMove.get(i),moveLocs.get(i));
                            letterPlacements.add(tp);
                        }
                        success = game.playLetterTiles(letterPlacements);
                        if(success){
                            isPlayLetters = false;
                            adjustButtons();
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Invalid"
                                    + " word formation");
                            moveLocs.clear();
                            lettersInMove.clear();
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Something went"
                                + "wrong with your selection. Try again.");
                        moveLocs.clear();
                        lettersInMove.clear();
                        
                    }
                }
            }
        };
        ActionListener cancelListener = new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent e){
              moveLocs.clear();
              lettersInMove.clear();
              specialInMove = null;
              isPlaySpecial = false;
              isPlayLetters = false;
              isExchange = false;
              passButton.setEnabled(true);
              purchaseSTButton.setEnabled(true);
              exchangeButton.setEnabled(true);
              playLTButton.setEnabled(true);
              playSTButton.setEnabled(true);
              submitMoveButton.setEnabled(false);
              cancelMoveButton.setEnabled(false);
              boardChanged(game.getBoard());
              playerChanged(game.getCurrentPlayer());
          }
        };
        passButton.addActionListener(passListener);
        purchaseSTButton.addActionListener(purchaseListener);
        exchangeButton.addActionListener(exchangeListener);
        submitMoveButton.addActionListener(submitListener);
        playLTButton.addActionListener(playLTListener);
        playSTButton.addActionListener(playSTListener);
        cancelMoveButton.addActionListener(cancelListener);
          
        moveChoicePanel.add(passButton);
        moveChoicePanel.add(playLTButton);
        moveChoicePanel.add(purchaseSTButton);
        moveChoicePanel.add(playSTButton);
        moveChoicePanel.add(exchangeButton);
        moveChoicePanel.add(submitMoveButton);
        moveChoicePanel.add(cancelMoveButton);
    }

    private JPanel createBoardPanel(){
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Scrabble Board"));
        panel.setLayout(new GridLayout(STANDARD_ROWS, STANDARD_COLS));
        for(int i = 0; i < STANDARD_ROWS; i++){
            for(int j = 0; j < STANDARD_COLS; j++){
                Square s = board.getSquare(new Location(i,j));
                displayBoard[i][j] = new JButton();
                displayBoard[i][j].setText(s.getText());
                displayBoard[i][j].addActionListener(new SquareListener(this,
                        i,j));
                displayBoard[i][j].setFont(
                        new Font("Arial", Font.BOLD, FONT_SIZE1));
                panel.add(displayBoard[i][j]);
            }
        }
        return panel;
    }
    /**
     * Method to add special tile to be played on a move. This is called by the
     * special tile listener when a special tile is clicked.
     * @param st Special tile to be played.
     */
    public void addSpecialTileForMove(SpecialTile st){
        if(specialInMove != null){
            JOptionPane.showMessageDialog(null,"You already selected a special"
                    + "tile, select location now");
        }
        else{
            specialInMove = st;
        }
    }
    /**
     * Method to add letter tile to be played on a move. This is called by
     * the letter tile listener.
     * @param lt Letter tile to be played.
     */
    public void addTileForMove(LetterTile lt){
        lettersInMove.add(lt);
    }
    /**
     * Method to add location for place letters move. This is called by the 
     * square listener.
     * @param loc Location to be played.
     */
    public void addLocationForMove(Location loc){
        if(isPlaySpecial && moveLocs.size() >= 1){
            JOptionPane.showMessageDialog(null,"You already selected a "
                    + "location, click submit move");
        }
        else{
            int row = loc.getRow();
            int col = loc.getCol();
            displayBoard[row][col].setBackground(Color.yellow);
            moveLocs.add(loc);
        }
    }
    @Override
    public void gameEnded(Player winner){
        if (winner != null) {
            JOptionPane.showMessageDialog(null, winner.getName() + 
                    " has won!");
        }
        /* shouldn't get here */
        else {
            JOptionPane.showMessageDialog(null,"No winner");
        }
        /* player will have to close the window and manually restart to
         * start a new game.
         */
    }
    @Override
    public void playerChanged(Player p){
        currPlayerLabel.setText("Current Player: " + p.getName());
        List<LetterTile> letterTiles = p.getLetters();
        List<SpecialTile> specialTiles = p.getSpecialTiles();
        players = game.getPlayers();
        String scoreText = "Player scores:   ";
        for(Player player : players){
            scoreText += (player.getName() + ": ");
            scoreText += (player.getScore()); 
            scoreText += "  ";
        }
        scoreLabel.setText(scoreText);
        
        letters.clear();        
        rackPanel.remove(letterRackPanel);  
        letterRackPanel = new JPanel(new GridLayout(1,letterTiles.size()));
        letterRackPanel.setBorder(BorderFactory.createTitledBorder("Letters"));
        letterRackPanel.setBackground(Color.WHITE);
        
        for(int i = 0; i < letterTiles.size(); i++){
            LetterTile lt = letterTiles.get(i);
            JButton tileButton = new JButton();
            letters.add(tileButton);
            tileButton.setText(lt.getLetter() + "," + lt.getWorth());
            tileButton.setPreferredSize(new Dimension(STD_SIZE,STD_SIZE));
            tileButton.setBackground(Color.lightGray);
            tileButton.setForeground(Color.red);
            tileButton.addActionListener(
                    new LetterTileListener(this,lt,tileButton));
            letterRackPanel.add(tileButton);
        }
        rackPanel.add(letterRackPanel, BorderLayout.NORTH);
         
        specials.clear();
        rackPanel.remove(specialRackPanel);
        specialRackPanel = new JPanel(new GridLayout(1,specialTiles.size()));
        specialRackPanel.setBorder(
                BorderFactory.createTitledBorder("Specials"));
        specialRackPanel.setBackground(Color.WHITE);

        for(int i = 0; i < specialTiles.size(); i++){
            SpecialTile st = specialTiles.get(i);
            JButton tileButton = new JButton(st.getName());
            specials.add(tileButton);            
            tileButton.addActionListener(new SpecialTileListener(
                    this,st,tileButton));
            tileButton.setBackground(Color.white);
            tileButton.setForeground(Color.blue);
            specialRackPanel.add(tileButton);
        }
        rackPanel.add(specialRackPanel,BorderLayout.SOUTH);

    }
    @Override
    public void boardChanged(ScrabbleBoard newBoard){
        Player currPlayer = game.getCurrentPlayer();
        String squareText = "";
        boolean isCoveredLT = false, isCoveredST = false;
        /* go through each square on the board and draw it depending on
         * whether there's a letter tile or special tile on it.
         */
        for(int i = 0; i < STANDARD_ROWS; i++){
            for(int j = 0; j < STANDARD_COLS; j++){
                Square s = newBoard.getSquare(new Location(i,j));
                LetterTile lt = s.getLetterTile();
                SpecialTile st = s.getSpecialTile();
                if(lt != null){
                    squareText = (lt.getLetter() + "," + lt.getWorth());
                    isCoveredLT = true;
                }
                if(st != null && currPlayer.equals(st.getOwner())){
                    squareText = st.getName();
                    isCoveredST = true;
                }
                JButton button = displayBoard[i][j];
                
                if(isCoveredLT){
                    button.setBackground(Color.lightGray);
                    button.setForeground(Color.red);
                    button.setFont(new Font("Arial", Font.BOLD, FONT_SIZE2));
                }
                else if(isCoveredST){
                    button.setBackground(Color.white);
                    button.setForeground(Color.blue);
                    button.setFont(new Font("Arial", Font.BOLD, FONT_SIZE4));
                }
                else{
                    squareText = s.getText();
                    button.setBackground(s.getColor());     
                    button.setForeground(Color.black);
                    button.setFont(new Font("Arial", Font.BOLD, FONT_SIZE1));
                    /* this is the best I could do to make the center square
                     * have a star.
                     */
                    if(s.getLocation().equals(newBoard.getCenterLocation())){
                        squareText = "*";
                        button.setBackground(Color.pink);
                        button.setFont(new Font("Arial", Font.BOLD, FONT_SIZE3));
                    }
                }
                button.setText(squareText);
                isCoveredLT = false;
                isCoveredST = false;
            }
        }
    }
}
