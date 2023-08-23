public class Main {

    private static final int NUMBER = 1_000_000;
    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);

        // concurrently
        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();

        System.out.println("We have " + inventoryCounter.getItems() + " items");
    }

    private static class IncrementingThread extends Thread {
        private final InventoryCounter counter;

        public IncrementingThread(InventoryCounter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < NUMBER; i++) {
                counter.increment();
            }
        }
    }

    private static class DecrementingThread extends Thread {
        private final InventoryCounter counter;

        public DecrementingThread(InventoryCounter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < NUMBER; i++) {
                counter.decrement();
            }
        }
    }

    private static class InventoryCounter {
        private int items = 0;
        private final Object lock = new Object();

        public void increment() {
            synchronized (lock) {
                items++;
            }
        }

        public synchronized void decrement() {
            synchronized (lock) {
                items--;
            }
        }

        public int getItems() {
            return items;
        }
    }
}