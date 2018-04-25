
package nl.tue.algorithms.dbl.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * 
 * @author Kees Voorintholt 1005136
 * @author Eric Arts 1004076
 * @since 25 MAR 2018
 */
public class InputReader {
    //Scannner for the input file
    private Scanner sc;
    
    //WIP
    private Scanner expectedSc;
    private static String expectedInput = "container height: # & \n rotations allowed: # \n number of rectangles: #";
    
    public InputReader() throws FileNotFoundException{
        //should be System.in
        this.sc = new Scanner(new File("tests/test.txt"));
        this.expectedSc = new Scanner(expectedInput);
    }
    
    /**
     * reads the input from the input file, stores it in variables, and prints those
     * @pre File has the expected format
     * @post containerHeight, rotations allowed, number of rectangles, and the
     * rectangles are printed to System.out
     * 
     * @throws ParseException if the precondition is violated
     * 
     * TODO: return/store the height etc. somewhere
     */
    public void readInput() throws ParseException {
        final boolean fixedHeight;
        final int containerHeight;
        final boolean canRotate;
        final int numberOfRectangles;        
        
        sc.next(); //container
        sc.next(); //height
        
        
        if (sc.next().equals("fixed")) {
            containerHeight = sc.nextInt();
            fixedHeight = true;
        } else {
            containerHeight = -1;
            fixedHeight = false;
        }
        
        sc.next(); //rotations
        sc.next(); //allowed
        canRotate = sc.next().equals("yes");
        
        sc.next(); //number
        sc.next(); //of
        sc.next(); //rectangles
        
        numberOfRectangles = sc.nextInt();
        
        for (int i = 0; i < numberOfRectangles; i++){
            final int width = sc.nextInt();
            final int height = sc.nextInt();
            
            System.out.println(Integer.toString(width) + "," + Integer.toString(height));
        }
        System.out.println("rotate: " + Boolean.toString(canRotate));
        System.out.println("number of rectangels: " + Integer.toString(numberOfRectangles));
        System.out.println("container Height: " + Integer.toString(containerHeight));
    }
    
    //TODO: main method to be moved somewhere else
    public static void main(String [ ] args) {
        try {
            InputReader in = new InputReader();
            in.readInput();
        } catch (FileNotFoundException | ParseException ex) {
            Logger.getLogger(InputReader.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    
}
