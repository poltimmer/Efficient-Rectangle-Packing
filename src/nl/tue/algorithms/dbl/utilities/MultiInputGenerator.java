//MOMOTOR_MERGER_INGORE_FILE
package nl.tue.algorithms.dbl.utilities;

import java.util.Scanner;

/**
 * Invokes InputGenerator to generate a given amount of inputs.
 * @author E.M.A. Arts (1004076)
 */
public class MultiInputGenerator {
    private static final String FILE_EXTENSION = ".txt";
    private final String filenamePrefix;
    private final int n;
    private final boolean rotate;
    private final boolean uniform;
    private final boolean alternate;
    private final int minRectangleSize;
    private final int requiredRectangles;
    private final int canvasWidth;
    private final int canvasHeight;
    private final boolean fixedHeight;
    
    private MultiInputGenerator() {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("how many inputs?");
        n = sc.nextInt();
        
        System.out.println("Filename Prefix?");
        filenamePrefix = sc.next();
        
        System.out.println("how many rectangles?");
        requiredRectangles = sc.nextInt();
        System.out.println("Rectangle target size?");
        minRectangleSize = sc.nextInt();
        //rotations?
        System.out.println("Rotations? y/n");
        char rotationChar = sc.next().charAt(0);
        if (rotationChar == 'y'){
            rotate = true;
        } else {
            rotate = false;
        }
        //more uniform or more random?
        System.out.println("uniform or random cuts? u/r (uniform is better)");
        char uniformChar = sc.next().charAt(0);
        if (uniformChar == 'u'){
            uniform = true;
        } else {
            uniform = false;
        }
        //more square rectangles?
        System.out.println("alternate cuts or randomise? a/r (alternating cuts prevents extremely long shapes)");
        char alternateChar = sc.next().charAt(0);
        if (alternateChar == 'a'){
            alternate = true;
        } else {
            alternate = false;
        }
        System.out.println("canvas height?");
        canvasHeight = sc.nextInt();
        System.out.println("canvas width?");
        canvasWidth = sc.nextInt();
        
        System.out.println("fixed height? T/F");
        char fixedChar = sc.next().charAt(0);
        if (fixedChar == 't'){
            fixedHeight = true;
        } else {
            fixedHeight = false;
        }
    }
    
    private void generate() {
        for (int i = 0; i < n; i++) {
            System.out.println("Generating Input " + (i+1) + " out of " + n +"");
            InputGenerator gen = new InputGenerator();
            gen.requiredRectangles = requiredRectangles;
            gen.MINSIZE = minRectangleSize;
            gen.rotate = rotate;
            gen.uniform = uniform;
            gen.alternate = alternate;
            gen.width = canvasWidth;
            gen.height = canvasHeight;
            
            gen.filename = filenamePrefix + "_" + i + FILE_EXTENSION;
            
            gen.print(gen.generate());
        }
    }
        
    public static void main(String[] args) {
        MultiInputGenerator m = new MultiInputGenerator();
        m.generate();
    }
}
