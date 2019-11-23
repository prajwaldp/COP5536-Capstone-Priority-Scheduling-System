import java.util.ArrayList;
import java.util.LinkedList;

public class RedBlackTree {
    public Node nil = new Node(0, 0, 0);
    public Node root = nil;

    public String output = "";

    public Node insert(int buildingNum, int totalTime) {
        Node n = new Node(buildingNum, 0, totalTime);

        n.setRed();
        n.setLeft(nil);
        n.setRight(nil);
        
        if (this.root == nil) {
            n.setBlack();
            n.setParent(nil);
            this.root = n;
            return this.root;
        }
        
        Node curr = this.root;
        Node prev = this.root;

        while (curr != nil) {
            prev = curr;
            if (n.getBuildingNum() < curr.getBuildingNum()) {
                curr = curr.getLeft();
            } else {
                curr = curr.getRight();
            }
        }
        
        n.setParent(prev);
        
        if (n.getBuildingNum() < prev.getBuildingNum()) {
            prev.setLeft(n);
        } else {
            prev.setRight(n);
        }
        
        this.balanceAfterInserting(n);

        return n;
    }

    public void balanceAfterInserting(Node p) {

        Node pp = p.getParent();  // Parent node
        Node gp = p.getParent().getParent();  // Grand parent node
        Node d;  // Uncle node

        while (pp.isRed()) {
            if (pp == gp.getLeft()) {
                d = gp.getRight();  // pp is left child of gp - set d as right child
                
                if (d.isRed()) {
                    /*
                        XYr => color flip gp, pp and p
                    */
                    pp.setBlack();
                    d.setBlack();
                    gp.setRed();
                    p = gp;
                } else {
                    /*
                        XYb => XY rotation
                    */
                    if (p == pp.getRight()) {
                        /*
                            XLb => XL rotate
                        */
                        p = pp;
                        this.rotateLeftAround(p);
                    }
                    /*
                        LRb or RRb => RR rotate
                    */
                    pp.setBlack();
                    gp.setRed();
                    this.rotateRightAround(gp);
                }
            } else {
                d = gp.getLeft();  // pp is right child of gp - set d as left child
                
                if (d.isRed()) {
                    /*
                        XYr => color flip gp, pp and p
                    */
                    pp.setBlack();
                    d.setBlack();
                    gp.setRed();
                    p = gp;
                } else {
                    /*
                        XYb => XY rotation
                    */
                    if (p == pp.getLeft()) {
                        /*
                            XRb => XR rotate
                        */
                        p = pp;
                        this.rotateRightAround(p);
                    }
                    /*
                        LRb or LLb => LL rotate
                    */
                    pp.setBlack();
                    gp.setRed();
                    this.rotateLeftAround(gp);
                }
            }
        }
        
        this.root.setBlack();
    }

    /*
        Private functions - not exposed as an API
    */

    private void rotateLeftAround(Node p) {
        Node pp = p.getParent();
        Node rightChild = p.getRight();
        
        p.setRight(rightChild.getLeft());
        
        if (rightChild.getLeft() != nil) {
            rightChild.getLeft().setParent(p);
        }
        
        rightChild.setParent(pp);
        
        if (pp == nil) {
            root = rightChild;
        } else if (p == pp.getLeft()) {
            pp.setLeft(rightChild);
        } else {
            pp.setRight(rightChild);
        }
        
        rightChild.setLeft(p);
        p.setParent(rightChild);
    }

    private void rotateRightAround(Node p) {
        Node pp = p.getParent();
        Node leftChild = p.getLeft();
        
        p.setLeft(leftChild.getRight());
        
        if (leftChild.getRight() != nil) {
            leftChild.getRight().setParent(p);
        }
        
        leftChild.setParent(pp);
        
        if (pp == nil) {
            root = leftChild;
        } else if (p == pp.getRight()) {
            pp.setRight(leftChild);
        } else {
            pp.setLeft(leftChild);
        }
        
        leftChild.setRight(p);
        p.setParent(leftChild);
    }

    public void swap(Node a, Node b) {
        if (a.getParent() == nil) {
            this.root = b;
        } else if (a == a.getParent().getLeft()) {
            a.getParent().setLeft(b);
        } else {
            a.getParent().setRight(b);
        }
        
        b.setParent(a.getParent());
    }

