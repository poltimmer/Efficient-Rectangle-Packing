//BEFORE HANDING IN, COMMENT THIS LINE BELOW
package nl.tue.algorithms.dbl.wholeFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Giant java file which holds all classes used for 2IO90 DBL Algorithms.
 * Only use this file when you want to hand in your project.
 * DO NOT MAKE CHANGES IN THIS FILE, CHANGE THE MAIN JAVA FILE AND REPLACE, IF NEEDED,
 * THE WHOLE CHANGED FILE HERE.
 */

/**
 * @author K.D. Voorintholt (1005136)
 * @author E.M.A. Arts (1004076)
 * @author T.M. Verberk ()
 * @author Koen (1018025)
 * @author Wouter ()
 * @author Pol (1007701)
 * @author Robin ()
 */
public class PackingSolver {
    public static void main(String [ ] args) {
        new Prototype().run();
    }
    
}
    /**
     * PASTE ALL CLASSES WHICH ARE PART OF THE ALGORITHM PACKAGE BELOW
     * MAKE SURE YOU COMMENT THE PACKAGE STATEMENTS
     */
class Prototype {        
        void run() {
            int x = 0; int y = 0;
            PackList rectangles;
            try {
                InputReader r = new InputReader();
                rectangles = r.readInput();
                String s = r.getInputMessage();
                System.out.print(s);
                System.out.println("placement of rectangles");
                for(int i = 0; i < rectangles.getRectanglesList().size(); i++) {
                    if (rectangles.canRotate()) {
                        System.out.print("no ");
                    }
                    System.out.println(x + " " + y);
                    x += rectangles.getRectanglesList().get(i).getWidth();
                }
            } catch(Exception e) {
                System.out.println("Exception thrown");
            }
        }
    }
    /**
     * END OF CLASSES WHICH ARE PART OF THE ALGORITHM PACKAGE
     */

    /**
     * PASTE ALL CLASSES WHICH ARE PART OF THE COMMON PACKAGE BELOW
     * MAKE SURE YOU COMMENT THE PACKAGE STATEMENTS
     */
abstract class Pack {
    protected final int containerHeight;
    protected final boolean canRotate;
    
    public Pack(int containerHeight, boolean canRotate) {
        this.containerHeight = containerHeight;
        this.canRotate = canRotate;
    }
    
    /**
     * Adds a rectangle to be packed to the pack
     * @param rec Rectangle to add
     * 
     * @pre rec != null     
     * @modifies this
     * @post    rectangles.contains(rec) &&
     *          numberOfRectangles = \old(numberOfRectangles) + 1
     * 
     * @throws NullPointerException if the precondition is violated
     */
    public abstract void addRectangle(Rectangle rec) throws NullPointerException;
    
    /** basic query */
    public int getContainerHeight() {
        return containerHeight;
    }
    
    /** basic query */
    public boolean canRotate() {
        return canRotate;
    }
    
    /** basic query */
    public abstract int getNumberOfRectangles();
    
    /** basic query */
    public boolean hasFixedHeight() {
        return containerHeight >= 0;
    }
}
    
class PackList extends Pack {
    
    private final List<Rectangle> rectangles;
    
    public PackList(int containerHeight, boolean canRotate) {
        super(containerHeight, canRotate);
        this.rectangles = new ArrayList<>();
    }
    
    @Override
    public void addRectangle(Rectangle rec) throws NullPointerException {
        if (rec == null) {
            throw new NullPointerException("Pack.addRectangle.pre violated: rec == null");
        }
        rectangles.add(rec);
    }
    
    /** basic query */
    @Override
    public int getNumberOfRectangles() {
        return rectangles.size();
    }
    
    /** basic query */
    public List<Rectangle> getRectanglesList() {
        return rectangles;
    }
    
    
    @Override
    public String toString(){
        return rectangles.toString();
    }
}
    
    
class Rectangle {
    private final int ID;
    private final int width;
    private final int height;
    
    public Rectangle(int ID, int width, int height) {
        this.ID = ID;
        this.width = width;
        this.height = height;
    }
    
    /** basic getter */
    public int getID() {
        return this.ID;
    }
    
    /** basic getter */
    public int getWidth() {
        return this.width;
    }
    
    /** basic getter */
    public int getHeight() {
        return this.height;
    }
    
    @Override
    public String toString(){
        return "" + width + " " + height + "";
    }
    
