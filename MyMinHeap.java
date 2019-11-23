public class MyMinHeap {

    private static int MAX_SIZE = 2000;

    private HeapItem heap[];
    public int size;

    MyMinHeap() {
        this.heap = new HeapItem[MAX_SIZE];
        this.size = 0;
    }

    HeapItem insert(int executedTime, int buildingNum, int totalTime) {
        HeapItem node = new HeapItem(executedTime, buildingNum, totalTime);
        int position = this.size++;
        this.heap[position] = node;

        while (heap[position].compareTo(heap[getParent(position)]) < 0) {
            this.swap(position, getParent(position));
            position = getParent(position);
        }

        return this.heap[position];
    }

    void insert(HeapItem heapItem) {
        int position = this.size++;
        this.heap[position] = heapItem;

        while (heap[position].compareTo(heap[getParent(position)]) < 0) {
            this.swap(position, getParent(position));
            position = getParent(position);
        }
    }

    HeapItem getMin() {
        return this.heap[0];
    }

    HeapItem removeMin() {

        if (this.size == 0) {
            return null;
        }

        HeapItem removedItem = this.heap[0];
        this.heap[0] = this.heap[--size];
        this.heap[size] = new HeapItem(Integer.MAX_VALUE, 0, 0);
        this.minHeapify(0);

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

    void minHeapify() {
        for (int i = (size / 2); i >= 0; i--) {
            minHeapify(i);
        }
    }

    void minHeapify(int i) {
        if (!isLeaf(i)) {
            if (this.heap[i].compareTo(this.heap[getLeftChild(i)]) > 0
                    || this.heap[i].compareTo(this.heap[getRightChild(i)]) > 0) {

                if (this.heap[getLeftChild(i)].compareTo(this.heap[getRightChild(i)]) < 0) {
                    swap(i, getLeftChild(i));
                    minHeapify(getLeftChild(i));
                } else {
                    swap(i, getRightChild(i));
                    minHeapify(getRightChild(i));
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
        if (position >= (size / 2) && position <= size) {
            return true;
        }
        return false;
    }

    boolean found(HeapItem currentlyWorkingOn, int bNum) {
        if (size > 0) {
            int b = 0;
            int e = size - 1;
            while (b <= e) {
                int m = (b + e) / 2;
                if (heap[m].buildingNum == bNum)
                    return true;
                else if (bNum > heap[m].buildingNum)
                    b = m + 1;
                else
                    e = m - 1;
            }
        }
        if (currentlyWorkingOn != null) {
            if (currentlyWorkingOn.buildingNum == bNum)
                return true;
        }
        return false;

    }

    // boolean searchMinHeap(int bNum) {
    // int b = 0;
    // int e = size;
    // while (b <= e) {
    // int m = (b + e) / 2;
    // if (heap[m].buildingNum == bNum)
    // return true;
    // else if (bNum > heap[m].buildingNum)
    // b = m + 1;
    // else
    // e = m - 1;
    // }
    // return false;
    // }

    // boolean searchCurrentNode(HeapItem currentlyWorkingOn, int bNum) {
    // if (currentlyWorkingOn.buildingNum == bNum)
    // return true;
    // return false;
    // }

    // public static void main(String[] args) {
    // MyMinHeap minHeap = new MyMinHeap();

    // minHeap.insert(4);
    // minHeap.insert(10);
    // minHeap.insert(23);
    // minHeap.insert(7);
    // minHeap.insert(10);

    // minHeap.print();

    // int removedItem = minHeap.removeMin();
    // System.out.println("Removed " + removedItem);
    // minHeap.print();
    // }
}
