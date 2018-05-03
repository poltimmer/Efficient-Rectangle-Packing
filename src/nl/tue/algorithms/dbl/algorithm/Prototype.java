package nl.tue.algorithms.dbl.algorithm;

import nl.tue.algorithms.dbl.common.HeightQueuePack;
import nl.tue.algorithms.dbl.io.InputReader;

public class Prototype {
    public static void main(String args[]) {
        new Prototype().run();
    }

    void run() {
        int x = 0; int y = 0;
        HeightQueuePack rectangles = new HeightQueuePack();
        try {
            InputReader<HeightQueuePack> r = new InputReader<>();
            rectangles = r.readInput(rectangles);

            String s = r.getInputMessage();
            System.out.print(s);
            System.out.println("placement of rectangles");
            for(int i = 0; i < rectangles.getRectanglesList().size(); i++) {
                if (rectangles.canRotate()) {
                    System.out.print("no ");
                }
                System.out.println(x + " " + y);
                x += rectangles.getOrderRectangles().get(i).getWidth();
            }
        } catch(Exception e) {
            System.out.println("Exception thrown");
        }

    }
}
