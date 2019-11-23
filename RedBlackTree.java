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
                        this.leftRotate(p);
                    }
                    /*
                        LRb or RRb => RR rotate
                    */
                    pp.setBlack();
                    gp.setRed();
                    this.rightRotate(gp);
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
                        this.rightRotate(p);
                    }
                    /*
                        LRb or LLb => LL rotate
                    */
                    pp.setBlack();
                    gp.setRed();
                    this.leftRotate(gp);
                }
            }
        }
        
        root.setBlack();
    }

    // This function performs a left rotation around the node ‘x’.
    public void leftRotate(Node x) {
        Node y = x.getRight();
        x.setRight(y.getLeft());
        if (y.getLeft() != nil) {
            y.getLeft().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == nil)
            root = y;
        else if (x == x.getParent().getLeft())
            x.getParent().setLeft(y);
        else
            x.getParent().setRight(y);
        y.setLeft(x);
        x.setParent(y);
    }

    // This function performs a right rotation around the node ‘x’.
    public void rightRotate(Node x) {
        Node y = x.getLeft();
        x.setLeft(y.getRight());
        if (y.getRight() != nil) {
            y.getRight().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == nil)
            root = y;
        else if (x == x.getParent().getRight())
            x.getParent().setRight(y);
        else
            x.getParent().setLeft(y);
        y.setRight(x);
        x.setParent(y);
    }

    // This function replaces the subtree rooted at node u with the subtree rooted
    // at node v.
    public void transplant(Node u, Node v) {
        if (u.getParent() == nil)
            root = v;
        else if (u == u.getParent().getLeft())
            u.getParent().setLeft(v);
        else
            u.getParent().setRight(v);
        v.setParent(u.getParent());
    }

    // A function to delete the node ‘z’ from a red-black tree. It has a time
    // complexity of O(log n).
    public void delete(Node z) {
        Node y = z; // y is the node that was either removed or moved within the tree.
        Node x; // x is the node that will move into y's original position
        int origColor = y.getColor(); //// need to know whether y was black
        if (z.getLeft() == nil) {
            // no left child?
            x = z.getRight();
            // replace z by its right child
            transplant(z, z.getRight());
        } else if (z.getRight() == nil) {
            // no right child?
            x = z.getLeft();
            // replace z by its left child
            transplant(z, z.getLeft());
        } else {
            // Node z has two children.
            // We find its successor y, which is in z's right subtree and has no left child.
            y = z.getRight();
            while (y.getLeft() != nil)
                y = y.getLeft();
            origColor = y.getColor();
            x = y.getRight();
            // Remove y out of its current location, and replace z with it.
            if (y.getParent() == z)
                x.setParent(y);
            else {
                // If y is not z's right child, then replace y as a child of its parent by y's
                // right child
                transplant(y, y.getRight());
                // turn z's right child into y's right child.
                y.setRight(z.getRight());
                y.getRight().setParent(y);
            }
            // Regardless of whether y was z's right child, replace z as a child of its
            // parent by y
            transplant(z, y);
            // replace y's left child by z's left child.
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
            // y is colored with the same color as z.
            y.setColor(z.getColor());
        }
        // If we removed a black node, then must call deleteFixUp() because
        // black-heights are now incorrect.
        if (origColor == 'b')
            deleteFixUp(x);
    }

    // This function fixes the possible violation of the red-black properties
    // caused by deleting a node using the delete() function.
    public void deleteFixUp(Node x) {
        while (x != root && x.isBlack()) {
            if (x == x.getParent().getLeft()) {
                Node w = x.getParent().getRight();
                if (w.isRed()) {
                    // Case 1: x's sibling w is red.
                    w.setBlack();
                    x.getParent().setRed();
                    leftRotate(x.getParent());
                    w = x.getParent().getRight();
                }
                if (w.getLeft().isBlack() && w.getRight().isBlack()) {
                    // Case 2: x's sibling w is black, and both of w's children are black.
                    w.setRed();
                    x = x.getParent();
                } else {
                    if (w.getRight().isBlack()) {
                        // Case 3: x's sibling w is black, and the left child of w is red,
                        // and w's right child is black.
                        w.getLeft().setBlack();
                        w.setRed();
                        rightRotate(w);
                        w = x.getParent().getRight();
                    }
                    // Case 4: x's sibling w is black, and the right child of w is red.
                    w.setColor(x.getParent().getColor());
                    x.getParent().setBlack();
                    w.getRight().setBlack();
                    leftRotate(x.getParent());
                    x = root;
                }
            } else {
                Node w = x.getParent().getLeft();
                if (w.isRed()) {
                    // Case 1: x's sibling w is red.
                    w.setBlack();
                    x.getParent().setRed();
                    rightRotate(x.getParent());
                    w = x.getParent().getLeft();
                }
                if (w.getRight().isBlack() && w.getLeft().isBlack()) {
                    // Case 2: x's sibling w is black, and both of w's children are black.
                    w.setRed();
                    x = x.getParent();
                } else {
                    if (w.getLeft().isBlack()) {
                        // Case 3: x's sibling w is black, wand the right child of w is red,
                        // and w's left child is black.
                        w.getRight().setBlack();
                        w.setRed();
                        leftRotate(w);
                        w = x.getParent().getLeft();
                    }
                    // Case 4: x's sibling w is black, and the left child of w is red.
                    w.setColor(x.getParent().getColor());
                    x.getParent().setBlack();
                    w.getLeft().setBlack();
                    rightRotate(x.getParent());
                    x = root;
                }
            }
        }
        x.setBlack();
    }

    // This function searches the Red Black Tree for a node with buildingNums = ‘x',
    // and
    // returns the node if found.
    // The time complexity of this function is O(log n).
    public Node search(int x) {
        Node ptr = root;
        while (ptr != nil) {
            if (ptr.getBuildingNum() == x)
                return ptr;
            else if (ptr.getBuildingNum() < x)
                ptr = ptr.getRight();
            else
                ptr = ptr.getLeft();
        }
        return nil;
    }

    public void search_range(Node root,int a,int b){
        // System.out.println("Hello");
        if(root == nil)
        {
            return;
        }

        if(root.getBuildingNum()>a)
        {
            // System.out.println(root.getBuildingNum());
            search_range(root.getLeft(),a,b);
        }

        if(a <= root.getBuildingNum() && root.getBuildingNum() <= b)
        {
            // System.out.println(root.getBuildingNum());
            this.output= this.output + "("+root.getBuildingNum()+"," + root.getExecutedTime()+"," +root.getTotal_time()+ "),";
        }

        if(root.getBuildingNum()< b)
        {
            // System.out.println(root.getBuildingNum());
            search_range(root.getRight(),a,b);
        }
    }

    // function to call search()
    public void callSearch(int ele) {
        search(ele);
    }

    // This function calls the search() function to find a node with buildingNums =
    // ‘x’.
    // If found, it then calls the delete() function passing the node that was
    // returned by the search() function as an argument.
    // The time complexity of this function is O(log n).
    public void remove(int x) {
        Node toDelete = search(x);
        delete(toDelete);
    }

    // This function calls the search() function to find a node with buildingNums =
    // ‘ID’.
    // If found, it then increases the count of that node by ‘m’, and prints the
    // count after increasing.
    // Else, it calls the insert() function passing ‘ID’ and ‘m’ as arguments.
    // The time complexity of this function in O(log n).
    // public void increase(int buildingNums, int m) {
    // Node toIncrease = search(buildingNums);
    // if (toIncrease != nil) {
    // int count = toIncrease.getCount();
    // count = count + m;
    // toIncrease.setCount(count);
    // System.out.println(toIncrease.getCount());
    // } else {
    // insert(buildingNums, m);
    // System.out.println(m);
    // }
    // }

    // This function calls the search() function to find a node with buildingNums =
    // ‘ID’.
    // If found, it then decreases the count of that node by ‘m’, and prints the
    // count after decreasing.
    // However, if the count <= 0 after decreasing, the delete function() is called
    // passing the node as an argument.
    // If the node is not present or is removed, then ‘0’ is printed.
    // The time complexity of this function in O(log n).
    // public void reduce(int buildingNums, int m) {
    // Node toReduce = search(buildingNums);
    // if (toReduce != nil) {
    // int count = toReduce.getCount();
    // count = count - m;
    // if (count > 0) {
    // toReduce.setCount(count);
    // System.out.println(toReduce.getCount());
    // } else {
    // delete(toReduce);
    // System.out.println("0");
    // }
    // } else
    // System.out.println("0");
    // }

    // This function calls the search() function to find a node with buildingNums =
    // ‘ID’.
    // If found, the function then prints the count of the found node. Else, it
    // prints ‘0’.
    // This function has a time complexity of O(log n).
    public void count(int buildingNums) {
        Node toCount = search(buildingNums);
        if (toCount != nil) {
            System.out.println(toCount.getExecutedTime());
            System.out.println(toCount.getTotalTime());
        } else
            System.out.println("0");
    }

    // Function for in order traversal
    public void inOrder(Node root) {
        if (root == nil) {
            return;
        }
        inOrder(root.getLeft());
        System.out.print(root.getBuildingNum() + "("+root.getTotalTime()+ ") " + root.color + "-> ");
        inOrder(root.getRight());
    }

    // Function for pre order traversal
    public void preOrder(Node root) {
        if (root == nil) {
            return;
        }
        System.out.print(root.getBuildingNum() + "->");
        preOrder(root.getLeft());
        preOrder(root.getRight());
    }

    // Function for post order traversal
    public void postOrder(Node root) {
        if (root == nil) {
            return;
        }
        postOrder(root.getLeft());
        postOrder(root.getRight());
        System.out.print(root.getBuildingNum() + "->");
    }

    // Function to print traversal
    public void printTraversal(String path) {
        if (root != nil) {
            if (path.equals(new String("postOrder"))) {
                postOrder(root);
            } else if (path.equals(new String("preOrder"))) {
                preOrder(root);
            } else {
                inOrder(root);
            }
        }
    }

    // Function to print by level order
    public void levelOrderTraversal(Node root, ArrayList<LinkedList<Node>> arrlist, int level) {
        if (root == null) {
            return;
        }
        LinkedList<Node> list = null;
        if (level == arrlist.size()) {
            list = new LinkedList<Node>();
            arrlist.add(list);
        } else {
            list = arrlist.get(level);
        }
        list.add(root);
        levelOrderTraversal(root.getLeft(), arrlist, level + 1);
        levelOrderTraversal(root.getRight(), arrlist, level + 1);
    }

    // Function to call levelOrderTraversal()
    // public void callLevelOrderTraversal() {
    // ArrayList<LinkedList<Node>> lists = new ArrayList<LinkedList<Node>>();
    // int lvl = 0;
    // levelOrderTraversal(root, lists, lvl);
    // int lenArrayList = lists.size();
    // for (int i = 0; i < lenArrayList; i++) {
    // /*
    // * for(int k=lenArrayList-i;k>0;k--) System.out.print(" ");
    // */
    // int lenLinkedList = lists.get(i).size();
    // for (int j = 0; j < lenLinkedList; j++) {
    // Node tmp = lists.get(i).get(j);
    // System.out.print(" " + "[" + tmp.getBuildingNum() + "," + tmp.getCount() +
    // "]" + tmp.getColor());
    // }
    // System.out.println(" ");

    // }
    // }

    // This function calls the initializeRBT() function, which then initializes the
    // Red Black Tree from the sorted array of events.
    // The returned node is then assigned as root of the Red Black Tree, and its
    // parent is set to nil.
    // The time complexity of this function is O(n).
    public void callInitialize(int arr[][], int start, int end, int n) {
        int h = (int) Math.ceil((Math.log(n)) / Math.log(2));
        int lvl = 0;
        root = initializeRBT(arr, start, end, lvl, h);
        root.setParent(nil);
    }

    // This function initializes the Red Black Tree from an array of events
    // containing buildingNums and count and sorted by buildingNums.
    // This is done by making the middle element of the sorted array as the root of
    // the Red Black Tree,
    // and then recursively doing it for the left half and the right half.
    // The root of the entire Red Black Tree is then returned.
    // All nodes other than the immediate parents of external nodes are colored
    // black,
    // while the immediate parents of external nodes are colored red.
    // The time complexity of this function is O(n).
    public Node initializeRBT(int arr[][], int start, int end, int lvl, int h) {
        if (start > end)
            return nil;
        int mid = (start + end) / 2;
        Node node = new Node(0, 0, 0);
        node.setBuildingNums(arr[mid][0]);
        // node.setCount(arr[mid][1]);
        // by default, node is colored black.
        if (lvl == 0) // if current level is pointing to root level
            node.setBlack();
        if (lvl == h - 1) // current level is equal to the level immediately above sentinel nodes
            node.setRed();
        node.setLeft(initializeRBT(arr, start, mid - 1, lvl + 1, h));
        node.getLeft().setParent(node);
        node.setRight(initializeRBT(arr, mid + 1, end, lvl + 1, h));
        node.getRight().setParent(node);
        return node;
    }
}
