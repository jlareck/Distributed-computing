import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        Barbershop barbershop = new Barbershop();
        CustomerGenerator customerGenerator = new CustomerGenerator(barbershop);
        Barber barber = new Barber(barbershop);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(barber);
        executor.execute(customerGenerator);

        stopAfter(executor, Config.Main.workTime);
    }


    private static void stopAfter(ExecutorService executor, int workTime) {
        try {
            Thread.sleep(workTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        try {
            executor.shutdownNow();
            executor.awaitTermination(0, TimeUnit.MICROSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}