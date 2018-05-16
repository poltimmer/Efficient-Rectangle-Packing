package nl.tue.algorithms.dbl.common;

import junit.framework.TestCase;

import java.util.PriorityQueue;

/**
 *
 * @author K.D. Voorintholt (1005136)
 *
 * @since 9 MAY 2018
 */


public class PackWidthQueueTest extends TestCase {
    /**
     * Test method for addRectangleSubclass in PackWidthQueue
     *
     */
    public void testAddRectangleSubclass() throws Exception {
        System.out.println("PackWidthQueue test");
        PackData data = new PackData(0, true, 0);
        PackWidthQueue instance = new PackWidthQueue(data);
        RectangleRotatable rectangle = new RectangleRotatable(0, 5, 7);

        //test if the pack is empty
        assertEquals(0, instance.getNumberOfRectangles());
        instance.addRectangleSubclass(rectangle);

        //test if the rectangle is really added to the pack
        assertEquals(1, instance.getNumberOfRectangles());
    }

    /**
     * Test method for getNumberOfRectangles in PackWidthQueue
     *
     */
    public void testGetNumberOfRectangles() throws Exception {
        System.out.println("GetNumberOfRectangles test");
        PackData data = new PackData(0, true, 2);
        PackWidthQueue instance = new PackWidthQueue(data);

        //First test if pack is empty
        assertEquals(0, instance.getNumberOfRectangles());
        RectangleRotatable rectangle1 = new RectangleRotatable(0, 5, 7);

        //tests if the size increases when the rectangles are added
        instance.addRectangleSubclass(rectangle1);
        assertEquals(1, instance.getNumberOfRectangles());
        RectangleRotatable rectangle2 = new RectangleRotatable(1, 4, 6);
        instance.addRectangleSubclass(rectangle2);
        assertEquals(2, instance.getNumberOfRectangles());
        instance.addRectangleSubclass(rectangle2);
        instance.addRectangleSubclass(rectangle2);
        assertEquals(4, instance.getNumberOfRectangles());
    }

    /**
     * Test method for getRectangles in PackWidthQueue
     *
     */
    public void testGetRectangles() throws Exception {
        System.out.println("GetRectangles test");
        PackData data = new PackData(0, true, 0);
        PackWidthQueue instance = new PackWidthQueue(data);

        //Two test rectangles, adding them to the pack
        RectangleRotatable rectangle1 = new RectangleRotatable(0, 5, 7);
        RectangleRotatable rectangle3 = new RectangleRotatable(1,6, 5);
        instance.addRectangleSubclass(rectangle1);
        instance.addRectangleSubclass(rectangle3);

        PriorityQueue<RectangleRotatable> rec = instance.getRectangles();
        //get the first rectangle from the queue
        RectangleRotatable rectangle2 = rec.remove();
        //test if the test method work
        assertEquals(rectangle2.getWidth(), rectangle1.getWidth());
        assertEquals(rectangle2.getHeight(), rectangle1.getHeight());
        assertEquals(rectangle1.getID(), rectangle2.getID());
        //get second rectangle from the queue
        rectangle2 = rec.remove();
        //test if the test method works
        assertEquals(rectangle3.getID(), rectangle2.getID());
        assertEquals(rectangle3.getHeight(), rectangle2.getHeight());
        assertEquals(rectangle3.getWidth(), rectangle2.getWidth());
    }

}