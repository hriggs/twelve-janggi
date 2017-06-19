import javax.swing.*;
import java.awt.image.*;
import java.awt.*;
import java.net.URL; 
import javax.imageio.ImageIO;
/**
 * The BackgroundImage class extends the JPanel class, so that images can be added to frames. 
 * BackgroundImage object can paint themselves. 
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BackgroundImage extends JPanel {
    BufferedImage image; 
    
    private static final long serialVersionUID = 1L; // to supress serializable class warning

    /**
     * Constructor for objects of class BackgroundImage
     */
    public BackgroundImage(String imageFile) {

        // access image
        URL url = this.getClass().getResource(imageFile);
        try { 
            image = ImageIO.read(url);
        }
        catch (Exception e) {}
    }

    /**
     * Allows the BackgroundImage to paint itself.
     * 
     * @param  g    Graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}