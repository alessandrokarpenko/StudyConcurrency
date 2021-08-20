package org.robot;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Assistant {

    private Dump dump;
    private String name;
    private int iteration = 0;
    private List<Detail> details;
    private final int detailsPerNight = 4;
    private final SecureRandom random = new SecureRandom();
    private ResettableCountDownLatch resettableCountDownLatch;

    public Assistant(String name, Dump dump) {
        this.name = name;
        this.dump = dump;
        details = new ArrayList<>();
        resettableCountDownLatch = new ResettableCountDownLatch(1);
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void tryToGrabSomeDetails() {
        int ranCount = random.nextInt(detailsPerNight);
        System.out.printf("%s --- step %s --- attempt to grab %s details\n", this.name, iteration, ranCount + 1);
        for (int i = 0; i <= ranCount; i++) {
            List<Detail> dumpDetails = dump.getDetails();
            synchronized (dumpDetails) {
                if (dumpDetails.size() > 0) {
                    Detail any = dumpDetails.stream().findAny().get();
                    dumpDetails.remove(any);
                    details.add(any);
                    System.out.printf("%s --- step %s Detail %s was grabbed. Total: %s\n", this.name, iteration, any, details.size());
                } else {
                    System.out.printf("%s --- step %s could not grab any details\n", this.name, iteration);
                    continue;
                }
            }
        }
    }

    public Callable<Void> execute() {
        return new Assistant.Executor();
    }

    public ResettableCountDownLatch getResettableCountDownLatch() {
        return resettableCountDownLatch;
    }

    class Executor implements Callable<Void> {

        @Override
        public Void call() throws Exception {
            tryToGrabSomeDetails();
            iteration++;
            resettableCountDownLatch.countDown();
            return null;
        }
    }

}