package org.maintask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Application {

    public static Lock mutex = new ReentrantLock();
    public static Condition condition = mutex.newCondition();


    public static void main(String[] args) throws InterruptedException {

        Dump dump = new Dump();
        Scientist scientistOne = new Scientist(dump);
        Scientist scientistTwo = new Scientist(dump);


        Thread d = new Thread(dump, "d");
        Thread sc1 = new Thread(scientistOne.getAssistant(), "sc1");
        Thread sc2 = new Thread(scientistTwo.getAssistant(), "sc2");


        ExecutorService executorService = Executors.newFixedThreadPool(3);


        executorService.submit(sc1);
        executorService.submit(sc2);
        executorService.submit(d);

        executorService.shutdown();


        while (executorService.awaitTermination(15, TimeUnit.SECONDS)) {
            scientistOne.tryToCreateRobots();
            scientistTwo.tryToCreateRobots();
            if (executorService.isTerminated()){
                break;
            }
        }


    }


}
