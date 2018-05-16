package nl.tue.algorithms.dbl.algorithm;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * an implementation of positionPlacesIterator
 *
 * @author s162449
 * name: Tom Verberk
 * studentID: 1016472
 */


public class PositionPlaces implements Iterable<Point>{

    // list of all places
    ArrayList positions = new ArrayList<Point>();

    /** Constructor for general range.  */
    public PositionPlaces(final ArrayList<Point> points) {
        positions= points;
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

            public Point next() throws NoSuchElementException {
                // Precondition.
                if (! hasNext()) {
                    throw new NoSuchElementException("IntRelationArraysIterator.next");
                }
                placement--;
                Point p = null;
                return p;
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
