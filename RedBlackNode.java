public class RedBlackNode {
    
    int buildingNum;
    int executedTime;
    int totalTime;
    
    RedBlackNode left;
    RedBlackNode right;
    RedBlackNode parent;
    
    int color;
    public HeapItem heapItem;

    private static int BLACK = 0;
    private static int RED = 1;
    static RedBlackNode SENTINEL_NODE = new RedBlackNode(0, 0, 0);

    RedBlackNode(int buildingNum, int executedTime, int totalTime) {
        this.left = null;
        this.right = null;
        this.parent = null;
        this.color = BLACK;
        this.heapItem = null;
        this.buildingNum = buildingNum;
        this.executedTime = executedTime;
        this.totalTime = totalTime;
    }

    /*
        Getters
    */

    public int getBuildingNum() {
        return this.buildingNum;
    }

    public int getExecutedTime() {
        return this.executedTime;
    }

    public int getTotalTime() {
        return this.totalTime;
    }

    public RedBlackNode getParent() {
        return this.parent;
    }

    public RedBlackNode getLeft() {
        return this.left;
    }

    public RedBlackNode getRight() {
        return this.right;
    }

    public int getColor() {
        return color;
    }

    public boolean isRed() {
        return this.color == RED;
    }

    public boolean isBlack() {
        return this.color == BLACK;
    }

    /*
        Setters
    */

    public void setBuildingNum(int buildingNum) {
        this.buildingNum = buildingNum;
    }

    public void setExecutedTime(int executedTime) {
        this.executedTime = executedTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public void setParent(RedBlackNode parent) {
        this.parent = parent;
    }

    public void setLeft(RedBlackNode node) {
        this.left = node;
    }

    public void setRight(RedBlackNode node) {
        this.right = node;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setBlack() {
        this.setColor(BLACK);
    }

    public void setRed() {
        this.setColor(RED);
    }

    public void setHeapItem(HeapItem heapItem) {
        this.heapItem = heapItem;
    }
}
