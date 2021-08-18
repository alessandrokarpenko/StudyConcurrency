package org.lessons.three.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Chopping Vegetables with a ThreadPool
 */

class VegetableChopper extends Thread {
    public void run() {
        System.out.println(Thread.currentThread().getName() + " chopped a vegetable!");
    }
}

public class ThreadPoolDemo {
    public static void main(String args[]) {

        int numProcs = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numProcs);

        for (int i = 0; i < 100; i++) {
            executorService.submit(new VegetableChopper());
        }

        executorService.shutdown();
    }
}