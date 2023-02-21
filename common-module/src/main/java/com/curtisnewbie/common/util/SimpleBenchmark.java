package com.curtisnewbie.common.util;

/**
 * Simple Benchmark Utility
 *
 * @author yongj.zhuang
 */
public final class SimpleBenchmark {

    private SimpleBenchmark() {}

    public static void benchmark(long iterations, Runnable r) {
        final long start = System.currentTimeMillis();
        for (long i = 0; i < iterations; i++) {
            r.run();
        }
        final long end = System.currentTimeMillis();
        final long spent = (end - start);
        final double eachMilli = (double) spent / (double) iterations;
        System.out.printf("iterations %,d, total spent %,dms, %,d op/s\n", iterations, spent, (long) (1000f / eachMilli));
    }
}
