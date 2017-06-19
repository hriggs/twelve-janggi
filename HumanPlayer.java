/**
 * The HumanPlayer class is used to create players controlled by humans. 
 * 
 * @author Hannah Riggs
 * @version Version 1
 */
public class HumanPlayer extends Player {
    /**
     * Constructor for objects of class HumanPlayer
     */
    public HumanPlayer(String team) {
        super(team); 
    }

    /**
     * Returns true since player is human. 
     * 
     * @return    true
     */
    @Override
    public boolean isHuman() {
        return true; 
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
        if (board.isInBounds(yStart/150, xStart/150) && board.isInBounds(yEnd/150, xEnd/150) 
            && board.getTeam(yStart/150, xStart/150).equals(this.getTeam())) {
           return board.movePiece(yStart/150, xStart/150, yEnd/150, xEnd/150);
        }   
        
        return false;  
    }
}