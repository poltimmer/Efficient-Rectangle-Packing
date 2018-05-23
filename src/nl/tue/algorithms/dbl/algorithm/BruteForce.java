package nl.tue.algorithms.dbl.algorithm;

import nl.tue.algorithms.dbl.common.PackData;
import nl.tue.algorithms.dbl.common.PackList;
import nl.tue.algorithms.dbl.common.RectangleRotatable;

import java.awt.*;
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
     
   public static void FindBestSolution(List<Point> possiblePlaces, PackList pack, List<RectangleRotatable> rectanglesUsed, int rectanglesLeft){

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
                   // change possiblePlaces


                   for (Point p : possiblePlaces) {

                       // hasPrevious();
                       // length of the possibleplaces;
                       // Iterator.
                       new PositionPlaces(a, p, possiblePlaces, pack, rectanglesUsed, rectanglesLeft);
                       //possiblePlaces.addAll(added);
                       //possiblePlaces.remove(p);
                       //findBestSolution(rectangles, rectanglesUsed, rectanglesLeft-1);
                   }
               }
           }
       }
   }
   
   public List<Point> PlaceRectangle(RectangleRotatable a, List<RectangleRotatable> rectanglesUsed, Point p){
       int widtha = a.width;
       int heighta = a.height;
       int pointX = p.x;
       int pointY = p.y;
       a.setLocation(pointX, pointY);
       Point right = new Point(pointX + widtha , pointY );
       Point up = new Point(pointX, pointY + heighta);
       List<Point> newPoints = new LinkedList<>();
       newPoints.add(right);
       newPoints.add(up);
       return newPoints;
   }
   
   }
 