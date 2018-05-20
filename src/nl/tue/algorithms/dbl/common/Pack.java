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
        return fixedHeight >= 0;
    }

    /** abstract query to be implemented by subclass **/
    public abstract Collection<RectangleRotatable> getRectangles();

    public List<RectangleRotatable> getOrderedRectangles() {
        return rectangleOrder;
    }
    
    /**
     * A rather inefficient method of getting the height of the container,
     * by iterating over all placed rectangles in the Pack.
     * Should be overridden by a subclass extending this class!
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
        return Math.max(highestY, 0);
    }
    
    /**
     * A rather inefficient method of getting the width of the container,
     * by iterating over all placed rectangles in the Pack.
     * Should be overridden by a subclass extending this class!
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
    
}
