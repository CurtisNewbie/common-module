package com.curtisnewbie.common.trace;

import brave.*;
import brave.baggage.BaggageField;
import com.curtisnewbie.common.util.AssertUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Optional;

import static java.lang.String.join;
import static java.util.Arrays.asList;

/**
 * Trace Utils
 *
 * @author yongj.zhuang
 */
public final class TraceUtils {

    public static final String USER_ID = "id";
    public static final String USERNAME = "username";
    public static final String USER_ROLE = "role";
    public static final String USER_NO = "userno";
    public static final String ROLE_NO = "roleno";
    public static final String SERVICES = "services";

    private TraceUtils() {

    }

    /** Put value for the key */
    public static void put(String key, String value) {
        Assert.notNull(key, "key == null");
        BaggageField bag = BaggageField.getByName(key);
        if (bag == null) {
            bag = BaggageField.create(key);
        }
        bag.updateValue(value);
    }

    /** Get value for the key */
    @Nullable
    public static String get(String key) {
        BaggageField bag = BaggageField.getByName(key);
        return bag != null ? bag.getValue() : null;
    }

    /** Get userNo from the trace info, if null throw exception */
    public static String requireUserNo() {
        final String userNo = get(USER_NO);
        AssertUtils.notNull(userNo, "Please login first");
        return userNo;
    }

    /** Put TUser to the trace info */
    public static void putTUser(TUser tu) {
        Assert.notNull(tu, "tUser == null");
        Assert.notNull(tu.getUsername(), "tUser.username == null");
        Assert.notNull(tu.getUserNo(), "tUser.userNo == null");
        Assert.notNull(tu.getRoleNo(), "tUser.roleNo == null");

        put(USER_ID, String.valueOf(tu.getUserId()));
        put(USERNAME, tu.getUsername());
        put(USER_NO, tu.getUserNo());
        put(ROLE_NO, tu.getRoleNo());
        put(USER_ROLE, tu.getRole());
        put(SERVICES, tu.getServices() != null ? join(",", tu.getServices()) : "");
    }

    /** Build TUser from the trace info, if it's absent, throws exception instead of returning null */
    public static TUser tUser() {
        Optional<TUser> tUser = nullableTUser();
        AssertUtils.isTrue(tUser.isPresent(), "Please login first");
        return tUser.get();
    }

    /** Check whether current request is authenticated by a user */
    public static boolean isLoggedIn() {
        return get(USER_ID) != null;
    }

    /** Build nullable TUser from the trace info */
    public static Optional<TUser> nullableTUser() {
        final String id = get(USER_ID);
        if (id == null)
            return Optional.empty();

        String ss = get(SERVICES);
        if (ss == null) ss = "";

        return Optional.of(TUser.builder()
                .userId(Integer.parseInt(id))
                .username(get(USERNAME))
                .role(get(USER_ROLE))
                .userNo(get(USER_NO))
                .roleNo(get(ROLE_NO))
                .services(asList(ss.split(",")))
                .build());
    }

    /**
     * Create new span before running the Runnable, and finish the span when Runnable returns
     */
    public static void runWithSpan(Runnable r, Tracer tracer) {
        final Span newSpan = tracer.nextSpan().start();
        runWithSpan(r, tracer, newSpan);
    }

    /**
     * Run with the given span, and finish the span when Runnable returns
     */
    public static void runWithSpan(Runnable r, Tracer tracer, Span nextSpan) {
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(nextSpan.start())) {
            r.run();
        }
    }

    /**
     * Get current Tracer and next Span, the tracer is nullable, and the next span may also be null if the tracer is
     * null already
     */
    public static CurrentTrace currentTrace() {
        final Tracer tracer = Tracing.currentTracer();
        final Span nextSpan = tracer != null ? tracer.nextSpan() : null;
        return new CurrentTrace(tracer, nextSpan);
    }

    /**
     * Wrapper for current Tracer and next Span
     */
    public static class CurrentTrace {
        public final Tracer tracer;
        public final Span nextSpan;

        public CurrentTrace(Tracer tracer, Span nextSpan) {
            this.tracer = tracer;
            this.nextSpan = nextSpan;
        }

        /**
         * Try run with the next Span, the next Span is null, then the runnable is executed without any tracing
         * information
         */
        public void tryRunWithSpan(Runnable r) {
            if (nextSpan != null) {
                TraceUtils.runWithSpan(r, tracer, nextSpan);
            } else {
                r.run();
            }
        }
    }
}

