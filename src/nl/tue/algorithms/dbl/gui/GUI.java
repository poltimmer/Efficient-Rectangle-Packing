//MOMOTOR_MERGER_IGNORE_FILE
package nl.tue.algorithms.dbl.gui;

import javax.swing.JFrame;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import nl.tue.algorithms.dbl.algorithm.*;
import nl.tue.algorithms.dbl.common.Pack;
import nl.tue.algorithms.dbl.common.RectangleRotatable;
import nl.tue.algorithms.dbl.utilities.PackingSolver;

/**
 * This class is never used in the Algorithm code, and is only used to visualize
 * solutions created by the various algorithms.
 * It uses a JPanel containing a BufferedImage. Rectangles are drawn onto this
 * image. This way, it would be possible to create a file out of them (though
 * this is not done as of now)
 * 
 * @author E.M.A. Arts (1004076)
 * @since 19 MAY 2018
 */
public class GUI extends JFrame {
    //Title of the frame
    public static final String TITLE = "PackingSolver GUI";

    /** Default width of the frame */
    public static int RENDER_WIDTH = 1024;
    /** Default height of the frame */
    public static int RENDER_HEIGHT = 640;

    /** Ensures that the entire image can be viewed if it's bigger than the frame */
    private final JScrollPane scrollPane;
    /** Where the rectangles are drawn */
    private final JPanel drawingPane;
    
    /** Background color of the scrollPane*/
    public static final Color BACKGROUND_COLOR = new Color(247, 247, 217);
    private BufferedImage img;
    private Graphics2D gImg;
    
    /** Rectangles are drawn SIZE_MODIFIER times as big as they actually are */
    public static int SIZE_MODIFIER = 10;
    
    /** Scroll speed of the Horizontal and Vertical Scroll bars of the JScrollPane */
    public static final int SCROLL_SPEED = 20;

    /** Possible colors of the rectangles */
    public static final Color[] VALID_RECTANGLE_COLORS =
            {   Color.BLACK,    Color.BLUE,     Color.CYAN,         Color.DARK_GRAY,
                Color.GRAY,     Color.GREEN,    Color.ORANGE,       Color.MAGENTA,
                Color.PINK,     Color.RED,      Color.YELLOW };
    
    /** Keeps track of which colour to use to draw a rectangle */
    private int colorPicker = 0;
    
    /** Whether to display coverage (in percentages) */
    public static boolean DISPLAY_COVERAGE = true;
    
    /** Whether to display container size */
    public static boolean DISPLAY_CONTAINER_SIZE = true;

