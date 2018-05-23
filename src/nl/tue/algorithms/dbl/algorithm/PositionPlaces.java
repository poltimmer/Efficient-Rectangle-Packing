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


    /** Constructor for general range.  */
    public PositionPlaces(RectangleRotatable a, final List<Point> points, PackList pack, List<RectangleRotatable> recUsed, Integer recLeft ) {
        positions= points;
        this.pack = pack;
        rectanglesUsed = recUsed;
        rectanglesLeft = recLeft;
        RectangleRotatable R1= a;

    }

    public Iterator<Point> iterator() {
        return new PositionPlacesIterator();
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
                placement = positions.size()-1;
                // and goes to 0
                sentinel = 0;

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
                return placement>=sentinel;
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
                // adjust the placement
                placement --;
                return point;
            }


        }
    }
