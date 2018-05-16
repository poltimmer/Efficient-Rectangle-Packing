/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package nl.tue.algorithms.dbl.common;

/**
 *
 * @author s165318
 */
public class ValidCheck {
    /**
    * A method that checks if the solution is still valid.
    *
    * @param p a pack with all the information
    * @pre solution is still valid up to this point && p != null
    * @modifies none
    * @throws solutionNotValidException if solution is not valid up to this point, IllegalArgumentException if p==null
    * @return boolean representing if solution is valid
    * @post (@code
    (\result == true ⇒ if solution is still valid) &&
    (\result == false ⇒ solution is no longer valid)
    */
    public boolean checkSolution(Pack p) {
        return false;
    }
    
    /** This method checks if the current solution has no overlap
     *
     * @param p a pack with all the information
     * @param r RectangleRotatable that has just been placed
     * @pre solution is still valid up to this point && p != null
     * @modifies none
     * @throws solutionNotValidException if solution is not valid up to this point, IllegalArgumentException if p==null
     * @return true if there is no overlap in the solution, false if there is overlap in the solution
     */
    
    public boolean noOverlapSolution(Pack p, RectangleRotatable r) {
        for (RectangleRotatable r2 : p.getRectangles()) {
            if (r.intersects(r2)){
                return false;
            }
        }
        return true;
    }
}
