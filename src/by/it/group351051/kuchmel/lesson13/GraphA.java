package by.it.group351051.kuchmel.lesson13;

import java.util.*;

public class GraphA {
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        String inputData = inputScanner.nextLine();
        Map<String, List<String>> adjacencyList = new HashMap<>();
        Map<String, Integer> incomingEdgesCount = new HashMap<>();

        String[] edges = inputData.split(", ");
        for (String edge : edges) {
            String[] nodes = edge.split(" -> ");
            String startNode = nodes[0];
            String endNode = nodes[1];

            adjacencyList.putIfAbsent(startNode, new ArrayList<>());
            adjacencyList.get(startNode).add(endNode);

            incomingEdgesCount.putIfAbsent(startNode, 0);
            incomingEdgesCount.put(endNode, incomingEdgesCount.getOrDefault(endNode, 0) + 1);
        }

        PriorityQueue<String> nodesWithZeroIncomingEdges = new PriorityQueue<>();
        for (Map.Entry<String, Integer> entry : incomingEdgesCount.entrySet()) {
            if (entry.getValue() == 0) {
                nodesWithZeroIncomingEdges.add(entry.getKey());
            }
        }

        List<String> sortedNodes = new ArrayList<>();
        while (!nodesWithZeroIncomingEdges.isEmpty()) {
            String currentNode = nodesWithZeroIncomingEdges.poll();
            sortedNodes.add(currentNode);

            if (adjacencyList.containsKey(currentNode)) {
                for (String neighbor : adjacencyList.get(currentNode)) {
                    incomingEdgesCount.put(neighbor, incomingEdgesCount.get(neighbor) - 1);
                    if (incomingEdgesCount.get(neighbor) == 0) {
                        nodesWithZeroIncomingEdges.add(neighbor);
                    }
                }
            }
        }

        System.out.println(String.join(" ", sortedNodes));
    }
}
