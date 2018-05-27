package nl.tue.algorithms.dbl.algorithm;
import com.sun.javafx.font.directwrite.RECT;
import nl.tue.algorithms.dbl.common.Node;
import nl.tue.algorithms.dbl.common.PackData;
import nl.tue.algorithms.dbl.common.PackWidthQueue;
import nl.tue.algorithms.dbl.common.RectangleRotatable;

import java.awt.*;
import java.util.Iterator;
import java.util.ArrayList;

import static nl.tue.algorithms.dbl.algorithm.BinaryTreeAuxiliary.fits;
import static nl.tue.algorithms.dbl.algorithm.BinaryTreeAuxiliary.growNode;
import static nl.tue.algorithms.dbl.algorithm.BinaryTreeAuxiliary.growTwoNodes;


/**
 * Algorithm for packing, using nodes.
 *
 * @author K.D. Vooritnholt (1005136)
 * @author Robin Jonker
 * @since 9 MAY 2018
 */

public class BinaryPacker extends  Algorithm<PackWidthQueue> {

    private final ArrayList<Node> nodeList = new ArrayList();
    private Node rightNode;
    private Node topNode;

    public BinaryPacker(PackData data) {
        super(new PackWidthQueue(data));
    }

    @Override
    public void solve() {
        //RectangleRotatable first = pack.getRectangles().peek(); //get the first rectangle

        if (!pack.getRectangles().isEmpty()) {
            RectangleRotatable r = pack.getRectangles().poll();
            r.setLocation(0, 0);
            Node node = new Node(r.x + r.width, r.y + r.height, r.width, r.height);
            nodeList.add(node);
            rightNode = node;
            topNode = node;
            placeRectangle(r.x, r.y);
        }
    }

    private void placeRectangle(int w, int h) {
        //If pack not empty take next rectangle
        if (!pack.getRectangles().isEmpty()) {
            RectangleRotatable r = pack.getRectangles().poll();
            if (nodeList.size() == 1) {
                placeOneNode(r);
            } else {
                //if more than one node, fill in the gap.
                for (Node n : nodeList) {
                    if (n != rightNode) {
                        if (fits(n, r) && r.width + n.getxNode() <= rightNode.getxNode()) { //if rectangle fits in node and does not extend the right most node
                            r.setLocation(n.getxNode() - n.getxRoom(), n.getyNode() - n.getyRoom()); //place node
                            n.setyRoom(n.getyRoom() - r.height); //change the values of the node
                            Node x = new Node(r.x + r.width, r.y + r.height, r.width, r.height); //adds new node

                            //removes node if node is not needed anymore
                            if (n.getyRoom() == 0) {
                                x.setxRoom(n.getxRoom() + x.getxRoom());
                                if (n == topNode && n == rightNode) {
                                    topNode = x;
                                    rightNode = x;
                                } else if (n == rightNode) {
                                    rightNode = x;
                                } else if (n == topNode) {
                                    topNode = x;
                                }
                                nodeList.remove(n);
                            }
                            nodeList.add(x);
                            break;
                        }
                    }
                }
                if (r.y == -1 || r.x == -1) {
                    //plaatsen bij de right node of top node
                    //If true then grow up, if false grow right
                    if (growTwoNodes(topNode, rightNode, r)) {
                        r.setLocation(0, topNode.getyNode());
                        Node x = new Node(r.x + r.width, r.y + r.height, r.width, r.height);
                        topNode = x;
                        for (Node n : nodeList) {
                            if (n == topNode) {
                                n.setyRoom(topNode.getxRoom() - r.width);
                            }
                        }
                        topNode = x;
                        nodeList.add(x);
                        placeRectangle(topNode.getxNode(), x.getyNode());
                    } else {
                        r.setLocation(rightNode.getxNode(), 0);
                        Node x = new Node(r.x + r.width, r.y + r.height, r.width, r.height);
                        for (Node n : nodeList) {
                            if (n == rightNode) {
                                n.setyRoom(rightNode.getyRoom() - r.height);
                            }
                        }
                        rightNode = x;
                        nodeList.add(x);
                        placeRectangle(topNode.getxNode(), x.getyNode());
                    }
                    //dmv growNode()
                }
            }
        } else {
            System.out.println("done");
        }
    }

    private void placeOneNode(RectangleRotatable r) {
        //If one node, just place it to the right or top
        Node n = nodeList.get(0); //get only node
        // if true, place up, otherwise place right, change values of xRoom or yRoom of Node n.
        if (BinaryTreeAuxiliary.growNode(n, r.x, r.y)){
            r.setLocation(0, n.getyNode());
            Node x = new Node(r.x + r.width, r.y + r.height, r.width, r.height);
            topNode = x;
            n.setxRoom(n.getxRoom() - r.width);
            nodeList.add(x);
            placeRectangle(n.getxNode(), x.getyNode());
        } else {
            r.setLocation(n.getxNode(), 0);
            Node x = new Node(r.x + r.width, r.y + r.height, r.width, r.height);
            rightNode = x;
            n.setyRoom(n.getyRoom() - x.getyNode());
            nodeList.add(x);
            placeRectangle(x.getxNode(), n.getyNode());
        }
    }
}
