/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.tue.algorithms.dbl.common;

/**
 * Data type for a rectangle. Each rectangle has a fixed width, a fixed height,
 * and a unique ID. The latter is not enforced, but is expected.
 * 
 * @author E.M.A. Arts (1004076)
 * @since 25 APR 2018
 */
public class Rectangle {
    private final int ID;
    private final int width;
    private final int height;
    private int x,y;
    
    public Rectangle(int ID, int width, int height) {
        this.ID = ID;
        this.width = width;
        this.height = height;
    }
    /** Getters and Setters for coordinates of the rectangle */
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    /** basic getter */
    public int getID() {
        return this.ID;
    }
    
    /** basic getter */
    public int getWidth() {
        return this.width;
    }
    
    /** basic getter */
    public int getHeight() {
        return this.height;
    }
    
    @Override
    public String toString(){
        return "(" + width + ", " + height + ")";
    }
    
    //TODO: possible extensions include rotations, positions, etc.
}
