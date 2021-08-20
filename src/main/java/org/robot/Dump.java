package org.robot;


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public class Dump {

    String name = "Dump";
    int iteration = 0;
    List<Detail> details;
    private final int starterDetailCount = 20;
    private final int detailsPerNight = 4;
    private final SecureRandom random = new SecureRandom();

    public Dump() {
        details = new ArrayList<>();
        addStarterPack();
    }

    public List<Detail> getDetails() {
        return details;
    }

    private void addStarterPack() {
        IntStream.range(1, starterDetailCount + 1).forEach(x -> details.add(Detail.getRandomDetail()));
        System.out.printf("%s --- default set of details was added to dump. Count: %s\n", this.name, details.size());
    }

    public Callable<Void> execute() {
        return new Executor();
    }

    public void addNightDetails() {
        int ranCount = random.nextInt(detailsPerNight);
        synchronized (details) {
            IntStream.range(0, ranCount + 1).forEach(x -> details.add(Detail.getRandomDetail()));
        }
        System.out.printf("%s --- step %s --- %s details were added. Count: %s\n", this.name, iteration, ranCount + 1, details.size());
    }

    class Executor implements Callable<Void> {

        @Override
        public Void call() throws Exception {
            addNightDetails();
            iteration++;
            return null;
        }
    }

}
