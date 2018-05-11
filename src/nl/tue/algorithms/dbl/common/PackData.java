package nl.tue.algorithms.dbl.common;

/**
 * Class that stores Pack data such as fixed container height (if any), whether
 * rotations are allowed, and the number of rectangles. Can be used to determine
 * which algorithm to select based on this data, without yet choosing a Data
 * representation for a Pack-subclass.
 * 
 * This class can be used to create a Pack-subclass
 * 
 * @author E.M.A. Arts (1004076)
 * @since 10 MAY 2018
 */
public class PackData {
    //immutable pack-data
    /** fixed container height. -1 if no fixed container height */
    private final int containerHeight;
    /** whether rotations are allowed */
    private final boolean canRotate;
    /** number of rectangles */
    private final int numberOfRectangles;
    
    public PackData (int containerHeight, boolean canRotate, int numberOfRectangles) {
        this.containerHeight = Math.max(containerHeight, -1);
        this.canRotate = canRotate;
        this.numberOfRectangles = numberOfRectangles;
    }
    
    //basic getters
    /** basic query */
    public boolean hasFixedHeight() {
        return containerHeight >= 0;
    }
    
    /** basic query */
    public int getContainerHeight() {
        return containerHeight;
    }
    
    /** basic query */
    public boolean canRotate() {
        return canRotate;
    }
    
    /** basic query */
    public int getNumberOfRectangles() {
        return numberOfRectangles;
    }
}
