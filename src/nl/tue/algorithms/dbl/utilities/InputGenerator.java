//MOMOTOR_MERGER_IGNORE_FILE

package nl.tue.algorithms.dbl.utilities;

import java.util.Random;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * This class is never used in the Algorithm code, and is only used to submit
 * to generate test cases. It automatically generates an input file that can be
 * used as input for the GUI class or PackingSolver class.
 * Its generated input will be located in res/inputGenerator/input.txt
 * (or creates the directory if it not yet exists).
 * (If there is already an input.txt in that folder, it is replaced) 
 * 
 * @author Pol (1007701)
 * @since 19 MAY 2018
 */

public class InputGenerator {
    Random r = new Random();
    Scanner sc = new Scanner(System.in);
    int height;
    int width;
    int[] bottomleft = {0,0};
    int[] bottomright;
    public static int MINSIZE;
    public static int recNumber = 0;
    public static int requiredRectangles;
    public static File OUTPUT = new File("res/inputGenerator");
    
    public void run(){
        OUTPUT.mkdirs();
        System.out.println("how many rectangles?");
        requiredRectangles = sc.nextInt();
        System.out.println("Rectangle target size?");
        MINSIZE = sc.nextInt();
        System.out.println("canvas height?");
        height = sc.nextInt();
        System.out.println("canvas width?");
        width = sc.nextInt();
        bottomright = new int[]{width,height};
        Rectangle rec = new Rectangle(bottomleft, bottomright);
        // start output
        try (
          BufferedWriter fileWriter =
            new BufferedWriter(new OutputStreamWriter(
                 new FileOutputStream(OUTPUT + "/input.txt"), "UTF-8"));
        ){
           fileWriter.write("container height: fixed " + height);
           fileWriter.newLine();
           fileWriter.write("rotations allowed: no");
           fileWriter.newLine();
           fileWriter.write("number of rectangles: " + recNumber);
           fileWriter.newLine();
           treeWalk(rec, fileWriter);
        } catch (IOException ex) {
          System.out.println("OEPS");
        }
        System.out.println(OUTPUT.getAbsolutePath());
        
    }
    
    public void treeWalk(Rectangle rec, BufferedWriter fileWriter) throws IOException {
      if (rec.leftChild != null && rec.rightChild != null) {
         treeWalk(rec.leftChild, fileWriter);
         treeWalk(rec.rightChild, fileWriter);
      } else {
         rec.print(fileWriter);
      }
    }

    public static void main(String[] args) {
        new InputGenerator().run();
    }
}


class Rectangle {
    Random r = new Random();
    int[] botleft;
    int[] topright;

    public Rectangle(){
        botleft = new int[]{0,0};
        topright = new int[]{50,50};
        InputGenerator.recNumber++;
        this.split(); //first split
    }

    public Rectangle(int[] bl, int[] tr) {
        botleft = bl;
        topright = tr;
        this.split();
    }

    Rectangle leftChild;
    Rectangle rightChild;

    void split() {
        boolean direction = true;
        if (InputGenerator.requiredRectangles <= InputGenerator.recNumber) { // if the required amount of rectangles is reached, then stop.
            return;
        }
        if (topright[0] - botleft[0] > InputGenerator.MINSIZE && topright[1] - botleft[1] > InputGenerator.MINSIZE) {
            if (r.nextBoolean()) { //split x direction
                direction = true;
            } else { //split y direction
                direction = false;
            }
        } else if (topright[0] - botleft[0] > InputGenerator.MINSIZE) {
            direction = true;
        } else if (topright[1] - botleft[1] > InputGenerator.MINSIZE) {
            direction = false;
        } else {
            return;
        }

        if (direction) {
            InputGenerator.recNumber++;
            int split = r.nextInt(topright[0] - botleft[0] - 1) + botleft[0] + 1;
            int[] leftTopRight = {split, topright[1]};
            int[] rightBotLeft = {split, botleft[1]};
            leftChild = new Rectangle(botleft, leftTopRight);
            rightChild = new Rectangle(rightBotLeft, topright);
        } else {
            InputGenerator.recNumber++;
            int split = r.nextInt(topright[1] - botleft[1] - 1) + botleft[1] + 1;
            int[] botTopRight = {topright[0], split};
            int[] topBotLeft = {botleft[0], split};
            leftChild = new Rectangle(botleft, botTopRight);
            rightChild = new Rectangle(topBotLeft, topright);
        }
    }

    int[][] info() {
        int[] size = {topright[0] - botleft[0], topright[1] - botleft[1]};
        int[][] posSize = {botleft,size};
        return posSize;
    }

    void print(BufferedWriter fileWriter) throws IOException{
        int[] size = {topright[0] - botleft[0], topright[1] - botleft[1]};
        fileWriter.write(size[0] + " " + size[1]);
        fileWriter.newLine();
    }
}