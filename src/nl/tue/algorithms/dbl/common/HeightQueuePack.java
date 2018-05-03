package nl.tue.algorithms.dbl.common;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class HeightQueuePack extends Pack {

    public HeightQueuePack() {
        super();
        rectangles = new PriorityQueue<>(new Comparator<Rectangle>() {
            @Override
            public int compare(Rectangle o1, Rectangle o2) {
                return (o1.getHeight() > o2.getHeight()) ? -1 : 1;
            }
        }); // Turn PriorityQueue into Max Heap.
    }

    @Override
    protected void addRectangleSubclass(Rectangle rec) {
        rectangles.add(rec);
    }

    @Override
    public int getNumberOfRectangles() {
        return rectangles.size();
    }

    @Override
    public Collection<Rectangle> getRectanglesList() {
        return rectangles;
    }
}
