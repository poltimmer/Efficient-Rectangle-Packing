
package nl.tue.algorithms.dbl.common;

import java.awt.Rectangle;

/**
 * Data type for a rectangle. Each rectangle has a fixed width, a fixed height,
 * and a unique ID. The latter is not enforced, but is expected.
 *
 * @author E.M.A. Arts (1004076)
 * @since 25 APR 2018
 */
public class RectangleRotatable extends Rectangle {
    private final int ID;
    private boolean rotated;
    
    public RectangleRotatable(int ID, int width, int height) {
        this.ID = ID;
        this.width = width;
        this.height = height;
        this.rotated = false;
    }
    
    public boolean isPlaced() {
        return x >= 0 && y >= 0;
    }
    
    /** This method checks if the two rectangles given as input do not overlap
     *
     * @param r the rectangle to compare to
     * @pre solution is still valid up to this point && p != null
     * @modifies none
     * @throws solutionNotValidException if solution is not valid up to this point, IllegalArgumentException if p==null
     * @return true if rectangles do not overlap, false if they do overlap
     */
    
    private boolean noOverlap2Rectangles(int twidth, int theight, int rx, int ry, int rwidth, int rheight) {
        int thisXMax = this.x + twidth;
        int thisYMax = this.y + theight;
        int rXMax = rx + rwidth;
        int rYMax = ry + rheight;
        
        // If one rectangle is on left side of other
        if (this.x > rXMax || rx > thisXMax){
            return false;
        }
        
        // If one rectangle is above other
        if (this.y > rYMax || ry > thisYMax){
            return false;
        }
        
        return true;
    }
    
    /** Check if two rectangles intersect.
     *  @param r RectangleRotatable to compare the newly placed rectangle to
     *  @return true if there is overlap, false if there is no overlap
     */
    public boolean intersects (RectangleRotatable r) {
        if (!this.rotated && !r.rotated){
            return noOverlap2Rectangles(this.width, this.height, r.x, r.y, r.width, r.height);
        }
        else if (!this.rotated && r.rotated){
            return noOverlap2Rectangles(this.width, this.height, r.x, r.y, r.height, r.width);
        }
        else if (this.rotated && !r.rotated){
            return noOverlap2Rectangles(this.height, this.width, r.x, r.y, r.width, r.height);
        }
        else{
            return noOverlap2Rectangles(this.height, this.width, r.x, r.y, r.height, r.width);
        }
    }
    
    
    /** Getters and Setters for coordinates of the rectangle */
    public boolean isRotated() {
        return rotated;
    }
    public void setRotated(boolean rotated) {
        this.rotated = rotated;
    }
    
    /** basic getter */
    public int getID() {
        return this.ID;
    }
    
    @Override
    public String toString(){
        return "(" + width + ", " + height + ")";
    }
    
    //TODO: possible extensions include rotations, positions, etc.
}
