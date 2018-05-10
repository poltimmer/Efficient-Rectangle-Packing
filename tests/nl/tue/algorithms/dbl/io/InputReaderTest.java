package nl.tue.algorithms.dbl.io;

import java.io.IOException;
import java.util.Scanner;
import junit.framework.TestCase;

/**
 *
 * @author E.M.A. Arts (1004076)
 * @author K.D. Voorintholt (1005136)
 * 
 * @since 9 MAY 2018
 */
public class InputReaderTest extends TestCase {
    
    public InputReaderTest(String testName) {
        super(testName);
    }
    
    /**
     * Test of getInputMessage method, of class InputReader.
     */
    public void testGetInputMessage() throws IOException {
        System.out.println("getInputMessage");
        String msg = "test true d full string 69";
        Scanner sc = new Scanner(msg);
        InputReader instance = new InputReader(System.in);

        assertEquals("", instance.getInputMessage());

        //parse the input
        while (sc.hasNext()) {
            instance.scanNextString(sc);
        }

        assertEquals(msg + " ", instance.getInputMessage());
    }

    /**
     * Test of scanNextString method, of class InputReader.
     */
    public void testScanNextString() {
        System.out.println("scanNextString");
        Scanner sc = new Scanner("test 23 yes whoa blargh d true");
        InputReader instance = new InputReader(System.in);
        
        assertEquals("test", instance.scanNextString(sc));
        assertEquals("test ", instance.getInputMessage());
        
        assertEquals("23", instance.scanNextString(sc));
        assertEquals("test 23 ", instance.getInputMessage());
        
        assertEquals("yes", instance.scanNextString(sc));
        assertEquals("test 23 yes ", instance.getInputMessage());
        
        assertEquals("whoa", instance.scanNextString(sc));
        assertEquals("test 23 yes whoa ", instance.getInputMessage());
        
        assertEquals("blargh", instance.scanNextString(sc));
        assertEquals("test 23 yes whoa blargh ", instance.getInputMessage());
        
        assertEquals("d", instance.scanNextString(sc));
        assertEquals("test 23 yes whoa blargh d ", instance.getInputMessage());
        
        assertEquals("true", instance.scanNextString(sc));
        assertEquals("test 23 yes whoa blargh d true ", instance.getInputMessage());
    }

    /**
     * Test of scanNextInteger method, of class InputReader.
     */
    public void testScanNextInteger() throws IOException {
        System.out.println("scanNextInteger");
        Scanner sc = new Scanner("8 9 " + Integer.MIN_VALUE + " 0 " + Integer.MAX_VALUE);
        InputReader instance = new InputReader(System.in);
        
        assertEquals(8, instance.scanNextInteger(sc));
        assertEquals("8 ", instance.getInputMessage());
        
        assertEquals(9, instance.scanNextInteger(sc));
        assertEquals("8 9 ", instance.getInputMessage());
        
        assertEquals(Integer.MIN_VALUE, instance.scanNextInteger(sc));
        assertEquals("8 9 " + Integer.MIN_VALUE + " ", instance.getInputMessage());
        
        assertEquals(0, instance.scanNextInteger(sc));
        assertEquals("8 9 " + Integer.MIN_VALUE + " 0 ", instance.getInputMessage());
        
        assertEquals(Integer.MAX_VALUE, instance.scanNextInteger(sc));
        assertEquals("8 9 " + Integer.MIN_VALUE + " 0 " + Integer.MAX_VALUE + " ", instance.getInputMessage());
    }   
}