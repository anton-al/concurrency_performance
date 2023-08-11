import java.math.BigInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread longExecutionThread = new LongExecutionThread(new BigInteger("200000"), new BigInteger("100000000"));

        // daemon stops then main thread ends (to prevent blocking main thread by computationThread)
//        longExecutionThread.setDaemon(true);
        longExecutionThread.start();
        Thread.sleep(100);
        longExecutionThread.interrupt();
    }

    private static class LongExecutionThread extends Thread {
        BigInteger base;
        BigInteger power;

        public LongExecutionThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + " ^ " + power + " = " + execute(base, power));
        }

        private BigInteger execute(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i=i.add(BigInteger.ONE)){
                if(Thread.interrupted()){
                    System.out.println("Thread was interrupted");
                    return result;
                }
                result = result.multiply(base);
            }
            return result;
        }
    }
}