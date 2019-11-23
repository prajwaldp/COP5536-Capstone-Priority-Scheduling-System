import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class bbst {
    static int INSERT = 0;
    static int PRINT = 1;
    static int PRINT_RANGE = 2;
    public static Node nil = new Node(0, 0, 0);

    static void insert(int buildingNum, int totalTime, MyMinHeap minHeap, RedBlackTree tree) {
        HeapItem heapItem = minHeap.insert(0, buildingNum, totalTime);
        Node treeNode = tree.insert(buildingNum, totalTime);
        heapItem.rbtNode = treeNode;
        treeNode.heapItem = heapItem;
    }

    static void insert(int executedTime, int buildingNum, int totalTime, MyMinHeap minHeap, RedBlackTree tree) {
        HeapItem heapItem = minHeap.insert(executedTime, buildingNum, totalTime);
        Node treeNode = tree.insert(buildingNum, totalTime);
        heapItem.rbtNode = treeNode;
        treeNode.heapItem = heapItem;
    }

    static void insert(HeapItem heapItem, MyMinHeap minHeap, RedBlackTree tree) {
        HeapItem insertedHeapItem = minHeap.insert(heapItem.executedTime, heapItem.buildingNum, heapItem.totalTime);
        Node treeNode = tree.insert(heapItem.buildingNum, heapItem.totalTime);
        insertedHeapItem.rbtNode = treeNode;
        treeNode.heapItem = insertedHeapItem;
    }

    static HeapItem removeMin(MyMinHeap minHeap, RedBlackTree tree) {

        HeapItem removedItem = minHeap.removeMin();

        if (removedItem != null) {
            // tree.delete(removedItem.rbtNode);
        }

        return removedItem;
    }

    public static void main(String[] args) throws IOException {
        File inputFile = new File("input2.txt");
        Scanner sc = new Scanner(inputFile);

        String input = "";
        String globalSplitInput[] = new String[2];
        String buildingSplitInput[] = new String[2];

        int inputGlobalTimer = -1;
        int buildingNum = -1;
        int totalTime = -1;
        int buildingNum1 = -1;
        int buildingNum2 = -1;
        String operation = "";
        int instructions[][] = new int[2000][4];
        int index = 0;
        HeapItem currentlyWorkingOn = null;

        int daysPassed = 0;
        int currInstruction = 0;

        RedBlackTree tree = new RedBlackTree();
        MyMinHeap minHeap = new MyMinHeap();

        while (sc.hasNextLine()) {
            input = sc.nextLine();
            globalSplitInput = input.split(":");
            inputGlobalTimer = Integer.parseInt(globalSplitInput[0]);
            buildingSplitInput = globalSplitInput[1].split("\\(");
            operation = buildingSplitInput[0].trim();

            if (!buildingSplitInput[1].contains(",")) {// Print(30)
                buildingNum = Integer.parseInt(buildingSplitInput[1].split("\\)")[0]);
                totalTime = -1;
            } else {
                buildingNum = Integer.parseInt(buildingSplitInput[1].split(",")[0]);

                if (operation.compareTo("PrintBuilding") == 0) {// Print(0,100)
                    // buildingNum1 = Integer.parseInt(buildingSplitInput[1].split(",")[0]);
                    buildingNum2 = Integer.parseInt(buildingSplitInput[1].split(",")[1].split("\\)")[0]);
                } else
                    // buildingNum = Integer.parseInt(buildingSplitInput[1].split(",")[0]);
                    totalTime = Integer.parseInt(buildingSplitInput[1].split(",")[1].split("\\)")[0]);
            }
            instructions[index][0] = inputGlobalTimer;
            switch (operation) {
            case "Insert":
                instructions[index][1] = INSERT;
                break;
            case "PrintBuilding":
                if (buildingNum2 == -1)
                    instructions[index][1] = PRINT;
                else
                    instructions[index][1] = PRINT_RANGE;

                break;
            default:
                System.out.println("Wrong Operation!!");
                break;
            }
            instructions[index][2] = buildingNum;
            if (totalTime == -1)
                instructions[index][3] = buildingNum2;
            else
                instructions[index][3] = totalTime;
            index++;

        }
        sc.close();

        for (int timer = 0;; timer++) {

            if (timer != 0 && instructions[currInstruction][0] == 0 && minHeap.size == 0
                    && currentlyWorkingOn == null) {
                break;
            }

            if (currentlyWorkingOn != null) {
                currentlyWorkingOn.executedTime++;
                currentlyWorkingOn.rbtNode.setExecuted_time(currentlyWorkingOn.rbtNode.getExecuted_time() + 1);
                daysPassed++;
            }

            if (instructions[currInstruction][0] == timer) {
                if (instructions[currInstruction][1] == INSERT) {
                    buildingNum = instructions[currInstruction][2];
                    if (minHeap.size > 0 || currentlyWorkingOn != null) {
                        if (minHeap.found(currentlyWorkingOn, buildingNum)) {
                            System.out.println("Duplicate building number not allowed!\nTerminating...");
                            break;
                        }
                    }
                    totalTime = instructions[currInstruction][3];
                    insert(buildingNum, totalTime, minHeap, tree);
                }

                else if (instructions[currInstruction][1] == PRINT) {

                    Node print = tree.findByBuildingNum(instructions[currInstruction][2]);
                    if (print == nil) {
                        System.out.print("(0,0,0)");
                    } else
                        System.out.print("(" + print.getBuildingNum() + "," + print.getExecutedTime() + ","
                                + print.getTotalTime() + ")");
                }

                else if (instructions[currInstruction][1] == PRINT_RANGE) {
                    System.out.println(tree.findInRange(buildingNum1, buildingNum2));
                }
                
                currInstruction++;
            }

            if (currentlyWorkingOn != null) {
                // currentlyWorkingOn.executedTime++;
                // daysPassed++;

                if (currentlyWorkingOn.executedTime == currentlyWorkingOn.totalTime) {
                    System.out.println("(" + currentlyWorkingOn.buildingNum + "," + timer + ")");
                    tree.delete(currentlyWorkingOn.rbtNode);
                    daysPassed = 0;
                    currentlyWorkingOn = removeMin(minHeap, tree);
                }

                if (daysPassed >= 5) {
                    // tree.delete(currentlyWorkingOn.rbtNode);
                    minHeap.insert(currentlyWorkingOn);
                    currentlyWorkingOn = removeMin(minHeap, tree);
                    daysPassed = 0;
                }

            } else if (minHeap.size > 0) {
                currentlyWorkingOn = removeMin(minHeap, tree);
            }
        }
    }
}
