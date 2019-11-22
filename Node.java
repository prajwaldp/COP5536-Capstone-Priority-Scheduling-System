public class Node {
    int buildingNums;
    int executed_time;
    int total_time;
    Node left;
    Node right;
    Node parent;
    char color;
    public HeapItem heapItem;

    Node() {
        this.left = null;
        this.right = null;
        this.parent = null;
        this.color = 'b';
        this.heapItem = null;
    }

    Node(int buildingNums, int executed_time, int total_time) {
        this.left = null;
        this.right = null;
        this.parent = null;
        this.color = 'b';
        this.heapItem = null;
        this.buildingNums = buildingNums;
        this.executed_time = executed_time;
        this.total_time = total_time;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public Node getParent() {
        return this.parent;
    }

    public int getBuildingNums() {
        return this.buildingNums;
    }

    public int getExecutedTime() {
        return this.executed_time;
    }

    public int getTotalTime() {
        return this.total_time;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getLeft() {
        return this.left;
    }

    public Node getRight() {
        return this.right;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setBuildingNums(int buildingNums) {
        this.buildingNums = buildingNums;
    }

    public int getExecuted_time() {
        return executed_time;
    }

    public void setExecuted_time(int executed_time) {
        this.executed_time = executed_time;
    }

    public int getTotal_time() {
        return total_time;
    }

    public void setTotal_time(int total_time) {
        this.total_time = total_time;
    }

    public char getColor() {
        return color;
    }

    public int getCount() {
        return 0;
    }

    public void setHeapItem(HeapItem heapItem) {
        this.heapItem = heapItem;
    }
}