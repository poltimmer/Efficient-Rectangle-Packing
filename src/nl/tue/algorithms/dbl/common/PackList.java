package nl.tue.algorithms.dbl.common;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author E.M.A. Arts (1004076)
 * @since 2 MAY 2018 
 * 
 * Simplest implementation of a Pack data type. Stores all rectangles in a
 * simple list
 * 
 * Representation Invariants:
 * TODO
 * 
 * Abstraction function:
 * TODO
 */
public class PackList extends Pack {

    private List<Rectangle> rectangles;
    public PackList(PackData data) {
        super(data);
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
