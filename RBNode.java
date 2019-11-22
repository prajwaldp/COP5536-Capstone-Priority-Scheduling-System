class RBNode {

    private static int RED = 0;
    private static int BLACK = 1;

    private int buildingNum;
    private int executedTime;
    private int color;
    private RBNode parent;
    private RBNode leftChild;
    private RBNode rightChild;

    public RBNode(int buildingNum, int executedTime, int color) {
        this.buildingNum = buildingNum;
        this.executedTime = executedTime;
        this.color = color;
        this.parent = null;
        this.leftChild = null;
        this.rightChild = null;
    }

    public RBNode(int buildingNum, int executedTime) {
        this.buildingNum = buildingNum;
        this.executedTime = executedTime;
        this.color = RED;
        this.parent = null;
        this.leftChild = null;
        this.rightChild = null;
    }

    // Setters

    public void setParent(RBNode n) {
        this.parent = n;
    }

    public void setLeftChild(RBNode n) {
        this.leftChild = n;
    }

    public void setRightChild(RBNode n) {
        this.rightChild = n;
    }

    public void setBlack() {
        this.color = BLACK;
    }

    public void setRed() {
        this.color = RED;
    }

    // Getters

    public int getBuildingNum() {
        return this.buildingNum;
    }

    public RBNode getParent() {
        return this.parent;
    }

    public RBNode getLeftChild() {
        return this.leftChild;
    }

    public RBNode getRightChild() {
        return this.rightChild;
    }

    public boolean isRed() {
        return this.color == RED;
    }

    public RBNode getUncle() {
        if (this.parent.parent.leftChild == this.parent) {
            return this.rightChild;
        } else {
            return this.leftChild;
        }
    }
}