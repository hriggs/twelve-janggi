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

    /**
     * Constructor for objects of class ComputerPlayer
     */
    public ComputerPlayer(String team) {
        super(team);     
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
       
        // if board not found, return false
        if (board == null) {
            return false;
        }
        
        // get pieces
        ArrayList<Piece> pieces = getPieces();
        
        if (!pieces.isEmpty()) {
          
          System.out.println("pieces list not empty"); // NOT SEEN
          // shuffle pieces
          Collections.shuffle(pieces);
        } else {
          System.out.println("pieces list is empty");
          return false;
        }
        
        Piece piece;
        ArrayList<int[]> validMoves;
        
        // keep trying to find piece until can make valid move
        for (int i = 0; i < pieces.size(); i++) {
          
          // get piece in list
          piece = pieces.get(i);
          
          System.out.println("piece type and team: " + piece.getTeam() + " " + piece.getType());
          
          // get valid moves for piece - currently only returning first possible coordinates for the man
          validMoves = board.getValidMoves(piece.getRow(), piece.getCol(), piece.getType(), "Green");
          
          System.out.println("got valid moves");
          
          // there are valid moves available, shuffle them
          if (!validMoves.isEmpty()) {
            System.out.println("Not empty");
            Collections.shuffle(validMoves);
            
          }
          
          int[] chosenMove;
          
          System.out.println("validMoves.size(): " + validMoves.size());
          
          // try every move until find valid one
          for (int k = 0; k < validMoves.size(); k++) {
            
            // choose valid move - 0 index = row, 1 index = column
            chosenMove = validMoves.get(k);
            
            board.setPieceType(piece.getType());
            
            // board.setPieceType("Man"); // only for testing
            
            // move piece
            boolean moved = board.movePiece(piece.getRow(), piece.getCol(), chosenMove[0], chosenMove[1]); 
            
            System.out.println("ret: " + moved);
            
            if (moved == true) {
              System.out.println("board moved: " + piece.getTeam() + " " + piece.getType() + 
                                 " to row " + chosenMove[0] + " and column " + chosenMove[1]);
              return true;
            }
          }
          
        }
        
        // no valid moves - should never reach this
        return false;  
     }
}