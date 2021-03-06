package nl.tue.algorithms.dbl.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Stores all the data necessary for the algorithm. This includes a fixed container
 * height (if any), whether rotations are allowed, the rectangles, and the number
 * of rectangles
 * 
 * @author E.M.A. Arts (1004076)
 * @author K.D. Voorintholt (1005136)
 * @since 25 APR 2018
 */
public abstract class Pack {
    /** fixed container height. -1 if no fixed container height */
    private final int fixedHeight;
    /** whether rotations are allowed */
    private final boolean canRotate;
    /** References to rectangles to keep their order */
    private final List<RectangleRotatable> rectangleOrder;
    
    public Pack(PackData data) {
        this.fixedHeight = data.getContainerHeight();
        this.canRotate = data.canRotate();
        this.rectangleOrder = new ArrayList<>();
    }
    
    /** Ratio at which to rotate rectangles
      * Rotations are always done if ROTATE_RATIO = 0
      * Rotations are disabled if ROTATE_RATIO = Double.MAX_VALUE
      * In all other cases, Rotations are done if the Width-Height ratio of a
      * rectangle in this pack >= ROTATE_RATIO
      * NOTE that algorithms may choose to not use this Ratio, or to change its
      * value.
      */
    public double ROTATE_RATIO = 3;
    
    /**
     * Adds a rectangle to be packed to the pack
     * @param rec Rectangle to add
     * 
     * @pre rec != null     
     * @modifies this
     * @post    orderedRectangles.contains(rec) &&
     *          numberOfRectangles = \old(numberOfRectangles) + 1
     * 
     * @throws NullPointerException if the precondition is violated
     */
    public void addRectangle(RectangleRotatable rec) throws NullPointerException {
        if (rec == null) {
            throw new NullPointerException("Given rectangle is null");
        }
        rectangleOrder.add(rec); //We add the rectangle to our ordered ArrayList.
        addRectangleSubclass(rec); //We add the rectangle to the collection we define in the subclass.
    }

    /**
     * Method which is implemented by concrete subclass which will insert the rectangle in it's own defined collection.
     * @param rec the rectangle to be added, rectangleOrder keeps track of the order in which the triangle was entered.
     * @post rectangles.contains(rec)
     */
    protected abstract void addRectangleSubclass(RectangleRotatable rec);

    /** basic query */
    public int getFixedHeight() {
        return fixedHeight;
    }
    
    /** basic query */
    public boolean canRotate() {
        return canRotate;
    }
    
    /** basic query */
    public abstract int getNumberOfRectangles();
    
    /** basic query */
    public boolean hasFixedHeight() {
        return fixedHeight > 0;
    }

    /** abstract query to be implemented by subclass **/
    public abstract Collection<RectangleRotatable> getRectangles();

    public List<RectangleRotatable> getOrderedRectangles() {
        return rectangleOrder;
    }
    
    /**
     * A rather inefficient method of getting the height of the container,
     * by iterating over all placed rectangles in the Pack.
     * Can be overridden by a subclass extending this class, and is recommended
     * to do so if there is a more efficient way.
     * 
     * @return The height of the container (0 if there are no rectangles or none
     * have been placed yet)
     */
    public int getContainerHeight() {        
        int highestY = Integer.MIN_VALUE;
        
        for (RectangleRotatable r : getOrderedRectangles()) {
            int height = !r.isRotated() ? r.height : r.width;
            if (r.y + height > highestY && r.isPlaced()) {
                highestY = r.y + height;
            }
        }
        
        return Math.max(0, Math.max(highestY, getFixedHeight()));
    }
    
    /**
     * A rather inefficient method of getting the width of the container,
     * by iterating over all placed rectangles in the Pack.
     * Can be overridden by a subclass extending this class, and is recommended
     * to do so if there is a more efficient way.
     * 
     * @return The width of the container (0 if there are no rectangles or none
     * have been placed yet)
     */
    public int getContainerWidth() {
        int highestX = Integer.MIN_VALUE;
        
        for (RectangleRotatable r : getOrderedRectangles()) {
            int width = !r.isRotated() ? r.width : r.height;
            if (r.x + width > highestX && r.isPlaced()) {
                highestX = r.x + width;
            }
        }
        return Math.max(highestX, 0);
    }
    
