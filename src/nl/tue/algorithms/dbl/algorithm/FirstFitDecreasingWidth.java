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
public class FirstFitDecreasingWidth extends Algorithm<PackWidthQueue> {   

    int height;
    int xPosition = 0;
    int yPosition = 0;
    int currentShelfWidth;
    
    /** cache of the pack's width and height; Should always be equal to
      * super.getContainerWidth() and super.getContaienrHeight() respectively
      */
    private int containerWidth;
    private int containerHeight;
    
    
    public FirstFitDecreasingWidth(PackData data) {
        super(new PackWidthQueue(data));
        
        this.containerWidth = 0;
        this.containerHeight = 0;
    }

    /**
     * Basic implementation of First-Fit Decreasing Height algorithm.
     */
    @Override
    public void solve() {     
        //do not run if there is no fixed height
        if (pack.hasFixedHeight()) {
            height = pack.getFixedHeight(); //The fixed height of our container.
            RectangleRotatable t = pack.getRectangles().peek(); //We get the first element of queue
            if (pack.canRotate()) { //We set the currentShelfWidth
                currentShelfWidth = Math.max(t.height, t.width);
            } else {
                currentShelfWidth = t.width;
            }

            while (!pack.getRectangles().isEmpty()) {
                RectangleRotatable r = pack.getRectangles().poll();
                placeRectangle(r);
            }
        } else {
            this.containerWidth = Integer.MAX_VALUE;
            this.containerHeight = Integer.MAX_VALUE;
        }
    }

    public void placeRectangle(RectangleRotatable r) {        
        if (r.isRotated()) {
            if (yPosition + r.width <= height) { //New rectangle fits on shelf
                r.x = xPosition;
                r.y = yPosition;
                yPosition += r.width;
            } else { //New rectangle doesn't fit on shelf
                xPosition = currentShelfWidth;
                yPosition = 0;
                r.x = xPosition;
                r.y = yPosition;
                yPosition += r.width;
                currentShelfWidth += r.height;
            }
            
            containerHeight = Math.max(containerHeight, r.y + r.width);
            containerWidth = Math.max(containerWidth, r.x + r.height);
        } else {
            //r is not rotated
            if (yPosition + r.height <= height) { //New rectangle fits on shelf
                r.x = xPosition;
                r.y = yPosition;
                yPosition += r.height;
            } else { //New rectangle doesn't fit on shelf
                xPosition = currentShelfWidth;
                yPosition = 0;
                r.x = xPosition;
                r.y = yPosition;
                yPosition += r.height;
                currentShelfWidth += r.width;
            }
            containerHeight = Math.max(containerHeight, r.y + r.height);
            containerWidth = Math.max(containerWidth, r.x + r.width);
        }
    }
    
    @Override
    public int getContainerHeight() {
        return containerHeight;
    }
    
    @Override
    public int getContainerWidth() {
        return containerWidth;
    }
}
