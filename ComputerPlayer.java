//import java.util.Random;
import java.util.ArrayList;
import java.util.*;
/**
 * The ComputerPlayer class is used for players controlled by the computer. 
 * 
 * @author Hannah Riggs
 * @version Version 1
 */
public class ComputerPlayer extends Player {
  
  // 1 == random strategy
  private int strategy;

    /**
     * Constructor for objects of class ComputerPlayer
     */
    public ComputerPlayer(String team, int strategy) {
        super(team);     
        this.strategy = strategy;
    }


    /**
     * Returns false as this is a computer player.  
     * 
     * @return    false
     */
    @Override
    public boolean isHuman() {
        return false; 
    }

     public boolean randomMove(Board board, int xStart, int yStart, int xEnd, int yEnd, ArrayList<int[]> greenKingMoves) {
       
        // if board not found, return false
        if (board == null) {
            return false;
        }
        
        // get pieces
        ArrayList<Piece> pieces = getPieces();
        
        if (!pieces.isEmpty()) {
          
          //System.out.println("pieces list not empty"); // NOT SEEN
          // shuffle pieces
          Collections.shuffle(pieces);
        } else {
          //System.out.println("pieces list is empty");
          return false;
        }
        
        Piece piece;
        ArrayList<int[]> validMoves;
        
        // keep trying to find piece until can make valid move
        for (int i = 0; i < pieces.size(); i++) {
          
          // get piece in list
          piece = pieces.get(i);
          
          //System.out.println("piece type and team: " + piece.getTeam() + " " + piece.getType());
          
          // thinking about moving king and passed in green king moves
          if (piece.getType().equals("King") && greenKingMoves != null) {
            validMoves = greenKingMoves;
          } else {
            // get valid moves for piece 
            validMoves = board.getValidMoves(piece, false);
          }
          
          // there are valid moves available, shuffle them
          if (!validMoves.isEmpty()) {
            System.out.println("Not empty");
            Collections.shuffle(validMoves);
            
          }
          
          int[] chosenMove;
          
          //System.out.println("validMoves.size(): " + validMoves.size());
          
          // try every move until find valid one
          for (int k = 0; k < validMoves.size(); k++) {
            
            // choose valid move - 0 index = row, 1 index = column
            chosenMove = validMoves.get(k);
            
            board.setPieceType(piece.getType());
            
            // move piece
            boolean moved = board.movePiece(piece.getRow(), piece.getCol(), chosenMove[0], chosenMove[1]); 
            
            //System.out.println("ret: " + moved);
            
            if (moved == true) {
              //System.out.println("board moved: " + piece.getTeam() + " " + piece.getType() + 
                                 //" to row " + chosenMove[0] + " and column " + chosenMove[1]);
              return true;
            }
          }
          
        }
        
        // no valid moves - should never reach this
        return false;  
     }
     
