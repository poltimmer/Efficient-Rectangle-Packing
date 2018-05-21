//MOMOTOR_MERGER_IGNORE_FILE
package nl.tue.algorithms.dbl.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author E.M.A. Arts (1004076)
 * @since 21 MAY 2018
 */
public class KeyboardListener extends KeyAdapter {

    private final GUI gui;
    
    public KeyboardListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch(ke.getKeyCode()) {
            case KeyEvent.VK_R:
            case KeyEvent.VK_F5:
                gui.reload();
                break;
        }
    }    
}