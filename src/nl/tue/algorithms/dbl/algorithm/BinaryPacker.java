package nl.tue.algorithms.dbl.algorithm;
import com.sun.javafx.font.directwrite.RECT;
import nl.tue.algorithms.dbl.common.Node;
import nl.tue.algorithms.dbl.common.PackData;
import nl.tue.algorithms.dbl.common.PackWidthQueue;
import nl.tue.algorithms.dbl.common.RectangleRotatable;

import java.awt.*;
import java.util.Iterator;
import java.util.ArrayList;


/**
 * Algorithm for packing, using nodes.
 *
 * @author K.D. Vooritnholt (1005136)
 * @author Robin Jonker
 * @since 9 MAY 2018
 */

public class BinaryPacker extends  Algorithm<PackWidthQueue> {

    private final ArrayList<Node> nodeList = new ArrayList();

    public BinaryPacker(PackData data) {
        super(new PackWidthQueue(data));
    }

    @Override
    public void solve() {
        //RectangleRotatable first = pack.getRectangles().peek(); //get the first rectangle

        if (!pack.getRectangles().isEmpty()) {
            RectangleRotatable r = pack.getRectangles().poll();
            r.setLocation(0, 0);
            Node node = new Node((int)r.getX(), (int) r.getY(), (int) r.getX(), (int) r.getY());
            nodeList.add(node);
            placeRectangle((int) r.getX(), (int) r.getY());
        }
    }

    private void placeRectangle(int w, int h) {
        if (!pack.getRectangles().isEmpty()) {
            RectangleRotatable r = pack.getRectangles().poll();
            if (nodeList.size() == 1) {
                //If one node, just place it to the right or top
                Node n = nodeList.get(0);
                // if true, place up, otherwise place right, change values of xRoom or yRoom of Node n.
                if (BinaryTreeAuxiliary.growNode(n, (int) r.getX(), (int) r.getY())) {
                    r.setLocation(0, n.getyNode());
                    Node x = new Node((int) r.getX(), (int) r.getY() + n.getyNode(), (int) r.getX(), (int) r.getY());
                    n.setxRoom(n.getxRoom() - x.getxNode());
                    nodeList.add(x);
                    placeRectangle(n.getxNode(), x.getyNode());
                } else {
                    r.setLocation(n.getxNode(), 0);
                    Node x = new Node((int) r.getX() + n.getxNode(), (int) r.getY(), (int) r.getX(), (int) r.getY());
                    n.setyRoom(n.getyRoom() - x.getyNode());
                    nodeList.add(x);
                    placeRectangle(x.getxNode(), n.getyNode());
                }
            } else {
                //if more than one node, fill in the gap.
            }
        } else {
            //no rectangles to place, finish him.
        }
    }
}
