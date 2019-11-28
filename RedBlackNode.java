public class RedBlackNode {

    int buildingNum;
    int executedTime;
    int totalTime;

    /**
     * Stores the left child of the RedBlackNode object
     */
    RedBlackNode left;

    /**
     * Stores the right child of the RedBlackNode object
     */
    RedBlackNode right;

    /**
     * Stores the parent of the RedBlackNode object
     */
    RedBlackNode parent;

    /**
     * Stores the corresponding HeapNode object in the MinHeap
     */
    private HeapNode heapNode;

    /**
     * Stores the color of the RedBlackNode object
     * 0 => Black
     * 1 => Red
     */
    int color;
    private static int BLACK = 0;
    private static int RED = 1;

    /**
     * An empty RedBlackNode that makes it simpler to deal with corner cases
     * (e.g. parent of the root node, children of leaf nodes)
     */
    static RedBlackNode SENTINEL_NODE = new RedBlackNode(0, 0, 0);

    /**
     * Initializes a new RedBlackNode
     * @param buildingNum The building number of the RedBlackNode object (the building)
     * @param executedTime The executed time of the RedBlackNode object (the building)
     * @param totalTime The total time required for completion of the RedBlackNode object (the building)
     */
    RedBlackNode(int buildingNum, int executedTime, int totalTime) {
        this.left = null;
        this.right = null;
        this.parent = null;
        this.color = BLACK;
        this.heapNode = null;
        this.buildingNum = buildingNum;
        this.executedTime = executedTime;
        this.totalTime = totalTime;
    }

    /*
        Getters
    */

    /**
     * Gets the building number attribute of the RedBlackNode
     * @return The building number of the RedBlackNode
     */
    public int getBuildingNum() {
        return this.buildingNum;
    }

    /**
     * Gets the executed time attribute of the RedBlackNode
     * @return The executed time of the RedBlackNode
     */
    public int getExecutedTime() {
        return this.executedTime;
    }

    /**
     * Gets the total time attribute of the RedBlackNode
     * @return The total time of the RedBlackNode
     */
    public int getTotalTime() {
        return this.totalTime;
    }

    /**
     * Gets the parent attribute of the RedBlackNode
     * @return The parent of the RedBlackNode
     */
    public RedBlackNode getParent() {
        return this.parent;
    }

    /**
     * Gets the leftChild attribute of the RedBlackNode
     * @return The left child of the RedBlackNode
     */
    public RedBlackNode getLeft() {
        return this.left;
    }

    /**
     * Gets the rghtChild attribute of the RedBlackNode
     * @return The right child of the RedBlackNode
     */
    public RedBlackNode getRight() {
        return this.right;
    }

    /**
     * Gets the color attribute of the RedBlackNode
     * @return The color of the RedBlackNode
     */
    public int getColor() {
        return color;
    }

    /**
     * Checks if the RedBlackNode is red
     * @return True if the node is RED, else False
     */
    public boolean isRed() {
        return this.color == RED;
    }

    /**
     * Checks if the RedBlackNode is black
     * @return True if the node is RED, else False
     */
    public boolean isBlack() {
        return this.color == BLACK;
    }

    /**
     * Returns the corresponding HeapNode object
     * @return True if the node is RED, else False
     */
    public HeapNode getHeapNode() {
        return this.heapNode;
    }

    /*
        Setters
    */

    /**
     * Sets the building number of the RedBlackNode object (the building)
     * @param buildingNum
     */
    public void setBuildingNum(int buildingNum) {
        this.buildingNum = buildingNum;
    }

    /**
     * Sets the executed time of the RedBlackNode object (the building)
     * @param executedTime
     */
    public void setExecutedTime(int executedTime) {
        this.executedTime = executedTime;
    }

    /**
     * Sets the total time of the RedBlackNode object (the building)
     * @param totalTime
     */
    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * Sets the parent of the RedBlackNode object (the building)
     * @param parent
     */
    public void setParent(RedBlackNode parent) {
        this.parent = parent;
    }

    /**
     * Sets the left child of the RedBlackNode object (the building)
     * @param node The node to set as the left child
     */
    public void setLeft(RedBlackNode node) {
        this.left = node;
    }

    /**
     * Sets the right child of the RedBlackNode object (the building)
     * @param node The node to set as the right child
     */
    public void setRight(RedBlackNode node) {
        this.right = node;
    }

    /**
     * Sets the color of the RedBlackNode obejct (the building)
     * @param color The color to set the node to
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Sets the color of the RedBlackNode object (the building) to BLACK
     */
    public void setBlack() {
        this.setColor(BLACK);
    }

    /**
     * Sets the color of the RedBlackNode object (the building) to RED
     */
    public void setRed() {
        this.setColor(RED);
    }

    /**
     * Sets the corresponsing HeapNode object of the RedBlackNode object (the building)
     * @param heapNode
     */
    public void setHeapNode(HeapNode heapNode) {
        this.heapNode = heapNode;
    }

    /*
        Modifiers
    */

    /**
     * Increments the executed time of the RedBlackNode object (the building) by 1
     */
    public void incrementExecutedTime() {
        this.executedTime += 1;
    }
}
