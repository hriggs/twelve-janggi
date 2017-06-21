import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;
/**
 * The Game class creates the Board object, draws it, and adds a mouse listener to the board. 
 * It also allows the players to take turns playing the game. 
 * 
 * @author Hannah Riggs
 * @version Version 1
 */
public class Game extends JPanel {
    private Board board; 
    
    // if player initially pressed valid square
    private boolean validPress; 
    
    private Player redPlayer;
    private Player greenPlayer;
    private Player currentPlayer;
    
    private static final long serialVersionUID = 2L; // to supress serializable class warning

    /**
     * Constructor for objects of class Game
     */
    public Game() {
        board = new Board();
        validPress = false;
    }

    /**
     * Draws the board.  
     * 
     * @param  g    Graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D artist = (Graphics2D) g;

        board.draw(artist);
    }

    /**
     * Adds a mouse listener to the board, so that moves can be made by the human players.
     */
    public void addMouseListener() {
        this.addMouseListener(new MouseListener()  {
                int xStart;
                int yStart;
                int xEnd;
                int yEnd; 
                boolean pieceNotSelected = true;
                boolean targetNotSelected = true;

                // when mouse pressed
                public void mousePressed(MouseEvent e) {

                  // disallow mouse pressed events if a side has won
                  if (board.isGameOver()) {
                    repaint();
                    return;
                  }   

                    // set selected piece, so that dragging will be visible and of correct type
                    if (pieceNotSelected) {    
                        board.clearSelectedSquares();
                        
                        // get starting x and y coordinates of mouse
                        xStart = e.getX();
                        yStart = e.getY();
                        
                        if (board.isInBounds(yStart/150, xStart/150)) {
                          
                          // do not let player move opponent's piece
                          if (!currentPlayer.getTeam().equals(board.getPieceTeam(yStart/150, xStart/150))) {
                            return;
                          }
                          
                          validPress = true;
                          
                          board.setSelected(xStart, yStart);
                          board.selectSquare(xStart, yStart);
                        
                          pieceNotSelected = false;
                          targetNotSelected = true;
                          board.setPieceType(board.getPieceType(yStart/150, xStart/150));
                          board.setPieceTeam(board.getPieceTeam(yStart/150, xStart/150));
                        }
                        

                    }
                    repaint();
                }

                // when mouse released
                public void mouseReleased(MouseEvent e) {
                  // disallow mouse released events if a side has won
                  if (board.isGameOver()) {
                    return;
                  }
                  
                    // stop piece from dragging 
                    board.clearSelected(); 
                    
                    // remove square highlight
                    board.clearSelectedSquares();
                    
                    pieceNotSelected = true;
                    validPress = false;
                    repaint();
                    
                    // get ending x and y coordinates of mouse
                    if (targetNotSelected) {
                        xEnd = e.getX();
                        yEnd = e.getY();
                        targetNotSelected = false;
                        
                        // the player has not moved yet
                        boolean hasMoved = false;
                        hasMoved = currentPlayer.move(board, xStart, yStart, xEnd, yEnd);
                        
                        repaint();
                        if (hasMoved) {
                          
                          if (board.isGameOver()) {
                            // display winner
                            displayWinner();
                            return;
                          }
                          
                          // switch to next player's turn
                          currentPlayer = getOpponent(currentPlayer);
                          
                          
                          if (!currentPlayer.isHuman()) {
                                try{ Thread.sleep(350); } catch(Exception ex) {}
                                
                                board.setPieceTeam("top"); // set the team to green/top
                                
                                board.clearSelectedSquares();
                                hasMoved = currentPlayer.move(board, 0,0,0,0);
                                
                                if (board.isGameOver()) {
                                  // display winner
                                  displayWinner();
                                }
                                
                                currentPlayer = getOpponent(currentPlayer);
                           }
                        
                        }
                    }
                }

                // must override
                public void mouseClicked(MouseEvent e) {
                }

                // must override
                public void mouseEntered(MouseEvent e) {
                }

                // must override
                public void mouseExited(MouseEvent e) {
                }
            }); 
    }

    /**
     * Adds a mouse mostion listener to the board. 
     */
    public void addMouseMotionListener() {
        this.addMouseMotionListener(new MouseMotionListener() {
                // must override
                public void mouseMoved(MouseEvent e) {
                }

                // must override
                public void mouseDragged(MouseEvent e) {
                  if (validPress) {
                    board.setSelected(e.getX(), e.getY());
                    repaint(); 
                  }
                }
            }); 
    }
    
        
    /**
     * Display the winner of the game is a window. 
     */
    private void displayWinner() {
      
          String winner = board.getWinner();
      
          // create message 
          String message = "The " + winner + " player has won!"; 
          
          // create icon
          ImageIcon icon;
          if (winner.equals("red")) {
            icon = new ImageIcon("images/red-king.png", "red kign image");
          } else {
            icon = new ImageIcon("images/green-king.png", "green king image");
          }

          // display
          JOptionPane.showMessageDialog(null, message, "The " + winner + " player has won!", 1, icon);
    }
    
    /**
     * Set the two players in the game.
     * 
     * @param  redPlayer    the red (bottom) player 
     * @param  greenPlayer  the green (top) player
     */
    public void setPlayers(Player redPlayer, Player greenPlayer) {
      // set players
      this.redPlayer = redPlayer;
      this.greenPlayer = greenPlayer;
      
       if (!greenPlayer.isHuman()) {
         // get the green pieces for the computer player
         greenPlayer.setPieces(board.getGreenPieces());
       }
      
      // allow for random number generation
      Random r = new Random();
      int number = r.nextInt(9);
      
      String message = "Flipping coin... ";
      ImageIcon icon;
      
      if (number < 5) {
        currentPlayer = redPlayer;
        message += "Red player goes first!";
        icon = new ImageIcon("images/red-king.png", "red king image");
      } else {
        currentPlayer = greenPlayer;
        message += "Green player goes first!";
        icon = new ImageIcon("images/green-king.png", "green king image");
        
        if (!greenPlayer.isHuman()) {
          board.setPieceTeam("top");
          currentPlayer.move(board, 0, 0, 0, 0);
          currentPlayer = getOpponent(currentPlayer);
        }
      }

      // display coin flip results
      JOptionPane.showMessageDialog(null, message, "The coin flip", 1, icon);
    }

    /**
     * Reset the game to the starting positions and add mouse listeners to board.
     */
    public void resetGame() {
        board.setPlayers();
        board.clearSelectedSquares();
        board.resetWinner();
        board.coordsInit();
        board.movesFileNewGame();
        
        // add mouse listeners to board
        addMouseListener();
        addMouseMotionListener(); 
        
        repaint(); 
    }

    /**
     * Returns the Player object of the player in the argument.
     * 
     * @param  player    player you are examining
     * @return    Player object of player
     */
    public Player getOpponent(Player player) {
        if (currentPlayer.getTeam().equals("bottom")) {
            return greenPlayer; 
        } else {
            return redPlayer; 
        }
    }
    
    /**
     * Closes the moves file, so information will be saved. 
     */
    public void closeMovesFile() {
      board.closeMovesFile();
    }
}