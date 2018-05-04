package nl.tue.algorithms.dbl.common;

import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;

public class HeightQueuePack extends Pack {

    private PriorityQueue<Rectangle> rectangles;
    public HeightQueuePack() {
        super();
        rectangles = new PriorityQueue<>(new Comparator<Rectangle>() {
            @Override
            public int compare(Rectangle o1, Rectangle o2) {
                return (o1.getHeight() < o2.getHeight()) ? -1 : 1;
            }
        }); // Turn PriorityQueue into Max Heap.
    }
    public Rectangle poll() {
        return rectangles.poll();
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
    public PriorityQueue<Rectangle> getRectangles() {
        return rectangles;
    }
}
