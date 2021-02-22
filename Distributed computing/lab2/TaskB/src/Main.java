public class Main {
    public static void main(String[] args) throws InterruptedException {
        int stolenThingsNumber = 50;
        Queue ivanovQueue = new Queue(5);
        Queue necheporycQueue = new Queue(5);

        ProducerIvanov ivanovHandler = new ProducerIvanov(ivanovQueue, stolenThingsNumber);
        ConsumerPetrov petrovHandler = new ConsumerPetrov(ivanovQueue, necheporycQueue, stolenThingsNumber);
        AccountantNechiporuk nechiporchukHandler = new AccountantNechiporuk(necheporycQueue, stolenThingsNumber);


        Thread thread1 = new Thread(ivanovHandler);
        Thread thread2 = new Thread(petrovHandler);
        Thread thread3 = new Thread(nechiporchukHandler);
        thread1.start();
        thread2.start();
        thread3.start();
        thread3.join();
        System.out.println("Total sum = " + nechiporchukHandler.getTotalSum()+"$");
    }
}
