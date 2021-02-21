import java.util.LinkedList;

public class Queue {
    private final LinkedList<Integer> queue = new LinkedList<>();
    private int maxSize;

    public Queue(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized void add(Integer elem) throws InterruptedException {
        while (queue.size() >= maxSize)
            wait();
        notifyAll();
        queue.add(elem);
    }

    public synchronized Integer poll() throws InterruptedException {
        while (queue.size() == 0)
            wait();
        notifyAll();
        return queue.removeFirst();
    }
}
