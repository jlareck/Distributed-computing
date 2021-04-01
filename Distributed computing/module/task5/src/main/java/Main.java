import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        List<Station> route = new ArrayList<>();
        route.add(new Station(1,2));
        route.add(new Station(2,1));
        route.add(new Station(3,3));
        int numOfIterations = 3;
        int numberOfBuses = 10;
        for (int i = 0; i < numberOfBuses; i++) {
            new Bus(i+1, route, numOfIterations);
            try {
                Thread.sleep( (int) (200 * Math.random()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Bus extends Thread {
        private int id;
        private LinkedList<Station> route;
        private Semaphore currentSemaphore;
        private Station targetStation;
        private int cycle;
        private final Station lastStation;
        private int cycles;


        public Bus(int id, List<Station> route, int cycles) {
            cycle = 0;
            this.id = id;
            this.route = new LinkedList<>(route);
            lastStation = this.route.peekLast();
            this.cycles = cycles;
            new Thread(this).start();
        }

        @Override
        public void run() {
            while (route.size() > 0 && cycle < cycles) {
                targetStation = route.pollFirst();
                route.addLast(targetStation);
                if (targetStation.equals(lastStation)) {
                    cycle++;
                }
                drive();

                try {
                    currentSemaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stopBus();
                leaveTheStation();
                currentSemaphore.release();
            }
        }

        public void drive() {
            currentSemaphore = targetStation.semaphore;
            System.out.println(this.id + " moved to " + targetStation.id + " station");
            try {
                sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.id + " arrived to " + targetStation.id + " station");
        }

        public void stopBus() {
            System.out.println(this.id + " stopped at " + targetStation.id + " station");
            targetStation.buses.add(this);
            System.out.print("Buses at " + targetStation.id + " station" + ": ");
            for (Bus bus: targetStation.buses) {
                System.out.print(bus.id + ",");
            }

            try {
                Thread.sleep(targetStation.max *30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void leaveTheStation() {
            targetStation.buses.remove(this);
            System.out.println(this.id + " leaved " + targetStation.id );
            System.out.print("Buses at " + targetStation.id + ": ");
            for (Bus bus: targetStation.buses) {
                System.out.print(bus.id + ",");
            }
        }
    }
    public static class Station {
        int id;
        Semaphore semaphore;
        int max;
        List<Bus> buses;
        public Station(int id, int max) {
            this.id = id;
            this.max = max;
            this.semaphore = new Semaphore(max, true);
            buses = new ArrayList<>();
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Station Station = (Station) o;
            return max == Station.max &&
                    id == Station.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, max);
        }
    }
}
