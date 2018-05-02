
package nl.tue.algorithms.dbl.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.tue.algorithms.dbl.common.Pack;
import nl.tue.algorithms.dbl.common.PackList;
import nl.tue.algorithms.dbl.common.Rectangle;


/**
 * Reads (and parses) the inputs from System.in
 * Each instance can only be used once (like Iterators) to read System.in's inputs
 * 
 * @author Kees Voorintholt (1005136)
 * @author E.M.A. Arts (1004076)
 * @since 25 APR 2018
 * 
 * TODO: Possibly also look at "fixed" and "free". Right now, if the input does
 * not match "fixed", it is assumed to be "free". Same with "yes" and "no".
 */
public class InputReader {
    //Scannner for the input file
    private final Scanner sc;
    
    //Scanner to keep track of which non-input string is expected
    private final Scanner expectedSc;
    //Expected sequence of non-input strings that the input file is required to have
    private final static String EXPECTED_INPUT = "container height: rotations allowed: number of rectangles:";
    
    public InputReader() throws FileNotFoundException{
        //should be System.in
        this.sc = new Scanner(new File("tests/test.txt"));
        this.expectedSc = new Scanner(EXPECTED_INPUT);
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
    public Pack readInput() throws IOException {
        //data we want to store
        final int containerHeight;
        final boolean canRotate;
        final int numberOfRectangles;        
      
        //skip "container height" text
        checkValidInputText(sc.next(), expectedSc.next());
        checkValidInputText(sc.next(), expectedSc.next());
        
        //Read free/fixed height input
        if (sc.next().equals("fixed")) {
            containerHeight = sc.nextInt();
        } else {
            containerHeight = -1;
        }
        
        //skip "rotations allowed" text
        checkValidInputText(sc.next(), expectedSc.next());
        checkValidInputText(sc.next(), expectedSc.next());
        canRotate = sc.next().equals("yes");
        
        //create the pack
        Pack pack = new PackList(containerHeight, canRotate);
        
        //skip "number of rectangles" text
        checkValidInputText(sc.next(), expectedSc.next());
        checkValidInputText(sc.next(), expectedSc.next());
        checkValidInputText(sc.next(), expectedSc.next());
        
        //Read number of rectangles-input
        numberOfRectangles = sc.nextInt();
        
        //Read each rectangle
        for (int i = 0; i < numberOfRectangles; i++){
            final int width = sc.nextInt();
            final int height = sc.nextInt();
            Rectangle rec = new Rectangle(i, width, height);
            //add each rectangle to the pack
            pack.addRectangle(rec);
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
    
    //TODO: main method to be moved somewhere else
    public static void main(String [ ] args) {
        try {
            InputReader in = new InputReader();
            Pack pack = in.readInput();
            System.out.println(pack);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InputReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException  ex) {
            Logger.getLogger(InputReader.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    
}
