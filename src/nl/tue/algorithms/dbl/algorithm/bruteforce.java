package nl.tue.algorithms.dbl.algorithm;

import nl.tue.algorithms.dbl.common.PackData;
import nl.tue.algorithms.dbl.common.PackWidthQueue;
import nl.tue.algorithms.dbl.common.Rectangle;

/**
 * Algorithm that brute forces a solution, with no rotation and no fixed height
 *
 * @author Tom Verberk
 * @since 16 May 2018
 */

 public class bruteforce1 extends Algorithm<PackData> {

   public bruteforce1(PackData data) {
       super(new PackData(data));
   }

   @Override
   public void solve(){ // method that solves this shit
     // declaring important variants
     List<Rectangle> rectangles = pack.getRectangles();
     int Rectanglesleft = rectangles.getnumberOfRectangles();
     int bestSolution = 1000000000;
     List<Rectangle> rectanglesUsed = null;
     findBestSolution(rectangles, rectanglesUsed, rectanglesLeft);
   }
     
   public int findBestSolution(List<Rectangle> rectangles, List<Rectangle> rectanglesUsed, int rectanglesLeft){
       if(rectanglesLeft!=0){ // add another rectangle
           for (Rectangle a: rectangles){ // loop over all the rectangles
                   boolean alreadyUsed = false;
                   for (Rectangle alreadyUsed: rectanglesUsed){ // check if the rectangle was already used
                           if(a.getID()==alreadyUsed.get(ID)){
                                 boolean alreadyUsed = true;
                               }
                   }
                  if(!alreadyUsed){
                        for(place p: possibleplaces){ //TRY ALL THE PLACES!!!
                                    //TRY ALL THE PLACES!!, has to be implemented
                                    a.setX(p.getX);
                                    a.setY(p.getY);
                          }
                        place(rectangles, rectanglesUsed, rectanglesLeft-1)
                  }
           }
       }
       else{ //rectanglesLeft == 0;
           if(areaSolution < bestSolution){
             bestSolution = areaSolution;
           }
       }
   }
   }
 }
