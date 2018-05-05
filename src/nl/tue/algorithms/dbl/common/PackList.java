/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.tue.algorithms.dbl.common;

import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Collection;
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

    private List<Rectangle> rectangles;
    public PackList() {
        super();
        this.rectangles = new ArrayList<>();
    }
    
    @Override
    public void addRectangle(Rectangle rec) throws NullPointerException {
        if (rec == null) {
            throw new NullPointerException("Pack.addRectangle.pre violated: rec == null");
        }
        rectangles.add(rec);
    }

    @Override
    protected void addRectangleSubclass(Rectangle rec) {
        rectangles.add(rec);
    }

    /** basic query */
    @Override
    public int getNumberOfRectangles() {
        return rectangles.size();
    }

    @Override
    public List<Rectangle> getRectangles() {
        return rectangles;
    }

    /** basic query */
    
    
    @Override
    public String toString(){
        return rectangles.toString();
    }
}
