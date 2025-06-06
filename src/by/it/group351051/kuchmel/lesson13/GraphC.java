package by.it.group351051.kuchmel.lesson13;

import java.util.*;

public class GraphC {
    private Map<String, List<String>> graph;
    private Map<String, List<String>> reversedGraph;
    private Set<String> visited;
    private List<String> order;
    private List<List<String>> components;

    public GraphC() {
        graph = new HashMap<>();
        reversedGraph = new HashMap<>();
        visited = new HashSet<>();
        order = new ArrayList<>();
        components = new ArrayList<>();
    }

    public void addEdge(String from, String to) {
        graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        reversedGraph.computeIfAbsent(to, k -> new ArrayList<>()).add(from);
        // Ensure all nodes are in both graphs even if they have no edges
        graph.putIfAbsent(to, new ArrayList<>());
        reversedGraph.putIfAbsent(from, new ArrayList<>());
    }

    private void dfsFirstPass(String node) {
        if (visited.contains(node)) return;
        visited.add(node);

        for (String neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            dfsFirstPass(neighbor);
        }

        order.add(node);
    }

    private void dfsSecondPass(String node, List<String> component) {
        if (visited.contains(node)) return;
        visited.add(node);
        component.add(node);

        for (String neighbor : reversedGraph.getOrDefault(node, new ArrayList<>())) {
            dfsSecondPass(neighbor, component);
        }
    }

    public void findSCCs() {
        // First pass (on original graph)
        visited.clear();
        order.clear();
        for (String node : graph.keySet()) {
            dfsFirstPass(node);
        }

        // Second pass (on reversed graph) in reverse order
        visited.clear();
        components.clear();
        Collections.reverse(order);

        for (String node : order) {
            if (!visited.contains(node)) {
                List<String> component = new ArrayList<>();
                dfsSecondPass(node, component);
                Collections.sort(component); // Lexicographical order
                components.add(component);
            }
        }
    }

    public List<List<String>> getComponents() {
        return components;
    }

    public static void main(String[] args) {
        GraphC graph = new GraphC();
        Scanner scanner = new Scanner(System.in);

        // Read the input string
        String input = scanner.nextLine();

        // Parse the edges
        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] parts = edge.split("->");
            graph.addEdge(parts[0], parts[1]);
        }

        // Find strongly connected components
        graph.findSCCs();

        // Print the components
        for (List<String> component : graph.getComponents()) {
            Collections.sort(component); // Ensure lexicographical order
            for (String node : component) {
                System.out.print(node);
            }
            System.out.println();
        }
    }
}