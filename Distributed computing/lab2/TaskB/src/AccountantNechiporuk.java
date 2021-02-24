public class AccountantNechiporuk implements Runnable {
    private Queue queue;
    public int totalSum;
    private int numberOfElements;
    public AccountantNechiporuk(Queue queue, int numberOfElements) {
        this.queue = queue;
        this.numberOfElements = numberOfElements;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfElements; i++) {
            try {
                Integer element = queue.poll();
                totalSum += element;
                System.out.println("The item with cost " + element + " was added to totalSum");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
