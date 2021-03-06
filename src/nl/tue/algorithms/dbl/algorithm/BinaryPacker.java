package nl.tue.algorithms.dbl.algorithm;
import nl.tue.algorithms.dbl.common.*;

import java.util.ArrayList;

/**
 * Algorithm for packing, using nodes.
 *
 * @author K.D. Vooritnholt (1005136)
 * @author Robin Jonker
 * @since 9 MAY 2018
 */

public class BinaryPacker extends  Algorithm<PackHeightQueue> {

    private final ArrayList<Node> nodeList = new ArrayList<>();
    private Node rightNode;
    private Node topNode;
    private final int H;

    //Make a pack with the rectangles sorted on height
    public BinaryPacker(PackData data) {
        super(new PackHeightQueue(data));
        H = pack.getFixedHeight();
    }

    @Override
    public void solve() {
        if (!pack.getRectangles().isEmpty()) {
            RectangleRotatable r = pack.getRectangles().poll(); //get first rectangle and delete it from the pack
            r.setLocation(0, 0); //first rec always in position 0,0
            Node node = new Node(r.getRotatedWidth(), r.getRotatedHeight(), r.getRotatedWidth(), r.getRotatedHeight()); //make new node top right corner of rec
            nodeList.add(node);
            rightNode = node;
            topNode = node;
            //placeRectangle(topNode, rightNode, nodeList);
            placeAllRectangles();
        }
    }

    private void placeOneNode(RectangleRotatable r) {
        //If one node, just place it to the right or top
        Node n = nodeList.get(0); //get only node
        // if true, place up, otherwise place right, change values of xRoom or yRoom of Node n.
        if (BinaryTreeAuxiliary.growNode(n, r.x, r.y, H)){
            r.setLocation(0, n.getyNode());
            Node x = new Node(r.x + r.getRotatedWidth(), r.y + r.getRotatedHeight(), r.getRotatedWidth(), r.getRotatedHeight());
            topNode = x;
            if (x.getxNode() >= rightNode.getxNode()) {
                rightNode = x;
            }
            n.setxRoom(n.getxRoom() - r.getRotatedWidth());
            nodeList.add(x);
        } else {
            r.setLocation(n.getxNode(), 0);
            Node x = new Node(r.x + r.getRotatedWidth(), r.y + r.getRotatedHeight(), r.getRotatedWidth(), r.getRotatedHeight());
            rightNode = x;
            if (x.getyNode() >= topNode.getyNode()) {
                topNode = x;
            }
            n.setyRoom(n.getyRoom() - r.getRotatedHeight());
            nodeList.add(x);
        }
        return;
    }
    
    private void placeAllRectangles() {
        for (int rectanglesPlaced = 1; rectanglesPlaced <= pack.getNumberOfRectangles(); rectanglesPlaced++ ) {
            if (!pack.getRectangles().isEmpty()) {
                RectangleRotatable r = pack.getRectangles().poll();
                //ValidCheck.print(r.getRotatedHeight());
                if (nodeList.size() == 1) {
                    placeOneNode(r);
                    //placeRectangle(topNode, rightNode, nodeList);
                }  else {
                    //if more than one node, fill in the gap.
                    for (Node n : nodeList) {
                        if (n != rightNode) {
                            if (BinaryTreeAuxiliary.fits(n, r) && r.getRotatedWidth() + n.getxNode() <= rightNode.getxNode()) { //if rectangle fits in node and does not extend the right most node
                                r.setLocation(n.getxNode(), n.getyNode() - n.getyRoom()); //place node
                                n.setyRoom(n.getyRoom() - r.getRotatedHeight()); //change the values of the node
                                Node x = new Node(r.x + r.getRotatedWidth(), r.y + r.getRotatedHeight(), r.getRotatedWidth(), r.getRotatedHeight()); //adds new node
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
                        if (BinaryTreeAuxiliary.growTwoNodes(topNode, rightNode, r, H)) {
                            r.setLocation(0, topNode.getyNode());
                            Node x = new Node(r.x + r.getRotatedWidth(), r.y + r.getRotatedHeight(), r.getRotatedWidth(), r.getRotatedHeight());
                            topNode = x;
                            nodeList.add(x);
                            //placeRectangle(topNode, rightNode, nodeList);
                        } else {
                            r.setLocation(rightNode.getxNode(), 0);
                            Node x = new Node(r.x + r.getRotatedWidth(), r.y + r.getRotatedHeight(), r.getRotatedWidth(), r.getRotatedHeight());
                            for (Node n : nodeList) {
                                if (n == rightNode) {
                                    n.setyRoom(rightNode.getyRoom() - r.getRotatedHeight());
                                }
                            }
                            rightNode = x;
                            nodeList.add(x);
                            //placeRectangle(topNode, rightNode, nodeList);
                        }

                    } else {
                        //placeRectangle(topNode, rightNode, nodeList);
                    }
                }
            } else {
                //ValidCheck.print("done");
            }
        }
    }
}
