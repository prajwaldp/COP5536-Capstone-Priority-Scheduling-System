import java.util.Comparator;

class HeapItem implements Comparable<HeapItem>{
    int executedTime;
    int buildingNum;
    int totalTime;
    Node rbtNode;

    HeapItem(int executedTime, int buildingNum, int totalTime) {
        this.executedTime = executedTime;
        this.buildingNum = buildingNum;
        this.totalTime = totalTime;
        this.rbtNode = null;
    }

    @Override
    public int compareTo(HeapItem heapItem) 
    {
        return Comparator.comparing(HeapItem::getExecutedTime)
            .thenComparing(HeapItem::getBuildingNum).compare(this, heapItem);
    }

    public int getExecutedTime() {
        return this.executedTime;
    }

    public int getBuildingNum() {
        return this.buildingNum;
    }
}
