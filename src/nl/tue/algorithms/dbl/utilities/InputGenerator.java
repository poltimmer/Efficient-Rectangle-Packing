//MOMOTOR_MERGER_IGNORE_FILE

package nl.tue.algorithms.dbl.utilities;

import java.util.ArrayList;
import java.util.List;
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
 * @author Pol Timmer (1007701)
 * @since 19 MAY 2018
 */

public class InputGenerator {
    Random random = new Random();
    Scanner sc = new Scanner(System.in);
    int height;
    int width;
    int[] bottomLeft = {0,0};
    int[] topRight;
    public static int MINSIZE = 1;
    public static int recNumber = 1;
    public static int requiredRectangles;
    public static File OUTPUT = new File("res/inputGenerator");
    public static List<Rectangle> leafList = new ArrayList<Rectangle>(); //list keeping track of all rectangles (leaves in tree)
    
    public void run(){
        OUTPUT.mkdirs();
        System.out.println("how many rectangles?");
        requiredRectangles = sc.nextInt();
        //System.out.println("Rectangle target size?");
        //MINSIZE = sc.nextInt();
        System.out.println("canvas height?");
        height = sc.nextInt();
        System.out.println("canvas width?");
        width = sc.nextInt();
        topRight = new int[]{width,height};
        Rectangle rec = new Rectangle(bottomLeft, topRight);
        // start splitting
        while (requiredRectangles > recNumber && !leafList.isEmpty()) {
            //randomly pick a rectangle
            int index = random.nextInt(leafList.size());
            Rectangle rectangle = leafList.get(index);
            //and split it if possible
            if (rectangle.topright[0] - rectangle.botleft[0] > MINSIZE && rectangle.topright[1] - rectangle.botleft[1] > MINSIZE) {
                rectangle.split(random.nextBoolean());
            } else if (rectangle.topright[0] - rectangle.botleft[0] > MINSIZE) {
                rectangle.split(true);
            } else if (rectangle.topright[1] - rectangle.botleft[1] > MINSIZE) {
                rectangle.split(false);
            } else {
                //if you can't split the rectangle in any way, you remove it from the list.
                InputGenerator.leafList.remove(index);
            }
        }
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
           //walk through all nodes in subtree of 'rec' (which is the root), and print if they're a leaf.
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
    Random random = new Random();
    int[] botleft;
    int[] topright;

    public Rectangle(int[] bl, int[] tr) {
        botleft = bl;
        topright = tr;
        InputGenerator.leafList.add(this); //add this rectangle to leaflist
    }

    Rectangle leftChild;
    Rectangle rightChild;

    void split(boolean direction) {
        // At this point, the rectangle will get split.
        InputGenerator.leafList.remove(InputGenerator.leafList.indexOf(this)); //remove this rectangle
        InputGenerator.recNumber++; //increment, for the amount of leaves is about to increment.
        if (direction) {
            int split = random.nextInt(topright[0] - botleft[0] - 1) + botleft[0] + 1;
            int[] leftTopRight = {split, topright[1]};
            int[] rightBotLeft = {split, botleft[1]};
            leftChild = new Rectangle(botleft, leftTopRight);
            rightChild = new Rectangle(rightBotLeft, topright);
        } else {
            int split = random.nextInt(topright[1] - botleft[1] - 1) + botleft[1] + 1;
            int[] botTopRight = {topright[0], split};
            int[] topBotLeft = {botleft[0], split};
            leftChild = new Rectangle(botleft, botTopRight);
            rightChild = new Rectangle(topBotLeft, topright);
        }
    }

    int[][] info() { //not used
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