import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RisingCity {
    public static void main(String[] args) throws IOException, DuplicateBuildingNumException {

        String inputFile = "input2.txt";
        String line;  // To store each command

        int worldTime = 0;  // Store the current timestamp since start of execution

        /*
            Store # days for which building has been in construction
            Reset to 0 when incremented from 5
        */
        int localTime = 0;
        
        Command c;

        int arg1, arg2;  // variables for Insert instruction

        RedBlackTree redBlackTree = new RedBlackTree();
        Heap heap = new Heap();
        HeapItem currentlyWorkingOn = null;

        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        line = br.readLine();
        c = new Command(line);

        while (true) {

            if (line == null && heap.getSize() == 0 && currentlyWorkingOn == null) {
                break;
            }

            if (currentlyWorkingOn != null) {
                currentlyWorkingOn.executedTime++;
                currentlyWorkingOn.rbtNode.incrementExecutedTime();
                localTime++;
            }

            if (c.getTimestamp() == worldTime) {
                arg1 = c.getArg1();  // Stores the buildingNum
                
                /*
                    Stores
                    - the totalTime for Command.INSERT
                    - -1 for Command.PRINT
                    - the second buildingNum for Command.PRINT_RANGE
                    
                */
                arg2 = c.getArg2();

                if (c.getOperationCode() == Command.INSERT) {

                    try {
                        if (currentlyWorkingOn != null && currentlyWorkingOn.buildingNum == arg1) {
                            throw new DuplicateBuildingNumException(arg1);
                        }

                        HeapItem heapItem = heap.insert(0, arg1, arg2);  // also throws DuplicateBuildingNumException
                        RedBlackNode redBlackNode = redBlackTree.insert(arg1, arg2);
                        heapItem.rbtNode = redBlackNode;
                        redBlackNode.setHeapItem(heapItem);
                    
                    } catch(DuplicateBuildingNumException e) {
                        System.err.println("Error at " + c.getLine() + ". Exiting with status -1");
                        System.exit(-1);
                    }
                }

                else if (c.getOperationCode() == Command.PRINT) {
                    System.out.println(redBlackTree.find(arg1));
                } else if (c.getOperationCode() == Command.PRINT_RANGE) {
                    System.out.println(redBlackTree.findInRange(arg1, arg2));
                }
                
                line = br.readLine();
                if (line != null) {
                    c = new Command(line);
                }
            }

            if (currentlyWorkingOn != null) {

                if (currentlyWorkingOn.executedTime == currentlyWorkingOn.totalTime) {
                    System.out.println("(" + currentlyWorkingOn.buildingNum + "," + worldTime + ")");
                    redBlackTree.delete(currentlyWorkingOn.rbtNode);
                    localTime = 0;
                    currentlyWorkingOn = heap.removeMin();
                }

                if (localTime >= 5) {
                    heap.insert(currentlyWorkingOn);
                    currentlyWorkingOn = heap.removeMin();
                    localTime = 0;
                }

            } else if (heap.getSize() > 0) {
                currentlyWorkingOn = heap.removeMin();
            }

            worldTime++;
        }

        br.close();
    }
}
