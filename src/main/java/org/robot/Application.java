package org.robot;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Application {

    private static final int nights = 100;

    public void doIt() throws InterruptedException {

        Dump dump = new Dump();
        Assistant assistant1 = new Assistant("Ass1", dump);
        Assistant assistant2 = new Assistant("Ass2", dump);
        Scientist scientist1 = new Scientist("Sc1", assistant1);
        Scientist scientist2 = new Scientist("Sc2", assistant2);

        for (int i = 0; i < nights; i++) {
            System.out.printf("----------------%s day----------------\n", i);
            ExecutorService executorService = Executors.newFixedThreadPool(5);
            executorService.invokeAll(List.of(scientist2.execute(), dump.execute(), assistant1.execute(), assistant2.execute(),
                    scientist1.execute()));
            executorService.shutdown();
            Thread.sleep(100);
        }

        System.out.println("==================================================================");
        System.out.printf("%s ---- %s robots\n", scientist1.getName(), scientist1.getRobotCount());
        System.out.printf("%s ---- %s robots\n", scientist2.getName(), scientist2.getRobotCount());
    }

    public static void main(String[] args) throws InterruptedException {
        new Application().doIt();
    }
}
