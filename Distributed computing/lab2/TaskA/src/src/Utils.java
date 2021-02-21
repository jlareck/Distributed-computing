package src;

import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

public class Utils {

    public static void print(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int value : matrix[i]) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
    public static void parallelSearch(int[][] matrix, int workersCount) throws InterruptedException {
        int hiveWorkersCount = 9;
        ThreadPoolExecutor executor =  (ThreadPoolExecutor) Executors.newFixedThreadPool(hiveWorkersCount);

        AtomicBoolean checkIfFound = new AtomicBoolean(false);
        Semaphore semaphore = new Semaphore(workersCount);
        for (int i = 0; i < matrix.length; i++) {
            if (checkIfFound.get())
                break;

            semaphore.acquire();
            executor.execute(new Worker(matrix[i], semaphore, checkIfFound, i));
        }
        executor.shutdown();
    }

}
