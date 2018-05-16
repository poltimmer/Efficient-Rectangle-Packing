package nl.tue.algorithms.dbl.io;

import java.io.PrintStream;
import nl.tue.algorithms.dbl.common.Pack;
import nl.tue.algorithms.dbl.common.RectangleRotatable;

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
     * Method which will print the output to systemOut
     *
     * @param systemOut PrintStream of where to print (E.g. System.out)
     * @param p pack which contains the rectangle input
     * @param prefix String to print before the actual calculated outputs
     *              E.g. a String from an InputReader's getInputMessage()-method.
     */
    public static void printOutput(PrintStream systemOut, Pack p, String prefix) {
        systemOut.print(prefix);
        systemOut.println("placement of rectangles");
        for (RectangleRotatable r : p.getOrderedRectangles()) {
            if (r.isRotated()) {
                systemOut.print("yes ");
            } else {
                systemOut.print("no ");
            }
            systemOut.println(r.getX()+ " " +r.getY());
        }
    }
}
