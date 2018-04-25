/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.tue.algorithms.dbl.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores all the data necessary for the algorithm. This includes a fixed container
 * height (if any), whether rotations are allowed, the rectangles, and the number
 * of rectangles
 * 
 * @author E.M.A. Arts (1004076)
 * @since 25 APR 2018
 */
public class Pack {
    private final int containerHeight;
    private final boolean canRotate;
    private int numberOfRectangles;
    
    //TODO: determine data type. Simple list for now. maybe make it an ADT?
    private final List<Rectangle> rectangles;
    
    public Pack(int containerHeight, boolean canRotate) {
        this.containerHeight = containerHeight;
        this.canRotate = canRotate;
        this.numberOfRectangles = 0;
        this.rectangles = new ArrayList<>();
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
    public void addRectangle(Rectangle rec) throws NullPointerException {
        if (rec == null) {
            throw new NullPointerException("Pack.addRectangle.pre violated: rec == null");
        }
        rectangles.add(rec);
        numberOfRectangles++;
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
    
    /** basic query */
    public boolean hasFixedHeight() {
        return containerHeight >= 0;
    }
    
    @Override
    public String toString(){
        return rectangles.toString();
    }
}
