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
     * @param p a pack with all the information
     * @param r the RectangleRotatable that has just been placed
     * @pre p != null && r != null
     * @modifies none
     * @throws IllegalArgumentException if p == null or if r == null
     * @return true if solution is still valid, false if the solution is not valid
     */
    public boolean checkSolution(Pack p, RectangleRotatable r) throws IllegalArgumentException {
        if (p == null || r == null){
            throw new IllegalArgumentException("Pack p is null");
        }
        else if (r == null) {
            throw new IllegalArgumentException("Rectangle r is null");
        }
        return fitsInContainer(p, r) && noIllegalRotation(p, r) && noOverlapSolution(p, r);
    }
    
    /**
     * This method checks if the newly placed rectangle does not overlap with any
     * of the earlier placed rectangles.
     *
     * @param p a pack with all the information
     * @param r the RectangleRotatable that has just been placed
     * @pre p != null && r != null
     * @modifies none
     * @return true if the rectangle does not overlap with other rectangles,
     * false if there is overlap in the solution
     */
    private boolean noOverlapSolution(Pack p, RectangleRotatable r) {
        for (RectangleRotatable r2 : p.getRectangles()) {
            if (r2.getX() >= 0 && r2.getY() >= 0 && r != r2 && r.intersects(r2)){
                return false;
            }
        }
        return true;
    }
    
    /**
     * This method checks if the most recently placed rectangle fits in the container.
     * This is only relevant in the case that there is a fixed height
     *
     * @param p a pack with all the information
     * @param r the RectangleRotatable that has just been placed
     * @pre p != null && r != null
     * @modifies none
     * @return true if there is no fixed height or if the newly placed rectangle does not go over this height,
     * false if the rectangle does go over this limit
     */
    private boolean fitsInContainer(Pack p, RectangleRotatable r){
        if (!p.hasFixedHeight()) {
            return true;
        }    
        int size = !r.isRotated() ? r.height : r.width;
        return r.y + size <= p.getContainerHeight();
    }
    
    /**
     * This method checks if the most recently placed rectangle has not been illegally rotated.
     * Illegally rotated means that there has been a rotation while rotations are not allowed
     *
     * @param p a pack with all the information
     * @param r the RectangleRotatable that has just been placed
     * @pre p != null && r != null
     * @modifies none
     * @return true if rotations are allowed or if there are no rotations,
     * false if there are illegal rotations
     */
    public boolean noIllegalRotation(Pack p, RectangleRotatable r){
        return p.canRotate() || !r.isRotated();
    }
}
