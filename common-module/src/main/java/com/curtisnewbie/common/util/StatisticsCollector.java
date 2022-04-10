package com.curtisnewbie.common.util;


import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

/**
 * Thread-safe Statistics Collector
 * <p>
 * Each {@link Stat} is associated with a key, usage:
 * </p>
 * <pre>
 * {@code
 * StatisticsCollector stat = new StatisticsCollector();
 * stat.addLongStat(REQUEST_COUNT_STAT, "Request Count:");
 * stat.addLongStat(SUCCESS_COUNT_STAT, "Success Count:");
 * stat.addLongStat(FAILURE_COUNT_STAT, "Failure Count:");
 *
 * // record stat in our code
 * stat.incr(REQUEST_COUNT_STAT);
 *
 * // log stat once it's finished
 * log.info("Statistics: {}", stat.print());
 * }
 * </pre>
 *
 * @author yongj.zhuang
 */
public final class StatisticsCollector {

    /** name -> Stat */
    private final ConcurrentMap<String, Stat> nameToStatMap = new ConcurrentHashMap<>();

    /** Add Long Stat */
    public void addLongStat(String name, String description) {
        nameToStatMap.putIfAbsent(name, new LongStat(description));
    }

    /** Add Boolean Stat */
    public void addBooleanStat(String name, String description) {
        nameToStatMap.putIfAbsent(name, new BoolStat(description));
    }

    /** Incr by 1 */
    public void incr(String name) {
        Stat o = nameToStatMap.get(name);
        if (o == null)
            return;
        ((LongStat) o).count.incrementAndGet();
    }

    /** Incr by delta */
    public void incrby(String name, long delta) {
        Stat o = nameToStatMap.get(name);
        if (o == null)
            return;
        ((LongStat) o).count.addAndGet(delta);
    }

    /** Decr by 1 */
    public void decr(String name) {
        Stat o = nameToStatMap.get(name);
        if (o == null)
            return;
        ((LongStat) o).count.decrementAndGet();
    }

    /** Decr by delta */
    public void decrby(String name, long delta) {
        Stat o = nameToStatMap.get(name);
        if (o == null)
            return;
        ((LongStat) o).count.addAndGet(-delta);
    }

    /** Update Boolean Stat */
    public void setFlag(String name, boolean flag) {
        Stat o = nameToStatMap.get(name);
        if (o == null)
            return;

        ((BoolStat) o).flag.set(flag);
    }

    /** Update Boolean Stat to true */
    public void setTrue(String name) {
        setFlag(name, true);
    }

    /** Update Boolean Stat to false */
    public void setFalse(String name) {
        setFlag(name, false);
    }

    /** Check if Boolean Stat is true, Return false if the stat doesn't exist */
    public boolean isTrue(String name) {
        Boolean b = getBool(name);
        return Objects.equals(b, true);
    }

    /** Get Boolean Stat */
    public Boolean getBool(String name) {
        Stat o = nameToStatMap.get(name);
        if (o == null)
            return null;
        return ((BoolStat) o).flag.get();
    }

    /** Get Long Stat */
    public Long getLong(String name) {
        Stat o = nameToStatMap.get(name);
        if (o == null)
            return null;
        return ((LongStat) o).count.get();
    }

    /** Print Statistics */
    public String print() {
        return nameToStatMap.values().stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }

    // --------------------------- private helpers -------------------------------

    /** Long Stat */
    private static class LongStat extends Stat {
        private final AtomicLong count = new AtomicLong(0);

        LongStat(String desc) {
            super(desc);
        }

        @Override
        public String toString() {
            return getDescription() + " " + count.get();
        }
    }

    /** Boolean Stat */
    private static class BoolStat extends Stat {
        private final AtomicBoolean flag = new AtomicBoolean(false);

        BoolStat(String desc) {
            super(desc);
        }

        @Override
        public String toString() {
            return getDescription() + " " + flag.get();
        }
    }

    /** Stat */
    private static abstract class Stat {

        /** Description */
        private final String description;

        Stat(String desc) {
            this.description = desc;
        }

        public String getDescription() {
            return description;
        }
    }
}
