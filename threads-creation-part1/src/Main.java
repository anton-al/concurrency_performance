public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("Thread "+Thread.currentThread().getName()+" started");
            System.out.println("Current thread priority: "+Thread.currentThread().getPriority());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException("Exception was thrown");
        });

        thread.setUncaughtExceptionHandler((t, e) -> {
            System.out.println("Handler cached exception: "+e);
            e.printStackTrace();
        });

        thread.setName("First Worker Thread");
        thread.setPriority(Thread.MAX_PRIORITY);
        System.out.println("We are in thread: " + Thread.currentThread().getName() + " before starting a new thread");
        thread.start();
        System.out.println("We are in thread: " + Thread.currentThread().getName() + " before starting a new thread");

        Thread.sleep(1000);
    }
}