/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.tue.algorithms.dbl.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import junit.framework.TestCase;
import nl.tue.algorithms.dbl.common.Pack;

/**
 *
 * @author E.M.A. Arts (1004076)
 * 
 * @since 4 MAY 2018
 */
public class InputReaderTest extends TestCase {
    
    public InputReaderTest(String testName) {
        super(testName);
    }

    public void t() {
        String data = "Hello, World!\r\n";
        InputStream stdin = System.in;
        try {
          System.setIn(new ByteArrayInputStream(data.getBytes()));
          Scanner scanner = new Scanner(System.in);
          System.out.println(scanner.nextLine());
        } finally {
          System.setIn(stdin);
        }
    }
    
    /**
     * Test of getInputMessage method, of class InputReader.
     */
    public void testGetInputMessage() {
        System.out.println("getInputMessage");
        InputReader instance = new InputReader(System.in);
        String expResult = "";
        String result = instance.getInputMessage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of readInput method, of class InputReader.
     */
    public void testReadInput() throws Exception {
        System.out.println("readInput");
        Pack pack = null;
        InputReader instance = new InputReader(System.in);
        Pack expResult = null;
        Pack result = instance.readInput(pack);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkValidInputText method, of class InputReader.
     */
    public void testCheckValidInputText() throws Exception {
        System.out.println("checkValidInputText");
        String realStr = "";
        String expStr = "";
        InputReader instance = new InputReader(System.in);
        instance.checkValidInputText(realStr, expStr);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
    public void testScanNextInteger() {
        System.out.println("scanNextInteger");
        Scanner sc = new Scanner("8 9 " + Integer.MIN_VALUE + " 0 " + Integer.MAX_VALUE);
        InputReader instance = new InputReader(System.in);
        
        assertEquals(8, instance.scanNextInteger(sc));
        assertEquals("8 ", instance.getInputMessage());
        
        assertEquals(9, instance.scanNextInteger(sc));
        assertEquals("8 9 ", instance.getInputMessage());
        
        assertEquals(Integer.MIN_VALUE, instance.scanNextInteger(sc));
        assertEquals("8 9 -2147483648 ", instance.getInputMessage());
        
        assertEquals(0, instance.scanNextInteger(sc));
        assertEquals("8 9 -2147483648 0 ", instance.getInputMessage());
        
        assertEquals(Integer.MAX_VALUE, instance.scanNextInteger(sc));
        assertEquals("8 9 -2147483648 0 2147483647 ", instance.getInputMessage());
    }   
}
