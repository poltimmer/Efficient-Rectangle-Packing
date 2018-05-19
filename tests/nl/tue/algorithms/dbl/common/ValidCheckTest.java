package nl.tue.algorithms.dbl.common;

import junit.framework.TestCase;

/**
 *
 * @author K.D. Voorintholt (1005136)
 *
 * @since 9 MAY 2018
 */
public class ValidCheckTest extends TestCase {
    
    public void testIsRectangleValidWithinPack() throws Exception {
        System.out.println("ValidCheck test");

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
        assertEquals(true, ValidCheck.isRectangleValidWithinPack(rec1, p));
        assertEquals(true, ValidCheck.isRectangleValidWithinPack(rec2, p));
        rec3.setLocation(1, 1);
        assertEquals(false, ValidCheck.isRectangleValidWithinPack(rec3, p));

        data = new PackData(4, false, 3);
        p = new PackList(data);
        assertEquals(false, ValidCheck.isRectangleValidWithinPack(rec2, p));
        assertEquals(true, ValidCheck.isRectangleValidWithinPack(rec1, p));

        rec1.setRotated(true);
        assertEquals(false, ValidCheck.isRectangleValidWithinPack(rec1, p));

        data = new PackData(4, true, 3);
        assertEquals(false, ValidCheck.isRectangleValidWithinPack(rec1, p));
        rec1.setRotated(false);
        assertEquals(true, ValidCheck.isRectangleValidWithinPack(rec1, p));
    }

    public void testIsRectangleNotOverlappingWithPack() throws Exception {
        System.out.println("ValidCheck test");

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

        assertEquals(true, ValidCheck.isRectangleNotOverlappingWithPack(rec2, p));
        rec2.setLocation(0,0);
        assertEquals(false, ValidCheck.isRectangleNotOverlappingWithPack(rec2, p));

    }

    public void testIsRectangleWithinPackHeight() throws Exception {
        System.out.println("FitsInContainer test");

        PackData data = new PackData(-1, false, 5);
        PackList p = new PackList(data);
        RectangleRotatable rec1 = new RectangleRotatable(0, 5, 1);
        RectangleRotatable rec2 = new RectangleRotatable(1, 400, 300);

        assertEquals(true, ValidCheck.isRectangleWithinPackHeight(rec1, p));
        assertEquals(true, ValidCheck.isRectangleWithinPackHeight(rec2, p));

        data = new PackData(1, false, 2);
        p = new PackList(data);

        //check if not fits
        rec1.setRotated(true);
        assertEquals(false, ValidCheck.isRectangleWithinPackHeight(rec1, p));
    }

    public void testIsRectangleNotRotatedIllegallyWithinPack() throws Exception {
        System.out.println("NoIllegalRotation test");

        PackData data1 = new PackData(-1, false, 5);
        PackData data2 = new PackData(100, true, 5);
        PackList p = new PackList(data1);

        RectangleRotatable rec1 = new RectangleRotatable(0, 3, 5);
        RectangleRotatable rec2 = new RectangleRotatable(1, 400, 300);

        rec2.setRotated(true);
        assertEquals(true, ValidCheck.isRectangleNotRotatedIllegallyWithinPack(rec1, p));
        assertEquals(false, ValidCheck.isRectangleNotRotatedIllegallyWithinPack(rec2, p));

        p = new PackList(data2);
        assertEquals(true, ValidCheck.isRectangleNotRotatedIllegallyWithinPack(rec1, p));
        assertEquals(true, ValidCheck.isRectangleNotRotatedIllegallyWithinPack(rec2, p));
    }

}