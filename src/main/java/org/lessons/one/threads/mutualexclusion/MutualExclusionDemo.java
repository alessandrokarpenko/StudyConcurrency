package org.lessons.one.threads.mutualexclusion;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Two shoppers adding items to a shared notepad
 */

class Shopper extends Thread {

    static int garlicCount = 0;
    static Lock pencil = new ReentrantLock();

    public void run() {

        for (int i=0; i<10_000_000; i++) {
            pencil.lock();
            garlicCount++;
            pencil.unlock();
        }

    }
}

public class MutualExclusionDemo {
    public static void main(String[] args) throws InterruptedException {
        long st = System.nanoTime();
        Thread barron = new Shopper();
        Thread olivia = new Shopper();
        barron.start();
        olivia.start();
        barron.join();
        olivia.join();
        System.out.println("We should buy " + Shopper.garlicCount + " garlic.");
        long fn = System.nanoTime();
        System.out.println((fn-st)/1000000);
    }
}