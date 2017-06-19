import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
/**
 * The GameApp class sets up the game by creating the game, GUI, 
 * and allowing the players to choose how they wish to play via menu items. 
 * 
 * @author Hannah Riggs
 * @version Version 1
 */
public class GameApp {
    private JFrame window;
    private Game game; 

    /**
     * Constructor for objects of class GameApp
     */
    public GameApp() {
        game = new Game();
        createGui();
    }
    
    /**
     * Main method that allows the game to start.
     */
    public static void main(String[] args) {
        new GameApp(); 
    }

    /**
     * Create the GUI for this game by creating the menu bar, adding images and adding the board to the window. 
     */
    public void createGui() {
        // create window
        window = new JFrame("Twelve Janggi");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setOpaque(true);

        // create menu bar
        makeMenuBar(); 

        // add game to center
        panel.add(game, BorderLayout.CENTER);

        // add north image
        BackgroundImage imageNorth = new BackgroundImage("images/south.png");
        imageNorth.setPreferredSize(new Dimension(350, 25));
        panel.add(imageNorth, BorderLayout.NORTH);

        // add east image
        BackgroundImage imageEast = new BackgroundImage("images/east.png");
        imageEast.setPreferredSize(new Dimension(100, 605));
        panel.add(imageEast, BorderLayout.EAST);

        // add south image
        BackgroundImage imageSouth = new BackgroundImage("images/south.png");
        imageSouth.setPreferredSize(new Dimension(350, 25));
        panel.add(imageSouth, BorderLayout.SOUTH);

        // add west image
        BackgroundImage imageWest = new BackgroundImage("images/east.png");
        imageWest.setPreferredSize(new Dimension(100, 605));
        panel.add(imageWest, BorderLayout.WEST); 
        
        // set window attributes
        window.setContentPane(panel);
        // set window features
        window.setSize(1253, 699);
        window.setResizable(false);
        window.setVisible(true);
    }

    /**
     * Create the menu bar.
     */
    public void makeMenuBar() {          
        // set colors of menu items
        Color color = new Color(131, 92, 61); 
        Color hoverColor = new Color(186, 145, 112); 

        UIManager.put("MenuBar.background", color);
        UIManager.put("MenuBar.selectionBackground", hoverColor);
        UIManager.put("Menu.selectionBackground", hoverColor);
        UIManager.put("MenuItem.background", color);
        UIManager.put("MenuItem.selectionBackground", hoverColor);

        color = new Color(184,176,162); 
        UIManager.put("MenuItem.foreground", color);

        // create menu bar
        JMenuBar menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);

        // new game
        JMenu gameMenu = new JMenu("New Game");
        menuBar.add(gameMenu);
        gameMenu.setForeground(color);

        // play against human
        JMenuItem humanItem = new JMenuItem("2 Players");
        humanItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) { 
                  
                    // create players
                    Player player1 = new HumanPlayer("bottom");
                    Player player2 = new HumanPlayer("top");

                    // reset game
                    game.resetGame(); 
                    game.setPlayers(player1, player2);
                }
            }); 
        gameMenu.add(humanItem);

        // play against computer as viking
        JMenuItem computer1Item = new JMenuItem("Play as red against computer");
        /*computer1Item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) { 
                    // create players
                    Player player1 = new HumanPlayer("bottom");
                    Player player2 = new ComputerPlayer("top");

                    // reset game
                    game.resetGame(); 
                    game.setPlayers(player1, player2); 
                }
            }); */
        gameMenu.add(computer1Item);

        // help
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        helpMenu.setForeground(color);

        // rules
        JMenuItem rulesItem = new JMenuItem("Rules");
        rulesItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) { 
                    // string with rules
                    String message = 
                      "rules go here!";

                    // create new frame
                    JFrame frame = new JFrame("The Rules of Hnefatafl"); 
                    frame.setLayout(new BorderLayout());

                    // create panels
                    JPanel title = new JPanel(); 
                    JPanel info = new JPanel(); 
                    JPanel image = new JPanel(); 

                    // add label to panel
                    JLabel label = new JLabel("The Rules of Hnefatafl");
                    label.setFont(new Font("Sans serif", Font.BOLD, 30));
                    title.add(label);

                    // add text to panel
                    JTextArea text = new JTextArea(message); 
                    text.setFont(new Font("Sans serif", Font.BOLD, 13));
                    text.setBackground(new Color(238, 238, 238));
                    text.setEditable(false);
                    info.add(text);

                    // add icon to panel
                    //BackgroundImage icon = new BackgroundImage("images/icon.png");
                    //icon.setPreferredSize(new Dimension(50, 50));
                    //image.add(icon);

                    // add panels to frame
                    frame.add(title, BorderLayout.NORTH);
                    frame.add(info, BorderLayout.CENTER);
                    frame.add(image, BorderLayout.SOUTH);

                    // set frame details
                    frame.setSize(730, 500);
                    frame.setResizable(false);
                    frame.setVisible(true);
                }
            }); 
        helpMenu.add(rulesItem);

        // about
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {  
                    // string with about info
                    String message =
                      "About twelve janggi info here!\n";

                    // create new frame
                    JFrame frame = new JFrame("About This Game"); 
                    frame.setLayout(new BorderLayout());

                    // create panels
                    JPanel title = new JPanel(); 
                    JPanel info = new JPanel(); 
                    JPanel image = new JPanel(); 

                    // add label to panel
                    JLabel label = new JLabel("About This Game");
                    label.setFont(new Font("Sans serif", Font.BOLD, 30));
                    title.add(label);

                    // add text to panel
                    JTextArea text = new JTextArea(message); 
                    text.setFont(new Font("Sans serif", Font.BOLD, 13));
                    text.setBackground(new Color(238, 238, 238));
                    text.setEditable(false);
                    info.add(text);

                    // add icon to panel
                    //BackgroundImage icon = new BackgroundImage("images/icon.png");
                    //icon.setPreferredSize(new Dimension(50, 50));
                    //image.add(icon);

                    // add panels to frame
                    frame.add(title, BorderLayout.NORTH);
                    frame.add(info, BorderLayout.CENTER);
                    frame.add(image, BorderLayout.SOUTH);

                    // set frame details
                    frame.setSize(730, 280);
                    frame.setResizable(false);
                    frame.setVisible(true);
                }
            }); 
        helpMenu.add(aboutItem);
        
        // other menu
        JMenu otherMenu = new JMenu("Other");
        otherMenu.setForeground(color);
        menuBar.add(otherMenu);

        // quit
        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {  
                    // close and save moves file
                    game.closeMovesFile();
                  
                    // shut down program
                    System.exit(0); 
                }
            }); 
        otherMenu.add(quitItem);
    }
}
