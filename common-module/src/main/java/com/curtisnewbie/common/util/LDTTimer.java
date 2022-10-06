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

    private LocalDateTime startTime = null;
    private LocalDateTime endTime = null;

    /** Get a new timer and start it */
    public static LDTTimer startTimer() {
        return new LDTTimer().start();
    }

    /** start the timer, repeatable action, only the first time calling this method takes effect */
    public LDTTimer start() {
        if (startTime != null) return this;
        startTime = LocalDateTime.now();
        return this;
    }

    /** stop the timer, repeatable action, only the first time calling this method takes effect */
    public LDTTimer stop() {
        if (endTime != null) return this;
        endTime = LocalDateTime.now();
        return this;
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
