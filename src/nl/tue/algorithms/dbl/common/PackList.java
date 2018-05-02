/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.tue.algorithms.dbl.common;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author E.M.A. Arts (1004076)
 * @since 2 MAY 2018
 * 
 * 
 * 
 * Representation Invariants:
 * TODO
 * 
 * Abstraction function:
 * TODO
 */
public class PackList extends Pack {
    
    private final List<Rectangle> rectangles;
    
    public PackList(int containerHeight, boolean canRotate) {
        super(containerHeight, canRotate);
        this.rectangles = new ArrayList<>();
    }
    
    @Override
    public void addRectangle(Rectangle rec) throws NullPointerException {
        if (rec == null) {
            throw new NullPointerException("Pack.addRectangle.pre violated: rec == null");
        }
        rectangles.add(rec);
    }
    
    /** basic query */
    @Override
    public int getNumberOfRectangles() {
        return rectangles.size();
    }
    
    /** basic query */
    public List<Rectangle> getRectanglesList() {
        return rectangles;
    }
    
    
    @Override
    public String toString(){
        return rectangles.toString();
    }
}
