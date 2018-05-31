package nl.tue.algorithms.dbl.algorithm;
import com.sun.javafx.font.directwrite.RECT;
import nl.tue.algorithms.dbl.common.*;

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

public class BinaryPacker extends  Algorithm<PackHeightQueue> {

    private ArrayList<Node> nodeList = new ArrayList();
    private Node rightNode;
    private Node topNode;

    //Make a pack with the rectangles sorted on height
    public BinaryPacker(PackData data) {
        super(new PackHeightQueue(data));
    }

    @Override
    public void solve() {
        if (!pack.getRectangles().isEmpty()) {
            RectangleRotatable r = pack.getRectangles().poll(); //get first rectangle and delete it from the pack
            r.setLocation(0, 0); //first rec always in position 0,0
            Node node = new Node(r.width, r.height, r.width, r.height); //make new node top right corner of rec
            nodeList.add(node);
            rightNode = node;
            topNode = node;
            placeRectangle(topNode, rightNode, nodeList);
        }
    }

    private void placeRectangle(Node top, Node right, ArrayList list) {
        nodeList = list;
        topNode = top;
        rightNode = right;
        //If pack not empty take next rectangle
        if (!pack.getRectangles().isEmpty()) {
            RectangleRotatable r = pack.getRectangles().poll();
            //System.out.println(r.height);
            if (nodeList.size() == 1) {
                placeOneNode(r);
                placeRectangle(topNode, rightNode, nodeList);
            } else {
                //if more than one node, fill in the gap.
                for (Node n : nodeList) {
                    if (n != rightNode) {
                        if (fits(n, r) && r.width + n.getxNode() <= rightNode.getxNode()) { //if rectangle fits in node and does not extend the right most node
                            r.setLocation(n.getxNode(), n.getyNode() - n.getyRoom()); //place node
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
                //if the rectangle couldnt be placed right of a node (except of the rightmost node)
                if (r.y <= -1 || r.x <= -1) {
                    //plaatsen bij de right node of top node
                    //If true then grow up, if false grow right
                    if (growTwoNodes(topNode, rightNode, r)) {
                        r.setLocation(0, topNode.getyNode());
                        Node x = new Node(r.x + r.width, r.y + r.height, r.width, r.height);
                        topNode = x;
                        nodeList.add(x);
                        placeRectangle(topNode, rightNode, nodeList);
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
                        placeRectangle(topNode, rightNode, nodeList);
                    }

                } else {
                    placeRectangle(topNode, rightNode, nodeList);
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
            if (x.getxNode() >= rightNode.getxNode()) {
                rightNode = x;
            }
            n.setxRoom(n.getxRoom() - r.width);
            nodeList.add(x);
        } else {
            r.setLocation(n.getxNode(), 0);
            Node x = new Node(r.x + r.width, r.y + r.height, r.width, r.height);
            rightNode = x;
            if (x.getyNode() >= topNode.getyNode()) {
                topNode = x;
            }
            n.setyRoom(n.getyRoom() - r.height);
            nodeList.add(x);
        }
        return;
    }
}
