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
 */
public class PackList extends Pack {

    private final List<RectangleRotatable> rectangles;
    public PackList(PackData data) {
        super(data);
        this.rectangles = new ArrayList<>();
    }


    @Override
    protected void addRectangleSubclass(RectangleRotatable rec) {
        rectangles.add(rec);
    }

    /** basic query */
    @Override
    public int getNumberOfRectangles() {
        return rectangles.size();
    }

    @Override
    public List<RectangleRotatable> getRectangles() {
        return rectangles;
    }
    
    @Override
    public String toString(){
        return rectangles.toString();
    }
}
