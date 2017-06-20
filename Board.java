import java.awt.image.BufferedImage; 
import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.io.PrintWriter;
/**
 * The Board class sets up the board with squares and pieces. 
 * It allows pieces to make (only legal) moves. 
 * 
 * @author Hannah Riggs
 * @version Version 1
 */
public class Board {
    BufferedImage redMinisterImg;
    BufferedImage greenMinisterImg;
    BufferedImage redGeneralImg;
    BufferedImage greenGeneralImg;
    BufferedImage redKingImg;
    BufferedImage greenKingImg;
    BufferedImage redManImg;
    BufferedImage greenManImg;
    BufferedImage redLordImg;
    BufferedImage greenLordImg;
    
    private Piece selectedPiece;
    
    private ArrayList<Piece> greenPieces;
    
    private Piece noPiece;
    
    // squares will be held in an array
    private Square[][] board; 
    
    // green spots for captured pieces - change to array later?
    private int greenRow;
    private int greenCol;
    
    // red spots for captured pieces - change to array later?
    private int redRow;
    private int redCol;
    
    // has the game ended yet?
    private boolean gameOver;
    
    // winner of the game
    private String winner;
    
    // keep track of the number of turns that a king has been in the opposite territory
    private int redKingTurns;
    private int greenKingTurns;

    // fields related to selected piece
    private boolean pieceSelected; 
    private String typeSelected; 
    private String teamSelected;

    // x and y coordinates of selected piece
    private int xSelect;
    private int ySelect; 
    
    // for writing moves to file
    PrintWriter writer;
    
    public void coordsInit() {
        // initialize captured pieces starting positions
        greenRow = 0;
        greenCol = 4;
        redRow = 2;
        redCol = 4;
        
        // the kings are not in the opponent's territory
        redKingTurns = 0;
        greenKingTurns = 0;
    }

