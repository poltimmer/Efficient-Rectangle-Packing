package nl.tue.algorithms.dbl.algorithm;
import nl.tue.algorithms.dbl.common.RectangleRotatable;

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
    List positions = new ArrayList<Point>();
    List<RectangleRotatable> rectangles = new ArrayList<RectangleRotatable>();
    List<RectangleRotatable> rectanglesUsed = new ArrayList<RectangleRotatable>();
    int rectanglesLeft;
    RectangleRotatable R1;
    Point point = new Point();


    /** Constructor for general range.  */
    public PositionPlaces(RectangleRotatable a, Point p, final List<Point> points, List<RectangleRotatable> rec, List<RectangleRotatable> recUsed, Integer recLeft ) {
        positions= points;
        rectangles = rec;
        rectanglesUsed = recUsed;
        rectanglesLeft = recLeft;
        RectangleRotatable R1= a;
        point = p;


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

        /** place where the extra points are added */
        private int addHere;





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
            public boolean hasNext() {
                return placement>sentinel;
            }

            public Void next() throws NoSuchElementException {
                // Precondition.
                if (! hasNext()) {
                    throw new NoSuchElementException("IntRelationArraysIterator.next");
                }
                placement--;
                int widtha = R1.width;
                int heighta = R1.height;
                int pointX = point.x;
                int pointY = point.y;
                R1.setLocation(pointX, pointY);

                // check here if the new thingy is valid.

                Point right = new Point(pointX + widtha , pointY );
                Point up = new Point(pointX, pointY + heighta);
                positions.add(right);
                positions.add(up);
                rectanglesUsed.add(R1);
                BruteForce.FindBestSolution(positions, rectangles, rectanglesUsed, rectanglesLeft);
                rectanglesUsed.remove(R1);
                positions.remove(right);
                positions.remove(up);


                return null;
            }



        /**
             * For that case remove is unsupported operation.
             *
             * @throws UnsupportedOperationException  if precondition violated
             * @pre {@code false}
             * @post {@code none}
             */
            public void remove() throws UnsupportedOperationException {
                throw new UnsupportedOperationException("Unsupported operation remove");
            }


        }
    }
