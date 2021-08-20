package org.robot;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {

    private static final int nights = 20;

    public void doIt() throws InterruptedException {

        Dump dump = new Dump();
        Assistant assistant1 = new Assistant("ass1", dump);
        Assistant assistant2 = new Assistant("ass2", dump);
        Scientist scientist1 = new Scientist("sc1", assistant1);
        Scientist scientist2 = new Scientist("sc2", assistant2);

        for (int i = 0; i < nights; i++) {
            System.out.printf("----------------%s day----------------\n", i);
            ExecutorService executorService = Executors.newFixedThreadPool(3);
            executorService.invokeAll(List.of(dump.execute(), assistant1.execute(), assistant2.execute(),
                    scientist1.execute(), scientist2.execute()));
            executorService.shutdown();
            Thread.sleep(100);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Application().doIt();
    }
}
