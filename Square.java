import java.awt.image.BufferedImage; 
import java.awt.*;
import java.awt.geom.*;
/**
 * The Square class is responsible for Square objects that have an image and can draw themselves. 
 * A Square also has a row, column, length and may be a top, bottom, or center square. 
 * It extends Rectangle. 
 * It also has a reference to the Piece that is on it.
 * 
 * @author Hannah Riggs
 * @version Version 1
 */
public class Square extends Rectangle {
    private BufferedImage image; 
    private boolean selected;
    private boolean squareSelected;
    
    private int row;
    private int col; 
    private int length;
    
    private boolean isTopPlayer;
    private boolean isBottomPlayer; 
    
    private Piece piece; 
    
    private static final long serialVersionUID = 3L; // to supress serializable class warning

    /**
     * Constructor for objects of class Square
     */
    public Square(BufferedImage image, int row, int col, int length, boolean isTopPlayer, boolean isBottomPlayer) {
        this.image = image;
        this.row = row;
        this.col = col;
        this.length = length;
        this.isTopPlayer = isTopPlayer;
        this.isBottomPlayer = isBottomPlayer; 
        selected = false;
        squareSelected = false;
        piece = new NoPiece(); 
    }

    /**
     * Draws the Square object.
     * 
     * @param  g    the Graphics2D object
     */
    public void draw(Graphics2D g) {
        // draw square
        g.drawImage(image, null, col * length, row * length);
        g.setColor(new Color(131, 92, 61));
        float thickness = 2;
        Stroke stroke = g.getStroke();
        // draw rectangle
        if (squareSelected) {
            g.setStroke(new BasicStroke(thickness));
            g.setColor(Color.black);
            g.draw(new Rectangle2D.Double(col*length, row*length, length,length));
            g.setColor(Color.black);
            g.setStroke(stroke);
        } else {
            g.draw(new Rectangle(col*length, row*length, length, length));
        }
        
        // draw piece on square
        if (!selected) {
          piece.draw(g, col * length, row * length);
        }
    }

    /**
     * Place a piece on this square.
     * 
     * @param  piece    the piece to put on square
     */
    public void place(Piece piece) {
        this.piece = piece; 
    }

    /**
     * Returns the type of the piece on the square.
     * 
     * @return    type of piece on square
     */
    /*public String getPieceType() {
        return piece.getType(); 
    }*/

    /**
     * Returns the Piece object on square.
     * 
     * @return    piece object on square
     */
    public Piece getPiece() {
        return piece; 
    }

    /**
     * Returns row of square.
     * 
     * @return    row of square
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns column of square.
     * 
     * @return    col of square
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns true if square belongs to top player, otherwise false.
     * 
     * @return    true if square belongs to top player, otherwise false
     */
    public boolean isTopPlayer() {
        return isTopPlayer; 
    }
    
    /**
     * Returns true if square belongs to bootom player, otherwise false.
     * 
     * @return    true if square belongs to bootom player, otherwise false
     */
    public boolean isBottomPlayer() {
        return isBottomPlayer; 
    }
    
    /**
     * Returns the type of the square. It will either be top, bottom, or center. 
     * 
     * @return    type of square
     */
    public String getType() {
        if (isTopPlayer == true) {
            return "top";
        } else if (isBottomPlayer == true) {
            return "bottom";
        } else {
            return "center";
        }
    }
    
    public void select() { selected = true; }
    public void selectSquare() { squareSelected = true; }
    public void deselectSquare() { squareSelected = false; }
    public void deselect() { selected = false; }
}