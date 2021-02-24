import java.util.LinkedList;

public class Queue {
    private final LinkedList<Integer> queue = new LinkedList<>();
    private int size;

    public Queue(int size) {
        this.size = size;
    }

    public synchronized Integer poll() throws InterruptedException {
        while (queue.size() == 0)
            wait();
        notifyAll();
        return queue.removeFirst();
    }

    public synchronized void add(Integer elem) throws InterruptedException {
        while (queue.size() >= size)
            wait();
        notifyAll();
        queue.add(elem);
    }


}
