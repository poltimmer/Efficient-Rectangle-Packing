package nl.tue.algorithms.dbl.common;

public class Node {
    private boolean isRoot = false;
    private int xNode;
    private int yNode;
    private int xRoom;
    private int yRoom;

    /**
     * Data type for a node. Each node has a fixed x, a fixed y,
     * and the room available. Nodes are used for the binaryPacker algorithm.
     *
     * @author K.D. Voorintholt (1005136)
     * @author Robin Jonker (1011291)
     * @since 25 APR 2018
     */
    public Node(int xNode, int yNode, int xRoom, int yRoom) {
        this.xNode = xNode;
        this.yNode = yNode;
        this.xRoom = xRoom;
        this.yRoom = yRoom;
        if(xNode == 0 && yNode == 0) {
            isRoot = true;
        }
    }

    public int getxNode() {
        return this.xNode;
    }

    public int getyNode() {
        return this.yNode;
    }

    public int getxRoom() {
        return this.xRoom;
    }

    public int getyRoom() {
        return this.yRoom;
    }

    public void setxRoom(int xRoom) {
        this.xRoom = xRoom;
    }

    public void setyRoom(int yRoom) {
        this.yRoom = yRoom;
    }

    public boolean getisRoot() {
        return this.isRoot;
    }
}
