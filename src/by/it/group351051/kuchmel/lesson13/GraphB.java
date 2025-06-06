package by.it.group351051.kuchmel.lesson13;

import java.util.*;

public class GraphB {

    private Map<Integer, List<Integer>> adjacencyList = new HashMap<>();
    private Set<Integer> vertices = new HashSet<>();

    public void addEdge(int from, int to) {
        adjacencyList.putIfAbsent(from, new ArrayList<>());
        adjacencyList.get(from).add(to);
        vertices.add(from);
        vertices.add(to);
    }

    public boolean hasCycle() {
        Set<Integer> visited = new HashSet<>();
        Set<Integer> inRecursionStack = new HashSet<>();

        for (int vertex : vertices) {
            if (!visited.contains(vertex)) {
                if (dfs(vertex, visited, inRecursionStack)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(int current, Set<Integer> visited, Set<Integer> inRecursionStack) {
        visited.add(current);
        inRecursionStack.add(current);

        if (adjacencyList.containsKey(current)) {
            for (int neighbor : adjacencyList.get(current)) {
                if (inRecursionStack.contains(neighbor)) {
                    return true; // найден цикл
                }
                if (!visited.contains(neighbor)) {
                    if (dfs(neighbor, visited, inRecursionStack)) {
                        return true;
                    }
                }
            }
        }

        inRecursionStack.remove(current);
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        GraphB graph = new GraphB();

        // Чтение строки и добавление рёбер в граф
        String[] edges = input.split(", ");
        for (String edge : edges) {
            String[] parts = edge.split(" -> ");
            int from = Integer.parseInt(parts[0]);
            int to = Integer.parseInt(parts[1]);
            graph.addEdge(from, to);
        }

        // Проверка наличия цикла
        if (graph.hasCycle()) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }
    }
}
