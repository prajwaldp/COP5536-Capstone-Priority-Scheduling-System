/**
 * A Custom Exception to handle duplicate building numbers.
 * Raised when the duplicate building number is inserted into
 * the heap or is set as the risingCity.activeBuilding
 */
public class DuplicateBuildingNumException extends Exception {
    private static final long serialVersionUID = 7248567347477897990L;

    /**
     *
     * @param buildingNum The duplicate building number that is inserted
     */
    DuplicateBuildingNumException(int buildingNum) {
        super("", new Error());
        String msg =  "Cannot insert " + buildingNum + ". Building number already exists.";
        System.err.println(msg);
    }
}
