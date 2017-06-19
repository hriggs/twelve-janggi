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
        if (board == null)
        {
            return false;
        }
        
        // get pieces
        ArrayList<Piece> pieces = getPieces();
        
        // shuffle pieces
        Collections.shuffle(pieces);
        
        while (!pieces.isEmpty()) {
        }
        
        // get the first Piece in list
        Piece piece = pieces.get(0);
        
        // get valid moves for piece - TODO returns null right now
        ArrayList<int[]> validMoves = board.getValidMoves(piece.getRow(), piece.getCol(), piece.getType(), "Green"); 

        // shuffle valid moves
        Collections.shuffle(validMoves);
        
                
        // TODO -- loop - keep getting new move until move works
        // remove a failed move from list
        // if all moves failed, then remove this piece from list
        // get next piece and continue with new piece
        
        // choose valid move - 0 index = row, 1 index = column
        int[] chosenMove = validMoves.get(0);
        
        // move piece
        board.movePiece(piece.getRow(), piece.getCol(), chosenMove[0], chosenMove[1]);
        
        // has successfully moved
        return true;  
     }
}