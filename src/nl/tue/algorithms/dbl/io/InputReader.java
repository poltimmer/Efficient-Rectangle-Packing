
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
 * @since 2 MAY 2018
 * 
 * TODO: Possibly also look at "fixed" and "free". Right now, if the input does
 * not match "fixed", it is assumed to be "free". Same with "yes" and "no".
 */
public class InputReader<P extends Pack> {
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
    public P readInput(P pack) throws IOException {
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
        pack.setCanRotate(canRotate);
        pack.setContainerHeight(containerHeight);

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
    
    //TODO: main method to be moved somewhere else
    /*
    public static void main(String [ ] args) {
        try {
            InputReader in = new InputReader();
            Pack pack = in.readInput();
            System.out.println(pack);
            System.out.println(in.getInputMessage());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InputReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException  ex) {
            Logger.getLogger(InputReader.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
*/
    
    
}
