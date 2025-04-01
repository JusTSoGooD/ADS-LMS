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
    Итерационно вычислить алгоритм преобразования двух данных непустых строк
    Вывести через запятую редакционное предписание в формате:
     операция("+" вставка, "-" удаление, "~" замена, "#" копирование)
     символ замены или вставки

    Sample Input 1:
    ab
    ab
    Sample Output 1:
    #,#,

    Sample Input 2:
    short
    ports
    Sample Output 2:
    -s,~p,#,#,#,+s,

    Sample Input 3:
    distance
    editing
    Sample Output 2:
    +e,#,#,-s,#,~i,#,-c,~g,


    P.S. В литературе обычно действия редакционных предписаний обозначаются так:
    - D (англ. delete) — удалить,
    + I (англ. insert) — вставить,
    ~ R (replace) — заменить,
    # M (match) — совпадение.
*/


public class C_EditDist {

    String getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        int len1 = one.length();
        int len2 = two.length();

        // Таблица для хранения минимальных расстояний
        int[][] dp = new int[len1 + 1][len2 + 1];

        // Таблица для хранения операций
        String[][] ops = new String[len1 + 1][len2 + 1];

        // Инициализация таблиц
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i;
            ops[i][0] = i == 0 ? "" : ops[i - 1][0] + "-"+ one.charAt(i - 1) + ",";
        }

        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
            ops[0][j] = j == 0 ? "" : ops[0][j - 1] + "+" + two.charAt(j - 1) + ",";
        }

        // Заполнение таблицы dp и таблицы операций
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                int cost = (one.charAt(i - 1) == two.charAt(j - 1)) ? 0 : 1;

                if (cost == 0) {
                    dp[i][j] = dp[i - 1][j - 1]; // Символы совпадают
                    ops[i][j] = ops[i - 1][j - 1] + "#,";
                } else {
                    int insertCost = dp[i][j - 1] + 1;
                    int deleteCost = dp[i - 1][j] + 1;
                    int replaceCost = dp[i - 1][j - 1] + 1;

                    // Выбираем минимальную стоимость
                    if (insertCost <= deleteCost && insertCost <= replaceCost) {
                        dp[i][j] = insertCost;
                        ops[i][j] = ops[i][j - 1] + "+" + two.charAt(j - 1) + ",";
                    } else if (deleteCost <= replaceCost) {
                        dp[i][j] = deleteCost;
                        ops[i][j] = ops[i - 1][j] + "-" + one.charAt(i - 1) + ",";
                    } else {
                        dp[i][j] = replaceCost;
                        ops[i][j] = ops[i - 1][j - 1] + "~" + two.charAt(j - 1) + ",";
                    }
                }
            }
        }


        String result = ops[len1][len2];
        return result;
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson07/dataABC.txt");
        C_EditDist instance = new C_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }

}