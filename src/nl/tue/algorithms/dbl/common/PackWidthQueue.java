package nl.tue.algorithms.dbl.common;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PackWidthQueue extends Pack {

    private PriorityQueue<RectangleRotatable> rectangles;
    public PackWidthQueue(PackData data) {
        super(data);
        rectangles = new PriorityQueue<>(new Comparator<RectangleRotatable>() {
            @Override
            public int compare(RectangleRotatable o1, RectangleRotatable o2) {
                if (!canRotate()) {
                    return (o1.getWidth() >= o2.getWidth()) ? -1 : 1;
                } else {
                    if (Math.max(o1.getHeight(), o1.getWidth()) >= Math.max(o2.getHeight(), o2.getWidth())) {
                        if (o1.getHeight() > o1.getWidth()) {
                            o1.setRotated(true);
                        }
                        return -1;
                    } else {
                        if (o2.getHeight() > o2.getWidth()) {
                            o2.setRotated(true);
                        }
                        return 1;
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
        return rectangles.size();
    }

    @Override
    public PriorityQueue<RectangleRotatable> getRectangles() {
        return rectangles;
    }
}