    /**
     * Constructor for objects of class Board
     */
    public Board() {
      // create file
      try {
        writer = new PrintWriter("moves.txt", "UTF-8");
      } catch (IOException e) {
        System.out.println("Moves file could not be created");
      }
      
        BufferedImage top = null; 
        BufferedImage center = null; 
        BufferedImage bottom = null; 
        BufferedImage stripe = null;
        
        // access images
        try {
            URL url = this.getClass().getResource("images/top.png");
            top = ImageIO.read(url);

            url = this.getClass().getResource("images/center.png");
            center = ImageIO.read(url);

            url = this.getClass().getResource("images/bottom.png");
            bottom = ImageIO.read(url);
            
            url = this.getClass().getResource("images/center-stripe.png");
            stripe = ImageIO.read(url);
        } catch (IOException e) {
          System.out.println("Board images could not be accessed");
        }
        
        // no one has won game yet
        gameOver = false;
        winner = "";
        
        // the kings are not in the opponent's territory
        redKingTurns = 0;
        greenKingTurns = 0;
        
        // initialize captured pieces starting positions
        coordsInit();
        
        // create a board of size 4 by 7
        board = new Square[4][7]; 

        // as a default, squares not corner or center squares
        boolean isTop = false; 
        boolean isBottom = false; 

        BufferedImage image; 

        // add the squares to the board
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 7; col++) {
                // as a default, squares are of type center, not top or bottom types
                image = center; 
                isTop = false;
                isBottom = false; 
              
                if ((row == 0 && col != 3) || (row == 1 && col > 3)) { // top player squares
                  image = top;
                  isTop = true; 
                }
              
                if ((row == 1 || row == 2) && col < 3) { // center squares
                  image = center;
                }
                
                if (col == 3) { // dark stripe in center
                  image = stripe;
                }
              
                if ((row == 3 && col != 3) || (row == 2 && col > 3)) { // bottom player squares
                  image = bottom;
                  isBottom = true; 
                }
                
                  // add a square to the board
                  board[row][col] = new Square(image, row, col, 150, isTop, isBottom);  
            }
        }

        // set the players on the board
        setPlayers(); 
    }

    /**
     * Draws the Board object.
     * 
     * @param  g    the Graphics2D object
     */
    public void draw(Graphics2D g) {
        // draws every square of board
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 7; col++) {
                board[row][col].draw(g); 
            }
        }

        // draw selected piece
        if (pieceSelected) {
          selectedPiece.draw(g, xSelect - 75, ySelect -75);
        }
    }

    /**
     * Returns the team of the piece at a certain row and column.
     * 
     * @param  row   row of the piece
     * @param  col   column of the piece
     * @return    the team of the piece
     */
    public String getTeam(int row, int col) {
        return board[row][col].getPiece().getTeam(); 
    }

    /**
     * Places a piece on the board, so that the square can reference it. 
     * 
     * @param  piece    piece object 
     * @param  row   row of the piece
     * @param  col   column of the piece
     */
    public void place(Piece piece, int row, int col) {
        board[row][col].place(piece); 
    }

    /**
     * Set the players on the board. 
     */
     public void setPlayers() {
       
        redMinisterImg = null;
        greenMinisterImg = null;
        
        redGeneralImg = null;
        greenGeneralImg = null;
        
        redKingImg = null;
        greenKingImg = null;
        
        redManImg = null;
        greenManImg = null;
        
        redLordImg = null;
        greenLordImg = null;

        // access images
        try {
            URL url = this.getClass().getResource("images/red-minister.png");
            redMinisterImg = ImageIO.read(url);
            
            url = this.getClass().getResource("images/green-minister.png");
            greenMinisterImg = ImageIO.read(url);
            
            url = this.getClass().getResource("images/red-general.png");
            redGeneralImg = ImageIO.read(url);
            
            url = this.getClass().getResource("images/green-general.png");
            greenGeneralImg = ImageIO.read(url);
            
            url = this.getClass().getResource("images/red-king.png");
            redKingImg = ImageIO.read(url);
            
            url = this.getClass().getResource("images/green-king.png");
            greenKingImg = ImageIO.read(url);
            
            url = this.getClass().getResource("images/red-man.png");
            redManImg = ImageIO.read(url);
            
            url = this.getClass().getResource("images/green-man.png");
            greenManImg = ImageIO.read(url);
            
            url = this.getClass().getResource("images/red-lord.png");
            redLordImg = ImageIO.read(url);
            
            url = this.getClass().getResource("images/green-lord.png");
            greenLordImg = ImageIO.read(url);
        } catch (IOException e) {
          System.out.println("Piece images could not be accessed");
        }
       
       // create piece objects and add to board
       Piece piece = new Piece(redMinisterImg, "bottom", "Minister", 3, 0);
       board[3][0].place(piece); 
       
       piece = new Piece(greenMinisterImg, "top", "Minister", 0, 2);
       board[0][2].place(piece);
       //greenPieces.add(piece);
       
       piece = new Piece(redGeneralImg, "bottom", "General", 3, 2);
       board[3][2].place(piece); 
       
       piece = new Piece(greenGeneralImg, "top", "General", 0, 0);
       board[0][0].place(piece);
       //greenPieces.add(piece);
       
       piece = new Piece(redKingImg, "bottom", "King", 3, 1);
       board[3][1].place(piece); 
       
       piece = new Piece(greenKingImg, "top", "King", 0, 1);
       board[0][1].place(piece);
       //greenPieces.add(piece);
       
       piece = new Piece(redManImg, "bottom", "Man", 2, 1);
       board[2][1].place(piece); 
       
       piece = new Piece(greenManImg, "top", "Man", 1, 1);
       board[1][1].place(piece);
       //greenPieces.add(piece);
       
       // add "no pieces" to squares
       noPiece = new NoPiece();
       
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 7; col++) {
              // put NoPiece pieces on right side of board
              if (col > 2) {
                board[row][col].place(noPiece);
              }
            }
        }
        
       // put no pieces on center squares of left side of board
       board[1][0].place(noPiece); 
       board[1][2].place(noPiece); 
       board[2][0].place(noPiece); 
       board[2][2].place(noPiece); 
       
       selectedPiece = noPiece;
     }
     
     /**
      * Increased the captured coordinates.
      */
     private void nextCapturedCoords() {
       if (teamSelected.equals("bottom")) { // update bottom/red
         
         if (redCol == 6) {
           redRow = 3;
           redCol = 4;
         } else {
           redCol++;
         }
       } else { // update top/green
         if (greenCol == 6) {
           greenRow = 1;
           greenCol = 4;
         } else {
           greenCol++;
         }
       }
     }
     
     /**
      * Decrease the captured coordinates.
      */
     private void prevCapturedCoords() {
       if (teamSelected.equals("bottom")) { // update bottom/red
         
         if (redCol == 4 && redRow == 3) {
           redRow = 2;
           redCol = 6;
         } else {
           redCol--;
         }
         
       } else { // update top/green
         if (greenCol == 4 && greenRow == 1) {
           greenRow = 0;
           greenCol = 6;
         } else {
           greenCol--;
         }
       }
     }

    /**
     * Allows a piece to be made between starting and ending coordinates. If move can be made, return true. Otherwise, return false. 
     * 
     * @param  rowStart    the starting row
     * @param  colStart    the starting column
     * @param  rowEnd    the ending row
     * @param  colEnd    the ending column
     * @return    true if moved, otherwise false
     */
     public boolean movePiece(int rowStart, int colStart, int rowEnd, int colEnd) { 
       
       System.out.println("Game over at start of movePiece? " + isGameOver());
       
       // if piece on right
       if (colStart > 3) {
         
         // cannot move piece within right side
         if (colEnd >= 3 || colEnd < 0) {
           return false;
         }
         
         Square endTile = board[rowEnd][colEnd];
         
         // ending square must be of own type or of type center and must be empty
         if ((endTile.getType().equals(teamSelected) || endTile.getType().equals("center")) && endTile.getPiece().getType().equals("none")) { 
           
           // move piece
           // store starting piece and reset ending coordinates
           Piece myPiece = board[rowStart][colStart].getPiece(); 
           myPiece.setRow(rowEnd);
           myPiece.setCol(colEnd);
           
           // no piece in original spot
           place(noPiece, rowStart, colStart); 
           
           // place piece in final spot
           place(myPiece, rowEnd, colEnd); 
           
           // reset captured coordinates
           prevCapturedCoords();
           
           // check if king won by being in enemy territory for second turn
           // if var == 1, then that means the first turn in the enemy territory was the previous turn
           if (redKingTurns == 1) {
             gameOver = true;
             winner = "red";
           } else if (greenKingTurns == 1) {
             gameOver = true;
             winner = "green";
           }
           
           // write move in file
           writer.println(teamSelected + " " + typeSelected + 
                          " moved from row " + rowStart + " and col " + colStart + 
                          " to row " + rowEnd + " and col " + colEnd);

           return true;
         } else {
           return false;
         }
       }
       
       if (colEnd == 3) {
         return false;
       }
       
       // can only move to square with no piece or opponent's piece
       if (!board[rowEnd][colEnd].getPiece().getTeam().equals(teamSelected)) {
         
         boolean canMove = false;
         boolean increasedKingCount = false;
         
         int rowSubtract = rowEnd - rowStart;
           
         int rowDif = Math.abs(rowSubtract);
         int colDif = Math.abs(colEnd - colStart);
         
         if (typeSelected.equals("General")) {
           
           // either row or column changes by 1, but not both
           if ((rowDif == 1 && colDif == 0) || (rowDif == 0 && colDif == 1)) {
             System.out.println("general can move here");
             canMove = true;
             //return true;
           }
         } else if (typeSelected.equals("Minister")) {
           
           // both row and column change by 1
           if (rowDif == 1 && colDif == 1) {
             System.out.println("minister can move here");
             canMove = true;
             //return true;
           } 
         } else if (typeSelected.equals("Man")) {
           
           // column stays the same
           if (colDif == 0) {
             
             // red man (bottom)
             if (teamSelected.equals("bottom") && rowSubtract == -1) {
               System.out.println("red man can move here");
               
               if (rowEnd == 0) { // will become feudal lord
                 // change into feudal lord
                 board[rowStart][colStart].getPiece().setImage(redLordImg);
                 board[rowStart][colStart].getPiece().setType("Lord");
               }
               
               canMove = true;
               //return true;
             }
             
             // green man (top)
             if (teamSelected.equals("top") && rowSubtract == 1) {
               System.out.println("green man can move here");
               canMove = true;
               
               if (rowEnd == 3) { // will become feudal lord
                 // change into feudal lord
                 board[rowStart][colStart].getPiece().setImage(greenLordImg);
                 board[rowStart][colStart].getPiece().setType("Lord");
               }
               //return true;
             }
           }
         } else if (typeSelected.equals("King")) {
           
           // valid move
           if ((rowDif == 1 && colDif == 0) || (rowDif == 0 && colDif == 1) || (rowDif == 1 && colDif == 1)) {
             canMove = true;
             
             // check if king moved into the opponent's territory (or continues in enemy territory)
             if (!board[rowEnd][colEnd].getType().equals("center") && !board[rowEnd][colEnd].getType().equals(teamSelected)) {
               
               if (teamSelected.equals("top")) { // top = green
                 
                 System.out.println("green king in enemy territory");
                 
                 greenKingTurns++;
                 increasedKingCount = true;
                 
                 if (greenKingTurns == 2) {
                   gameOver = true;
                   winner = "green";
                 }
                 
               } else { // bottom = red
                 
                 System.out.println("redking in enemy territory");
                 
                 redKingTurns++;
                 increasedKingCount = true;
                 
                 if (redKingTurns == 2) {
                   gameOver = true;
                   winner = "red";
                 }
               }
               
             } else { // king moves out of enemy territory
               if (teamSelected.equals("top")) { // top = green
                 greenKingTurns = 0;
               } else { // bottom = red
                 redKingTurns = 0;
               }
               
             }
             
           }
           
         } else if (typeSelected.equals("Lord")) { // then do feudal lord stuff here
           
           // bottom = red
           if (teamSelected.equals("bottom")) {
             if ((rowDif == 1 && colDif == 0) || (rowDif == 0 && colDif == 1) || (rowSubtract == -1 && colDif == 1)) {
               canMove = true;
             }
           } else {
             if ((rowDif == 1 && colDif == 0) || (rowDif == 0 && colDif == 1) || (rowSubtract == 1 && colDif == 1)) {
               canMove = true;
             }
           }
           
         } else {
           return false;
         }
         
         if (canMove) {
           // store starting piece and reset ending coordinates
           Piece myPiece = board[rowStart][colStart].getPiece(); 
           myPiece.setRow(rowEnd);
           myPiece.setCol(colEnd);
           
           // store opponent's piece 
           Piece opponentPiece = board[rowEnd][colEnd].getPiece(); 
           
           // piece was captured
           if (!opponentPiece.getType().equals("none")) {
             
             // change team to my team
             opponentPiece.setTeam(teamSelected);
           
             // set new coordinates of piece - green = default
             int capturedRow = greenRow;
             int capturedCol = greenCol;
             
             String type = opponentPiece.getType();
             
             if (teamSelected.equals("bottom")) { // bottom = red
               capturedRow = redRow;
               capturedCol = redCol;
               
               if (type.equals("Minister")) {
                 opponentPiece.setImage(redMinisterImg);
               } else if (type.equals("General")) {
                 opponentPiece.setImage(redGeneralImg);
               } else if (type.equals("Man")) {
                 opponentPiece.setImage(redManImg);
               } else if (type.equals("Lord")) {
                 opponentPiece.setImage(redManImg); 
                 opponentPiece.setType("Man");
               } else if (type.equals("King")) {
                 gameOver = true;
                 winner = "red";
               }
             } else {  // top = green
               if (type.equals("Minister")) {
                 opponentPiece.setImage(greenMinisterImg);
               } else if (type.equals("General")) {
                 opponentPiece.setImage(greenGeneralImg);
               } else if (type.equals("Man")) {
                 opponentPiece.setImage(greenManImg);
               } else if (type.equals("Lord")) {
                 opponentPiece.setImage(greenManImg); 
                 opponentPiece.setType("Man");
               } else if (type.equals("King")) {
                 gameOver = true;
                 winner = "green";
               }
             }
             
             // only put piece on right if it is not a king (kings just disappear)
             if (!type.equals("King")) {
               opponentPiece.setRow(capturedRow);
               opponentPiece.setCol(capturedCol); 
               place(opponentPiece, capturedRow, capturedCol);
              
               // update captured coordinates
               nextCapturedCoords();
             }

           }

           // no piece in original spot
           place(noPiece, rowStart, colStart); 
           
           // place piece in final spot
           place(myPiece, rowEnd, colEnd); 
           
              // check if king won by being in enemy territory for second turn
              // if var == 1, then that means the first turn in the enemy territory was the previous turn
              if (redKingTurns == 1 && increasedKingCount == false) {
                gameOver = true;
                winner = "red";
              } else if (greenKingTurns == 1 && increasedKingCount == false) {
                gameOver = true;
                winner = "green";
              }
            
           // write move in file
           writer.println(teamSelected + " " + typeSelected + 
                          " moved from row " + rowStart + " and col " + colStart + 
                          " to row " + rowEnd + " and col " + colEnd);
           
           return true;
         }
         
       }
       
       return false;
     }

    /**
     * Returns true if coordinates are on the board. Otherwise, return false. 
     * 
     * @param  row    the row
     * @param  col    the column
     * @return    true if coordinate in bounds, otherwise false
     */
    public boolean isInBounds(int row, int col) {
        // out of bounds
        if (row < 0 || row > 3 || col < 0 || col > 6) {
            return false;
        }
        
        // in bounds of array
        return true; 
    }

    /**
     * Sets the x and y coordinates of the selected piece and then sets a pieceSelected to true;
     * 
     * @param  x    x-coordinate
     * @param  y    y-coordinate
     */
    public void setSelected(int xSelect, int ySelect)
    {
        this.xSelect = xSelect;
        this.ySelect = ySelect;
        
        if (!pieceSelected) {
            pieceSelected = true; 
            board[ySelect/150][xSelect/150].select();
            selectedPiece = board[ySelect/150][xSelect/150].getPiece();
            System.out.println("slected piece: " + selectedPiece.getType());
        }
    }

    public void selectSquare(int xSelect, int ySelect) {
        board[ySelect/150][xSelect/150].selectSquare();
    }
    
    /**
     * Sets pieceSelected to false; 
     */
    public void clearSelected() {
        for(int row = 0; row < 4; row++)
            for(int col = 0; col < 7; col++)
                board[row][col].deselect();
        pieceSelected = false;
    }

    public void clearSelectedSquares() {
        for(int row = 0; row < 4; row++)
            for(int col = 0; col < 7; col++)
                board[row][col].deselectSquare();
        pieceSelected = false;
    }

    /**
     * Sets the type of the selected piece.
     * 
     * @param  pieceType    type that the piece will be set to
     */
    public void setPieceType(String pieceType) {
        typeSelected = pieceType; 
    }

    /**
     * Sets the team of the selected piece.
     * 
     * @param  pieceTeam    team that the piece will be set to
     */
    public void setPieceTeam(String pieceTeam) {
        teamSelected = pieceTeam; 
    }

    /**
     * Get the team of the piece at the given row and column.
     * 
     * @param  row   row on board
     * @param  col   column on board
     * @return  team of piece
     */
    public String getPieceTeam(int row, int col) {
        return board[row][col].getPiece().getTeam();
    }

    /**
     * Get the type of the piece at the given row and column.
     * 
     * @param  row   row on board
     * @param  col   column on board
     * @return  type of piece
     */
    public String getPieceType(int row, int col) {
        return board[row][col].getPiece().getType();
    }
    
    /**
     * Returns true if the game is over, otherwise false.
     * 
     * @return  true if the game is over, otherwise false
     */
    public boolean isGameOver() {
      return gameOver;
    }
    
    /**
     * Returns the winner of the game.
     * 
     * @return  winner of the game
     */
    public String getWinner() {
      return winner;
    }
    
    /**
     * Resets the winner of the game to an empty string and sets gameOver to false.
     */
    public void resetWinner() {
      winner = "";
      gameOver = false;
    }
    
    /**
     * TODO - has to do with computer player
     */
    public ArrayList<Piece> getGreenPieces() {
      return greenPieces;
    }
    
    /**
     * TODO - has to do with computer player
     */
    public ArrayList<int[]> getValidMoves(int rowStart, int colStart, String pieceType, String pieceTeam) {
      return null;
    }
    
    /**
     * Closes the moves file, so information will be saved.
     */
    public void closeMovesFile() {
      if (writer != null) {
        writer.close();
      }
    }
    
    /**
     * Indicate that a new game has started in the moves file
     */
    public void movesFileNewGame() {
      if (writer != null) {
        writer.println("NEW GAME: \n");
      }
    }
}