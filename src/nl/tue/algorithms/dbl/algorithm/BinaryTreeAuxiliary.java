/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.tue.algorithms.dbl.algorithm;


/**
     * Auxiliary functions for the BinaryTree algorithm.
     * 
     * @author Robin Jonker
     */
public class BinaryTreeAuxiliary {
    
    /**
     * Call this function with the width and height of the to be placed rectangle
     * to see if it should be placed up or right of the node.
     * Only useful if there is one node, and defaults to the right if right and up are the same.
     * 
     */
    public boolean growNode(Node node, int w, int h) {
        boolean canGrowUp  = (w <= this.node.w);
        boolean canGrowRight = (h <= this.node.h);

        boolean shouldGrowRight = canGrowRight && (this.node.h >= (this.node.w + w)); // attempt to keep square-ish by growing right when height is much greater than width
        boolean shouldGrowUp  = canGrowUp  && (this.node.w >= (this.node.h + h)); // attempt to keep square-ish by growing down  when width  is much greater than height

        if (shouldGrowRight) {
            return false;
        }
        else if (shouldGrowUp) {
            return true;
        }
        else if (canGrowRight) {
            return false;
        }
        else if (canGrowUp) {
            return true;
        }
        else {
            // Throw errorS
            // still should never happen if the rectangles are sorted in advance
        }
        return false; //defaults to false, but this should never happen once the error is created
    }
}