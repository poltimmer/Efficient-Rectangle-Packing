package nl.tue.algorithms.dbl.common;

import junit.framework.TestCase;

/**
 *
 * @author K.D. Voorintholt (1005136)
 *
 * @since 9 MAY 2018
 */
public class PackListTest extends TestCase {
    /**
     * Test of addRectangle method, of class PackList.
     */
    public void testAddRectangle() throws Exception {
        System.out.println("Test addRectangle");
        PackList testPack = new PackList();
        Rectangle rec1 = new Rectangle(0, 5, 8);
        Rectangle rec2 = new Rectangle(1, 3, 2);

        assertEquals(0, testPack.getNumberOfRectangles());
        testPack.addRectangle(rec1);
        testPack.addRectangle(rec2);
        assertEquals(2, testPack.getNumberOfRectangles());
    }

    /**
     * Test of addRectangleSubclass method, of class PackList.
     */
    public void testAddRectangleSubclass() throws Exception {
    }

    /**
     * Test of getNumberOfRectangles method, of class PackList.
     */
    public void testGetNumberOfRectangles() throws Exception {
        System.out.println("Test getNumberOfRectangles");
        PackList testPack = new PackList();
        Rectangle rec1 = new Rectangle(0, 5, 8);
        Rectangle rec2 = new Rectangle(1, 3, 2);

        assertEquals(0, testPack.getNumberOfRectangles());
        testPack.addRectangle(rec1);
        testPack.addRectangle(rec2);
        assertEquals(2, testPack.getNumberOfRectangles());
        testPack.addRectangle(rec1);
        assertEquals(3, testPack.getNumberOfRectangles());

    }

    /**
     * Test of getRctangles method, of class PackList.
     */
    public void testGetRectangles() throws Exception {
        System.out.println("Test addRectangle");
        PackList testPack = new PackList();
        Rectangle rec1 = new Rectangle(0, 5, 8);
        Rectangle rec2 = new Rectangle(1, 3, 2);
    }

}