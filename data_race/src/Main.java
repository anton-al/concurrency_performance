public class Main {
    public static void main(String[] args) throws InterruptedException {
        Shareable sharedClass = new SharedClass();
        Shareable sharedClassWithVolatile = new SharedClassWithVolatile();

        program(sharedClass);
        program(sharedClassWithVolatile);

    }

    private static void program(Shareable shareable) throws InterruptedException {
        long start = System.currentTimeMillis();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                shareable.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                shareable.checkForDataRace();
            }

        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        long end = System.currentTimeMillis();
        System.out.println("Time for " +shareable.getClass().getSimpleName()+": " + (end - start));
    }

    public interface Shareable {
        void increment();
        void checkForDataRace();
    }

    public static class SharedClass implements Shareable {
        private int x = 0;
        private int y = 0;

        public void increment() {
            x++;
            y++;
        }

        public void checkForDataRace() {
            if (y > x) {
                System.out.println("y > x - Data Race is detected");
            }
        }
    }

    public static class SharedClassWithVolatile implements Shareable {
        private volatile int x = 0;
        private volatile int y = 0;

        public void increment() {
            x++;
            y++;
        }

        public void checkForDataRace() {
            if (y > x) {
                System.out.println("y > x - Data Race is detected");
            }
        }
    }
}