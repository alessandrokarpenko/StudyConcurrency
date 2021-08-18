package org.lessons.one.threads.synchronizedmethod;

/**
 * Two shoppers adding items to a shared notepad
 */

class Shopper extends Thread {

    static int garlicCount = 0;

    //one way
    private static synchronized void add() {
        garlicCount++;
    }

    public void run() {
        for (int i=0; i<10_000_000; i++) {
            //another way
            synchronized (Shopper.class) {
                add();
            }
        }
    }
}

public class SynchronizedMethodDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread barron = new Shopper();
        Thread olivia = new Shopper();
        barron.start();
        olivia.start();
        barron.join();
        olivia.join();
        System.out.println("We should buy " + Shopper.garlicCount + " garlic.");
    }
}