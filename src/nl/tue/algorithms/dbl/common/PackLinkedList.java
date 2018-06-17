package nl.tue.algorithms.dbl.common;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Stores the rectangles in a sorted LinkedList, sorted on Width. If rotations
 * are allowed, rectangles are rotated if the width-height ratio exceeds
 * ROTATE_RATIO
 */

public class PackLinkedList extends Pack {
    private final LinkedList<RectangleRotatable> rectangles;

    public PackLinkedList(PackData data) {
        super(data);
        rectangles = new LinkedList<>();
    }

    public void sort() {
        Comparator<RectangleRotatable> recComparator = new Comparator<RectangleRotatable>() {
            @Override
            public int compare(RectangleRotatable r1, RectangleRotatable r2) {
                if (r1.getRotatedWidth() > r2.getRotatedWidth()) {
                    return -1;
                } else if (r1.getRotatedWidth() < r2.getRotatedWidth()) {
                    return 1;
                } else {
                    //r1.getRotatedWidth() == r2.getRotatedWidth()
                    return r1.getRotatedHeight() >= r2.getRotatedHeight() ? -1 : 1;
                }
            }
        };
        Collections.sort(rectangles, recComparator);
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
    public LinkedList<RectangleRotatable> getRectangles() {
        return rectangles;
    }
}