    /**
     * Sets up things such as the frame title, frame size, scroll pane, resize
     * listener, and BufferedImage to draw on.
     * @param imageWidth width of the bufferedimage on which will be drawn
     * @param imageHeight height of the bufferedimage on which will be drawn
     * @pre imageWidth > 0 && imageHeight > 0
     */
    private GUI(int imageWidth, int imageHeight) throws IllegalArgumentException {   
        if (imageWidth <= 0 || imageHeight <= 0) {
            throw new IllegalArgumentException("GUI.pre violated: Expected an"
                    + "Image width and Image height greater than 0.Instead got"
                    + "width = <" + imageWidth + ">, height = <" +  imageHeight + ">");
        }

        //Title and dimensions
        setTitle(TITLE);
        setVisible(true);
        setMinimumSize(new Dimension(RENDER_WIDTH, RENDER_HEIGHT));     
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        //setup scrollPane
        scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(SCROLL_SPEED);
        this.add(scrollPane);        
        
        //Resize listener to make the scrollpane have the same size as the frame
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeFrame(e.getComponent().getSize());
            }
        });
        
        setDrawSize(imageWidth, imageHeight);  
        
        //Add the Image to the scrollPane
        drawingPane = initDrawingPane();
        scrollPane.setViewportView(drawingPane);  
    }
    
    /**
     * 
     */
    private void setDrawSize(int imageWidth, int imageHeight) {        
        BufferedImage oldImg = img;

        //setup BufferedImage
        this.img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        this.gImg = img.createGraphics();

        //draw a BG colour
        gImg.setColor(BACKGROUND_COLOR);
        gImg.fillRect(0, 0, imageWidth, imageHeight);
        
        //if there was previous image, redraw it
        if (oldImg != null) {
            gImg.drawImage(oldImg, 0, img.getHeight() - oldImg.getHeight(), oldImg.getWidth(), oldImg.getHeight(), null);
        }        
    }
    
    /**
     * Initializes the drawPane that draws the BufferedImage
     * @param img BufferedImage to draw
     * @pre img != null
     * @return A JPanel that draws img
     */
    private JPanel initDrawingPane() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                // Creating a copy of the Graphics so any reconfiguration we do on
                // it doesn't interfere with what Swing is doing.
                Graphics2D g2 = (Graphics2D) g.create();
                // Drawing the image.
                int w = img.getWidth();
                int h = img.getHeight();
                g2.drawImage(img, 0, 0, w, h, null);

                // At the end, we dispose the Graphics copy we've created
                g2.dispose();
            }
            //auto-resize
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(img.getWidth(), img.getHeight());
            }
        };
    }
    
    /**
     * Gets the next colour from VALID_RECTANGLE_COLORS. This ensures that every
     * valid colour is used equally
     * @return  The next colour from VALID_RECTANGLE_COLORS, or the first one if
     *          the last colour was reached
     */
    private Color getNextColor() {
        if (colorPicker < VALID_RECTANGLE_COLORS.length - 1) {
            colorPicker++;
        } else {
            colorPicker = 0;
        }
        return VALID_RECTANGLE_COLORS[colorPicker];
    }
    
    /**
     * Sets the size of the frame
     * @param width Width of the new size
     * @param height Height of the new size
     * @post The frame has a size of width by height pixels
     */
    private static void setPixelSize(int width, int height) {
        RENDER_WIDTH = width;
        RENDER_HEIGHT = height;
    }

    /**
     * Sets the size of the frame
     * @param d Dimension of the new size
     * @pre d != null
     * @post The frame has size of Dimensions d
     */
    private static void resizeFrame(Dimension d) {
        setPixelSize(d.width, d.height);
    }    
    
    /**
     * Draws specified rotatable rectangle on screen
     * @param r The RectangleRotatable to draw
     * @pre r != null
     * @post The specified rectangle is drawn on the screen with an arbitrary
     * colour
     */
    public void drawRectangle(RectangleRotatable r) {
        Color col = getNextColor();
        if (!r.isRotated()) {
            drawRectangle(r, col);
        } else {
            drawRectangle(new Rectangle(r.x, r.y, r.height, r.width), col);
        }
    }
    
    /**
     * Draws the specified rectangle with a specified colour on screen
     * @param r The Rectangle to draw
     * @param col The colour to draw with
     * @pre r != null && col != null
     * @post The given rectangle is drawn on the screen with the given colour
     */
    private void drawRectangle(Rectangle r, Color col) { 
        gImg.setColor(col);
        //(0,0) is at the bottom-left of the screen
        gImg.fillRect(r.x * SIZE_MODIFIER,
                img.getHeight() - r.y * SIZE_MODIFIER - r.height * SIZE_MODIFIER,
                r.width * SIZE_MODIFIER, r.height * SIZE_MODIFIER);
    }
    
    /**
     * Draws all rectangles from a given Pack on the screen
     * @param p Pack to draw all the rectangles from
     * @pre p != null
     * @post All rectangles from the given pack are drawn on the screen
     */
    public void drawPack(Pack p) {
        for (RectangleRotatable r : p.getOrderedRectangles()) {
            drawRectangle(r);
        }
    }
    
    /**
     * Clears the specified rectangleRotatable off the screen
     * @param r RectangleRotatable to clear
     * @pre r != null
     * @post The specified rectangleRotatable is filled with BACKGROUND_COLOR
     */
    public void clearRectangle(RectangleRotatable r) {
        if (!r.isRotated()) {
            clearRectangle((Rectangle) r);
        } else {
            clearRectangle(new Rectangle(r.x, r.y, r.height, r.width));
        }
    }
    
    /**
     * Clears the specified Rectangle off the screen
     * @param r Rectangle to clear
     * @pre r != null
     * @post The specified rectangle is filled with BACKGROUND_COLOR
     */
    private void clearRectangle(Rectangle r) {
        drawRectangle(r, BACKGROUND_COLOR);
    }
    
    /**
     * Clears the entire screen
     * @post The entire screen is filled with BACKGROUND_COLOR
     */
    public void clearScreen() {
        clearRectangle(new Rectangle(0, 0, RENDER_WIDTH, RENDER_HEIGHT));
    }
    
    /**
     * Main Method
     */
    public static void main(String[] args) {
        startDefault();        
    }

    /**
     * Setting up
     */
    private static void startDefault() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        /* Create and display the frame */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                GUI gui = new GUI(RENDER_WIDTH, RENDER_HEIGHT);
                KeyboardListener keyboardListener = new KeyboardListener(gui);
                gui.addKeyListener(keyboardListener);
                gui.reload();
               
            }
        });
    }
    
    /**
     * Reloads the GUI, asking for new inputs as well.
     */
    public void reload() {   
        try {
            System.out.println("GUI Reloaded, please respecify inputs");
            System.out.print("> ");
            
            PackingSolver solver = new PackingSolver(System.in, FirstFitDecreasingWidth.class);
            //A specific algorithm can be chosen by using something like:
            //new PackingSolver(System.in, BruteForce.class);
            
            //add the rectangles to the solver's Pack and calculate
            //how to place those rectangles in this pack
            solver.readRectangles();
            solver.solve();
            
            //Get the pack
            Pack p = solver.getAlgorithm().getPack();
            
            //Print useful data
            System.out.println("Result using " + solver.getAlgorithm().getClass().getSimpleName() + " is shown on the Screen");
            System.out.println("Input contained " + p.getNumberOfRectangles() + " rectangles: ");
            System.out.println(p.getOrderedRectangles());
            
            //Make the GUI draw this pack
            setDrawSize(p.getContainerWidth()*SIZE_MODIFIER, p.getContainerHeight()*SIZE_MODIFIER);
            System.out.println("New draw size: (" + img.getWidth() + ", " + img.getHeight() + ")");
            this.drawPack(p);
            
            drawingPane.revalidate();
            drawingPane.repaint();
            
            //update title
            this.setTitle(TITLE);
            
            //update coverage percentage
            if (DISPLAY_COVERAGE) {
                appendTitle(" | Coverage : " + p.getCoveragePercentage() + "%");
            }
            
            //update container sizes
            if (DISPLAY_CONTAINER_SIZE) {
                appendTitle(" | Size : " + solver.getAlgorithm().getContainerWidth() + " * " + solver.getAlgorithm().getContainerHeight() + "");
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Appends a given string to this Frame's title
     * @param str The string to append to the title
     */
    private void appendTitle(String str) {
        this.setTitle(getTitle() + str);
    }
}