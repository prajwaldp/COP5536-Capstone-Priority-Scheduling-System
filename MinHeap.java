/**
 * MinHeap is a class-based implementation of a Heap data structure
 * The heap implemented here is a min-heap (the value at a node is
 * less than the value in both its child nodes).
 */
public class MinHeap {

    /**
     * The max size of the heap (needed for allocating memory)
     */
    private static int MAX_SIZE = 2000;

    /**
     * The array that stores the HeapNode
     */
    private HeapNode heap[];

    /**
     * The current size of the heap
     */
    private int size;

    /**
     * Initializes a new Heap object
     */
    MinHeap() {
        this.heap = new HeapNode[MAX_SIZE];
        this.size = 0;
    }

    /**
     * @return The current size of the heap
     */
    int getSize() {
        return this.size;
    }

    /**
     * Creates a HeapNode object with the executedTime, buildingNum and totalTime provided.
     * @param executedTime The executed time of the HeapNode object to be created and inserted into the heap.
     * @param buildingNum The building number of the HeapNode object to be created and inserted into the heap.
     * @param totalTime The total time of the HeapNode object to be created and inserted into the heap.
     * @throws DuplicateBuildingNumException
     * @return The HeapNode that was inserted into the heap
     */
    HeapNode insert(int executedTime, int buildingNum, int totalTime) throws DuplicateBuildingNumException {
        HeapNode node = new HeapNode(executedTime, buildingNum, totalTime);
        int position = this.size++;
        this.heap[position] = node;

        while (this.heap[position].compareTo(this.heap[getParent(position)]) < 0) {

            if (this.heap[position].getBuildingNum() ==
                    this.heap[getParent(position)].getBuildingNum()) {
                throw new DuplicateBuildingNumException(this.heap[position].getBuildingNum());
            }

            this.swap(position, getParent(position));
            position = getParent(position);
        }

        return this.heap[position];
    }

    /**
     * Inserts the heap item into the heap
     * @param heapNode The HeapNode object that is inserted
     * @throws DuplicateBuildingNumException
     */
    void insert(HeapNode heapNode) throws DuplicateBuildingNumException {
        int position = this.size++;
        this.heap[position] = heapNode;

        while (heap[position].compareTo(heap[getParent(position)]) < 0) {

            if (heap[position].getBuildingNum() ==
                    heap[getParent(position)].getBuildingNum()) {
                throw new DuplicateBuildingNumException(heap[position].getBuildingNum());
            }

            this.swap(position, getParent(position));
            position = getParent(position);
        }
    }

    /**
     * Get the next smallest item in the min-heap
     * @return The HeapNode at position 0, i.e. with the smallest value in the min-heap
     */
    HeapNode peek() {
        return this.heap[0];
    }

    /**
     * Removes the HeapNode with the smalled value from the min-heap
     * @return The removed HeapNode
     */
    HeapNode removeMin() {

        if (this.size == 0) {
            return null;
        }

        HeapNode removedItem = this.heap[0];
        this.heap[0] = this.heap[--size];
        this.heap[size] = new HeapNode(Integer.MAX_VALUE, 0, 0);
        this.heapify(0);

        return removedItem;
    }

    /**
     * Swaps the HeapNode objects at positions a and b of the heap.
     * The objects themselves are not swapped, but the attributes
     * of the HeapNode object (executedTime, buildingNum,
     * totalTime and rbtNode) are swapped between the objects
     * @param a The index of the first HeapNode object in the heap
     * @param b The index of the second HeapNode object in the heap
     */
    void swap(int a, int b) {
        HeapNode tmp = new HeapNode(Integer.MIN_VALUE, 0, 0);

        tmp.executedTime = this.heap[a].executedTime;
        this.heap[a].executedTime = this.heap[b].executedTime;
        this.heap[b].executedTime = tmp.executedTime;

        tmp.buildingNum = this.heap[a].buildingNum;
        this.heap[a].buildingNum = this.heap[b].buildingNum;
        this.heap[b].buildingNum = tmp.buildingNum;

        tmp.totalTime = this.heap[a].totalTime;
        this.heap[a].totalTime = this.heap[b].totalTime;
        this.heap[b].totalTime = tmp.totalTime;

        tmp.redBlackTreeNode = this.heap[a].redBlackTreeNode;
        this.heap[a].redBlackTreeNode = this.heap[b].redBlackTreeNode;
        this.heap[b].redBlackTreeNode = tmp.redBlackTreeNode;

    }

    /**
     * Fixes the entire heap, should be used after a removeMin
     */
    void heapify() {
        for (int i = (size / 2); i >= 0; i--) {
            heapify(i);
        }
    }

    /**
     * Fixes the heap from index i, i.e. the tree rooted at position i
     * The value of the HeapNode's executedTime and buildingNum
     * is used for the camparison.
     * @param i The index of the root of the subtree to fix
     */
    void heapify(int i) {
        if (!isLeaf(i)) {
            if (this.heap[i].compareTo(this.heap[getLeftChild(i)]) > 0
                    || this.heap[i].compareTo(this.heap[getRightChild(i)]) > 0) {

                if (this.heap[getLeftChild(i)].compareTo(this.heap[getRightChild(i)]) < 0) {
                    this.swap(i, getLeftChild(i));
                    this.heapify(getLeftChild(i));
                } else {
                    this.swap(i, getRightChild(i));
                    this.heapify(getRightChild(i));
                }
            }
        }
    }

    /**
     * @param index The index whose parent's index is to be returned
     * @return The index of the parent of the index provided
     */
    private int getParent(int index) {
        return index / 2;
    }

    /**
     * @param index The index whose left child's index is to be returned
     * @return The index of the left child of the index provided
     */
    private int getLeftChild(int index) {
        return (2 * index) + 1;
    }

    /**
     * @param index The index whose right child's index is to be returned
     * @return The index of the right child of the index provided
     */
    private int getRightChild(int index) {
        return (2 * index) + 2;
    }

    /**
     * @param index The index of the HeapNode which is to be determined to be a root or not
     * @return true if the HeapNode at the index provided is a leaf node, else false
     */
    private boolean isLeaf(int index) {
        return index >= (this.size / 2) && index <= this.size;
    }
}
