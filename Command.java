public class Command {

    private int timestamp;
    private String operation;
    private int arg1;
    private int arg2 = -1;
    private String line = "";

    /*
        Operation Codes
    */
    static int INSERT = 0;
    static int PRINT = 1;
    static int PRINT_RANGE = 2;

    Command(String line) {
        this.line = line;
        this.constructFromInputLine(line);
    }

    public int getTimestamp() {
        return this.timestamp;
    }

    public String getOperation() {
        return this.operation;
    }

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

    public int getArg1() {
        return this.arg1;
    }

    public int getArg2() {
        return this.arg2;
    }

    public String getLine() {
        return this.line;
    }

    public String toString() {
        return this.timestamp + ", " + this.operation + ", " + this.arg1 + ", " + this.arg2;
    }

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