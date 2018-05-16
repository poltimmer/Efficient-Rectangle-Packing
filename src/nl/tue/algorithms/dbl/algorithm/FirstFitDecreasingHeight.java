package nl.tue.algorithms.dbl.algorithm;

import nl.tue.algorithms.dbl.common.PackData;
import nl.tue.algorithms.dbl.common.PackWidthQueue;
import nl.tue.algorithms.dbl.common.RectangleRotatable;

/**
 * Algorithm for strip packing, places the rectangles in decreasing order until the rectangles don't fit on current
 * 'shelf' anymore, when we start a new shelf.
 *
 * @author Koen Degeling
 * @since 9 MAY 2018
 */
public class FirstFitDecreasingHeight extends Algorithm<PackWidthQueue> {   

    public FirstFitDecreasingHeight(PackData data) {
        super(new PackWidthQueue(data));
    }
    
    @Override
    public void solve() {
        try {            
            final int height = pack.getContainerHeight(); //The fixed height of our container.
            RectangleRotatable t = pack.getRectangles().poll();
            t.setLocation(0, 0); //Set first rectangle
            int currentShelfWidth = (int) t.getWidth(); //Width of the current shelf.
            //position for the new triangle
            int xPosition = 0;
            int yPosition = (int) t.getHeight();
            while (!pack.getRectangles().isEmpty()) {
                RectangleRotatable r = pack.getRectangles().poll();
                if ((yPosition + r.getHeight()) <= height) {
                    // We set the position of the triangle on the shelf
                    r.setLocation(xPosition, yPosition);
                    yPosition += r.getHeight();
                } else {
                    //Triangle doesn't fir on the shelf, so we place it on new one
                    xPosition = currentShelfWidth;
                    yPosition = 0;
                    currentShelfWidth += r.getWidth();
                    r.setLocation(xPosition, yPosition);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
