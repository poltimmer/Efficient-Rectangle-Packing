/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package nl.tue.algorithms.dbl.common;

/**
 *
 * @author Robin Jonker (1011291)
 * @author E.M.A. Arts (1004076)
 * @since 16 MAY 2018
 */
public class ValidCheck {
    /**
     * A method that checks if the solution is still valid.
     *
     * @param r the RectangleRotatable that has just been placed
     * @param p a pack with all the information
     * @pre p != null && r != null
     * @modifies none
     * @throws IllegalArgumentException if p == null or if r == null
     * @return the boolean
     */
    public static boolean isRectangleValidWithinPack(RectangleRotatable r, Pack p) throws IllegalArgumentException {
        if (p == null || r == null){
            throw new IllegalArgumentException("Pack p is null");
        }
        else if (r == null) {
            throw new IllegalArgumentException("Rectangle r is null");
        }
        return isRectangleWithinPackHeight(r, p) && isRectangleNotRotatedIllegallyWithinPack(r, p) && isRectangleNotOverlappingWithPack(r, p);
    }
    
    /**
     * This method checks if the newly placed rectangle does not overlap with any
     * of the earlier placed rectangles.
     *
     * @param r the RectangleRotatable that has just been placed
     * @param p a pack with all the information
     * @pre p != null && r != null
     * @modifies none
     * @return the boolean
     */
    protected static boolean isRectangleNotOverlappingWithPack(RectangleRotatable r, Pack p) {
        for (RectangleRotatable r2 : p.getRectangles()) {
            if (r2.isPlaced() && r != r2 && r.intersects(r2)){
                return false;
            }
        }
        return true;
    }
    
    /**
     * This method checks if the most recently placed rectangle fits in the container.
     * This is only relevant in the case that there is a fixed height
     *
     * @param r the RectangleRotatable that has just been placed
     * @param p a pack with all the information
     * @pre p != null && r != null
     * @modifies none
     * @return the boolean
     */
    protected static boolean isRectangleWithinPackHeight(RectangleRotatable r, Pack p){
        if (!p.hasFixedHeight()) {
            return true;
        }    
        int size = !r.isRotated() ? r.height : r.width;
        return r.y + size <= p.getFixedHeight();
    }
    
    /**
     * This method checks if the most recently placed rectangle has not been illegally rotated.
     * Illegally rotated means that there has been a rotation while rotations are not allowed
     *
     * @param r the RectangleRotatable that has just been placed
     * @param p a pack with all the information
     * @pre p != null && r != null
     * @modifies none
     * @return the boolean
     */
    protected static boolean isRectangleNotRotatedIllegallyWithinPack(RectangleRotatable r, Pack p){
        return p.canRotate() || !r.isRotated();
    }
}
