package nl.tue.algorithms.dbl.algorithm;

import nl.tue.algorithms.dbl.common.Pack;

/**
 *
 * @author E.M.A. Arts (1004076)
 * @since 10 MAY 2018
 */
public abstract class Algorithm<P extends Pack> {
    protected P pack;
    
    public Algorithm(P pack) {
        this.pack = pack;
    }
    
    public P getPack() {
        //this intentionally 'leaks' a reference
        return pack;
    }
    
    /**
     * Sets valid positions for all rectangles in this.pack such that they do not
     * overlap and their combined area is minimized.
     */
    public abstract void solve();
}
