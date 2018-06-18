package nl.tue.algorithms.dbl.algorithm;

import nl.tue.algorithms.dbl.common.PackData;
import nl.tue.algorithms.dbl.common.PackList;
import nl.tue.algorithms.dbl.common.RectangleRotatable;
import nl.tue.algorithms.dbl.common.ValidCheck;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Algorithm that brute forces a solution. It tries all possibilities and presents
 * the best solution out of those. That is, the possibility with the lowest area.
 *
 * @author Tom Verberk
 * @since 16 May 2018
 */
 public class BruteForce extends Algorithm<PackList> {

    public BruteForce(PackData data) {
        super(new PackList(data));
    }

    public List<Point> possiblePlaces;
    public int bestSolution = Integer.MAX_VALUE;
    public int newSolution;
    public List<RectangleRotatable> bestSolutionRectangles;
    int count = 0;
    int limit = 1;
    
    //Whether a solution with 100% coverage has been found
    private boolean hundredPercentCoverageFound = false;

    @Override
    public void solve() { // method that solves this shit
        // declaring important variants
        int rectanglesLeft = pack.getOrderedRectangles().size();
        ValidCheck.println(pack.getOrderedRectangles());

        List<RectangleRotatable> rectanglesUsed;
        rectanglesUsed = new LinkedList<>();
        int amountRectangles = rectanglesLeft;
        if (amountRectangles<8){
            for(int i = 1; i<=amountRectangles; i++){
                limit = limit * i*i;
                ValidCheck.println("limit = " + limit);
            }
        } else {
            limit = Integer.MAX_VALUE-1;
        }
        if(pack.canRotate()){
            limit = limit*4;
        }

        //initiate the possiblePlaces list
        Point startPosition = new Point(0, 0);
        possiblePlaces = new LinkedList<>();
        possiblePlaces.add(startPosition);

        findBestSolution(possiblePlaces, pack, rectanglesUsed, rectanglesLeft);
        ValidCheck.println("count =" + count);
        copyToPack(bestSolutionRectangles,pack);

    }

    public void findBestSolution(List<Point> possiblePlaces, PackList pack, List<RectangleRotatable> rectanglesUsed, int rectanglesLeft) {
        // check if we have already found a solution with 100% coverage (cannot be improved)
        // also check if current solution is better than best solution
        // kinda untested, but for example the test input 5 rectangles v1 gives better results when this is enabled...
        if (hundredPercentCoverageFound || !solutionStillBetterThanBest()) {
            return;
        }
        
        
        if (count > limit) {
            return;
        } else {
            if (rectanglesLeft > 0) { // add another rectangle
                for (RectangleRotatable a : pack.getOrderedRectangles()) { // loop over all the rectangles
                    boolean alreadyUsed = false;
                    if (!rectanglesUsed.isEmpty()) {
                        for (RectangleRotatable usedRectangle : rectanglesUsed) { // check if the rectangle was already used
                            if (a.getID() == usedRectangle.getID()) {
                                alreadyUsed = true;
                                break; // break the for loop.
                            }
                        }
                    }
                    if (!alreadyUsed) { // if  the rectangle was not already used
                        // initiate the Iterator
                        PositionPlaces positions = new PositionPlaces(a, possiblePlaces, pack, rectanglesUsed, rectanglesLeft);
                        Iterator iter = positions.iterator();

                        boolean rotated = !pack.canRotate();

                        // loop over all the possible positions
                        while (iter.hasNext() || !rotated) {

                            // get the new point and add new points to possibleplaces
                            if(pack.canRotate()) {
                                positions.setChange(rotated);
                            }
                            // get information from the point
                            Point p = (Point) iter.next();

                            int pointX = p.x;
                            int pointY = p.y;

                            // place the rectangle
                            a.setLocation(pointX, pointY);

                            if (pack.canRotate()){
                                a.setRotated(rotated);
                                rotated = !rotated;
                            }

                            // check here if the new thingy is valid.
                            if (!ValidCheck.isRectangleValidWithinPack(a, pack)) {
                                a.setLocation(Integer.MIN_VALUE, Integer.MIN_VALUE);
                                // remove the points that were added
                                if(pack.canRotate()) {
                                    a.setRotated(false);
                                }
                                iter.remove();

                            } else { //Valid position // if it is valid start the recursion
                                // add the rectangle to rectangles used
                                rectanglesUsed.add(a);

                                // get the new possitions from the iterator
                                possiblePlaces = positions.getPositions();

                                // recurse over the pack with the new rectangles.
                                findBestSolution(possiblePlaces, pack, rectanglesUsed, rectanglesLeft - 1);

                                // remove the rotation if it is rotated
                                if(pack.canRotate()) {
                                    a.setRotated(false);
                                }
                                // remove the points again
                                iter.remove();

                                // reset the coordinates of the rectangle
                                a.setLocation(Integer.MIN_VALUE, Integer.MIN_VALUE);

                                // remove the rectangle
                                rectanglesUsed.remove(a);
                            }
                        }
                    }
                }
            } else {  // if rectanglesLeft == 0
                count++;
                
                newSolution = getContainerArea();
                if (newSolution < bestSolution) {
                    // change the best solution
                    ValidCheck.println("new solution = " + newSolution);
                    ValidCheck.println("old solution was = " + bestSolution);
                    bestSolution = newSolution;

                    // place the best solution in a new list.
                    bestSolutionRectangles = new LinkedList<>();

                    for (RectangleRotatable a : rectanglesUsed) {
                        bestSolutionRectangles.add(a.copy());
                    }
                    
                    //optimal solution found. I.e. No unused area left
                    if (newSolution - pack.getUsedArea() == 0) {
                        hundredPercentCoverageFound = true;
                        ValidCheck.println("100% COVERAGE FOUND!!!");
                    }

                }
            }
        }
    }


    public void copyToPack(List<RectangleRotatable> Rectangles, PackList pack){
        for(RectangleRotatable R1: pack.getOrderedRectangles()){
            int idA=R1.getID();
            for(RectangleRotatable R2: Rectangles) {
                int idB = R2.getID();
                if (idA == idB) {
                    R1.setLocation(R2.getLocation());
                    R1.setRotated(R2.isRotated());
                }
            }
        }
    }
    
    private boolean solutionStillBetterThanBest() {
        return bestSolution > getContainerArea();
    }

 }
