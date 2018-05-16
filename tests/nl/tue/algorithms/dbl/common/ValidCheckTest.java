package nl.tue.algorithms.dbl.common;

import junit.framework.TestCase;
import org.w3c.dom.css.Rect;

/**
 *
 * @author K.D. Voorintholt (1005136)
 *
 * @since 9 MAY 2018
 */
public class ValidCheckTest extends TestCase {
    public void testCheckSolution() throws Exception {
        System.out.println("ValidCheck test");
        ValidCheck instance = new ValidCheck();
        PackData data = new PackData(-1, false, 3);
        PackList p = new PackList(data);
        //make test rectangles
        RectangleRotatable rec1 = new RectangleRotatable(0, 5, 1);
        RectangleRotatable rec2 = new RectangleRotatable(1, 400, 300);
        RectangleRotatable rec3 = new RectangleRotatable(2, 2,2);
        //set location and add to the pack
        rec1.setLocation(0,0);
        rec2.setLocation(6,6);
        rec3.setLocation(-1,-1);
        p.addRectangleSubclass(rec1);
        p.addRectangleSubclass(rec2);
        p.addRectangleSubclass(rec3);

        //checks
        assertEquals(true, instance.checkSolution(p, rec1));
        assertEquals(true, instance.checkSolution(p, rec2));
        rec3.setLocation(1, 1);
        assertEquals(false, instance.checkSolution(p, rec3));

        data = new PackData(4, false, 3);
        p = new PackList(data);
        assertEquals(false, instance.checkSolution(p, rec2));
        assertEquals(true, instance.checkSolution(p, rec1));

        rec1.setRotated(true);
        assertEquals(false, instance.checkSolution(p, rec1));

        data = new PackData(4, true, 3);
        assertEquals(false, instance.checkSolution(p, rec1));
        rec1.setRotated(false);
        assertEquals(true, instance.checkSolution(p, rec1));
    }

    public void testNoOverlapSolution() throws Exception {
        System.out.println("ValidCheck test");
        ValidCheck instance = new ValidCheck();
        PackData data = new PackData(-1, false, 3);
        PackList p = new PackList(data);
        RectangleRotatable rec1 = new RectangleRotatable(0, 5, 1);
        RectangleRotatable rec2 = new RectangleRotatable(1, 400, 300);
        RectangleRotatable rec3 = new RectangleRotatable(2, 2,2);


        rec1.setLocation(0,0);
        rec2.setLocation(6,6);
        rec3.setLocation(-1,-1);
        p.addRectangleSubclass(rec1);
        p.addRectangleSubclass(rec2);
        p.addRectangleSubclass(rec3);

        assertEquals(true, instance.noOverlapSolution(p, rec2));
        rec2.setLocation(0,0);
        assertEquals(false, instance.noOverlapSolution(p, rec2));

    }

    public void testFitsInContainer() throws Exception {
        System.out.println("FitsInContainer test");
        ValidCheck instance = new ValidCheck();
        PackData data = new PackData(-1, false, 5);
        PackList p = new PackList(data);
        RectangleRotatable rec1 = new RectangleRotatable(0, 5, 1);
        RectangleRotatable rec2 = new RectangleRotatable(1, 400, 300);

        assertEquals(true, instance.checkSolution(p, rec1));
        assertEquals(true, instance.checkSolution(p, rec2));

        data = new PackData(1, false, 2);
        p = new PackList(data);

        //check if not fits
        rec1.setRotated(true);
        assertEquals(false, instance.checkSolution(p, rec1));
    }

    public void testNoIllegalRotation() throws Exception {
        System.out.println("NoIllegalRotation test");
        ValidCheck instance = new ValidCheck();
        PackData data1 = new PackData(-1, false, 5);
        PackData data2 = new PackData(100, true, 5);
        PackList p = new PackList(data1);

        RectangleRotatable rec1 = new RectangleRotatable(0, 3, 5);
        RectangleRotatable rec2 = new RectangleRotatable(1, 400, 300);

        rec2.setRotated(true);
        assertEquals(true, instance.noIllegalRotation(p, rec1));
        assertEquals(false, instance.noIllegalRotation(p, rec2));

        p = new PackList(data2);
        assertEquals(true, instance.noIllegalRotation(p, rec1));
        assertEquals(true, instance.noIllegalRotation(p, rec2));

    }

}