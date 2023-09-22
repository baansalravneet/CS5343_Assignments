public class Main {
    private static final int[] nodeOrder = new int[] { 40, 60, 20, 80, 50, 10, 30, 15, 5, 35, 25, 45, 55, 70, 90, 32, 33, 48, 46 };
    private static final int[] deleteNodes = new int[] { 40, 20 };
    
    public static void main(String[] args) {
        Solution s = new Solution();
        BSTNode head = initializeBST(s);
        s.inOrderTraversal(head);
        for (int i : deleteNodes) {
            System.out.println("Deleting "+i+"...");
            head = s.delete(head, i);
            s.inOrderTraversal(head);
        }
    }
    private static BSTNode initializeBST(Solution s) {
        BSTNode head = null;
        for (int i : nodeOrder) {
            head = s.insertNode(head, i, null);
        }
        return head;
    }
}

class Solution {
    BSTNode insertNode(BSTNode node, int val, BSTNode parent) {
        if (node == null) return new BSTNode(val, parent, false);
        if (node.val > val) {
            if (node.leftChild == null) node.leftChild = new BSTNode(val, node, true);
            else insertNode(node.leftChild, val, node);
        } else {
            if (node.rightChild == null) node.rightChild = new BSTNode(val, node, false);
            else insertNode(node.rightChild, val, node);
        }
        return node;
    }
    BSTNode delete(BSTNode node, int nodeValue) {
        // if the tree is empty, return null
        if (node == null) return node;
        // if the current node is the node to delete
        if (node.val == nodeValue) {
            // if it is a leaf node
            if (node.leftChild == null && node.rightChild == null) {
                if (node.parent == null) return null;
                if (node.isLeftChild) node.parent.leftChild = null;
                else node.parent.rightChild = null;
                return null;
            }
            // if left child is null
            if (node.leftChild == null) {
                if (node.parent == null) {
                    node.rightChild.parent = null;
                    return node.rightChild;
                }
                if (node.isLeftChild) node.parent.leftChild = node.rightChild;
                else node.parent.rightChild = node.rightChild;
                node.rightChild.parent = node.parent;
                node.rightChild.isLeftChild = node.isLeftChild;
                return null;
            }
            BSTNode predecessor = findPredecessor(node);
            node.val = predecessor.val;
            if (predecessor.isLeftChild) {
                predecessor.parent.leftChild = predecessor.leftChild;
                if (predecessor.leftChild != null) predecessor.leftChild.parent = predecessor.parent;
            } else {
                predecessor.parent.rightChild = predecessor.leftChild;
                if (predecessor.leftChild != null) {
                    predecessor.leftChild.isLeftChild = false;
                    predecessor.leftChild.parent = predecessor.parent;
                }
            }
        } else if (node.val < nodeValue) delete(node.rightChild, nodeValue);
        else delete(node.leftChild, nodeValue);
        return node;
    }
    void inOrderTraversal(BSTNode node) {
        if (node == null) {
            System.out.println("Empty Tree");
            return;
        }
        System.out.println("In Order Traversal:");
        recursiveInOrderPrint(node);
        System.out.println();
    }
    private void recursiveInOrderPrint(BSTNode node) {
        if (node == null) return;
        recursiveInOrderPrint(node.leftChild);
        System.out.print(node.val + " ");
        recursiveInOrderPrint(node.rightChild);
    }
    private BSTNode findPredecessor(BSTNode node) {
        BSTNode pointer = node.leftChild;
        while (pointer.rightChild != null) pointer = pointer.rightChild;
        return pointer;
    }
}

class BSTNode {
    int val;
    BSTNode leftChild;
    BSTNode rightChild;
    BSTNode parent;
    boolean isLeftChild;
    BSTNode(int val, BSTNode parent, boolean isLeftChild) {
        this.val = val;
        this.parent = parent;
        this.isLeftChild = isLeftChild;
    }
}