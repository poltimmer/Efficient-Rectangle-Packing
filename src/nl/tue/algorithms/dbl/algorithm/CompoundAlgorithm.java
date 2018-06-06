package nl.tue.algorithms.dbl.algorithm;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import nl.tue.algorithms.dbl.common.Pack;
import nl.tue.algorithms.dbl.common.PackData;
import nl.tue.algorithms.dbl.common.PackList;
import nl.tue.algorithms.dbl.common.RectangleRotatable;

/**
 * An Algorithm that executes all algorithms that are added to it, and presents
 * the best solution out of all generated solutions by its added algorithms as
 * its own solution
 * Note that this may take very long if many algorithms are added, or if slow
 * algorithms are added.
 * 
 * @author E.M.A. Arts (1004076)
 * @since 6 JUN 2018
 */
public class CompoundAlgorithm extends Algorithm<Pack> {
    /** List of subscribed algorithms */
    private final List<Algorithm> algos;
    public String bestAlgoName;
    
    public CompoundAlgorithm(PackData data) {
        super(new PackList(data));
        algos = new ArrayList<>();
    }

    /**
     * @throws IllegalStateException if algos.isEmpty()
     */
    @Override
    public void solve() throws IllegalStateException {
        if (algos.isEmpty()) {
            throw new IllegalStateException("ERROR: No algorithms to execute for this CompoundAlgorithm");
        }
        
        //keep track of the 'best' algorithm (the one with the lowest container area)
        Algorithm bestAlgo = null;
        int minContainerArea = Integer.MAX_VALUE;
        
        //execute all added algorithms
        for (Algorithm algo : algos) {
            algo.solve();
            int area = algo.getContainerArea();
            
            //if the area is lower than the current best area, then a new 'best'
            //algorithm is found
            if (area < minContainerArea) {
                minContainerArea = area;
                bestAlgo = algo;
            }
        }
        
        //overwrite the current pack by the 'best' pack
        this.pack = bestAlgo.pack;
        this.bestAlgoName = bestAlgo.getClass().getSimpleName();
    }
    
    /**
     * Adds an Algorithm that will be executed when solve() is called.
     * If it provides the best solution out of all Algorithms that are executed,
     * then CompoundAlgorithm's solution will be this solution.
     * @param algoClass Class of an Algorithm to add
     * @param bothRotations whether to execute the algorithm twice: once with
     *                      rotations and once without (if rotations are allowed
     *                      in the first place)
     * 
     * @pre algoClass != Algorithm.class && algoClass != null
     * @post this.algos.contains(newInstance(algoClass))
     * @throws IllegalArgumentException if precondition is violated
     */
    public void add(Class <? extends Algorithm> algoClass, boolean bothRotations) throws IllegalArgumentException {
        if (algoClass == Algorithm.class) {
            throw new IllegalArgumentException("CompoundAlgorithm.pre violated: Algorithm.class is NOT a valid class!");
        }
        
        //create packdata for this algorithm
        PackData data = new PackData(pack.getFixedHeight(), pack.canRotate(), pack.getNumberOfRectangles());
        add(algoClass, data);
        
        if (bothRotations && pack.canRotate()) {
            data = new PackData(pack.getFixedHeight(), false, pack.getNumberOfRectangles());
            add(algoClass, data);
        }
    }
    
    /**
     * 
     * @param data 
     */
    private void add(Class <? extends Algorithm> algoClass, PackData data) {
        Algorithm algo;
        try {
            //create the algorithm by calling its constructor
            algo = algoClass.getConstructor(PackData.class).newInstance(data);
            //add the rectangles to it
            addRectanglesToPack(algo.pack);           
            //add it to our list
            algos.add(algo);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            //display error if class could not be created
            System.err.println("ERROR: COULD NOT INSTANTIATE ALGORITHM OF CLASS " + algoClass.getSimpleName());
            e.printStackTrace();
        }
    }

    /**
     * Adds all rectangles from this pack to a given pack
     * @param p The pack to add the rectangles to
     * @pre p != null
     */
    private void addRectanglesToPack(Pack p) {
        //add each rectangle of this pack to algo's pack (as a copy!)
        for (RectangleRotatable r : pack.getOrderedRectangles()) {
            p.addRectangle(r.copy());
        }
    }
    
}
