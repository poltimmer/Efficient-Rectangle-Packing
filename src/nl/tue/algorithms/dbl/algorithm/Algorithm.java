package nl.tue.algorithms.dbl.algorithm;

import nl.tue.algorithms.dbl.common.Pack;

/**
 *
 * @author E.M.A. Arts (1004076)
 * @since 10 MAY 2018
 */
public abstract class Algorithm<P extends Pack> {
    protected P pack;
    public String bestAlgoName;
    
    public Algorithm(P pack) {
        this.bestAlgoName = getClass().getSimpleName();
        this.pack = pack;
    }
    
    public P getPack() {
        //this intentionally 'leaks' a reference
        return pack;
    }
    
    /**
     * Sets valid positions for all rectangles in this.pack such that they do not
     * overlap and their combined area is minimized.
     */
    public abstract void solve();
    
    /**
     * Gets the height of the Pack. The invoked default implementation of the Pack
     * is rather inefficient however.
     * Should be overridden by a subclass extending this class if efficiency is
     * important!
     * 
     * @return The height of the pack's container (0 if there are no rectangles
     * or none have been placed yet)
     */
    public int getContainerHeight() {
        return pack.getContainerHeight();
    }
    
    /**
     * Gets the width of the Pack. The invoked default implementation of the Pack
     * is rather inefficient however.
     * Should be overridden by a subclass extending this class if efficiency is
     * important!
     * 
     * @return The width of the pack's container (0 if there are no rectangles
     * or none have been placed yet)
     */
    public int getContainerWidth() {
        return pack.getContainerWidth();
    }
    
    /**
     * Gets the area of the Pack by invoking the Pack's implementation of it.
     * 
     * @return The area of the pack's container (0 if there are no rectangles
     * or none have been placed yet)
     */
    public int getContainerArea() {
        int w = getContainerWidth();
        int h = getContainerHeight();

        //detect overflow
        boolean overflow = (w!=0 && w*h/w != h) || (h!=0 && h*w/h != w);
        return overflow ? Integer.MAX_VALUE : getContainerHeight()*getContainerWidth();
    }
}
