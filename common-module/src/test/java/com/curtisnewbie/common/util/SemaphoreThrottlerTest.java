package com.curtisnewbie.common.util;

import org.junit.jupiter.api.*;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class SemaphoreThrottlerTest {

    @Test
    public void should_throttle() throws InterruptedException {
        SemaphoreThrottler throttler = new SemaphoreThrottler(1);
        AtomicInteger counter = new AtomicInteger(0);
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                throttler.queue(() -> {
                    System.out.printf("[Thread: %s] count: %d\n", Thread.currentThread().getId(), counter.incrementAndGet());
                    Thread.sleep(200);
                    return null;
                });
            }).start();
        }
        while(counter.get() < 30);
    }

}