    //TODO: possible extensions include rotations, positions, etc.
}
    
    /**
     * END OF CLASSES WHICH ARE PART OF THE COMMON PACKAGE
     */


    /**
     * PASTE ALL CLASSES WHICH ARE PART OF THE GUI PACKAGE BELOW
     * MAKE SURE YOU COMMENT THE PACKAGE STATEMENTS
     */

    /**
     * END OF CLASSES WHICH ARE PART OF THE GUI PACKAGE
     */


    /**
     * PASTE ALL CLASSES WHICH ARE PART OF THE IO PACKAGE BELOW
     * MAKE SURE YOU COMMENT THE PACKAGE STATEMENTS
     */
class InputReader {
    //Scannner for the input file
    private final Scanner sc;
    
    //Scanner to keep track of which non-input string is expected
    private final Scanner expectedSc;
    //Expected sequence of non-input strings that the input file is required to have
    private final static String EXPECTED_INPUT = "container height: rotations allowed: number of rectangles:";
    
    private StringBuilder inputMsg;
    
    public InputReader() throws FileNotFoundException {
        this.sc = new Scanner(System.in);
        this.expectedSc = new Scanner(EXPECTED_INPUT);
        this.inputMsg = new StringBuilder();
    }
    
    /**
     * 
     * @return 
     */
    public String getInputMessage() {
        return inputMsg.toString();
    }
    
    /**
     * reads the input from the input file, stores it in variables, and prints those
     * @pre File has the expected format
     * @post Scanner sc and Scanner expectedSc are closed
     * 
     * @return  a Pack containing containerHeight, all the rectangles, the number
     *          of rectangles, and whether rotations are allowed
     * 
     * @throws IOException if the precondition is violated
     */
    public PackList readInput() throws IOException {
        //data we want to store
        final int containerHeight;
        final boolean canRotate;
        final int numberOfRectangles;
      
        //skip "container height" text
        checkValidInputText(scanNextString(sc), expectedSc.next());
        checkValidInputText(scanNextString(sc), expectedSc.next());
        
        //Read free/fixed height input
        if (scanNextString(sc).equals("fixed")) {
            containerHeight = scanNextInteger(sc);
        } else {
            containerHeight = -1;
        }
        
        inputMsg.append("\n");
        
        //skip "rotations allowed" text
        checkValidInputText(scanNextString(sc), expectedSc.next());
        checkValidInputText(scanNextString(sc), expectedSc.next());
        canRotate = scanNextString(sc).equals("yes");
        
        //create the pack
        PackList pack = new PackList(containerHeight, canRotate);
        
        inputMsg.append("\n");
        
        //skip "number of rectangles" text
        checkValidInputText(scanNextString(sc), expectedSc.next());
        checkValidInputText(scanNextString(sc), expectedSc.next());
        checkValidInputText(scanNextString(sc), expectedSc.next());
        
        //Read number of rectangles-input
        numberOfRectangles = scanNextInteger(sc);
        
        inputMsg.append("\n");
        
        //Read each rectangle
        for (int i = 0; i < numberOfRectangles; i++){
            final int width = scanNextInteger(sc);
            final int height = scanNextInteger(sc);
            Rectangle rec = new Rectangle(i, width, height);
            //add each rectangle to the pack
            pack.addRectangle(rec);
            
            inputMsg.append("\n");
        }
        
        //we can only use Scanners once (like Iterators), so close them
        sc.close();
        expectedSc.close();
        
        //return the pack
        return pack;
    }
    
    /**
     * Checks to see if the expected String matches the provided string, and
     * throws an IOException if not the case.
     * @param realStr The provided string
     * @param expStr The expected string
     * 
     * @pre realStr != null && expStr != null
     * @throws IOException if expStr != realStr
     */
    public void checkValidInputText(String realStr, String expStr) throws IOException {
        if (!realStr.equals(expStr)) {
            throw new IOException(
                    "Expected <" + expStr + "> but got <" + realStr + "> while parsing System.in");
        }
    }
    
    /**
     * 
     * @param  
     */
    public String scanNextString(Scanner sc) {
        String scanned = sc.next();
        inputMsg.append(scanned);
        inputMsg.append(" ");
        return scanned;
    }
    
    /**
     * 
     * @param  
     */
    public int scanNextInteger(Scanner sc) {
        int scanned = sc.nextInt();
        inputMsg.append(scanned);
        inputMsg.append(" ");
        return scanned;
    }
    
        
    }
    
    

    /**
     * END OF CLASSES WHICH ARE PART OF THE IO PACKAGE
     */
