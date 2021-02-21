public class AccountantNechiporuk implements Runnable {
    private Queue queue;
    private int numberOfElements;
    private int sum;

    public AccountantNechiporuk(Queue queue, int numberOfElements) {
        this.queue = queue;
        this.numberOfElements = numberOfElements;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfElements; i++) {
            try {
                Integer element = queue.poll();
                sum += element;
                System.out.println("The item with cost " + element + "$ was received");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getSum() {
        return sum;
    }
}
