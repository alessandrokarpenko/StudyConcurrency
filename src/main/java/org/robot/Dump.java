package org.robot;


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Dump implements Runnable {

    private final String name = "Dump";
    private final List<Detail> availableDetails;
    private final int startedDetailCount = 20;
    private final int detailsPerNight = 4;
    private final SecureRandom random = new SecureRandom();
    private int iteration = 0;

    public boolean doIteration = false;

    public Dump() {
        availableDetails = new ArrayList<>();
        IntStream.range(0, startedDetailCount).forEach(i -> availableDetails.add(Detail.getRandomDetail()));
        System.out.println("First bunch of detail was added. Total: " + availableDetails.size());
    }

    public List<Detail> getSomeDetails() {
        return null;
    }

    public void addSomeDetails() {

    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("%s -> iteration #%s", name, iteration++));
        }
    }

}
