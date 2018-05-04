package nl.tue.algorithms.dbl.algorithm;

import nl.tue.algorithms.dbl.common.HeightQueuePack;
import nl.tue.algorithms.dbl.common.Rectangle;
import nl.tue.algorithms.dbl.io.InputReader;

public class Prototype {
    public static void main(String args[]) {
        new Prototype().run();
    }

    void run() {
        int x = 0; int y = 0;
        HeightQueuePack pack = new HeightQueuePack();
        try {
            InputReader<HeightQueuePack> i = new InputReader<>();
            pack = i.readInput(pack);

            String s = i.getInputMessage();
            System.out.print(s);
            System.out.println("placement of rectangles");
            while (!pack.getRectangles().isEmpty()) {
                System.out.println(pack.getRectangles().poll().getWidth());
            }
        } catch(Exception e) {
            System.out.println("Exception thrown");
        }

    }
}
