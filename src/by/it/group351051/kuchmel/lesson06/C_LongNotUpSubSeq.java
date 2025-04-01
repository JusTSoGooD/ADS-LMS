package by.it.group351051.kuchmel.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Stack;
/*
Задача на программирование: наибольшая невозростающая подпоследовательность

Дано:
    целое число 1<=n<=1E5 ( ОБРАТИТЕ ВНИМАНИЕ НА РАЗМЕРНОСТЬ! )
    массив A[1…n] натуральных чисел, не превосходящих 2E9.

Необходимо:
    Выведите максимальное 1<=k<=n, для которого гарантированно найдётся
    подпоследовательность индексов i[1]<i[2]<…<i[k] <= длины k,
    для которой каждый элемент A[i[k]] не больше любого предыдущего
    т.е. для всех 1<=j<k, A[i[j]]>=A[i[j+1]].

    В первой строке выведите её длину k,
    во второй - её индексы i[1]<i[2]<…<i[k]
    соблюдая A[i[1]]>=A[i[2]]>= ... >=A[i[n]].

    (индекс начинается с 1)

Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ

    Sample Input:
    5
    5 3 4 4 2

    Sample Output:
    4
    1 3 4 5
*/


public class C_LongNotUpSubSeq {

    int getNotUpSeqSize(InputStream stream) throws FileNotFoundException {
        // подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        // !!! НАЧАЛО ЗАДАЧИ !!!
        int n = scanner.nextInt(); // длина последовательности
        int[] m = new int[n]; // массив для чисел
        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt(); // считываем последовательность
        }

        // Массив для хранения длин наибольших невозрастающих подпоследовательностей
        int[] dp = new int[n];
        // Массив для восстановления индексов последовательности
        int[] prev = new int[n];

        // Изначально каждая позиция может быть началом последовательности
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            prev[i] = -1; // -1 означает отсутствие предыдущего элемента
        }

        // Заполнение массива dp и prev
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (m[i] <= m[j] && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }
        }

        // Найдем максимальное значение в dp
        int maxLength = 0, lastIndex = -1;
        for (int i = 0; i < n; i++) {
            if (dp[i] > maxLength) {
                maxLength = dp[i];
                lastIndex = i;
            }
        }

        // Восстановление индексов
        Stack<Integer> resultIndices = new Stack<>();
        while (lastIndex != -1) {
            resultIndices.push(lastIndex + 1); // +1 для перевода в 1-индексацию
            lastIndex = prev[lastIndex];
        }

        // Выводим результат
        System.out.println(maxLength); // Длина наибольшей невозрастающей подпоследовательности
        while (!resultIndices.isEmpty()) {
            System.out.print(resultIndices.pop() + " "); // Индексы подпоследовательности
        }

        // !!! КОНЕЦ ЗАДАЧИ !!!
        return maxLength;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson06/dataC.txt");
        C_LongNotUpSubSeq instance = new C_LongNotUpSubSeq();
        int result = instance.getNotUpSeqSize(stream);
        System.out.print(result);
    }

}