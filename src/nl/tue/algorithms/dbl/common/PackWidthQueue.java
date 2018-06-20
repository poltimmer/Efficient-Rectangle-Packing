package nl.tue.algorithms.dbl.common;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PackWidthQueue extends Pack {

    private final PriorityQueue<RectangleRotatable> rectangles;
    
    public PackWidthQueue(PackData data) {
        super(data);
        rectangles = new PriorityQueue<>(new Comparator<RectangleRotatable>() {
            @Override
            public int compare(RectangleRotatable r1, RectangleRotatable r2) {
                if (r1.getRotatedWidth() > r2.getRotatedWidth()) {
                    return -1;
                } else if (r1.getRotatedWidth() < r2.getRotatedWidth()) {
                    return 1;
                } else {
                    //r1.getRotatedWidth() == r2.getRotatedWidth()
                    if (r1.getRotatedHeight() == r2.getRotatedHeight()) {
                        return 0;
                    }
                    
                    return r1.getRotatedHeight() > r2.getRotatedHeight() ? -1 : 1;
                }
            }
        }); // Turn PriorityQueue into Max Heap.
    }

    @Override
    protected void addRectangleSubclass(RectangleRotatable r) {
        //rotate the rectangle for a specific ratio
        if (shouldRotateByRatioWidth(r)) {
            r.setRotated(true);
        }
        rectangles.add(r);
    }

    @Override
    public int getNumberOfRectangles() {
        return getOrderedRectangles().size();
    }

    @Override
    public PriorityQueue<RectangleRotatable> getRectangles() {
        return rectangles;
    }
}
