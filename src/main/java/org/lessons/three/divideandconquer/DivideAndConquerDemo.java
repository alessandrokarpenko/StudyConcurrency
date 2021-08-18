package org.lessons.three.divideandconquer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Recursively sum a range of numbers
 */


class RecursiveSum extends RecursiveTask<Long> {

    private final long lo;
    private final long hi;

    public RecursiveSum(long lo, long hi) {
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    protected Long compute() {
        if (hi - lo <= 100_000) {
            long total = 0;
            for (long i = lo; i < hi; i++) {
                total += i;
            }
            return total;
        } else {
            long mid = (hi + lo) / 2;
            RecursiveSum left = new RecursiveSum(lo, mid);
            RecursiveSum right = new RecursiveSum(mid, hi);
            left.fork();
            return right.compute() + left.join();
        }
    }
}

public class DivideAndConquerDemo {


    public static void main(String args[]) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        Long invoke = pool.invoke(new RecursiveSum(0, 1_000_000_000));
        pool.shutdown();
        System.out.println(invoke);
    }


}