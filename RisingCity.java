import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class risingCity {
    /**
     * The main function is in the risingCity class and is the entry point of the program
     * @param args The command line arguments given to the program. The path to the input
     * file for the program is expected.
     * @throws IOException
     * @throws DuplicateBuildingNumException
     */
    public static void main(String[] args) throws IOException, DuplicateBuildingNumException {

        if (args.length != 1) {
            System.out.println("Invalid arguments");
            System.exit(-1);
        }

        RedBlackTree redBlackTree = new RedBlackTree();
        MinHeap heap = new MinHeap();
        HeapNode activeBuilding = null;  // The building being worked on

        String outputFile = "output_file.txt";
        BufferedWriter outputFileHandler = new BufferedWriter(new FileWriter(outputFile));
        StringBuilder str = new StringBuilder();

        int day = 0;  // Store the days passed since start of execution

        /*
            Store # days for which building has been in construction
            Reset to 0 when incremented from 5
        */
        int activeBuildingDay = 0;

        Command c;  // An object that serializes each line in the input file

        int arg1, arg2;  // variables for Insert instruction

        String inputFile = args[0];
        String line;  // To store each command
        BufferedReader inputFileHandler = new BufferedReader(new FileReader(inputFile));
        line = inputFileHandler.readLine();
        c = new Command(line);

        while (true) {

            /*
             The program exits when
             - All the lines in the input file are processed, and
             - The heap has no more nodes, and
             - There is no building currently being worked on
             */
            if (line == null && heap.getSize() == 0 && activeBuilding == null) {
                break;
            }

            /*
             if there is a building being worked on,
             - increment the executed time of the heap node, and the corresponding red black node
             - increment the days the building has been being worked on
             */
            if (activeBuilding != null) {
                activeBuilding.incrementExecutedTime();
                activeBuilding.redBlackTreeNode.incrementExecutedTime();
                activeBuildingDay++;
            }

            if (c.getTimestamp() == day) {
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
                        if (activeBuilding != null && activeBuilding.buildingNum == arg1) {
                            throw new DuplicateBuildingNumException(arg1);
                        }

                        HeapNode heapNode = heap.insert(0, arg1, arg2);  // also throws DuplicateBuildingNumException
                        RedBlackNode redBlackNode = redBlackTree.insert(arg1, arg2);
                        heapNode.redBlackTreeNode = redBlackNode;
                        redBlackNode.setHeapNode(heapNode);

                    } catch(DuplicateBuildingNumException e) {
                        System.err.println("Error at " + c.getLine() + ". Exiting with status -1");
                        System.exit(-1);
                    }
                }

                else if (c.getOperationCode() == Command.PRINT) {
                    str.append(redBlackTree.find(arg1));
                    str.append("\n");
                } else if (c.getOperationCode() == Command.PRINT_RANGE) {
                    str.append(redBlackTree.findInRange(arg1, arg2));
                    str.append("\n");
                }

                line = inputFileHandler.readLine();
                if (line != null) {
                    c = new Command(line);
                }
            }

            if (activeBuilding != null) {

                if (activeBuilding.executedTime == activeBuilding.totalTime) {
                    str.append("(" + activeBuilding.buildingNum + "," + day + ")");
                    str.append("\n");

                    redBlackTree.delete(activeBuilding.redBlackTreeNode);
                    activeBuildingDay = 0;
                    activeBuilding = heap.removeMin();
                }

                if (activeBuildingDay >= 5) {
                    heap.insert(activeBuilding);
                    activeBuilding = heap.removeMin();
                    activeBuildingDay = 0;
                }

            } else if (heap.getSize() > 0) {
                activeBuilding = heap.removeMin();
            }

            day++;
        }

        inputFileHandler.close();
        outputFileHandler.write(str.toString());
        outputFileHandler.close();
    }
}
