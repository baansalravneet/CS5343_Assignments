import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        HashTable table = new HashTable(31);
        Scanner sc = new Scanner(new File("input.txt"));
        while (sc.hasNextLine()) {
            String word = sc.nextLine();
            table.insert(word);
        }
        System.out.println("Number of collisions: " + table.getCollisions());
    }
}

class HashTable {
    private static final int uniqueCharacters = 26 + 26 + 1; // 26 small, 26 capital, 1 space
    private String[] hashTable;
    private int tableSize;
    private int collisions;
    private int inserts;

    public HashTable(int tableSize) {
        System.out.println();
        initialise(tableSize);
    }

    private void initialise(int tableSize) {
        this.hashTable = new String[tableSize];
        this.collisions = 0;
        this.inserts = 0;
        this.tableSize = tableSize;
        System.out.printf("Hash Table initialised with size: %d\n\n", tableSize);
    }

    public void insert(String word) {
        System.out.printf("Word to Insert: %s\n", word);
        int hashValue = getHash(word);
        System.out.printf("Hash Value (index): %d\n", hashValue);
        if (hashTable[hashValue] == null) {
            hashTable[hashValue] = word;
            System.out.printf("No collision. %s inserted at index %d\n\n", word, hashValue);
        } else {
            System.out.printf("Collision found at %d. Existing word: %s\n", hashValue, hashTable[hashValue]);
            System.out.println("Probing...");
            collisions++;
            int i = 1;
            int nextPosition = probe(hashValue, i++);
            while (hashTable[nextPosition] != null) {
                System.out.printf("Probing: %d. Collision Found. Existing word: %s\n", nextPosition,
                        hashTable[nextPosition]);
                nextPosition = probe(nextPosition, i++);
                collisions++;
            }
            this.hashTable[nextPosition] = word;
            System.out.printf("Empty position found: %d\n\n", nextPosition);
        }
        inserts++;
        if (overloaded()) {
            System.out.println("Load Factor crossed. Increasing size...");
            System.out.println("Inserts: " + inserts);
            System.out.printf("Collisions so far: %d\n", collisions);
            increaseSize();
        }
    }

    private int probe(int hash, int i) {
        return (hash + 2 * i - 1) % tableSize;
    }

    private boolean overloaded() {
        return (double) inserts / tableSize >= 0.5;
    }

    private void increaseSize() {
        String[] oldState = this.hashTable;
        int nextSize = getNextPrime(tableSize * 2);
        System.out.printf("Setting the table size to: %d\n", nextSize);
        initialise(nextSize);
        for (String word : oldState) {
            if (word != null)
                insert(word);
        }
    }

    private int getNextPrime(int prime) {
        while (!isPrime(++prime)) {
        }
        return prime;
    }

    private boolean isPrime(int x) {
        for (int i = 2; i <= (int) Math.sqrt(x); i++) {
            if (x % i == 0) return false;
        }
        return true;
    }

    private int getHash(String word) {
        long hash = 0;
        int MOD = hashTable.length;
        for (int i = 0; i < word.length(); i++) {
            int character = word.charAt(word.length() - 1 - i);
            hash += character * (long) Math.pow(uniqueCharacters, i);
            hash %= MOD;
        }
        return (int) hash;
    }

    public int getCollisions() {
        return this.collisions;
    }
}