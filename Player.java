import java.util.ArrayList;
/**
 * The abstract class Player can be extended to create more specific types of player objects. 
 * 
 * @author Hannah Riggs
 * @version Version 1
 */
public abstract class Player {
    private String team; 
    
    private ArrayList<Piece> pieces;
    
    /**
     * Constructor for objects of class Player
     */
    public Player(String team) {
        this.team = team;
    }
    
    public void setPieces(ArrayList<Piece> pieces) {
      this.pieces = pieces;
    }
    
    public ArrayList<Piece> getPieces() {
      return pieces;
    }
    
    /**
     * Returns true if player is human. Otherwise if player is a computer player, return false. 
     * 
     * @return    true if human, otherwise false
     */
    public abstract boolean isHuman(); 
    
    /**
     * Returns the team of the player.
     * 
     * @return    team of player
     */
    public String getTeam() {
        return team; 
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
    public abstract boolean move(Board board, int xStart, int yStart, int xEnd, int yEnd);
  
    public boolean equals(Player player) {
      return player == this;
    }
}