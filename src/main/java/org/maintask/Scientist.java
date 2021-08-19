package org.maintask;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static java.lang.Thread.sleep;

public class Scientist {

    private String name;
    private static int number = 0;
    private Dump dump;


    private List<Detail> availableDetails;
    private Robot robot;
    private static List<Detail> requiredDetails;

    static {
        requiredDetails = Arrays.asList(Detail.values());
    }


    public Scientist(Dump dump) {
        availableDetails = new ArrayList<>();
        robot = new Robot();
        name = "Scientist #" + number++;
        this.dump = dump;
    }

    public void tryToCreateRobots() {
        System.out.println(this.name + " is trying to create robots");

        Map<Detail, Long> collection = availableDetails.stream().collect(Collectors.groupingBy(x -> x, Collectors.counting()));
        collection.forEach((k, v) -> System.out.println(this.name + " details: " + k + " -> " + v));

        if (availableDetails.containsAll(requiredDetails)) {

            Long value = collection.entrySet().stream()
                    .min(Comparator.comparingLong(Map.Entry::getValue)).get().getValue();
            System.out.println("Robot count to be built: " + value);

            robot.newRobotsWereCreated(value);

            for (Detail detail : requiredDetails) {
                LongStream.range(0, value).forEach(i -> availableDetails.remove(detail));
            }

        } else {
            System.out.println(this.name + " can't build any more");
        }

    }

    public Assistant getAssistant() {
        return new Assistant();
    }

    class Assistant implements Runnable {

        private String name = Scientist.this.name;

        @Override
        public void run() {
            for (int i = 0; i < dump.cycles; i++) {
                List<Detail> someDetail = dump.getSomeDetail();
                System.out.println(name + " gets some detail: " + someDetail.size());
                availableDetails.addAll(someDetail);
            }
        }
    }

}