     public boolean protectKingMove(Board board, int xStart, int yStart, int xEnd, int yEnd) {
       
       System.out.println("in protect king move");
       
        // if board not found, return false
        if (board == null) {
            return false;
        }
        
        // get pieces
        ArrayList<Piece> pieces = getPieces();
        
        Piece piece;
        ArrayList<int[]> validMoves = new ArrayList<int[]>();
        int[] chosenMove;
        
        ArrayList<int[]> greenKingMoves = new ArrayList<int[]>();
        
        // my king's row and column
        int kingRow = -1;
        int kingCol = -1;
        
        // look for piece that can take king
        for (int i = 0; i < pieces.size(); i++) {
          
          // get piece in list
          piece = pieces.get(i);
          
          // get valid moves for piece 
          validMoves = board.getValidMoves(piece, false);
          
          // store king's moves
          if (piece.getType().equals("King")) {
            greenKingMoves = validMoves;
            kingRow = piece.getRow();
            kingCol = piece.getCol();
          }
          
          for (int j = 0; j < validMoves.size(); j++) {
            
            chosenMove = validMoves.get(j);
            
            if (piece.getType().equals("King")) {
              //System.out.println("looking at king's moves");
              //System.out.println("validMoves size for KING: " + validMoves.size());
              //System.out.println("row: " + chosenMove[0] + " column: " + chosenMove[1]);
            }
            
            if (board.getPieceType(chosenMove[0], chosenMove[1]).equals("King")) {
              board.movePiece(piece.getRow(), piece.getCol(), chosenMove[0], chosenMove[1]);
              return true;
            }
          }
        }
                  
        // opponent's king could not be captured, so...

        // get list of red pieces
        ArrayList<Piece> redPieces = board.getRedPieces();
        
        // store coordinates of piece that could take king
        int enemyRow = -1;
        int enemyCol = -1;
        
        System.out.println("number of redPieces: " + redPieces.size());
        System.out.println("king row: " + kingRow + " col: " + kingCol);
        
        // check if king can be taken this turn and
        // do not move where green king can be captured next turn
        for (int i = 0; i < redPieces.size(); i++) {
          // get red piece in list
          piece = redPieces.get(i);
          
          //System.out.println("LOOK HEREEEEEEEEEEEEEEE: " + piece.getTeam() + " " + piece.getType());
          
          // get valid moves for red piece 
          validMoves = board.getValidMoves(piece, true);
          
          // iterate over all possible moves for red piece
          for (int j = 0; j < validMoves.size(); j++) {
            
            chosenMove = validMoves.get(j);
            
            //System.out.println("move row: " + chosenMove[0] + " col: " + chosenMove[1]);
            
            // check if this piece can take king where king is right now
            if (chosenMove[0] == kingRow && chosenMove[1] == kingCol) {
              System.out.println("KING CAN BE CAPTURED NEXT TURN by " + piece.getTeam() + " " + piece.getType());
              
              // save current location of enemy
              enemyRow = piece.getRow();
              enemyCol = piece.getCol();
            }
            
            // iterate over all possible king moves and remove those that would allow king to be captured next turn
            for (int k = 0; k < greenKingMoves.size(); k++) {
              if (chosenMove[0] == greenKingMoves.get(k)[0] && chosenMove[1] == greenKingMoves.get(k)[1]) {
                System.out.println("REMOVED from king's move row: " + chosenMove[0] + " " + chosenMove[1]);
                greenKingMoves.remove(k);
              }
            }

          }
        }
        
        // if king can be taken where it is right now, try to take that piece
        if (enemyRow != -1) {
          
          System.out.println("IN SAVE KING LOOP");
          System.out.println("enemyRow: " + enemyRow + " enemyCol: " + enemyCol);
          
          // iterate over all green pieces
          for (int i = 0; i < pieces.size(); i++) {
          
            // get piece in list
            piece = pieces.get(i);
          
            // thinking about moving king and passed in green king moves
            if (piece.getType().equals("King") && greenKingMoves != null) {
              System.out.println("using greenKingMoves");
              validMoves = greenKingMoves;
            } else {
              // get valid moves for piece 
              validMoves = board.getValidMoves(piece, false);
            }
          
            // try every move until find valid one
            for (int k = 0; k < validMoves.size(); k++) {
            
              // choose valid move - 0 index = row, 1 index = column
              chosenMove = validMoves.get(k);
              
              // check if this move takes attacking piece
              if (chosenMove[0] == enemyRow && chosenMove[1] == enemyCol) {
                System.out.println("TAKE ATTACKING PIECE");
                board.setPieceType(piece.getType());
                board.movePiece(piece.getRow(), piece.getCol(), chosenMove[0], chosenMove[1]);
                return true;
              }
            }
          }
        }
        
        // TODO - piece could not be safely taken -- move king out of the way
        
        // make a random move
        return randomMove(board, xStart, yStart, xEnd, yEnd, greenKingMoves);
        
     }
     
    /**
     * Allows the player to make a move using starting and ending coordinates. 
     * If successful, return true. Otherwise, return false. 
     * 
     * @param  colStart    the starting column
     * @param  rowStart    the starting row
     * @param  colEnd    the ending column
     * @param  rowEnd    the ending row
     * @return    true if successful, otherwise false
     */
    @Override
    public boolean move(Board board, int xStart, int yStart, int xEnd, int yEnd) {
      if (strategy == 1) {
        return randomMove(board, xStart, yStart, xEnd, yEnd, null);
      } else if (strategy == 2) {
        return protectKingMove(board, xStart, yStart, xEnd, yEnd); 
      }
      
      return false;
    }
}