    public void delete(Node n) {
        Node x, y;
        boolean yIsBlack;

        y = n;
        yIsBlack = y.isBlack();
        
        if (n.getLeft() == nil) {
            x = n.getRight();
            this.swap(n, n.getRight());
        } else if (n.getRight() == nil) {
            x = n.getLeft();
            this.swap(n, n.getLeft());
        } else {
            y = n.getRight();
            
            while (y.getLeft() != nil) {
                y = y.getLeft();
            }
            
            yIsBlack = y.isBlack();
            
            x = y.getRight();
            
            if (y.getParent() == n) {
                x.setParent(y);
            } else {
                this.swap(y, y.getRight());
                y.setRight(n.getRight());
                y.getRight().setParent(y);
            }

            this.swap(n, y);
            y.setLeft(n.getLeft());
            y.getLeft().setParent(y);
            y.setColor(n.getColor());
        }

        if (yIsBlack) {
            balanceAfterDeleting(x);
        }
    }

    /*
        Deleting a node
        ===============

        y = root of deficient subtree
        py = parent of y

        - If y is a red node, make it black
        - If y is black and no py ⇒ done
        - If y is black and py exists
            - Xcn, X = R or L, c = color of v, n = # red nodes of v
            - Rb0
                - py = b ⇒ color change v
                - py = r ⇒ color change py and v
            - Rb1 and Rb2 ⇒ LR rotation
            - Rr0 ⇒ LL rotation
            - Rr1 ⇒ LR rotation
    */
    public void balanceAfterDeleting(Node y) {

        /* 
            py => parent of `y`
            v => sibling of `y`
        */
        Node py, v;

        // If y is black and py exists
        while (y != root && y.isBlack()) {

            py = y.getParent();

            if (y == py.getLeft()) {
                // X = L
                v = py.getRight();
                
                if (v.isRed()) {
                    // c = R
                    v.setBlack();
                    py.setRed();
                    this.rotateLeftAround(py);
                    v = py.getRight();
                }
                
                if (v.getLeft().isBlack() && v.getRight().isBlack()) {
                    v.setRed();
                    y = py;
                } else {
                    
                    if (v.getRight().isBlack()) {
                        v.getLeft().setBlack();
                        v.setRed();
                        this.rotateRightAround(v);
                        v = py.getRight();
                    }
                    
                    v.setColor(py.getColor());
                    py.setBlack();
                    v.getRight().setBlack();
                    this.rotateLeftAround(py);
                    y = root;
                }
            } else {
                // X = R
                v = py.getLeft();
                
                if (v.isRed()) {
                    // c = R
                    v.setBlack();
                    py.setRed();
                    this.rotateRightAround(py);
                    v = py.getLeft();
                }

                if (v.getRight().isBlack() && v.getLeft().isBlack()) {
                    v.setRed();
                    y = py;
                } else {
                    if (v.getLeft().isBlack()) {
                        v.getRight().setBlack();
                        v.setRed();
                        rotateLeftAround(v);
                        v = py.getLeft();
                    }
                    v.setColor(py.getColor());
                    py.setBlack();
                    v.getLeft().setBlack();
                    this.rotateRightAround(py);
                    y = root;
                }
            }
        }
        
        y.setBlack();
    }

    public Node findByBuildingNum(int buildingNum) {
        Node tmp = this.root;
        
        while (tmp != nil) {
            if (tmp.getBuildingNum() == buildingNum) {
                return tmp;
            } else if (tmp.getBuildingNum() < buildingNum) {
                tmp = tmp.getRight();
            } else {
                tmp = tmp.getLeft();
            }
        }
        
        return nil;
    }

    public String findInRange(int buildingNum1, int buildingNum2) {
        String outputStr = this.findInRange(this.root, buildingNum1, buildingNum2);

        if (outputStr.length() != 0) {
            return outputStr.substring(0, outputStr.length() - 1);
        }

        return "(0,0,0)";
    }

    private String findInRange(Node node, int buildingNum1, int buildingNum2) {
        String outputStr = "";
        int buildingNum = node.getBuildingNum();

        if (node == nil) {
            return outputStr;
        }

        if (buildingNum > buildingNum1) {
            outputStr += this.findInRange(node.getLeft(), buildingNum1, buildingNum2);
        }

        if (buildingNum1 <= buildingNum && buildingNum <= buildingNum2) {
            outputStr += "(" + buildingNum + "," + node.getExecutedTime() + "," + node.getTotal_time()+ "),";
        }

        if (node.getBuildingNum() < buildingNum2) {
            outputStr += this.findInRange(node.getRight(), buildingNum1, buildingNum2);
        }
        
        return outputStr;
    }
}
