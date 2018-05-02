package nl.tue.algorithms.dbl.algorithm;

import nl.tue.algorithms.dbl.common.Pack;
import nl.tue.algorithms.dbl.common.PackList;
import nl.tue.algorithms.dbl.io.InputReader;

public class Prototype {
    public static void main(String args[]) {
        new Prototype().run();
    }

    void run() {
        int x = 0; int y = 0;
        PackList rectangles;
        try {
            InputReader r = new InputReader();
            rectangles = r.readInput();
            String s = r.getInputMessage();
            System.out.print(s);
            System.out.println("placement of rectangles");
            for(int i = 0; i < rectangles.getRectanglesList().size(); i++) {
                if (rectangles.canRotate()) {
                    System.out.print("no ");
                }
                System.out.println(x + " " + y);
                x += rectangles.getRectanglesList().get(i).getWidth();
            }
        } catch(Exception e) {
            System.out.println("Exception thrown");
        }

    }
}
