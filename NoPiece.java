import java.awt.*;
/**
 * The class NoPiece, which extends Piece, is used as a placeholder on squares that don't have any visible 
 * pieces, so that no squares have null values on them. Thus, other methods are easier to call since we 
 * don't need to worry about NullPointerExceptions. 
 * 
 * @author Hannah Riggs
 * @version Version 1
 */
public class NoPiece extends Piece {
    /**
     * Constructor for objects of class NoPiece
     */
    public NoPiece() {
    }

    /**
     * Draws the Piece object.
     * 
     * @param  g    the Graphics2D object
     */
    public void draw(Graphics2D g, int x, int y) {
    }

    /**
     * Returns the type of the piece ("none").
     * 
     * @return    type of piece
     */
    public String getType() {
        return "none";
    }

    /**
     * Returns the team that the piece is on ("none").
     * 
     * @return    team of piece
     */
    public String getTeam() {
        return "none"; 
    }
}