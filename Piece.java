import java.awt.image.BufferedImage; 
import java.awt.*;

/**
 * The Piece class is an abstract class that can be extended to create more specific piece types.
 * A Piece has an image and can draw itself.
 * 
 * @author Hannah Riggs
 * @version Version 1
 */
public class Piece {
    private BufferedImage image; 
    private String team;
    private String type;
    private int row;
    private int col;

    /**
     * Constructor for objects of class Piece
     */
    public Piece(BufferedImage image, String team, String type, int row, int col) {
        this.image = image; 
        this.team = team;
        this.type = type;
        this.row = row;
        this.col = col;
    }

    /**
     * Default constructor of class Piece
     */
    public Piece() {
    }

    /**
     * Draws the Piece object.
     * 
     * @param  g    the Graphics2D object
     */
    public void draw(Graphics2D g, int x, int y) {
        g.drawImage(image, null, x, y);
    }

    /**
     * Returns the type of the piece.
     * 
     * @return    type of piece
     */
    public String getType() {
      return type;
    }
    
    /**
     * Sets the type of the piece.
     * 
     * @param  newType    set the piece to type to newType
     */
    public void setType(String newType) {
      type = newType;
    }
    
    /**
     * Returns the team that the piece is on.
     * 
     * @return    team of piece
     */
    public String getTeam() {
      return team;
    }

    /**
     * Sets the team of the piece.
     * 
     * @param  newTeam    set the team to newTeam
     */
    public void setTeam(String newTeam) {
      team = newTeam;
    }
    
    /**
     * Returns the row of the piece.
     * 
     * @return    row
     */
    public int getRow() {
      return row;
    }
    
    /**
     * Sets the row of the piece.
     * 
     * @param  newRow    new row of piece
     */
    public void setRow(int newRow) {
      row = newRow;
    }
    
    /**
     * Returns the column of the piece.
     * 
     * @return    column
     */
    public int getCol() {
      return col;
    }
    
    /**
     * Sets the column of the piece.
     * 
     * @param  newCol    new column of piece
     */
    public void setCol(int newCol) {
      col = newCol;
    }
    
    /**
     * Sets the image of the piece.
     * 
     * @param  newImage    new image of piece
     */
    public void setImage(BufferedImage newImage) {
      image = newImage;
    }
}