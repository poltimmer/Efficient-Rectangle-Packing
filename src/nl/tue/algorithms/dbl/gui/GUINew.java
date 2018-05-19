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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import nl.tue.algorithms.dbl.algorithm.Algorithm;
import nl.tue.algorithms.dbl.algorithm.FirstFitDecreasingHeight;
import nl.tue.algorithms.dbl.common.Pack;
import nl.tue.algorithms.dbl.common.PackData;
import nl.tue.algorithms.dbl.common.RectangleRotatable;
import nl.tue.algorithms.dbl.io.InputReader;
import nl.tue.algorithms.dbl.io.OutputWriter;
import nl.tue.algorithms.dbl.utilities.PackingSolver;

/**
 * This class is never used in the Algorithm code, and is only used to visualize
 * solutions created by the various algorithms.
 * This class is currently a WIP
 * 
 * @author E.M.A. Arts (1004076)
 * @since 19 MAY 2018
 */

public class GUINew extends JFrame {

    public static final String TITLE = "Rectangle Packing";

    /** Default width of the frame */
    public static int RENDER_WIDTH = 1024;
    /** Default height of the frame */
    public static int RENDER_HEIGHT = 640;

    private final JScrollPane scrollPane;
    private final JPanel drawingPane;
    
    private static int IMG_WIDTH = 1024;
    private static int IMG_HEIGHT = 640;
    /** Background color of the scrollPane*/
    private static final Color BACKGROUND_COLOR = Color.ORANGE;
    private final BufferedImage img;
    private final Graphics2D gImg;
    
    public static int SIZE_MODIFIER = 10;
    
    private static final int SCROLL_SPEED = 20;

    /** Possible colors of the rectangles */
    public static final Color[] VALID_RECTANGLE_COLORS =
            {   Color.BLACK,    Color.BLUE,     Color.CYAN,         Color.DARK_GRAY,
                Color.GRAY,     Color.GREEN,    Color.LIGHT_GRAY,   Color.MAGENTA,
                Color.PINK,     Color.RED,      Color.WHITE,        Color.YELLOW };

    private int colorPicker = 0;
    //private final Random randomGen;

    private GUINew() {
        //randomGen = new Random();
        
        this.img = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        this.gImg = img.createGraphics();
        
        gImg.setColor(BACKGROUND_COLOR);
        gImg.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);
        
        setTitle("PackingSolver GUI");
        setVisible(true);
        setMinimumSize(new Dimension(RENDER_WIDTH, RENDER_HEIGHT));     
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        drawingPane = new JPanel() {
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
            
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(img.getWidth(), img.getHeight());
            }
        };
        
        scrollPane = new JScrollPane(drawingPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(SCROLL_SPEED);
        this.add(scrollPane);        
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeFrame(e.getComponent().getSize());
            }
        });   
        
        
        try {
            test();
        } catch (IOException ex) {
            Logger.getLogger(GUINew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Color getRandomColor() {
        //int index = randomGen.nextInt(VALID_RECTANGLE_COLORS.length);
        int index = colorPicker;
        if (colorPicker - 1 < VALID_RECTANGLE_COLORS.length) {
            colorPicker++;
        } else {
            colorPicker = 0;
        }
        return VALID_RECTANGLE_COLORS[index];
    }
    
    public static void main(String[] args) {
        startDefault();        
    }

    private static void startDefault() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUINew();
            }
        });
    }

    /**
     * The method which is the same as PackingSolver.main, except for the last line drawPack(...
     * If the packing solver changes, we have to copy paste it here too.
     */
    private void test() throws IOException {
        InputReader reader = new InputReader(System.in);
        PackData data = reader.readPackData();

        Algorithm algo;
        //an algorithm based on pack data can be chosen here. E.g.:
        /* 
            if (data.canRotate()) {
                algo = new AlgorithmOne(data);
            } else {
                algo = new AlgorithmTwo(data);
            }
        */
        //since we only have 1 algorithm, we have to do this:
        algo = new FirstFitDecreasingHeight(data);
        
        reader.readRectangles(algo.getPack());

        algo.solve();

        OutputWriter.printOutput(System.out, algo.getPack(), reader.getInputMessage());
        
        drawPack(algo.getPack());

    }

    private static void setPixelSize(int width, int height) {
        RENDER_WIDTH = width;
        RENDER_HEIGHT = height;
    }

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
        Color col = getRandomColor();
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
                RENDER_HEIGHT - r.y * SIZE_MODIFIER - r.height * SIZE_MODIFIER,
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
}
