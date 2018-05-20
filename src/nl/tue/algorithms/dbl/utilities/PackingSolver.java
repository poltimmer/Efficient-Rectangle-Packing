package nl.tue.algorithms.dbl.utilities;

import java.io.IOException;
import nl.tue.algorithms.dbl.algorithm.Algorithm;
import nl.tue.algorithms.dbl.algorithm.FirstFitDecreasingHeight;
import nl.tue.algorithms.dbl.algorithm.BruteForce;
import nl.tue.algorithms.dbl.common.PackData;
import nl.tue.algorithms.dbl.gui.GUI;
import nl.tue.algorithms.dbl.gui.GUINew;
import nl.tue.algorithms.dbl.io.InputReader;
import nl.tue.algorithms.dbl.io.OutputWriter;

/**
 * Class that is expected by Momotor. Should be the only class with a main method!
 */

/**
 * @author K.D. Voorintholt (1005136)
 * @author E.M.A. Arts (1004076)
 * @author T.M. Verberk (1016472)
 * @author K. Degeling (1018025)
 * @author Wouter (1009509)
 * @author Pol (1007701)
 * @author Robin (1011291)
 * 
 * @since 9 MAY 2018
 */
public class PackingSolver {
    public static void main(String args[]) throws IOException {

        InputReader reader = new InputReader(System.in);
        PackData data = reader.readPackData();

        Algorithm algo;
        //an algorithm based on pack data can be chosen here. E.g.:
        /* 
            if (data.canRotate()) {
                algo = new AlgorithmOne(data);
            } else {
                algo = new AlgorithmTwo(data);
            }
        */
        //since we only have 1 algorithm, we have to do this:
        algo = new FirstFitDecreasingHeight(data);
        
        reader.readRectangles(algo.getPack());

        algo.solve();

        OutputWriter.printOutput(System.out, algo.getPack(), reader.getInputMessage());
    }
}
