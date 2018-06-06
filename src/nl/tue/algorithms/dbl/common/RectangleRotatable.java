
package nl.tue.algorithms.dbl.common;

import java.awt.Rectangle;

/**
 * Data type for a rectangle. Each rectangle has a fixed width, a fixed height,
 * and a unique ID. The latter is not enforced, but is expected.
 *
 * @author E.M.A. Arts (1004076)
 * @author Robin Jonker (1011291)
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
        this.x = Integer.MIN_VALUE;
        this.y = Integer.MIN_VALUE;
    }
    
    /**
     * Checks whether the rectangle is placed, that is, it has positive x and y
     * coordinates.
     * @return true if the rectangle is placed. false otherwise
     */
    public boolean isPlaced() {
        return x >= 0 && y >= 0;
    }
    
    /**
     * Gets the area of the rectangle
     * @post \result == this.width * this.height
     * @return area of the rectangle
     */
    public int getArea() {
        return width*height;
    }
    
    /** 
     * This method checks if the two rectangles given as input do not overlap.
     * @param twidth width of the first rectangle
     * @param theight height of the first rectangle
     * @param rx x-coordinate of the 2nd rectangle
     * @param ry y-coordinate of the 2nd rectangle
     * @param rwidth width of the 2nd rectangle
     * @param rheight height of the 2nd rectangle
     * @return true if rectangles overlap, false if they don't overlap
     */
    private boolean overlapsRectangle(int twidth, int theight, int rx, int ry, int rwidth, int rheight) {
        int thisXMax = this.x + twidth;
        int thisYMax = this.y + theight;
        int rXMax = rx + rwidth;
        int rYMax = ry + rheight;
        
        // If one rectangle is on left side of other
        if (this.x >= rXMax || rx >= thisXMax){
            return false;
        }
        
        // If one rectangle is above other
        if (this.y >= rYMax || ry >= thisYMax){
            return false;
        }
        
        //None of the above cases hold, so the rectangles must overlap
        return true;
    }
    
    /** 
     *  Checks if two rectangles intersect by calling overlapsRectangle
     *  Height and width of a rectangle is mirrored if it has been rotated
     * 
     *  @param r RectangleRotatable to compare the newly placed rectangle to
     *  @return true if there is overlap, false if there is no overlap
     */
    public boolean intersects(RectangleRotatable r) {
        if (!this.rotated && !r.rotated){
            return overlapsRectangle(this.width, this.height, r.x, r.y, r.width, r.height);
        }
        else if (!this.rotated && r.rotated){
            return overlapsRectangle(this.width, this.height, r.x, r.y, r.height, r.width);
        }
        else if (this.rotated && !r.rotated){
            return overlapsRectangle(this.height, this.width, r.x, r.y, r.width, r.height);
        }
        else{
            return overlapsRectangle(this.height, this.width, r.x, r.y, r.height, r.width);
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
    
    public int getRotatedWidth() {
        return rotated ? this.height : this.width;
    }
    
    public int getRotatedHeight() {
        return rotated ? this.width : this.height;
    }
    
    /**
     * Copies this rectangle and returns a new instance
     * @return A rectangle with the same width, height, location, etc. as this
     */
    public RectangleRotatable copy() {
        RectangleRotatable r = new RectangleRotatable(ID, width, height);
        r.setLocation(x, y);
        return r;
    }
}
