package nl.tue.algorithms.dbl.algorithm;
import nl.tue.algorithms.dbl.common.PackList;
import nl.tue.algorithms.dbl.common.RectangleRotatable;
import nl.tue.algorithms.dbl.common.ValidCheck;

import java.awt.*;
import java.util.*;
import java.util.List;

/*
 * an implementation of positionPlacesIterator
 *
 * @author s162449
 * name: Tom Verberk
 * studentID: 1016472
 */


public class PositionPlaces implements Iterable<Point> {

    // list of all places
    List<Point> positions;
    PackList pack;
    List<RectangleRotatable> rectanglesUsed;
    int rectanglesLeft;
    RectangleRotatable R1;
    Point point;
    Point pointRight;
    Point pointUp;


    /** Constructor for general range.  */
    public PositionPlaces(RectangleRotatable a, final List<Point> points, PackList pack, List<RectangleRotatable> recUsed, Integer recLeft ) {
        positions= points;
        this.pack = pack;
        rectanglesUsed = recUsed;
        rectanglesLeft = recLeft;
        R1= a;

    }

    public Iterator<Point> iterator() {
        return new PositionPlacesIterator();
    }

    public List<Point> getPositions(){
        return positions;
    }


    /**
     * Inner class for a low-to-high iterator.
     */
    private class PositionPlacesIterator implements Iterator<Point> {
        /** Current placement for iterator. */
        private int placement;

        /** Limit of iteration per one dimension. */
        private final int sentinel;

            /**
             * Constructs iterator in initial state.
             *
             * @pre {@code true}
             * @post {@code placement == 0 && sentinel == extent() && returned == 0
             *               && number == (\num of i; i < extent() * extent();
             *               areRelated(i / extent(), i % extent()); )}
             */
            public PositionPlacesIterator() {
                // placement starts with the size of position.
                placement = 0;
                // and goes to 0
                sentinel = positions.size()-1;

            }
            /**
             * Check if it has next element.
             *
             * @pre {@code true}
             * @post {@code \result == (placement < sentinel * sentinel && returned < amountPairs)}
             * @return  True, if next element exists, otherwise false
             */
            public boolean hasNext()
            {
                return placement<=sentinel;
            }


            /** places the rectangle at the next possible place
             *
             */

            public Point next() throws NoSuchElementException {
                // Precondition.
                if (! hasNext()) {
                    throw new NoSuchElementException("IntRelationArraysIterator.next");
                }
                // pick the next point
                point = positions.get(placement);

                // coordinates of the point
                int pointX = point.x;
                int pointY = point.y;

                // width and height of the rectangle
                int widtha = R1.width;
                int heighta = R1.height;

                // ad the new points
                pointRight = new Point(pointX + widtha , pointY );
                pointUp = new Point(pointX, pointY + heighta);

                // add the points to position
                positions.add(pointRight);
                positions.add(pointUp);

                placement ++;
                return point;
            }

            public void remove(){
                positions.remove(pointRight);
                positions.remove(pointUp);
            }


        }
    }