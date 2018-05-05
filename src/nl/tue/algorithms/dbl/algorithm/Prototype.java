package nl.tue.algorithms.dbl.algorithm;

import nl.tue.algorithms.dbl.common.HeightQueuePack;
import nl.tue.algorithms.dbl.io.InputReader;

public class Prototype {
    public static void main(String args[]) {
        new Prototype().run();
    }

    void run() {
        int x = 0; int y = 0;
        HeightQueuePack pack = new HeightQueuePack();
        try {
            InputReader<HeightQueuePack> i = new InputReader<>(System.in);
            pack = i.readInput(pack);
            while (!pack.getRectangles().isEmpty()) {
                
            }
            i.printOutput(pack);
        } catch(Exception e) {
            System.out.println("Exception thrown");
        }

    }
}
