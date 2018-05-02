/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.tue.algorithms.dbl.common;


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
    protected final int containerHeight;
    protected final boolean canRotate;
    
    public Pack(int containerHeight, boolean canRotate) {
        this.containerHeight = containerHeight;
        this.canRotate = canRotate;
    }
    
    /**
     * Adds a rectangle to be packed to the pack
     * @param rec Rectangle to add
     * 
     * @pre rec != null     
     * @modifies this
     * @post    rectangles.contains(rec) &&
     *          numberOfRectangles = \old(numberOfRectangles) + 1
     * 
     * @throws NullPointerException if the precondition is violated
     */
    public abstract void addRectangle(Rectangle rec) throws NullPointerException;
    
    /** basic query */
    public int getContainerHeight() {
        return containerHeight;
    }
    
    /** basic query */
    public boolean canRotate() {
        return canRotate;
    }
    
    /** basic query */
    public abstract int getNumberOfRectangles();
    
    /** basic query */
    public boolean hasFixedHeight() {
        return containerHeight >= 0;
    }
}
