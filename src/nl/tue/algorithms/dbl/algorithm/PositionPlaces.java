package nl.tue.algorithms.dbl.algorithm;
import nl.tue.algorithms.dbl.common.Pack;
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


public class PositionPlaces implements Iterable<Void> {

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

    public Iterator<Void> iterator() {
        return new PositionPlacesIterator();
    }



    /**
     * Inner class for a low-to-high iterator.
     */
    private class PositionPlacesIterator implements Iterator<Void> {
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
                placement = positions.size();
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
                return placement>sentinel;
            }


            /** places the rectangle at the next possible place
             *
             */

            public Void next() throws NoSuchElementException {
                // Precondition.
                if (! hasNext()) {
                    throw new NoSuchElementException("IntRelationArraysIterator.next");
                }
                // pick the next point
                point = positions.get(placement);
                // adjust the placement
                placement --;

                // get information from the point

                int pointX = point.x;
                int pointY = point.y;
                // place the rectangle
                R1.setLocation(pointX, pointY);

                // check here if the new thingy is valid.
                if (!ValidCheck.isRectangleValidWithinPack(R1, pack)) {
                    R1.setLocation(-1, -1);
                    next();
                    return null;
                }

                // get the information from the rectangle
                int widtha = R1.width;
                int heighta = R1.height;

                // calculate the new points
                Point right = new Point(pointX + widtha , pointY );
                Point up = new Point(pointX, pointY + heighta);

                // add the points to position
                positions.add(right);
                positions.add(up);

                // add the rectangle to rectangles used
                rectanglesUsed.add(R1);

                // place the next rectangle
                BruteForce.FindBestSolution(positions, pack, rectanglesUsed, rectanglesLeft);

                // remove the rectangle
                rectanglesUsed.remove(R1);

                // remove the added positions from the list
                positions.remove(right);
                positions.remove(up);
                return null;
            }


        }
    }
