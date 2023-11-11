import java.util.*;

public class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        s.printGraph();
        int source = 1;
        int target = 10;
        System.out.printf("Source: %d\n", source);
        System.out.printf("Destination: %d\n", target);
        s.djikstra(source, target, 10);
        s.printTree(10);
    }
}

class Solution {
    private static final String graphEdgeFormat = "      %d\n%d ---------> %d\n\n";
    private static final int[][] graph = new int[][] { // { source, destination, distance }
        {1, 2, 73},
        {1, 3, 89},
        {2, 3, 57},
        {2, 4, 64},
        {3, 4, 75},
        {3, 5, 91},
        {4, 5, 52},
        {4, 6, 70},
        {5, 6, 78},
        {5, 7, 95},
        {6, 7, 86},
        {6, 8, 57},
        {7, 8, 68},
        {7, 9, 72},
        {8, 9, 59},
        {8, 10, 82},
        {9, 10, 63},
        {2, 7, 76},
        {4, 9, 68},
        {4, 8, 94}
    };
    
    private int[] distance;
    private int[] parent;
    
    public void djikstra(int source, int target, int n) {
        // distance array to keep track of distance to every node from the source
        int[] distance = new int[n];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source-1] = 0;
        
        // parent array to keep track of parent of each node
        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        // priority queue to give which node to pick next, also stores previous node
        Queue<int[]> priQueue = new PriorityQueue<>((a,b) -> a[1]-b[1]); // { node, distance, parent }
        priQueue.add(new int[] {source, distance[source-1], -1});
        
        // vistied array to keep track of which node has been visited so far
        boolean[] visited = new boolean[n];
        visited[source-1] = true;
        
        while (!priQueue.isEmpty()) {
            int[] currentNode = priQueue.poll();
            if (distance[currentNode[0]-1] > currentNode[1]) {
                distance[currentNode[0]-1] = currentNode[1];
                parent[currentNode[0]-1] = currentNode[2];
            }
            distance[currentNode[0]-1] = Math.min(distance[currentNode[0]-1], currentNode[1]);
            visited[currentNode[0]-1] = true;
            for (int[] edge : graph) {
                int s = edge[0]; // source
                int d = edge[1]; // destination
                int dist = edge[2]; // distance
                if (s != currentNode[0]) continue;
                if (!visited[d-1]) {
                    int nextDist = currentNode[1] + dist;
                    parent[d-1] = currentNode[0]; 
                    priQueue.add(new int[] {d, nextDist, s});
                }
            }
        }

        this.distance = distance;
        this.parent = parent;

        System.out.printf("Shortest Path Length: %d\n", distance[target-1]);
    }
    public void printGraph() {
        System.out.println();
        for (int[] edge : graph) {
            System.out.printf(graphEdgeFormat, edge[2], edge[0], edge[1]);
        }
        System.out.println();
    }

    public void printTree(int n) {
        System.out.println();
        System.out.println("Showing distance from source and the parent of each vertex");
        System.out.println();
        for (int source = 1; source <= n; source++) {
            for (int i = 1; i <= n; i++) {
                if (source == parent[i-1]) {
                    System.out.printf(graphEdgeFormat, distance[i-1], source, i);
                }
            }
        }
    }
}