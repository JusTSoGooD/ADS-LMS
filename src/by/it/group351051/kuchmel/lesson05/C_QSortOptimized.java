package by.it.group351051.kuchmel.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Видеорегистраторы и площадь 2.
Условие то же что и в задаче А.

        По сравнению с задачей A доработайте алгоритм так, чтобы
        1) он оптимально использовал время и память:
            - за стек отвечает элиминация хвостовой рекурсии,
            - за сам массив отрезков - сортировка на месте
            - рекурсивные вызовы должны проводиться на основе 3-разбиения

        2) при поиске подходящих отрезков для точки реализуйте метод бинарного поиска
        для первого отрезка решения, а затем найдите оставшуюся часть решения
        (т.е. отрезков, подходящих для точки, может быть много)

    Sample Input:
    2 3
    0 5
    7 10
    1 6 11
    Sample Output:
    1 0 0

*/


public class C_QSortOptimized {

    //отрезок
    private class Segment  implements Comparable{
        int start;
        int stop;

        Segment(int start, int stop){
            this.start = start;
            this.stop = stop;
        }

        @Override
        public int compareTo(Object o) {
            Segment other = (Segment) o;
            if (this.start != other.start) {
                return Integer.compare(this.start, other.start);
            }
            return Integer.compare(this.stop, other.stop);
        }
    }


    // Функция сортировки с использованием 3-разбиения (QuickSort с оптимизацией)
    private void quickSort(Segment[] segments, int left, int right) {
        while (left < right) {
            int lt = left, gt = right;
            Segment pivot = segments[left];
            int i = left + 1;

            while (i <= gt) {
                if (segments[i].compareTo(pivot) < 0) {
                    swap(segments, lt++, i++);
                } else if (segments[i].compareTo(pivot) > 0) {
                    swap(segments, i, gt--);
                } else {
                    i++;
                }
            }

            // Рекурсивные вызовы на основе 3-разбиения
            if (lt - left < right - gt) {
                quickSort(segments, left, lt - 1);
                left = gt + 1; // Хвостовая рекурсия
            } else {
                quickSort(segments, gt + 1, right);
                right = lt - 1; // Хвостовая рекурсия
            }
        }
    }

    // Вспомогательная функция для обмена элементов
    private void swap(Segment[] segments, int i, int j) {
        Segment temp = segments[i];
        segments[i] = segments[j];
        segments[j] = temp;
    }

    // Реализация бинарного поиска
    private int binarySearch(Segment[] segments, int point) {
        int low = 0, high = segments.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (segments[mid].start <= point && segments[mid].stop >= point) {
                return mid; // Нашли подходящий сегмент
            } else if (segments[mid].start > point) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1; // Если подходящий сегмент не найден
    }

    int[] getAccessory2(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //число отрезков отсортированного массива
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        //число точек
        int m = scanner.nextInt();
        int[] points = new int[m];
        int[] result = new int[m];

        //читаем сами отрезки
        for (int i = 0; i < n; i++) {
            //читаем начало и конец каждого отрезка
            segments[i] = new Segment(scanner.nextInt(), scanner.nextInt());
        }

        //читаем точки
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }

        // Сортируем отрезки
        quickSort(segments, 0, n - 1);

        // Для каждой точки проверяем количество подходящих отрезков
        for (int i = 0; i < m; i++) {
            int point = points[i];
            int index = binarySearch(segments, point);

            if (index != -1) {
                int count = 0;

                // Идём назад от найденного отрезка
                for (int j = index; j >= 0 && segments[j].start <= point; j--) {
                    if (segments[j].stop >= point) count++;
                }

                // Идём вперёд от найденного отрезка
                for (int j = index + 1; j < n && segments[j].start <= point; j++) {
                    if (segments[j].stop >= point) count++;
                }

                result[i] = count;
            } else {
                result[i] = 0; // Если точка не попадает ни в один отрезок
            }
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataC.txt");
        C_QSortOptimized instance = new C_QSortOptimized();
        int[] result=instance.getAccessory2(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}
