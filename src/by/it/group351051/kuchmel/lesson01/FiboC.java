package by.it.group351051.kuchmel.lesson01;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * время расчета должно быть не более 2 секунд
 */

public class FiboC {

    private long startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {
        FiboC fibo = new FiboC();
        int n = 10;
        int m = 2;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано
        int pisanoPeriod = findPisanoPeriod(m);
        int reducedN = (int)(n % pisanoPeriod);
        return fibonacciModul(reducedN, m);
    }

    int findPisanoPeriod(int m) {
        int prev = 0;
        int curr = 1;
        for (int i = 0; i < m*m; i++) {
            int temp = (prev + curr) % m;
            prev = curr;
            curr = temp;
            if  (prev == 0 && curr == 1) return i + 1;
        }
        return 1;
    }

    long fibonacciModul(int n, int m) {
        if (n <= 1) return n;

        long prev = 0;
        long curr = 1;
        for (int i = 2; i <=n; i++) {
            long temp = (prev + curr) % m;
            prev = curr;
            curr = temp;
        }
        return curr;
    }


}

