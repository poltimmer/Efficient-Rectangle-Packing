package nl.tue.algorithms.dbl.common;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class PackLinkedList extends Pack {
    /**
     * Stores the rectangles in a sorted LinkedList. If rotations are allowed, the list will store the rectangle on
     * max (height, width).
     */
    private LinkedList<RectangleRotatable> rectangles;

    public PackLinkedList(PackData data) {
        super(data);
        rectangles = new LinkedList<>();
    }

    public void sort() {
        Comparator<RectangleRotatable> recComparator = new Comparator<RectangleRotatable>() {
            @Override
            public int compare(RectangleRotatable o1, RectangleRotatable o2) {
                if (!canRotate()) {
                    return (o1.getWidth() >= o2.getWidth()) ? -1 : 1;
                } else {
                    if (o2.getHeight() > o2.getWidth()) {
                        o2.setRotated(true);
                    }
                    if (o1.getHeight() > o1.getWidth()) {
                        o1.setRotated(true);
                    }

                    if (Math.max(o1.getHeight(), o1.getWidth()) > Math.max(o2.getHeight(), o2.getWidth())) {
                        return -1;
                    } else if (Math.max(o1.getHeight(), o1.getWidth()) < Math.max(o2.getHeight(), o2.getWidth())) {
                        return 1;
                    } else {
                        if (Math.min(o1.getHeight(), o1.getWidth()) >= Math.min(o2.getHeight(), o2.getWidth())) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                }
            }
        };
        Collections.sort(rectangles, recComparator);
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
    public LinkedList<RectangleRotatable> getRectangles() {
        return rectangles;
    }
}
