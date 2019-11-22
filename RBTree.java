public class RBTree {

    private RBNode root;

    public RBTree() {
        this.root = null;
    }

    public RBTree(RBNode root) {
        this.root = root;
    }

    public RBNode insert(RBNode n) {
        if (this.root == null) {
            this.root = n;
        }

        RBNode curr = root;
        RBNode prev = null;

        while (curr != null) {
            prev = curr;
            
            if (n.getBuildingNum() < curr.getBuildingNum()) {
                curr = curr.getLeftChild();
            } else {
                curr = curr.getRightChild();
            }
        }
        
        n.setParent(prev);

        if (n.getBuildingNum() < prev.getBuildingNum()) {
            prev.setLeftChild(n);
        } else {
            prev.setRightChild(n);
        }

        this.balanceAfterInsert(n);

        return n;
    }

    private void balanceAfterInsert(RBNode n) {
        while (n.getParent().isRed()) {

            RBNode uncle = n.getUncle();

            if (uncle.isRed()) {
                n.getParent().setBlack();
                n.getParent().getParent().setBlack();
                uncle.setBlack();
                n = n.getParent().getParent();
            } else {
                
            }

        }
    }
}