package org.robot;

import java.util.List;
import java.util.concurrent.Callable;

public class Scientist {

    private String name;
    private Assistant assistant;
    int iteration = 0;
    int robotCount = 0;
    private final List<Detail> necessaryParts = List.of(Detail.HEAD, Detail.LEFT_HAND, Detail.RIGHT_HAND, Detail.BODY,
            Detail.LEFT_LEG, Detail.LEFT_LEG, Detail.HDD, Detail.CPU, Detail.RAM);

    public Scientist(String name, Assistant assistant) {
        this.name = name;
        this.assistant = assistant;
    }

    public void tryToBuildRobot() {

    }

    public Callable<Void> execute() {
        return new Scientist.Executor();
    }

    class Executor implements Callable<Void> {

        @Override
        public Void call() throws Exception {
            tryToBuildRobot();
            iteration++;
            return null;
        }
    }

}
