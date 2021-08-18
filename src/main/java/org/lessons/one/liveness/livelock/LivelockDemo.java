package org.lessons.one.liveness.livelock; /**
 * A polite philosopher makes sure their friends eat first
 */

import java.util.Random;
import java.util.concurrent.locks.*;

class Philosopher extends Thread {

    private Lock firstChopstick, secondChopstick;
    private static int sushiCount = 500_000;
    private Random random = new Random();

    public Philosopher(String name, Lock firstChopstick, Lock secondChopstick) {
        this.setName(name);
        this.firstChopstick = firstChopstick;
        this.secondChopstick = secondChopstick;
    }

    public void run() {
        while (sushiCount > 0) { // eat sushi until it's all gone

            // pick up chopsticks
            firstChopstick.lock();
            if (!secondChopstick.tryLock()) {
                System.out.println(this.getName() + " release first ");
                firstChopstick.unlock();
                try {
                    Thread.sleep(random.nextInt(5));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    // take a piece of sushi
                    if (sushiCount > 0) {
                        sushiCount--;
                        System.out.println(this.getName() + " took a piece! Sushi remaining: " + sushiCount);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // put down chopsticks
                    secondChopstick.unlock();
                    firstChopstick.unlock();
                }
            }
        }
    }
}

public class LivelockDemo {
    public static void main(String[] args) {
        Lock chopstickA = new ReentrantLock();
        Lock chopstickB = new ReentrantLock();
        Lock chopstickC = new ReentrantLock();
        new Philosopher("Barron", chopstickA, chopstickB).start();
        new Philosopher("Olivia", chopstickB, chopstickC).start();
        new Philosopher("Steve", chopstickC, chopstickA).start();
    }
}