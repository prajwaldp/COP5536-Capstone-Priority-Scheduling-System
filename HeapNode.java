import java.util.Comparator;

/**
 * A HeapNode is stored in the MinHeap.heap and represents
 * an individual building with the building number, th
 * executed time and the total time.
 */

class HeapNode implements Comparable<HeapNode>{

    /**
     * The executed time of the building
     */
    int executedTime;

    /**
     * The building number of the building
     */
    int buildingNum;

    /**
     * The time needed (days) to complete the building
     */
    int totalTime;

    /**
     * Each HeapNode object has a corresponding RedBlackNode object
     * This field maintains the relationship
     */
    RedBlackNode redBlackTreeNode;

    /**
     * Initialized a new HeapNode object with no corresponding RedBlackNode
     * @param executedTime The executed time of the building, initially 0
     * @param buildingNum The building number of the building
     * @param executedTime The total time required to complete the building
     */
    HeapNode(int executedTime, int buildingNum, int totalTime) {
        this.executedTime = executedTime;
        this.buildingNum = buildingNum;
        this.totalTime = totalTime;
        this.redBlackTreeNode = null;
    }

    /**
     * Compares the HeapNode object with heapNode using the
     * keys executedTime and buildingNode in that order
     * @param heapNode The heapNode to compare this (the object whose method is called) with
     * @return -1 if this <= heapNode, 0 if this == heapNode, 1 if this >= heapNode
     */
    @Override
    public int compareTo(HeapNode heapNode) {
        return Comparator.comparing(HeapNode::getExecutedTime)
            .thenComparing(HeapNode::getBuildingNum).compare(this, heapNode);
    }

    /**
     * @return The executedTime of the HeapNode object
     */
    public int getExecutedTime() {
        return this.executedTime;
    }

    /**
     * @return The buildingNum of the HeapNode object
     */
    public int getBuildingNum() {
        return this.buildingNum;
    }

    /* Setters */

     /**
      * Incremented the executedTime field of the HeapNode by 1
      */
     public void incrementExecutedTime() {
         this.executedTime++;
     }
}
