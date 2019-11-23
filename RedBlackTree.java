public class RedBlackTree {

    RedBlackNode root = RedBlackNode.SENTINEL_NODE;

    public RedBlackNode insert(int buildingNum, int totalTime) {
        RedBlackNode n = new RedBlackNode(buildingNum, 0, totalTime);

        n.setRed();
        n.setLeft(RedBlackNode.SENTINEL_NODE);
        n.setRight(RedBlackNode.SENTINEL_NODE);
        
        if (this.root == RedBlackNode.SENTINEL_NODE) {
            n.setBlack();
            n.setParent(RedBlackNode.SENTINEL_NODE);
            this.root = n;
            return this.root;
        }
        
        RedBlackNode curr = this.root;
        RedBlackNode prev = this.root;

        while (curr != RedBlackNode.SENTINEL_NODE) {
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

    public void balanceAfterInserting(RedBlackNode p) {

        RedBlackNode pp = p.getParent();  // Parent node
        RedBlackNode gp = p.getParent().getParent();  // Grand parent node
        RedBlackNode d;  // Uncle node

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

    private void rotateLeftAround(RedBlackNode p) {
        RedBlackNode pp = p.getParent();
        RedBlackNode rightChild = p.getRight();
        
        p.setRight(rightChild.getLeft());
        
        if (rightChild.getLeft() != RedBlackNode.SENTINEL_NODE) {
            rightChild.getLeft().setParent(p);
        }
        
        rightChild.setParent(pp);
        
        if (pp == RedBlackNode.SENTINEL_NODE) {
            root = rightChild;
        } else if (p == pp.getLeft()) {
            pp.setLeft(rightChild);
        } else {
            pp.setRight(rightChild);
        }
        
        rightChild.setLeft(p);
        p.setParent(rightChild);
    }

    private void rotateRightAround(RedBlackNode p) {
        RedBlackNode pp = p.getParent();
        RedBlackNode leftChild = p.getLeft();
        
        p.setLeft(leftChild.getRight());
        
        if (leftChild.getRight() != RedBlackNode.SENTINEL_NODE) {
            leftChild.getRight().setParent(p);
        }
        
        leftChild.setParent(pp);
        
        if (pp == RedBlackNode.SENTINEL_NODE) {
            root = leftChild;
        } else if (p == pp.getRight()) {
            pp.setRight(leftChild);
        } else {
            pp.setLeft(leftChild);
        }
        
        leftChild.setRight(p);
        p.setParent(leftChild);
    }

    public void swap(RedBlackNode a, RedBlackNode b) {
        if (a.getParent() == RedBlackNode.SENTINEL_NODE) {
            this.root = b;
        } else if (a == a.getParent().getLeft()) {
            a.getParent().setLeft(b);
        } else {
            a.getParent().setRight(b);
        }
        
        b.setParent(a.getParent());
    }

    public void delete(RedBlackNode n) {
        RedBlackNode x, y;
        boolean yIsBlack;

        y = n;
        yIsBlack = y.isBlack();
        
        if (n.getLeft() == RedBlackNode.SENTINEL_NODE) {
            x = n.getRight();
            this.swap(n, n.getRight());
        } else if (n.getRight() == RedBlackNode.SENTINEL_NODE) {
            x = n.getLeft();
            this.swap(n, n.getLeft());
        } else {
            y = n.getRight();
            
            while (y.getLeft() != RedBlackNode.SENTINEL_NODE) {
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
    public void balanceAfterDeleting(RedBlackNode y) {

        /* 
            py => parent of `y`
            v => sibling of `y`
        */
        RedBlackNode py, v;

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

    public String find(int buildingNum) {
        RedBlackNode node = this.findByBuildingNum(buildingNum);

        if (node == RedBlackNode.SENTINEL_NODE) {
            return "(0,0,0)";
        }

        return "(" + node.getBuildingNum() + "," + node.getExecutedTime() + "," + node.getTotalTime() + ")";
    }

    public String findInRange(int buildingNum1, int buildingNum2) {
        String outputStr = this.findInRange(this.root, buildingNum1, buildingNum2);

        if (outputStr.length() != 0) {
            return outputStr.substring(0, outputStr.length() - 1);
        }

        return "(0,0,0)";
    }

    private RedBlackNode findByBuildingNum(int buildingNum) {
        RedBlackNode tmp = this.root;
        
        while (tmp != RedBlackNode.SENTINEL_NODE) {
            if (tmp.getBuildingNum() == buildingNum) {
                return tmp;
            } else if (tmp.getBuildingNum() < buildingNum) {
                tmp = tmp.getRight();
            } else {
                tmp = tmp.getLeft();
            }
        }
        
        return RedBlackNode.SENTINEL_NODE;
    }

    private String findInRange(RedBlackNode node, int buildingNum1, int buildingNum2) {
        String outputStr = "";
        int buildingNum = node.getBuildingNum();

        if (node == RedBlackNode.SENTINEL_NODE) {
            return outputStr;
        }

        if (buildingNum > buildingNum1) {
            outputStr += this.findInRange(node.getLeft(), buildingNum1, buildingNum2);
        }

        if (buildingNum1 <= buildingNum && buildingNum <= buildingNum2) {
            outputStr += "(" + buildingNum + "," + node.getExecutedTime() + "," + node.getTotalTime()+ "),";
        }

        if (buildingNum < buildingNum2) {
            outputStr += this.findInRange(node.getRight(), buildingNum1, buildingNum2);
        }
        
        return outputStr;
    }
}
