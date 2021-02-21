public class ConsumerPetrov implements Runnable{
    private Queue ivanovQueue;
    private Queue nechiporchukQueue;
    private int elementsToHandle;

    public ConsumerPetrov(Queue ivanovQueue, Queue nechiporchukQueue, int elementsToHandle) {
        this.ivanovQueue = ivanovQueue;
        this.nechiporchukQueue = nechiporchukQueue;
        this.elementsToHandle = elementsToHandle;
    }

    @Override
    public void run() {
        for (int i = 0; i < elementsToHandle; i++ ) {
            try {
                Integer elem = ivanovQueue.poll();
                System.out.println("Petrov\t\tloaded item with value\t" + elem + "$");
                nechiporchukQueue.add(elem);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
