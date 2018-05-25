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
 * Algorithm that brute forces a solution, with no rotation and no fixed height
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

    @Override
    public void solve() { // method that solves this shit
        // declaring important variants
        int rectanglesLeft = pack.getRectangles().size();
        int bestSolution = Integer.MAX_VALUE;
        List<RectangleRotatable> rectanglesUsed;
        rectanglesUsed = new LinkedList<>();


        //initiate the possiblePlaces list
        Point startPosition = new Point(0, 0);
        possiblePlaces = new LinkedList<>();
        possiblePlaces.add(startPosition);

        FindBestSolution(possiblePlaces, pack, rectanglesUsed, rectanglesLeft);
        for(RectangleRotatable a: bestSolutionRectangles){
            System.out.println(a.getLocation());
        }
        copyToPack(bestSolutionRectangles,pack);

    }

    public void FindBestSolution(List<Point> possiblePlaces, PackList pack, List<RectangleRotatable> rectanglesUsed, int rectanglesLeft) {

        if (rectanglesLeft != 0) { // add another rectangle
            for (RectangleRotatable a : pack.getRectangles()) { // loop over all the rectangles

                boolean alreadyUsed = false;
                if (!rectanglesUsed.isEmpty()) {
                    for (RectangleRotatable usedRectangle : rectanglesUsed) { // check if the rectangle was already used
                        if (a.getID() == usedRectangle.getID()) {
                            alreadyUsed = true;
                        }
                    }
                }
                if (!alreadyUsed) {


                    // initiate the possibleplaces
                    PositionPlaces positions = new PositionPlaces(a, possiblePlaces, pack, rectanglesUsed, rectanglesLeft);
                    Iterator iter = positions.iterator();
                    boolean isValidPosition = false;
                    while(iter.hasNext()){
                        while (!isValidPosition && iter.hasNext()) {

                            Point p = (Point) iter.next();

                            // get information from the point

                            int pointX = p.x;
                            int pointY = p.y;

                            // place the rectangle
                            a.setLocation(pointX, pointY);




                            // check here if the new thingy is valid.
                            if (!ValidCheck.isRectangleValidWithinPack(a, pack)) {
                                a.setLocation(Integer.MIN_VALUE, Integer.MIN_VALUE);
                                iter.remove();
                            } else {//

                                // place the rectangle
                                isValidPosition = true;
                            }
                        }

                        if(isValidPosition) { // check if the last rectangle places was valid
                            isValidPosition = false;

                            // add the rectangle to rectangles used
                            rectanglesUsed.add(a);

                            // get the new possitions from the iterator
                            possiblePlaces = positions.getPositions();


                            FindBestSolution(possiblePlaces, pack, rectanglesUsed, rectanglesLeft - 1);

                            // remove the rectangle
                            rectanglesUsed.remove(a);
                        }
                    }
                }
            } return;
        } else {  // if rectanglesLeft == 0
            int maxRightBorder = 0;
            int maxTopBorder = 0;
            for (RectangleRotatable R1 : pack.getRectangles()) {
                // calculate the rightborder for each rectangle
                int rightBorder = R1.x + R1.width;
                // check whether this is greater than the current rightest border
                if (rightBorder > maxRightBorder) {
                    maxRightBorder = rightBorder; // change the new one
                }
                // calculate the topborder for each rectangle
                int topBorder = (int) (R1.getY() + R1.getHeight());
                // check whether this is greater than the current rightest border
                if (topBorder > maxTopBorder) {
                    maxTopBorder = topBorder; // change the new one
                }
            }
            newSolution = maxRightBorder * maxTopBorder;
            if (newSolution < bestSolution) {
                // change the best solution
                System.out.println(bestSolution);
                System.out.println(newSolution);
                bestSolution = newSolution;
                bestSolutionRectangles = new LinkedList<>();


                for(RectangleRotatable a: rectanglesUsed) {
                    bestSolutionRectangles.add((RectangleRotatable) a.clone());
                }

            }
            return;
        }
    }


    public void copyToPack(List<RectangleRotatable> Rectangles, PackList pack){
        for(RectangleRotatable R1: pack.getOrderedRectangles()){
            int idA=R1.getID();
            for(RectangleRotatable R2: Rectangles) {
                int idB = R2.getID();
                if (idA == idB) {
                    R1.setLocation(R2.getLocation());
                }
            }
        }
    }

 }
