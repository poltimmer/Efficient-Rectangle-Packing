package nl.tue.algorithms.dbl.algorithm;

import nl.tue.algorithms.dbl.common.PackWidthQueue;
import nl.tue.algorithms.dbl.common.Rectangle;
import nl.tue.algorithms.dbl.io.InputReader;

/**
 * Algorithm for strip packing, places the rectangles in decreasing order until the rectangles don't fit on current
 * 'shelf' anymore, when we start a new shelf.
 *
 * @author Koen Degeling
 */
public class FirstFitDecreasingHeight {
    public static void main(String args[]) {
        new FirstFitDecreasingHeight().solve();
    }
    /**
     *
     */
    public void solve() {
        PackWidthQueue p = new PackWidthQueue();
        try {
            InputReader<PackWidthQueue> i = new InputReader<>(System.in);
            p = i.readInput(p);
            final int height = p.getContainerHeight(); //The fixed height of our container.
            Rectangle t = p.getRectangles().poll();
            t.setX(0); t.setY(0); //Set first rectangle
            int currentShelfWidth = t.getWidth(); //Width of the current shelf.
            //position for the new triangle
            int xPosition = 0;
            int yPosition = t.getHeight();
            while (!p.getRectangles().isEmpty()) {
                Rectangle r = p.getRectangles().poll();
                if ((yPosition+r.getHeight()) <= height) {
                    // We set the position of the triangle on the shelf
                    r.setX(xPosition);
                    r.setY(yPosition);
                    yPosition += r.getHeight();
                } else {
                    //Triangle doesn't fir on the shelf, so we place it on new one
                    xPosition = currentShelfWidth;
                    yPosition = 0;
                    currentShelfWidth += r.getWidth();
                    r.setX(xPosition); r.setY(yPosition);
                }
            }
            i.printOutput(p);
        } catch (Exception e) {}
    }
}
