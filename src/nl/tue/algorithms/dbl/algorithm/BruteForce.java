package nl.tue.algorithms.dbl.algorithm;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import nl.tue.algorithms.dbl.common.PackData;
import nl.tue.algorithms.dbl.common.PackList;
import nl.tue.algorithms.dbl.common.RectangleRotatable;

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
   

   @Override
   public void solve(){ // method that solves this shit
     // declaring important variants
     List<RectangleRotatable> rectangles = pack.getRectangles();
     int rectanglesLeft = rectangles.size();
     int bestSolution = 1000000000;
     List<RectangleRotatable> rectanglesUsed;
     rectanglesUsed = new LinkedList<>();
     
      //initiate the possiblePlaces list
     Point startPosition = new Point(0, 0);
     possiblePlaces = new LinkedList<>();
     possiblePlaces.add(startPosition);
     
     findBestSolution(rectangles, rectanglesUsed, rectanglesLeft);
   }
     
   public int findBestSolution(List<RectangleRotatable> rectangles, List<RectangleRotatable> rectanglesUsed, int rectanglesLeft){
       int bestSolution = 0;
       if(rectanglesLeft!=0){ // add another rectangle
           for (RectangleRotatable a: rectangles){ // loop over all the rectangles
                   boolean alreadyUsed = false;
                   if(!rectanglesUsed.isEmpty()){
                   for(RectangleRotatable usedRectangle: rectanglesUsed){ // check if the rectangle was already used
                           if(a.getID()==usedRectangle.getID()){
                                 alreadyUsed = true;
                               }
                   }
                   }
                  if(!alreadyUsed){
                        // change possiblePlaces
                      
                      
                        for(Point p: possiblePlaces){
                            
                            // hasPrevious();
                            // length of the possibleplaces;
                            // Iterator.
                            
                            List<Point> added = PlaceRectangle(a, rectanglesUsed, p);
                            //possiblePlaces.addAll(added);
                            //possiblePlaces.remove(p);
                            findBestSolution(rectangles, rectanglesUsed, rectanglesLeft-1);
                        }
                }
           }
       }
       
       else{ //rectanglesLeft == 0;
           //calculate solution;
           
           int areaSolution = 2;
           
           if(bestSolution==0){
               bestSolution = areaSolution;
           } else if (bestSolution < areaSolution){
             bestSolution = areaSolution;
           }
       }
       return bestSolution;
   }
   
   public List<Point> PlaceRectangle(RectangleRotatable a, List<RectangleRotatable> rectanglesUsed, Point p){
       int widtha = (int) a.getWidth();
       int heighta = (int) a.getHeight();
       int pointX = (int) p.getX();
       int pointY = (int) p.getY();
       a.setLocation(pointX, pointY);
       Point right = new Point(pointX + widtha , pointY );
       Point up = new Point(pointX, pointY + heighta);
       List<Point> newPoints;
       newPoints = new LinkedList<Point>();
       newPoints.add(right);
       newPoints.add(up);
       return newPoints;
   }
   
   }
 