package com.curtisnewbie.common.util;


import org.springframework.util.*;

import java.time.*;

/**
 * Timer based on {@link LocalDateTime}
 * <p>
 * It's <b>not</b> thread-Safe
 * </p>
 *
 * @author yongj.zhuang
 */
public class LDTTimer {

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    /** Get a new timer and start it */
    public static LDTTimer startTimer() {
        LDTTimer t = new LDTTimer();
        t.start();
        return t;
    }

    /** start the timer */
    public void start() {
        Assert.isNull(startTime, "Timer started already");
        startTime = LocalDateTime.now();
    }

    /** stop the timer */
    public void stop() {
        Assert.isNull(endTime, "Timer stopped already");
        endTime = LocalDateTime.now();
    }

    /** Get duration */
    public Duration duration() {
        Assert.notNull(startTime, "Timer hasn't started yet");
        Assert.notNull(endTime, "Timer hasn't stopped yet");
        return Duration.between(startTime, endTime);
    }

    /** Print Duration */
    public String printDuration() {
        final Duration duration = duration();
        final String s = duration.toString().substring(2);
        return s.replace("H", " Hours ").replace("M", " Minutes ").replace("S", " Seconds ");
    }

}
