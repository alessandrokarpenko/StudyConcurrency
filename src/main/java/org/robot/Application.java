package org.robot;

public class Application {

    private static final int nights = 100;

    public static void main(String[] args) throws InterruptedException {


        Application application = new Application();
        Dump dump = new Dump();
        Thread threadD = new Thread(dump);
        threadD.start();

        for (int i = 0; i < nights; i++) {
            synchronized (dump) {
                System.out.println(String.format("----------------------%s day----------------------", i));
                synchronized (dump) {
                    dump.notifyAll();
                }
            }
        }

    }
}
