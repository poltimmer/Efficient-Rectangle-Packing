package nl.tue.algorithms.dbl.algorithm;

import nl.tue.algorithms.dbl.common.Node;
import nl.tue.algorithms.dbl.common.RectangleRotatable;

/**
     * Auxiliary functions for the BinaryTree algorithm.
     * 
     * @author Robin Jonker
 *   * @author K.D. Voorintholt (1005136)
     */
public class BinaryTreeAuxiliary {
    
    /**
     * Decides where the algorithm should grow to (right or up).
     * Only works in the case of one node
     * 
     * 
     * @param node the one node available
     * @param w the width of the rectangle to be placed
     * @param h the height of the rectangle to be placed
     * 
     * @throws IllegalStateException if both canGrowUp and canGrowRight are false
     * 
     * @return true if algo should grow up, false if algo should grow right
     */
    public static boolean growNode(Node node, int w, int h) throws IllegalStateException {
        boolean canGrowUp  = (w <= node.getxNode());
        boolean canGrowRight = (h <= node.getyNode());

        boolean shouldGrowRight = canGrowRight && (node.getyNode() >= (node.getxNode() + w)); // attempt to keep square-ish by growing right when height is much greater than width
        boolean shouldGrowUp  = canGrowUp  && (node.getxNode() >= (node.getyNode() + h)); // attempt to keep square-ish by growing down  when width  is much greater than height

        if (!(canGrowRight || canGrowUp)) {
            throw new IllegalStateException("Rectangle can't be placed, make sure to sort the list of rectangles on width or height");
        }
        else if (shouldGrowRight) {
            return false;
        }
        else if (shouldGrowUp) {
            return true;
        }
        else if (canGrowRight) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Decides where the algorithm should grow to (right or up).
     * Only works in the case of one node
     *
     *
     * @param topNode the top node available
     * @param rightNode the right node available
     * @param r the rectangle to place.
     *
     * @throws IllegalStateException if both canGrowUp and canGrowRight are false
     *
     * @return true if algo should grow up, false if algo should grow right
     */
    public static boolean growTwoNodes(Node topNode, Node rightNode, RectangleRotatable r) {
        boolean canGrowUp  = (r.getRotatedWidth() <= topNode.getxNode());
        boolean canGrowRight = (r.getRotatedHeight() <= rightNode.getyNode());

        boolean shouldGrowRight = canGrowRight && (topNode.getyNode() >= (rightNode.getxNode() + r.getRotatedWidth())); // attempt to keep square-ish by growing right when height is much greater than width
        boolean shouldGrowUp  = canGrowUp  && (rightNode.getxNode() >= (topNode.getyNode() + r.getRotatedHeight())); // attempt to keep square-ish by growing down  when width  is much greater than height

        if (!(canGrowRight || canGrowUp)) {
            throw new IllegalStateException("Rectangle can't be placed, make sure to sort the list of rectangles on width or height");
        }
        else if (shouldGrowRight) {
            return false;
        }
        else if (shouldGrowUp) {
            return true;
        }
        else if (canGrowRight) {
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean fits(Node node, RectangleRotatable rec) {
        //size of rectangle
        int recHeight = (int) rec.getRotatedHeight();
        //int recWidth = (int) rec.getWidth();


        int nodeHeight = node.getyRoom();
        //int nodeWidth = node.getxRoom();

        if (recHeight <= nodeHeight) {
            return true;
        }
        return false;
    }
}