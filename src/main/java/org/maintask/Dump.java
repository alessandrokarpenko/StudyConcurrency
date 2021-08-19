package org.maintask;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;

public class Dump implements Runnable {

    private final String name = "dump";
    public int detailsPerNight = 4;
    public final List<Detail> details;
    public final int cycles = 100;
    private final SecureRandom secureRandom = new SecureRandom();

    {
        details = new ArrayList<>();
        IntStream.range(0, 20).forEach(i -> details.add(Factory.createRandomDetail()));
        System.out.println("First bunch of detail was added. Total: " + details.size());
    }

    public void addSomeNewDetail() {
        int index = secureRandom.nextInt(detailsPerNight);
        synchronized (details) {
            IntStream.range(0, index).forEach(i -> details.add(Factory.createRandomDetail()));
        }
        System.out.println("Daily bunch of detail was added. Total: " + details.size());
    }

    public List<Detail> getSomeDetail() {
        List<Detail> out = new ArrayList<>();
        int count = Math.min(details.size(), detailsPerNight);
        int randomCount = secureRandom.nextInt(count);
        synchronized (details) {
            for (int j = 0; j < randomCount; j++) {
                Detail detail = details.stream().findAny().get();
                out.add(detail);
                details.remove(detail);
            }
        }
        return out;
    }


    @Override
    public void run() {
        for (int i = 0; i < cycles; i++) {
            addSomeNewDetail();
        }
    }

}
