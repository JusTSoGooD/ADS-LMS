package by.it.group351051.kuchmel.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Задача на программирование: расстояние Левенштейна
    https://ru.wikipedia.org/wiki/Расстояние_Левенштейна
    http://planetcalc.ru/1721/

Дано:
    Две данных непустые строки длины не более 100, содержащие строчные буквы латинского алфавита.

Необходимо:
    Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ
    Рекурсивно вычислить расстояние редактирования двух данных непустых строк

    Sample Input 1:
    ab
    ab
    Sample Output 1:
    0

    Sample Input 2:
    short
    ports
    Sample Output 2:
    3

    Sample Input 3:
    distance
    editing
    Sample Output 3:
    5

*/

public class A_EditDist {


    int getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        int len1 = one.length();
        int len2 = two.length();

        int[][] dp = new int[len1 + 1][len2 + 1];

        // Заполнение таблицы: строки и столбцы для пустых подстрок
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i; // Требуется i операций для преобразования в пустую строку
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j; // Требуется j операций для преобразования пустой строки в строку two
        }

        // Заполнение основной таблицы с учетом операций вставки, удаления и замены
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1]; // Если символы одинаковы, то никаких операций не нужно
                } else {
                    dp[i][j] = Math.min(Math.min(
                                    dp[i - 1][j] + 1,    // Удаление
                                    dp[i][j - 1] + 1),   // Вставка
                            dp[i - 1][j - 1] + 1); // Замена
                }
            }
        }
        // Результат - это минимальное количество операций для преобразования строк
        int result = dp[len1][len2];
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson07/dataABC.txt");
        A_EditDist instance = new A_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }
}
