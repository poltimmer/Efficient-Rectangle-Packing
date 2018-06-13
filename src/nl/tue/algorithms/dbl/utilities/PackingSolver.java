package nl.tue.algorithms.dbl.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

import nl.tue.algorithms.dbl.algorithm.*;
import nl.tue.algorithms.dbl.common.PackData;
import nl.tue.algorithms.dbl.common.ValidCheck;
import nl.tue.algorithms.dbl.io.InputReader;
import nl.tue.algorithms.dbl.io.OutputWriter;

/**
 * Class that is expected by Momotor.
 * Solves a given PackingProblem from an Inputstream.
 * Can be forced to use a specific algorithm to solve the PackignProblem.
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
    
    private final InputReader reader;
    private final Algorithm algo;
    
    public PackingSolver(InputStream in) throws IOException {
        this(in, null);
    }
    
    public PackingSolver(InputStream in, Class<? extends Algorithm> algoClass) throws IOException {
        reader = new InputReader(in);
        PackData data = reader.readPackData();

        Algorithm algo;
        if (algoClass != null && algoClass != Algorithm.class) {
            try {
                algo = algoClass.getConstructor(PackData.class).newInstance(data);
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                algo = selectAlgorithm(data);
                e.printStackTrace();
            }
        } else {
            algo = selectAlgorithm(data);
        }
        this.algo = algo;        
    }
    
    private Algorithm selectAlgorithm(PackData data) {
        //an algorithm based on pack data can be chosen here.        
        //for small input sizes (n <= 5) we want bruteforce
        return new RecursiveFit(data);
        /*if (data.getNumberOfRectangles() <= 5) {
            return new BruteForce(data);
        }
        //for bigger inputs without fixed height defined we can only use BinaryPacker
        else if (!data.hasFixedHeight()) {
            return new BinaryPacker(data);
        } else {
            return new FirstFitDecreasingWidth(data);
        }*/
    }
    
    public void readRectangles() throws IOException {
        reader.readRectangles(algo.getPack());
    }
    
    public void solve() {
        algo.solve();
    }
    
    public Algorithm getAlgorithm() {
        return algo;
    }
    
    public void printOutput(PrintStream out, boolean repeatInput) {
        String s = repeatInput? reader.getInputMessage() : "";
        OutputWriter.printOutput(out, algo.getPack(), s);
    }
    
    
    //Expected by Momotor; Uses System.in and System.out
    public static void main(String args[]) throws IOException {
        PackingSolver solver = new PackingSolver(System.in);
        ValidCheck.print("Result using " + solver.getAlgorithm().getClass().getSimpleName() + " is shown on the Screen");
        //A specific algorithm can instead be chosen by using:
        //new PackingSolver(System.in, BruteForce.class);
        
        solver.readRectangles();
        solver.solve();
        
        solver.printOutput(System.out, true);
    }
}