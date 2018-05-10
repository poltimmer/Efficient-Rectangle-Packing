package nl.tue.algorithms.dbl.io;

import nl.tue.algorithms.dbl.common.Pack;
import nl.tue.algorithms.dbl.common.Rectangle;

/**
 * Class related to writing outputs.
 * 
 * @author Koen Degeling (1018025)
 * @author E.M.A. Arts (1004076)
 * @since 10 MAY 2018
 * 
 */
public class OutputWriter {
    /**
     * Method which will print the output to System.out
     *
     * @param p pack which contains the rectangle input
     * @param prefix String to print before the actual calculated outputs
     *              E.g. a String from an InputReader's getInputMessage()-method.
     */
    public static void printOutput(Pack p, String prefix) {
        System.out.print(prefix);
        System.out.println("placement of rectangles");
        for (Rectangle r : p.getOrderedRectangles()) {
            if (r.isRotated()) {
                System.out.print("yes ");
            } else {
                System.out.print("no ");
            }
            System.out.println(r.getX()+ " " +r.getY());
        }
    }
}
