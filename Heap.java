public class Heap {

    /*
        A class-based implementation of the heap data structure
        The heap implemented here is a min-heap
    */

    private static int MAX_SIZE = 2000;

    private HeapItem heap[];
    private int size;

    Heap() {
        this.heap = new HeapItem[MAX_SIZE];
        this.size = 0;
    }

    int getSize() {
        return this.size;
    }

    HeapItem insert(int executedTime, int buildingNum, int totalTime) throws DuplicateBuildingNumException {
        HeapItem node = new HeapItem(executedTime, buildingNum, totalTime);
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

    void insert(HeapItem heapItem) throws DuplicateBuildingNumException {
        int position = this.size++;
        this.heap[position] = heapItem;

        while (heap[position].compareTo(heap[getParent(position)]) < 0) {

            if (heap[position].getBuildingNum() ==
                    heap[getParent(position)].getBuildingNum()) {
                throw new DuplicateBuildingNumException(heap[position].getBuildingNum());
            }

            this.swap(position, getParent(position));
            position = getParent(position);
        }
    }

    HeapItem peek() {
        return this.heap[0];
    }

    HeapItem removeMin() {

        if (this.size == 0) {
            return null;
        }

        HeapItem removedItem = this.heap[0];
        this.heap[0] = this.heap[--size];
        this.heap[size] = new HeapItem(Integer.MAX_VALUE, 0, 0);
        this.heapify(0);

        return removedItem;
    }

    void swap(int a, int b) {
        HeapItem tmp = new HeapItem(Integer.MIN_VALUE, 0, 0);

        tmp.executedTime = this.heap[a].executedTime;
        this.heap[a].executedTime = this.heap[b].executedTime;
        this.heap[b].executedTime = tmp.executedTime;

        tmp.buildingNum = this.heap[a].buildingNum;
        this.heap[a].buildingNum = this.heap[b].buildingNum;
        this.heap[b].buildingNum = tmp.buildingNum;

        tmp.totalTime = this.heap[a].totalTime;
        this.heap[a].totalTime = this.heap[b].totalTime;
        this.heap[b].totalTime = tmp.totalTime;

        tmp.rbtNode = this.heap[a].rbtNode;
        this.heap[a].rbtNode = this.heap[b].rbtNode;
        this.heap[b].rbtNode = tmp.rbtNode;

    }

    void heapify() {
        for (int i = (size / 2); i >= 0; i--) {
            heapify(i);
        }
    }

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

    private int getParent(int position) {
        return position / 2;
    }

    private int getLeftChild(int position) {
        return (2 * position) + 1;
    }

    private int getRightChild(int position) {
        return (2 * position) + 2;
    }

    private boolean isLeaf(int position) {
        return position >= (this.size / 2) && position <= this.size;
    }
}
