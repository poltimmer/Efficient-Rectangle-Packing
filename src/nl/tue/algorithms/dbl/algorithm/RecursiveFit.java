package nl.tue.algorithms.dbl.algorithm;

import nl.tue.algorithms.dbl.common.PackData;
import nl.tue.algorithms.dbl.common.PackLinkedList;
import nl.tue.algorithms.dbl.common.RectangleRotatable;

import java.util.Iterator;
import java.util.LinkedList;

public class RecursiveFit extends Algorithm<PackLinkedList> {
    /** The container height */
    private int H;
    private boolean guessedHeight;
    
    private boolean wasRun;
    
    /** The linked list containing all the rectangles to be placed.
     *  After a rectangle has been placed, it will be removed from the list */
    private LinkedList<RectangleRotatable> list;

    public RecursiveFit(PackData data) {
        super(new PackLinkedList(data));
        H = pack.getFixedHeight();
        wasRun = false;
        guessedHeight = !pack.hasFixedHeight();
    }

    @Override
    public void solve() {
        if (pack.hasFixedHeight()) {
            pack.sort();
            list = pack.getRectangles();
            placeRectangle(list.pollFirst(), 0, 0, Integer.MAX_VALUE);
            
            wasRun = true;
        } else {
            pack.sort();
            list = pack.getRectangles();
            H = 2 * Math.max(list.getFirst().width, list.getFirst().height);
            placeRectangle(list.pollFirst(), 0, 0, Integer.MAX_VALUE);

            wasRun = true;
        }
    }

    /**
     * Recurrence for strip packing problem
     * @param r the rectangle to be placed
     * @param x the x coordinate of the location
     * @param y the y coordinate of the location
     * @param s the shelf height
     */
    private void placeRectangle(RectangleRotatable r, int x, int y, int s) {
        /** We place rectangle */
        r.x = x; r.y = y;
        /** Place new rectangle */
        Iterator<RectangleRotatable> it = list.iterator();
        while (it.hasNext()) {
            RectangleRotatable rNew = it.next();
            int yNew = (r.isRotated()) ? y + (int)r.getWidth() : y + (int)r.getHeight();
            int sNew = (r.isRotated()) ? x + (int)r.getHeight() : x + (int)r.getWidth();
            if (rectangleFitter(rNew, x, yNew, sNew)) {
                it.remove();
                placeRectangle(rNew, x, yNew, sNew);
                break;
            }
        }
        /** We start a new shelf */
        it = list.iterator();
        while (it.hasNext()) {
            RectangleRotatable rNew = it.next();
            int xNew = (r.isRotated()) ? x + (int)r.getHeight() : x + (int)r.getWidth();
            if (rectangleFitter(rNew, xNew, y ,s)) {
                it.remove();
                placeRectangle(rNew, xNew, y, s);
                break;
            }
        }
    }

    /**
     * Returns the remaining area when placing rectangle r at pos(x,y) with bounds s and H. Takes rotation of objects
     * into account.
     * @param r rectangle to be placed
     * @param x position
     * @param y position
     * @param s shelf position
     * @return \return A >= 0 if r fits, where A is the remaining area or \return = -1 if r doesn't fit
     */
    public boolean rectangleFitter(RectangleRotatable r, int x, int y, int s) {
        if (pack.canRotate()) {
            if ((x+(int)r.getHeight()<=s) && (y+(int)r.getWidth()<=H)) {
                r.setRotated(true);
                return true;
            } else if ((x+(int)r.getWidth()<=s) && (y+(int)r.getHeight()<=H)) {
                r.setRotated(false);
                return true;
            } else {
                return false;
            }
        } else if (r.height > H) {
            //special case if a rectangle exceeds the fixed height, but according
            //to the pack we cannot rotate.
            if (guessedHeight) {
                //our guess was wrong => update it
                H = r.height;
                return (x + r.width <= s) && (y + r.height <= H);
            } else {
                //if we were given this height, the given input was invalid.
                //We have to break a rule so rotate the rectanglea anyway
                r.setRotated(true);

                return x + r.height <= s && y + r.width <= H;
            }
        } else {
            return (x+(int)r.getWidth()<=s) && (y+(int)r.getHeight()<=H);
        }
    }
    
    @Override
    public int getContainerHeight() {
        return wasRun ? super.getContainerHeight() : Integer.MAX_VALUE;
    }
    
    @Override
    public int getContainerWidth() {
        return wasRun ? super.getContainerWidth() : Integer.MAX_VALUE;
    }
}
