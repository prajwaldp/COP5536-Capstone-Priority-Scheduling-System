public class DuplicateBuildingNumException extends Exception {
    private static final long serialVersionUID = 7248567347477897990L;

    DuplicateBuildingNumException(int buildingNum) {
        super("", new Error());
        String msg =  "Cannot insert " + buildingNum + ". Building number already exists.";
        System.err.println(msg);
    }
}
