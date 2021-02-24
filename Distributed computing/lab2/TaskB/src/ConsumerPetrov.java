public class ConsumerPetrov implements Runnable{
    private Queue producerQueue;
    private Queue accountantQueue;
    private int numberOfElements;

    public ConsumerPetrov(Queue producerQueue, Queue accountantQueue, int numberOfElements) {
        this.producerQueue = producerQueue;
        this.accountantQueue = accountantQueue;
        this.numberOfElements = numberOfElements;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfElements; i++ ) {
            try {
                Integer element = producerQueue.poll();
                accountantQueue.add(element);
                System.out.println("The item with cost " + element + " was loaded to accountant Nechiporuk");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
