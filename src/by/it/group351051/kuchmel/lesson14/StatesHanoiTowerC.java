package by.it.group351051.kuchmel.lesson14;

import java.util.*;

public class StatesHanoiTowerC {

    // Метод для рекурсивного решения задачи Ханойских башен
    static void hanoi(int n, char from, char to, char aux, int[] heights, List<Integer> maxHeights) {
        if (n == 1) {
            // Перемещаем одно кольцо с одного стержня на другой
            heights[from - 'A']--;
            heights[to - 'A']++;
            int maxHeight = Math.max(Math.max(heights[0], heights[1]), heights[2]);
            maxHeights.add(maxHeight);
            return;
        }
        // Рекурсивно решаем для N-1 колец
        hanoi(n - 1, from, aux, to, heights, maxHeights);

        // Перемещаем одно кольцо
        heights[from - 'A']--;
        heights[to - 'A']++;
        int maxHeight = Math.max(Math.max(heights[0], heights[1]), heights[2]);
        maxHeights.add(maxHeight);

        // Рекурсивно решаем для оставшихся N-1 колец
        hanoi(n - 1, aux, to, from, heights, maxHeights);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int[] heights = new int[3]; // Массив для хранения количества колец на каждом из стержней
        heights[0] = N; // Все кольца начинаются на стержне A
        List<Integer> maxHeights = new ArrayList<>();

        // Запускаем алгоритм Ханойских башен
        hanoi(N, 'A', 'B', 'C', heights, maxHeights);

        // Группировка шагов по максимальной высоте стержней
        Map<Integer, Integer> groupSizes = new HashMap<>();
        for (int maxHeight : maxHeights) {
            groupSizes.put(maxHeight, groupSizes.getOrDefault(maxHeight, 0) + 1);
        }

        // Вывод размеров групп в порядке возрастания
        List<Integer> result = new ArrayList<>(groupSizes.values());
        Collections.sort(result);
        for (int size : result) {
            System.out.print(size + " ");
        }
    }
}
