package org.robot;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ResettableCountDownLatch {

    private final int initialCount;
    private volatile CountDownLatch latch;

    public ResettableCountDownLatch(int  count) {
        initialCount = count;
        latch = new CountDownLatch(count);
    }

    public void reset() {
        latch = new CountDownLatch(initialCount);
    }

    public void countDown() {
        latch.countDown();
    }

    public void await() throws InterruptedException {
        latch.await();
    }

    public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
        return latch.await(timeout, unit);
    }
}
