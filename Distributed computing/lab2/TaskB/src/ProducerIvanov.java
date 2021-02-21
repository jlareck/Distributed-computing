public class ProducerIvanov implements Runnable {
    private Queue queue;
    private int numberOfElements;

    public ProducerIvanov(Queue queue, int numberOfElements) {
        this.queue = queue;
        this.numberOfElements = numberOfElements;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfElements; i++) {
            try {
                int elem = i+10;
                queue.add(elem);
                System.out.println("The item with price " + elem + " was stolen");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
