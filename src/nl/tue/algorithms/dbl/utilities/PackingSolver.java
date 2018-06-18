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
    
    /**
     * Select an algorithm based on given PackData
     * @return An Algorithm to run
     */
    private Algorithm selectAlgorithm(PackData data) {
        
        if (data.getNumberOfRectangles() <= 5) {
            //for small input sizes (n <= 5) we want bruteforce
            //Since BruteForce always finds the optimal solution, there's no need
            //to run CompoundAlgorithm.
            
            return new BruteForce(data);
            
        } else { //n > 5
            //we want to run the compoundAlgorithm for big inputs
            CompoundAlgorithm compoundAlgo = new CompoundAlgorithm(data);
            
            //for free height invoke BinaryPacker only, for fixed height also invoke RecursiveFit; 
            //Class invokeClass = data.hasFixedHeight() ? RecursiveFit.class : BinaryPacker.class;
            
            //rotate no rectangles
            compoundAlgo.add(BinaryPacker.class, CompoundAlgorithm.RotationMode.ROTATIONMODE_NONE); 
            
            //rotate all rectangles
            compoundAlgo.add(BinaryPacker.class, CompoundAlgorithm.RotationMode.ROTATIONMODE_ALL); 
            
            //rotate rectangles based on their largest side
            compoundAlgo.add(BinaryPacker.class, CompoundAlgorithm.RotationMode.ROTATIONMODE_BIGGEST_SIDE); 

            //rotate rectangles if their width-height ratio >= the default ratio as defined in Pack
            compoundAlgo.add(BinaryPacker.class, CompoundAlgorithm.RotationMode.ROTATIONMODE_DEFAULT_RATIO); 
            
            //rotate rectangles if their width-height ratio >= 10, 0.01, 0.1 and 0.25 respectively. 
            //I.e. for very thin and long rectangles
            compoundAlgo.add(BinaryPacker.class, 10);
            compoundAlgo.add(BinaryPacker.class, 0.01);
            compoundAlgo.add(BinaryPacker.class, 0.1);
            compoundAlgo.add(BinaryPacker.class, 0.25);

            //rotate no rectangles
            compoundAlgo.add(RecursiveFit.class, CompoundAlgorithm.RotationMode.ROTATIONMODE_NONE);

            //rotate all rectangles
            compoundAlgo.add(RecursiveFit.class, CompoundAlgorithm.RotationMode.ROTATIONMODE_ALL);

            //rotate rectangles based on their largest side
            compoundAlgo.add(RecursiveFit.class, CompoundAlgorithm.RotationMode.ROTATIONMODE_BIGGEST_SIDE);

            //rotate rectangles if their width-height ratio >= the default ratio as defined in Pack
            compoundAlgo.add(RecursiveFit.class, CompoundAlgorithm.RotationMode.ROTATIONMODE_DEFAULT_RATIO);

            //rotate rectangles if their width-height ratio >= 10, 0.01, 0.1 and 0.25 respectively.
            //I.e. for very thin and long rectangles
            compoundAlgo.add(RecursiveFit.class, 10);
            compoundAlgo.add(RecursiveFit.class, 0.01);
            compoundAlgo.add(RecursiveFit.class, 0.1);
            compoundAlgo.add(RecursiveFit.class, 0.25);

            //actualy return the compoundAlgorithm now that the above has been defined for it.
            return compoundAlgo;
        }
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
        ValidCheck.println("Result using " + solver.getAlgorithm().getClass().getSimpleName() + " is shown on the Screen");
        //A specific algorithm can instead be chosen by using:
        //new PackingSolver(System.in, BruteForce.class);
        
        solver.readRectangles();
        solver.solve();
        
        solver.printOutput(System.out, true);
    }
}