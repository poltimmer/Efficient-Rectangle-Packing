package nl.tue.algorithms.dbl.algorithm;

import nl.tue.algorithms.dbl.common.PackWidthQueue;
import nl.tue.algorithms.dbl.io.InputReader;

/**
 * 
 * @author Koen Degeling
 * @since 9 MAY 2018
 */

public class Prototype {
    public void run() {
        int x = 0; int y = 0;
        PackWidthQueue pack = new PackWidthQueue();
        try {
            InputReader<PackWidthQueue> i = new InputReader<>(System.in);
            pack = i.readInput(pack);
            while (!pack.getRectangles().isEmpty()) {
                
            }
            i.printOutput(pack);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
