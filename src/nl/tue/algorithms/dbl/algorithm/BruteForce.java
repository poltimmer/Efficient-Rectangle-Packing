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
 public class BruteForce extends Algorithm<PackList>{

   public BruteForce(PackData data) {
       super(new PackList(data));
   }
   
   public List<Point> possiblePlaces;
   public int bestSolution = Integer.MAX_VALUE;

   @Override
   public void solve(){ // method that solves this shit
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
   }
     
   public void FindBestSolution(List<Point> possiblePlaces, PackList pack, List<RectangleRotatable> rectanglesUsed, int rectanglesLeft){

       if(rectanglesLeft!=0) { // add another rectangle
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
                   System.out.println("!alreadyUsed");
                   // change possiblePlaces

                       // hasPrevious();
                       // length of the possibleplaces;
                       // Iterator.
                       PositionPlaces positions = new PositionPlaces(a, possiblePlaces, pack, rectanglesUsed, rectanglesLeft);
                       Iterator iter = positions.iterator();
                       Point p = (Point) iter.next();

                       // get information from the point

                       int pointX = p.x;
                       int pointY = p.y;

                       // place the rectangle
                       a.setLocation(pointX, pointY);

                       System.out.println("outside");
                       // check here if the new thingy is valid.
                       if (!ValidCheck.isRectangleValidWithinPack(a, pack)) {
                           System.out.println("hallo");
                           a.setLocation(-1, -1);
                           iter.remove();
                           return;
                       }


                       // add the rectangle to rectangles used
                       rectanglesUsed.add(a);

                       // add the with and height to possiblePlaces
                       possiblePlaces = positions.getPositions();
                       FindBestSolution(possiblePlaces, pack, rectanglesUsed, rectanglesLeft-1);
                       // remove the rectangle
                       rectanglesUsed.remove(a);

                       return;
                   }
               }
           }
       }
   }
 