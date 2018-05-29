package nl.tue.algorithms.dbl.common;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PackHeightQueue extends Pack {

    private final PriorityQueue<RectangleRotatable> rectangles;

    public PackHeightQueue(PackData data) {
        super(data);
        rectangles = new PriorityQueue<>(new Comparator<RectangleRotatable>() {
            @Override
            public int compare(RectangleRotatable o1, RectangleRotatable o2) {
                if (!canRotate()) {
                    return (o1.getHeight() >= o2.getHeight()) ? -1 : 1;
                } else {
                    if (o1.getHeight() > o1.getWidth()) {
                        o1.setRotated(true);
                    }
                    if (o2.getHeight() > o2.getWidth()) {
                        o2.setRotated(true);
                    }
                    if (Math.max(o1.getHeight(), o1.getWidth()) > Math.max(o2.getHeight(), o2.getWidth())) {
                        return -1;
                    } else if (Math.max(o1.getHeight(), o1.getWidth()) < Math.max(o2.getHeight(), o2.getWidth())) {
                        return 1;
                    } else { // Rectangles have the same max(h,w)
                        if (Math.min(o1.getHeight(), o1.getWidth()) >= Math.min(o2.getHeight(), o2.getWidth())) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                }
            }
        }); // Turn PriorityQueue into Max Heap.
    }

    @Override
    protected void addRectangleSubclass(RectangleRotatable rec) {
        rectangles.add(rec);
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