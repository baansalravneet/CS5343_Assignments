public class Main {
    private static final int[] heapArray = new int[] { 15, 4, 5, 12, 9, 10, 8, 13, 1, 7, 14, 11, 6, 3, 15, 2 };

    public static void main(String[] args) {

        System.out.println("Initial array:");
        printArray(heapArray);

        heapify(heapArray); // making a max heap

        System.out.println("Array after converting it into a max heap:");
        printArray(heapArray);

        heapSort(heapArray);

        System.out.println("Array after heap sort");
        printArray(heapArray);

    }

    private static void heapSort(int[] array) {
        if (array[0] == 1) {
            array[0]--;
            return;
        }
        swap(array, 1, array[0]);
        array[0]--;
        percolateDown(array, 1, array[0]);
        heapSort(array);
    }

    private static void printArray(int[] array) {
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    private static void heapify(int[] array) {
        int currentIndex = array[0] / 2;
        int maxIndex = array[0];
        while (currentIndex > 0) {
            percolateDown(array, currentIndex, maxIndex);
            currentIndex--;
        }
    }

    private static void percolateDown(int[] array, int currentIndex, int maxIndex) {
        if (maxIndex < currentIndex) {
            return;
        }
        int leftChildIndex = currentIndex * 2;
        int rightChildIndex = 1 + leftChildIndex;
        // no children
        if (leftChildIndex > maxIndex) return;
        // only left child or left child > right child
        if (rightChildIndex > maxIndex || array[leftChildIndex] > array[rightChildIndex]) {
            if (array[leftChildIndex] > array[currentIndex]) {
                swap(array, currentIndex, leftChildIndex);
                percolateDown(array, leftChildIndex, maxIndex);
                return;
            }
        } else {
            if (array[rightChildIndex] > array[currentIndex]) {
                swap(array, currentIndex, rightChildIndex);
                percolateDown(array, rightChildIndex, maxIndex);
                return;
            }
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}