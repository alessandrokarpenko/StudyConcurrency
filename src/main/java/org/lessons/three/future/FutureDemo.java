package org.lessons.three.future;

import java.util.concurrent.*;

/**
 * Check how many vegetables are in the pantry
 */


class HowManyVegetables implements Callable {

    @Override
    public Object call() throws Exception {
        System.out.println("Olivia is counting vegetables...");
        Thread.sleep(3000);
        return 42;
    }
}


public class FutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Barron asks how many...");
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future res = executorService.submit(new HowManyVegetables());
        System.out.println("Barron can do other things while...");
        System.out.println(res.get());
        executorService.shutdown();

    }

}