package org.robot;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Scientist {

    private String name;
    private Assistant assistant;
    private int iteration = 0;
    private int robotCount = 0;
    private final List<Detail> necessaryParts = List.of(Detail.HEAD, Detail.LEFT_HAND, Detail.RIGHT_HAND, Detail.BODY,
            Detail.LEFT_LEG, Detail.LEFT_LEG, Detail.HDD, Detail.CPU, Detail.RAM);


    public Scientist(String name, Assistant assistant) {
        this.name = name;
        this.assistant = assistant;
    }

    public void tryToBuildRobots() {
        System.out.printf("%s --- step %s --- try to build robot\n", this.name, iteration);
        List<Detail> currentDetails = assistant.getDetails();
        synchronized (currentDetails) {
            if (currentDetails.containsAll(necessaryParts)) {

                Map<Detail, Long> detailsAsMap = currentDetails.stream()
                        .collect(Collectors.groupingBy(x -> x, Collectors.counting()));
                detailsAsMap.forEach((k, v) -> System.out.println(this.name + " details: " + k + " -> " + v));

                Long value = detailsAsMap.entrySet().stream()
                        .min(Comparator.comparingLong(Map.Entry::getValue)).get().getValue();

                robotCount += value;

                for (Detail detail : necessaryParts) {
                    LongStream.range(0, value).forEach(i -> currentDetails.remove(detail));
                }

                System.out.printf("%s --- step %s --- %s robots were built. Total: %s\n", this.name, iteration, value, robotCount);

            } else {
                System.out.printf("%s --- step %s --- can't build robot now\n", this.name, iteration);
            }
        }
    }

    public Callable<Void> execute() {
        return new Scientist.Executor();
    }

    public String getName() {
        return name;
    }

    public int getRobotCount() {
        return robotCount;
    }

    class Executor implements Callable<Void> {

        @Override
        public Void call() throws Exception {
            assistant.getResettableCountDownLatch().await();
            tryToBuildRobots();
            iteration++;
            assistant.getResettableCountDownLatch().reset();
            return null;
        }
    }

}
