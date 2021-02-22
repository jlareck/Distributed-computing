public class ConsumerPetrov implements Runnable{
    private Queue ivanovQueue;
    private Queue nechiporchukQueue;
    private int numberOfElements;

    public ConsumerPetrov(Queue ivanovQueue, Queue nechiporchukQueue, int numberOfElements) {
        this.ivanovQueue = ivanovQueue;
        this.nechiporchukQueue = nechiporchukQueue;
        this.numberOfElements = numberOfElements;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfElements; i++ ) {
            try {
                Integer element = ivanovQueue.poll();
                System.out.println("The item with cost " + element + " was loaded to accountant Nechiporuk");
                nechiporchukQueue.add(element);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
