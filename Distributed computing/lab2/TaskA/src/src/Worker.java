package src;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.Semaphore;
public class Worker implements Runnable {
    private int[] row;
    private Semaphore semaphore;
    private AtomicBoolean found;
    private int id;

    public Worker(int[] row, Semaphore semaphore, AtomicBoolean found, int id) {
        this.row = row;
        this.semaphore = semaphore;
        this.found = found;
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Hive " + id + " sent");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int value : row) {
            if (value == 1) {
                synchronized (found) {
                    found.set(true);
                    System.out.println("Found at " + id + " " + Arrays.toString(row));
                }
                break;
            }
        }
        System.out.println("Worker " + id + " returned");
        semaphore.release();
    }
}
