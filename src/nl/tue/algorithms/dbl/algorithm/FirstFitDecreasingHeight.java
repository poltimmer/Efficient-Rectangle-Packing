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

    int height;
    int xPosition = 0;
    int yPosition = 0;
    int currentShelfWidth;

    /**
     * Basic implementation of First-Fit Decreasing Height algorithm.
     */
    @Override
    public void solve() {
            height = pack.getFixedHeight(); //The fixed height of our container.
            RectangleRotatable t = pack.getRectangles().peek(); //We get the first element of queue
            if (pack.canRotate()) { //We set the currentShelfWidth
                currentShelfWidth = Math.max((int)t.getHeight(), (int)t.getWidth());
            } else {
                currentShelfWidth = t.width;
            }

            while (!pack.getRectangles().isEmpty()) {
                RectangleRotatable r = pack.getRectangles().poll();
                placeRectangle(r);
            }
    }


    public void placeRectangle(RectangleRotatable r) {
        if (r.isRotated()) {
            if (yPosition + (int)r.getWidth() <= height) { //New rectangle fits on shelf
                r.x = xPosition;
                r.y = yPosition;
                yPosition += r.getWidth();
            } else { //New rectangle doesn't fit on shelf
                xPosition = currentShelfWidth;
                yPosition = 0;
                r.x = xPosition;
                r.y = yPosition;
                yPosition += (int)r.getWidth();
                currentShelfWidth += r.getHeight();
            }
        } else {
            if (yPosition + (int)r.getHeight() <= height) { //New rectangle fits on shelf
                r.x = xPosition;
                r.y = yPosition;
                yPosition += r.getHeight();
            } else { //New rectangle doesn't fit on shelf
                xPosition = currentShelfWidth;
                yPosition = 0;
                r.x = xPosition;
                r.y = yPosition;
                yPosition += (int)r.getHeight();
                currentShelfWidth += r.getWidth();
            }
        }
    }
}
