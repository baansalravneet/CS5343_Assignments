public class Main {
    public static void main(String[] args) {
        TernarySearch ts = new TernarySearch();
        int[] arr = initSortedArray();
        ts.search(arr, 17);
        ts.search(arr, 89);
    }
    // initialises a sorted array with 50 elements
    private static int[] initSortedArray() {
        int[] arr = new int[50];
        for (int i = 0; i < 50; i++) {
            arr[i] = i;
        }
        return arr;
    }
}

class TernarySearch {
    public void search(int[] arr, int target) {
        System.out.println("Target="+target);
        int index = recursionHelper(arr, 0, arr.length-1, target);
        if (index == -1) {
            System.out.println("Target not found!");
        } else {
            System.out.println("Target found at index: " + index);
        }
        System.out.println();
    }
    private int recursionHelper(int[] arr, int start, int end, int target) {
        if (start > end) {
            return -1;
        }
        int mid1 = start + (end - start) / 3;
        int mid2 = start + (2 * ((end - start) / 3));
        System.out.println("Start="+start+" End="+end+" Mid1="+mid1+" Mid2="+mid2);
        if (arr[mid1] == target) {
            return mid1;
        }
        if (arr[mid2] == target) {
            return mid2;
        }
        if (target < arr[mid1]) {
            return recursionHelper(arr, start, mid1 - 1, target);
        } else if (target > arr[mid1] && target < arr[mid2]) {
            return recursionHelper(arr, mid1 + 1, mid2 - 1, target);
        }
        return recursionHelper(arr, mid2 + 1, end, target);
    }
}
