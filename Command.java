/**
* The Command object created for each line of the input file.
* This class provides methods for parsing and getting individual
* components such as the day number, operation and the arguments
* to the operation.
*/
public class Command {

    /**
     * The day number
     */
    private int timestamp;

    /**
     * The operation string, can be "Insert", "Print" or "PrintBuilding"
     */
    private String operation;

    /**
     * The first argument in the operation
     */
    private int arg1;

    /**
     * The second argument in the operation
     * Is -1 for the "Print" operation
     */
    private int arg2 = -1;

    /**
     * The entire line as a string
     */
    private String line = "";

    /**
     * Operation code for the "Insert" command
     */
    static int INSERT = 0;

    /**
     * Operation code for the "Print" command
     */
    static int PRINT = 1;

    /**
     * Operation code for the "PrintBuilding" command
     */
    static int PRINT_RANGE = 2;

    /**
     * Builds a Command object from the line in the input file
     * @param line The line in the input file
     * @return the amount of health hero has after attack
     */
    Command(String line) {
        this.line = line;
        this.constructFromInputLine(line);
    }

    /**
     * @return The timestamp of the Command object
     */
    public int getTimestamp() {
        return this.timestamp;
    }

    /**
     * @return The operation of the Command object
     */
    public String getOperation() {
        return this.operation;
    }

    /**
     * @return The operation code (an Integer) corresponding to the operation,
     * -1 if the operation cannot be found
     */
    public int getOperationCode() {
        if (this.operation.compareTo("Insert") == 0) {
            return Command.INSERT;
        } else if (this.operation.compareTo("Print") == 0) {
            return Command.PRINT;
        } else if (this.operation.compareTo("PrintBuilding") == 0) {
            return PRINT_RANGE;
        }

        System.out.println("Error: Unknown operation " + this.operation);
        return -1;  // Error
    }

    /**
     *
     * @return The first argument in the operation
     */
    public int getArg1() {
        return this.arg1;
    }

    /**
     *
     * @return The second argument in the operation
     */
    public int getArg2() {
        return this.arg2;
    }

    /**
     *
     * @return The operation as it appears in the input file
     */
    public String getLine() {
        return this.line;
    }

    /**
     *
     * @return The string version of the Command
     */
    public String toString() {
        return this.timestamp + ", " + this.operation + ", " + this.arg1 + ", " + this.arg2;
    }

    /**
     * Parses the line from the input file into individual components
     * of the Command object
     */
    private void constructFromInputLine (String line) {
        String e[];  // to store individual elements
        String args[];  // to store the args array, e.g. [1, 2]
        String argsStr;  // to store the args string, e.g. "1,2"

        // Parse the timestamp
        e = line.split(": ");
        this.timestamp = Integer.parseInt(e[0]);

        // Parse the operation
        e = e[1].split("\\(");
        this.operation = e[0];

        // Remove the trailing )
        argsStr = e[1].substring(0, e[1].length() - 1);

        args = argsStr.split(",");
        this.arg1 = Integer.parseInt(args[0]);
        this.arg2 = args.length == 2 ? Integer.parseInt(args[1]) : -1;
    }
}
