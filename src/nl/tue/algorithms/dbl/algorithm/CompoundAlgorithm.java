package nl.tue.algorithms.dbl.algorithm;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import nl.tue.algorithms.dbl.common.Pack;
import nl.tue.algorithms.dbl.common.PackData;
import nl.tue.algorithms.dbl.common.PackList;
import nl.tue.algorithms.dbl.common.Pair;
import nl.tue.algorithms.dbl.common.RectangleRotatable;
import nl.tue.algorithms.dbl.common.ValidCheck;

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
    private final List<Pair<Class<? extends Algorithm>, Double>> algos;
    
    public enum RotationMode {
        ROTATIONMODE_NONE,          //rotate no rectangles
        ROTATIONMODE_ALL,           //rotate all rectangles
        ROTATIONMODE_BIGGEST_SIDE,  //rotate rectangles with a width-height ratio >= 1 (I.e. biggest side)
        ROTATIONMODE_DEFAULT_RATIO, //rotate rectangles using the default ratio
    }
    
    public CompoundAlgorithm(PackData data) {
        //Any Pack-subclass would work here. PackList was chosen arbitrarily
        super(new PackList(data));
        algos = new ArrayList<>();
    }

    /**
     * @throws IllegalStateException if algos.isEmpty()
     */
    @Override
    public void solve() throws IllegalStateException {
        //there must be at least 1 algorithm
        if (algos.isEmpty()) {
            throw new IllegalStateException("ERROR: No algorithms to execute for this CompoundAlgorithm");
        }
        
        //keep track of the 'best' algorithm (the one with the lowest container area)
        Algorithm bestAlgo = null;
        int minContainerArea = Integer.MAX_VALUE;
        
        //execute all added algorithms
        for (Pair<Class<? extends Algorithm>, Double> algoClass : algos) {
            //only create an instance of this class now as to preserve memory
            //(data structures may be HUGE when having many rectangles)
            Algorithm algo = newAlgoFromClass(algoClass.a, algoClass.b);
            
            //solve the packingProblem using the created algorithm, and retrieve
            //its size
            int area;
            
            try {
                algo.solve();
                area = algo.getContainerArea();
            } catch (Exception e) {
                //mask errors when not in debug mode
                if (ValidCheck.DEBUG_ENABLED) {
                    System.err.println("EXCEPTION THROWN");
                    throw e;
                }
                //and just ignore this result
                area = Integer.MAX_VALUE;
            }
            
            //if the area is lower than the current best area, then a new 'best'
            //algorithm is found
            if (area < minContainerArea) {
                minContainerArea = area;
                //only storing the data structure of the best algorithm is enough
                //The Java Garbage Collector can get rid of the worse solutions
                bestAlgo = algo;
                this.pack.ROTATE_RATIO = algo.pack.ROTATE_RATIO;
            }
            
            //extra debug stuff
            if (ValidCheck.DEBUG_ENABLED) {
                //display coverage as well           
                ValidCheck.println(" (" + algo.pack.getCoveragePercentage() + "%)");
                
                //sanity check to easily spot out any error in any invoked algorithm (makes it run slower though)
                for (RectangleRotatable r : algo.pack.getOrderedRectangles()) {
                    if (!ValidCheck.isRectangleValidWithinPack(r, algo.pack) &&
                            !(ValidCheck.isRectangleRotatedIllegallyWithinPack(r, algo.pack)
                            && r.height > algo.pack.getFixedHeight() && algo.pack.hasFixedHeight())) {
                        System.out.println(algoClass.a + ": WARNING ERROR IN THIS PACK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                        //one error msg is enough
                        break;
                    }
                }
            }
            
        }
        
        //overwrite the current pack by the 'best' pack
        copyRectanglesFromPackToPack(bestAlgo.pack, this.pack);
        this.bestAlgoName = bestAlgo.getClass().getSimpleName();
    }
    
    /**
     * Adds an Algorithm that will be executed when solve() is called.
     * If it provides the best solution out of all Algorithms that are executed,
     * then CompoundAlgorithm's solution will be this solution.
     * @param algoClass Class of an Algorithm to add
     * @param rotationRatio Rotate rectangles with a w-h ratio >= rotationRatio
     * 
     * @pre algoClass != Algorithm.class && algoClass != null
     * @post this.algos.contains(newInstance(algoClass))
     * @throws IllegalArgumentException if precondition is violated
     */
    public void add(Class <? extends Algorithm> algoClass, double rotationRatio) throws IllegalArgumentException {
        if (algoClass == Algorithm.class || algoClass == null) {
            throw new IllegalArgumentException("CompoundAlgorithm.add.pre violated: Algorithm.class is NOT a valid class!");
        }
        
        Pair<Class <? extends Algorithm>, Double> p;
        if (!pack.canRotate()) {
            p = new Pair(algoClass, Double.MAX_VALUE);
        } else {
            p = new Pair(algoClass, rotationRatio);
        }
        
        //only add it if it is not already in the list of algos
        if (!contains(p)) {
            algos.add(p);
        }
    }
    
    public void add(Class <? extends Algorithm> algoClass) {
        add(algoClass, RotationMode.ROTATIONMODE_DEFAULT_RATIO);
    }
    
    public void add(Class <? extends Algorithm> algoClass, RotationMode mode) {
        switch (mode) {
            case ROTATIONMODE_ALL:           add(algoClass, 0); break;
            case ROTATIONMODE_NONE:          add(algoClass, Double.MAX_VALUE); break;
            case ROTATIONMODE_BIGGEST_SIDE:  add(algoClass, 1); break;
            case ROTATIONMODE_DEFAULT_RATIO: 
            default:                         add(algoClass, -1); break;
        }
    }
    
    /**
     * Instantiates an instance of a given Algorithm class using this.pack, but
     * ignoring the value of this.pack.rotationsAllowed and instead using rotations
     * for it.
     * @param  algoClass the Algorithm class to create an instance of
     * @param executeAsIfRotationsAllowed whether to execute the algorithm as if it had rotations
     */
    private Algorithm newAlgoFromClass(Class <? extends Algorithm> algoClass, double rotationRatio) {
        Algorithm algo = null;
        try {
            //Double.MAX_VALUE indicates that rotations are not allowed
            boolean rotations = rotationRatio < Double.MAX_VALUE;
            
            //create packdata
            PackData data = new PackData(pack.getFixedHeight(), rotations, pack.getNumberOfRectangles());
            
            //create the algorithm by calling its constructor
            algo = algoClass.getConstructor(PackData.class).newInstance(data);
            
            //when using a non-default rotationRatio:
            if (rotationRatio >= 0) {
                //set the rotation-ratio (some Packs do NOT use this though!)
                algo.getPack().ROTATE_RATIO = rotationRatio;
            }
            ValidCheck.print("CompoundAlgorithm: " + algoClass.getSimpleName() + " with ROTATE_RATIO = " + algo.getPack().ROTATE_RATIO);
            
            //add the rectangles to the pack
            copyRectanglesFromPackToPack(this.pack, algo.pack);           
            
            //return the algo
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            //display error if class could not be created
            System.err.println("ERROR: COULD NOT INSTANTIATE ALGORITHM OF CLASS " + algoClass.getSimpleName());
            e.printStackTrace();
        }
        return algo;
    }

    /**
     * Adds all rectangles from this pack to a given pack (as a copy!)
     * @param p The pack to add the rectangles to
     * @pre p != null
     */
    private void copyRectanglesFromPackToPack(Pack fromPack, Pack toPack) {
        //clear all rectangles from the pack's Data Structure first
        toPack.getOrderedRectangles().clear();
        toPack.getRectangles().clear();
        
        //add each rectangle of this pack to algo's pack (as a copy!)
        for (RectangleRotatable r : fromPack.getOrderedRectangles()) {
            RectangleRotatable rCopy = r.copy();
            toPack.addRectangle(rCopy);
        }
    }
    
    /**
     * Checks whether a pair with the same values as reqPair is inside algos
     * @param reqPair The Pair to check for
     * @return true iff algos contains a reqPair with the same algoClass and rotateRatio
     */
    public boolean contains(Pair<Class<? extends Algorithm>, Double> reqPair) {
        for (Pair<Class<? extends Algorithm>, Double> pair : algos) {
            if (pair.a == reqPair.a && Double.compare(pair.b, reqPair.b) == 0) {
                return true;
            }
        }
        return false;
    }
}
