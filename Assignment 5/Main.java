import java.util.*;

abstract class TopoSort<E> {
    public abstract void getSortedOrder(Map<E, List<E>> graph) throws CycleFoundException;

    public void printOrder(List<E> result) {
        for (E i : result) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    Map<E, Integer> getPredCount(Map<E, List<E>> graph) {
        Map<E, Integer> predCount = new HashMap<>();
        graph.forEach((node, children) -> {
            predCount.put(node, predCount.getOrDefault(node, 0));
            for (E child : children) {
                predCount.put(child, predCount.getOrDefault(child, 0) + 1);
            }
        });
        return predCount;
    }
}

class BFSTopoSort<E> extends TopoSort<E> {
    @Override
    public void getSortedOrder(Map<E, List<E>> graph) throws CycleFoundException {
        Map<E, Integer> predCount = getPredCount(graph);
        Queue<E> bfsQueue = new LinkedList<>();
        List<E> result = new ArrayList<>();
        predCount.forEach((node, count) -> {
            if (count == 0) {
                bfsQueue.add(node);
            }
        });
        while (!bfsQueue.isEmpty()) {
            E current = bfsQueue.poll();
            result.add(current);
            if (graph.containsKey(current) && !graph.get(current).isEmpty()) {
                List<E> neighbourhood = graph.get(current);
                for (E neighbour : neighbourhood) {
                    int count = predCount.get(neighbour);
                    count--;
                    if (count == 0) {
                        bfsQueue.add(neighbour);
                    }
                    predCount.put(neighbour, count);
                }
            }
        }
        if (result.size() != graph.size()) {
            throw new CycleFoundException();
        } else {
            System.out.print("Topological ordering using BFS: ");
            printOrder(result);
        }
    }
}

class DFSTopoSort<E> extends TopoSort<E> {
    @Override
    public void getSortedOrder(Map<E, List<E>> graph) throws CycleFoundException {
        List<E> result = new ArrayList<>();
        Set<E> visited = new HashSet<>();
        Set<E> nodesOnPath = new HashSet<>();
        Map<E, Integer> predCount = getPredCount(graph);
        for (Map.Entry<E, Integer> e : predCount.entrySet()) {
            if (e.getValue() == 0) {
                dfs(graph, e.getKey(), result, visited, nodesOnPath);
            }
        }
        Collections.reverse(result);
        System.out.print("Topological ordering using DFS: ");
        printOrder(result);
    }

    private void dfs(Map<E, List<E>> graph, E currentNode, List<E> result, Set<E> visited, Set<E> nodesOnPath)
            throws CycleFoundException {
        nodesOnPath.add(currentNode);
        visited.add(currentNode);
        List<E> neighbourhood = graph.get(currentNode);
        for (E neighbour : neighbourhood) {
            if (!visited.contains(neighbour)) {
                dfs(graph, neighbour, result, visited, nodesOnPath);
            } else if (nodesOnPath.contains(neighbour)) {
                throw new CycleFoundException();
            }
        }
        result.add(currentNode);
        nodesOnPath.remove(currentNode);
    }
}

public class Main {
    public static void main(String[] args) {
        Map<Character, List<Character>> graph1 = generateGraph1();
        Map<Integer, List<Integer>> graph2 = generateGraph2();
        TopoSort bfsSort = new BFSTopoSort();
        TopoSort dfsSort = new DFSTopoSort();
        System.out.println("Graph 1:");
        try {
            bfsSort.getSortedOrder(graph1);
        } catch (CycleFoundException e) {
            System.out.println("Graph has a cycle. No topological ordering possible.");
        }
        try {
            dfsSort.getSortedOrder(graph1);
        } catch (CycleFoundException e) {
            System.out.println("Graph has a cycle. No topological ordering possible.");
        }
        System.out.println("Graph 2:");
        try {
            bfsSort.getSortedOrder(graph2);
        } catch (CycleFoundException e) {
            System.out.println("Graph has a cycle. No topological ordering possible.");
        }
        try {
            dfsSort.getSortedOrder(graph2);
        } catch (CycleFoundException e) {
            System.out.println("Graph has a cycle. No topological ordering possible.");
        }
    }

    private static Map<Character, List<Character>> generateGraph1() {
        Map<Character, List<Character>> adjList = new TreeMap<>();
        adjList.put('m', List.of('q', 'r', 'x'));
        adjList.put('n', List.of('o', 'q', 'u'));
        adjList.put('o', List.of('r', 's', 'v'));
        adjList.put('p', List.of('o', 's', 'z'));
        adjList.put('q', List.of('t'));
        adjList.put('r', List.of('u', 'y'));
        adjList.put('s', List.of('r'));
        adjList.put('t', new ArrayList<>());
        adjList.put('u', List.of('t'));
        adjList.put('v', List.of('w', 'x'));
        adjList.put('w', List.of('z'));
        adjList.put('x', new ArrayList<>());
        adjList.put('y', List.of('v'));
        adjList.put('z', new ArrayList<>());
        return adjList;
    }

    private static Map<Integer, List<Integer>> generateGraph2() {
        Map<Integer, List<Integer>> adjList = new TreeMap<>();
        adjList.put(1, List.of(2, 5, 6));
        adjList.put(2, List.of(5, 7));
        adjList.put(3, List.of(4));
        adjList.put(4, List.of(5));
        adjList.put(5, List.of(7, 8));
        adjList.put(6, List.of(5, 8));
        adjList.put(7, List.of(4, 8));
        adjList.put(8, new ArrayList<>());
        return adjList;
    }
}

class CycleFoundException extends Exception {
}