    /**
     * Calculates the area of the pack. Invokes getWidth() and getHeight()
     * @return the area of this pack
     */
    public int getContainerArea() {       
        int w = getContainerWidth();
        int h = getContainerHeight();

        //detect overflow
        boolean overflow = (w!=0 && w*h/w != h) || (h!=0 && h*w/h != w);
        return overflow ? Integer.MAX_VALUE : getContainerHeight()*getContainerWidth();
    }
    
    /**
     * Gets the size of the area of the pack's container that is covered with
     * rectangles.
     * @return The area that is covered with rectangles.
     */
    public int getUsedArea() {
        int area = 0;
        for (RectangleRotatable r : getOrderedRectangles()) {
            area += r.getArea();
        }
        return area;
    }
    
    /**
     * Gets the size of the area that is not filled with rectangles
     * @return The area not covered by rectangles
     */
    public int getUnusedArea() {
        return getContainerArea() - getUsedArea();
    }
    
    /**
     * Calculates the coverage (in percentage) of the pack. That is, calculates
     * how much of the pack's container is filled with rectangles
     * @return The coverage as a value from 0-100, representing a percentage
     */
    public long getCoveragePercentage() {
        long containerArea = getContainerArea();
        if (containerArea == 0) {
            return 0;
        }
        
        return (long) getUsedArea() * 100 / (long) getContainerArea();
    }
    
    /**
     * Checks whether the pack should rotate r based on Ratios, for WidthQueue
     * @param r Rectangle
     * @return true if the rectangle should be rotated
     */
    protected boolean shouldRotateByRatioWidth(RectangleRotatable r) {
        return shouldRotateByRatio(r.getHeight(), r.getWidth(), r.getRotatedWidth(), r.getRotatedHeight());
    }
    
    /**
     * Checks whether the pack should rotate r based on Ratios, for HeightQueue
     * @param r Rectangle
     * @return true if the rectangle should be rotated
     */
    protected boolean shouldRotateByRatioHeight(RectangleRotatable r) {
        return shouldRotateByRatio(r.getWidth(), r.getHeight(), r.getRotatedWidth(), r.getRotatedHeight());
    }
    
    private boolean shouldRotateByRatio(double width, double height, int rotatedWidth, int rotatedHeight) {
        //Rectangle has the right ratio to rotate
        boolean canRotateDueToRatio = ROTATE_RATIO < Double.MAX_VALUE && (width / height >= ROTATE_RATIO);
        //Rectangle's height > fixed height
        boolean heightExceedsFixedHeight = hasFixedHeight() && rotatedHeight > getFixedHeight();
        //Rectangle's width > fixed height
        boolean widthExceedsFixedHeight = hasFixedHeight() && rotatedWidth > getFixedHeight();
        
        //cannot rotate and height > fixedheight
        if (heightExceedsFixedHeight && !canRotate()) {
            ValidCheck.println("WARNING: RECTANGLE HAS TO BE ROTATED BECAUSE IT "
                    + "EXCEEDS THE FIXED HEIGHT WHILE IT ACTUALLY CAN'T IN THIS PACK");
        }
        
        //canRotate and width > fixedheight and height > fixedheigt
        if (widthExceedsFixedHeight && heightExceedsFixedHeight && canRotate()) {
            ValidCheck.println("WARNING: RECTANGLES WIDTH AND HEIGHT BOTH EXCEED"
                    + "THE FIXED HEIGTH! THERE IS NOW WAY TO PLACE THIS RECTANGLE"
                    + "IN A VALID WAY");
        }  
        
                //rotations are allowed AND the ratio is correct AND width does not exceed fixed height OR
        return (canRotate() && canRotateDueToRatio && !widthExceedsFixedHeight) ||
                //There is a fixed height and the rectangle's height exceeds this
                heightExceedsFixedHeight;
    }
}
