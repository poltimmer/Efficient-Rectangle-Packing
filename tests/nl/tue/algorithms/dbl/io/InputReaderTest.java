package nl.tue.algorithms.dbl.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import junit.framework.TestCase;
import nl.tue.algorithms.dbl.common.PackData;
import nl.tue.algorithms.dbl.common.PackList;
import nl.tue.algorithms.dbl.common.Rectangle;

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
    public void testGetInputMessage() {
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
        Scanner sc = new Scanner("8 9 " + Integer.MIN_VALUE + " 0 " + Integer.MAX_VALUE + " f");
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
        
        
        try {
            instance.scanNextInteger(sc);
            fail("instance.scanNextInteger(sc) should have thrown an IOException!");
        } catch (IOException ex) {
            //input msg should be unchanged
            assertEquals("8 9 " + Integer.MIN_VALUE + " 0 " + Integer.MAX_VALUE + " ", instance.getInputMessage());
        }
    }  
    
            
        
    /**
    * Test of readPackData method, of class InputReader.
    */
   public void testReadPackData() throws IOException {
        System.out.println("readPackData");

        InputStream input = new ByteArrayInputStream("".getBytes());
        InputReader instance = new InputReader(input);

        //close the reader
        instance.closeReader();
        assertEquals(instance.isClosed(), true);
        
        //Test Exception throwing when instance is closed
        try {
             instance.readPackData();
             fail("instance.readPackData() should've thrown an IllegalStateException"
                     + " since it is already closed!");
        } catch (IllegalStateException e) {
            System.out.println("instance.readPackData() correctly threw an IllegalStateException");
        }
        
        //'simulate' System.in using a fake inputStream
        String msg = "container height: fixed 18\n" +
                 "rotations allowed: no\n" +
                 "number of rectangles: 3\n" +
                 "any data here doesn't matter";
       
        input = new ByteArrayInputStream(msg.getBytes());
        instance = new InputReader(input);
        PackData data = instance.readPackData();
            
        //Test actual reading
        assertEquals(data.hasFixedHeight(), true);
        assertEquals(data.getContainerHeight(), 18);
        assertEquals(data.canRotate(), false);
        assertEquals(data.getNumberOfRectangles(), 3);
        
        //Test excpetion throwing when attempting to packData read twice
        try {
            instance.readPackData();
            fail("instance.readPackData() should've thrown an IllegalStateException"
                     + "since it has already read packData!");
        } catch (IllegalStateException e) {
            System.out.println("instance.readPackData() correctly threw an IllegalStateException");
        }
        
        //Test the inputMessage after reading
        String expectedMsg = "container height: fixed 18 \n" +
                 "rotations allowed: no \n" +
                 "number of rectangles: 3 \n";
        assertEquals(instance.getInputMessage(), expectedMsg);
   }   
   
   /**
    * Test of readRectangles method, of class InputReader.
    */
   public void testReadRectangles() throws IOException {
        System.out.println("readRectangles");
        String msg = "container height: fixed 18\n" +
                "rotations allowed: no\n" +
                "number of rectangles: 3\n" +
                "8 9\n" +
                "3 9\n" +
                "11 3";
        PackData data = null;
        
        //test closing the InputReader
        InputStream input = new ByteArrayInputStream(msg.getBytes());
        InputReader instance = new InputReader(input);

        data = instance.readPackData();

        instance.closeReader();
        assertEquals(instance.isClosed(), true);
        try {
            instance.readRectangles(new PackList(data));
            fail("instance.readRectangles() should've thrown an IllegalStateException"
                    + " since it is already closed!");
        } catch (IllegalStateException e) {
            System.out.println("instance.readRectangles() correctly threw an IllegalStateException");
        }
       
        //'simulate' System.in using a fake inputStream
        input = new ByteArrayInputStream(msg.getBytes());
        instance = new InputReader(input);
        
        //Test reading Rectangles before reading PackData
        try {
            instance.readRectangles(new PackList(data));
            fail("instance.readRectangles() should've thrown an IllegalStateException"
                     + "since it has not yet read packData!");
        } catch (IllegalStateException e) {
            System.out.println("instance.readRectangles() correctly threw an IllegalStateException");
        }
        
        //'simulate' System.in using a fake inputStream
        input = new ByteArrayInputStream(msg.getBytes());
        instance = new InputReader(input);
        data = instance.readPackData();
        
        PackList pack = new PackList(data);
        instance.readRectangles(pack);

        Set<Rectangle> expRectangles = new HashSet<>();
        expRectangles.add(new Rectangle(0, 8, 9));
        expRectangles.add(new Rectangle(1, 3, 9));
        expRectangles.add(new Rectangle(2, 11, 3));
        
        List<Rectangle> recs = pack.getRectangles();
        //Test reading rectangles
        for (Rectangle rec : recs) {
            boolean matchFound = false;
            for (Rectangle expRec : expRectangles) {
                //if the rectangles are equal
                if (rec.getID() == expRec.getID() &&
                        rec.getHeight() == expRec.getHeight() &&
                        rec.getWidth() == expRec.getWidth()) {
                    //Remove the expected rectangle from the expected list
                    expRectangles.remove(expRec);
                    matchFound = true;
                    break;
                }
            }
            //Check for rectangles that are in the Pack but not the input
            if (!matchFound) {
                fail("Pack contained unexpected Rectangle(" + rec.getID() + ", "
                        + rec.getWidth() + ", " + rec.getHeight() + ")");
            }
        }
        //The expected list is empty if all expected rectangles were in the Pack
        assertEquals(expRectangles.isEmpty(), true);
        
        
        //Test exception throwing when attempting to read twice
        try {
            instance.readRectangles(pack);
            fail("instance.readRectangles() should've thrown an IllegalStateException"
                     + "since it has already read packData!");
        } catch (IllegalStateException e) {
            System.out.println("instance.readRectangles() correctly threw an IllegalStateException");
        }
        
        //Test read message
        String expectedMsg = "container height: fixed 18 \n" +
                "rotations allowed: no \n" +
                "number of rectangles: 3 \n" +
                "8 9 \n" +
                "3 9 \n" +
                "11 3 \n";
        assertEquals(instance.getInputMessage(), expectedMsg);
   }
    
}