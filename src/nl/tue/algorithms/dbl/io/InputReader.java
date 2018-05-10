
package nl.tue.algorithms.dbl.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import nl.tue.algorithms.dbl.common.Pack;
import nl.tue.algorithms.dbl.common.PackData;
import nl.tue.algorithms.dbl.common.Rectangle;

/**
 * Reads (and parses) the inputs from a given input stream (E.g. System.in)
 * Each instance can only be used once (like Iterators) to read the input
 * stream's inputs
 * 
 * @author Kees Voorintholt (1005136)
 * @author E.M.A. Arts (1004076)
 * @since 10 MAY 2018
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
    
    //stores the input as raw text
    private StringBuilder inputMsg;
    
    //used to throw exceptions and to check whether this reader is closed.
    private boolean closed;
    private boolean packDataRead;
    private boolean rectanglesRead;
    private int numberOfRectangles;
    
    //constructor
    public InputReader(InputStream systemIn) {
        this.sc = new Scanner(systemIn);
        this.expectedSc = new Scanner(EXPECTED_INPUT);
        this.inputMsg = new StringBuilder();
        this.closed = false;
        this.packDataRead = false;
        this.rectanglesRead = false;
    }
    
    /** basic getter */
    public boolean isClosed() {
        return closed;
    }
    
    /** @throws IllegalStateException if this.closed*/
    private void checkNotClosed() throws IllegalStateException {
        if (closed) {
            throw new IllegalStateException("InputReader is closed; no inputs can be read");
        }
    }
    
    /**
     * Method which returns the input.
     * @return string containing the input
     */
    public String getInputMessage() {
        return inputMsg.toString();
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
     * A method that reads and returns a scanner's next string, but also stores
     * the scanned string in a StringBuilder
     * @param sc The scanner that reads over InputStream passed in this
     * instance's constructor
     * 
     * @pre sc != null && sc.hasNext()
     * @modifies this.inputMsg
     * @post this.inputMsg = \old(this.inputMsg) + sc.next() + " "
     * 
     * @return the next integer from the given scanner
     */
    public String scanNextString(Scanner sc) {
        String scanned = sc.next();
        inputMsg.append(scanned);
        inputMsg.append(" ");
        return scanned;
    }
    
    /**
     * A method that reads and returns a scanner's next integer, but also stores
     * the scanned integer in a StringBuilder
     * @param sc The scanner that reads over InputStream passed in this
     * instance's constructor
     * 
     * @pre sc != null && sc.hasNextInt()
     * @modifies this.inputMsg
     * @post this.inputMsg = \old(this.inputMsg) + sc.nextInt() + " "
     * 
     * @throws IOException if there is no next integer to scan.
     * @return the next integer from the given scanner
     */
    public int scanNextInteger(Scanner sc) throws IOException {
        if (!sc.hasNextInt()) {
            throw new IOException("Integer value expected while scanning inputs. Got <" + sc.next() + "> instead");
        }
        
        int scanned = sc.nextInt();
        inputMsg.append(scanned);
        inputMsg.append(" ");
        return scanned;
    }  
    
    /**
     * Reads the PackData from this.systemIn, and returns this PackData
     * Can only be called once.
     * 
     * @pre this.systemIn has the expected format && !this.packDataRead
     *      && !this.closed
     * @modifies this
     * @post this.packDataRead
     * 
     * @return  a PackData instance containing fixed height, rotations allowed,
     *          and number of rectangles
     * 
     * @throws IOException if this.systemIn does NOT have the expected format
     * @throws IllegalStateException if this.packDataRead || this.closed
     */
    public PackData readPackData() throws IOException, IllegalStateException {
        checkNotClosed();
        if (packDataRead) {
            throw new IllegalStateException("packData has already been read by this InputReader");
        }
        
        //data we want to store
        final int containerHeight;
        final boolean canRotate;
      
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

        inputMsg.append("\n");
        
        //skip "number of rectangles" text
        checkValidInputText(scanNextString(sc), expectedSc.next());
        checkValidInputText(scanNextString(sc), expectedSc.next());
        checkValidInputText(scanNextString(sc), expectedSc.next());
        
        //Read number of rectangles-input
        numberOfRectangles = scanNextInteger(sc);
        
        inputMsg.append("\n");
        
        packDataRead = true;
        
        //create the packData
        return new PackData(containerHeight, canRotate, numberOfRectangles);
    }
    
    /**
     * Reads the rectangles from this.systemIn, and stores those in a given pack
     * Must be called after readPackData() and can only be called once.
     * 
     * @pre this.systemIn has the expected format && this.packDataRead && !this.closed && !this.rectanglesRead
     * @modifies pack, this
     * @post    Scanner sc and Scanner expectedSc are closed && this.closed &&
     *          this.rectanglesRead
     * 
     * @throws IOException if this.systemIn does NOT have the expected format
     * @throws IllegalStateException if this.rectanglesRead ||
     *                                  !this.packDataRead || this.closed
     */
    public <P extends Pack> void readRectangles(P pack) throws IOException, IllegalStateException {
        checkNotClosed();
        if (rectanglesRead){
            throw new IllegalStateException("Rectangles have already been read by this InputReader");
        }
        if (!packDataRead) {
            throw new IllegalStateException("packData has not yet been read by this InputReader!");
        }
        
        //Read each rectangle
        for (int i = 0; i < numberOfRectangles; i++){
            final int width = scanNextInteger(sc);
            final int height = scanNextInteger(sc);
            Rectangle rec = new Rectangle(i, width, height);
            //add each rectangle to the pack
            pack.addRectangle(rec);
            
            inputMsg.append("\n");
        }
        
        rectanglesRead = true;
        
        closeReader();
    }
    
    /**
     * Closes this InputReader and prevents it from being used again.
     * Also closes the Scanners associated with this reader.
     * 
     * @modifies this
     * @post this.closed && Scanner sc is closed && Scanner expectedSc is closed
     */
    public void closeReader() {
        //we can only use Scanners once (like Iterators), so close them
        sc.close();
        expectedSc.close();
        closed = true;
    }
}
