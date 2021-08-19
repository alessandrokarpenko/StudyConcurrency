package org.maintask;

public class Robot {

    long count;


    public Robot() {
        count = 0;
    }

    public void newRobotWasCreated() {
        count++;
        System.out.println("Robots built: " + count);
    }

    public void newRobotsWereCreated(Long count) {
        this.count += count;
        System.out.println("Robots built: " + count);
    }
}
