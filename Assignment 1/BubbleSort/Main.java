// defining the node class of the linked list
class Node {
    int val;
    Node next;
    Node(int val) {
        this.val = val;
    }
}

// main class which initialises a linked list and called the sortAndPrint method
public class Main {

	// main method
    public static void main(String[] args) {
        BubbleSortLinkedList bsll = new BubbleSortLinkedList();
        Node linkedList = initLL();
        bsll.sortAndPrint(linkedList);
    }

    // returns the head of a unsorted linked list
    private static Node initLL() {
        int[] array = new int[] {6,9,2,1,12,4,10,14,5,3,11,15,13,7,8};
        Node head = new Node(array[0]);
        Node pointer = head;
        for (int i = 1; i < array.length; i++) {
            pointer.next = new Node(array[i]);
            pointer = pointer.next;
        }
        return head;
    }
}

// class containing the sorting algorithm
class BubbleSortLinkedList {

	// iteratively calls the sort method until the list is sorted
    public void sortAndPrint(Node temp) {
        while (true) {
            printLL(temp);
            Pair<Node, Boolean> next = sort(new Pair<>(temp, false));
            if (!next.value) {
                break;
            } else {
                temp = next.key;
            }
        }
    }
    
    // recursively runs one cycle of bubble sort
    private Pair<Node, Boolean> sort(Pair<Node, Boolean> pair) {
        Node temp = pair.key;
        boolean swapped = pair.value;
        if (temp.next == null) {
            return new Pair<>(temp, swapped);
        }
        if (temp.next.val < temp.val) {
            temp = swapWithNext(temp);
            swapped = true;
        }
        Pair<Node, Boolean> next = sort(new Pair<>(temp.next, swapped));
        temp.next = next.key;
        swapped = swapped || next.value;
        return new Pair<>(temp, swapped);
    }
    
    // method to swap the current node with the next
    private Node swapWithNext(Node temp) {
        if (temp.next == null) {
            return temp;
        }
        Node next = temp.next;
        temp.next = next.next;
        next.next = temp;
        return next;
    }

    // method to print the linked list
    private void printLL(Node temp) {
        if (temp.next == null) {
            System.out.println(temp.val);
            return;
        }
        System.out.print(temp.val + " -> ");
        printLL(temp.next);
    }
}

// class for the return object
class Pair<K,V> {
    K key;
    V value;
    Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
