package nl.tue.algorithms.dbl.common;

import junit.framework.TestCase;

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
        PackData data = new PackData(0, false, 2);
        PackList p = new PackList(data);
        RectangleRotatable rec1 = new RectangleRotatable(0, 3, 5);
        //RectangleRotatable rec2 = new RectangleRotatable(1, 4, 3);

        assertEquals(true, instance.checkSolution(p, rec1));
        data = new PackData(1, false, 2);
        p = new PackList(data);

        //check if not fits
        assertEquals(false, instance.checkSolution(p, rec1));

    }

    public void testNoOverlapSolution() throws Exception {
        System.out.println("ValidCheck test");
        ValidCheck instance = new ValidCheck();
        PackData data = new PackData(0, false, 5);
        PackList p = new PackList(data);
        RectangleRotatable rec = new RectangleRotatable(0, 3, 5);
    }

    public void testFitsInContainer() throws Exception {
        System.out.println("FitsInContainer test");
        ValidCheck instance = new ValidCheck();
        PackData data = new PackData(0, false, 5);
        PackList p = new PackList(data);
        RectangleRotatable rec = new RectangleRotatable(0, 3, 5);
    }

    public void testNoIllegalRotation() throws Exception {
        System.out.println("NoIllegalRotation test");
        ValidCheck instance = new ValidCheck();
        PackData data = new PackData(0, false, 5);
        PackList p = new PackList(data);
        RectangleRotatable rec = new RectangleRotatable(0, 3, 5);
    }

}