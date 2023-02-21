package com.curtisnewbie.common.util;

/**
 * Simple Benchmark Utility
 *
 * @author yongj.zhuang
 */
public final class SimpleBenchmark {

    private SimpleBenchmark() {}

    public static void benchmark(boolean preheat, String name, long iterations, Runnable r) {
        // preheat
        if (preheat) for (long i = 0; i < 30; i++) r.run();

        final long start = System.currentTimeMillis();
        for (long i = 0; i < iterations; i++) r.run();
        final long end = System.currentTimeMillis();
        final long spent = (end - start);
        final double eachMilli = (double) spent / (double) iterations;
        if (name != null)
            System.out.printf("%-30s iterations: %,d  total spent: %,5dms %,16d op/s\n", name, iterations, spent, (long) (1000d / eachMilli));
        else
            System.out.printf("iterations: %,d  total spent: %,5dms %,16d op/s\n", iterations, spent, (long) (1000d / eachMilli));
    }

    public static void benchmark(long iterations, Runnable r) {
        benchmark(true, null, iterations, r);
    }

    public static void benchmark(String name, long iterations, Runnable r) {
        benchmark(true, name, iterations, r);
    }
}
