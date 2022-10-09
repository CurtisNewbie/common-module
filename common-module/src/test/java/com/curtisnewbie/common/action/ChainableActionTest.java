package com.curtisnewbie.common.action;

import com.curtisnewbie.common.data.*;
import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;

/**
 * @author yongj.zhuang
 */
@Slf4j
public class ChainableActionTest {

    @Test
    public void should_start_chainable_action() {
        IntWrapper iw = new IntWrapper(0);


        Exception ex = null;
        try {
            ChainableAction.buildFirst((t) -> {
                        iw.incr();
                        return 1; // t=1
                    }).then(t -> {
                        iw.incr();
                        return t + 1;
                    }) // t=2
                    .then(t -> {
                        log.info("step: {}, t: {}, throwing exception", iw.incr(), t);
                        throw new IllegalStateException("unknown error");
                    })
                    .compensate((e, prev) -> {
                        log.info("step: {}, e: {}, prev: {}, compensating, return previous value", iw.incr(), e, prev);
                        return new Compensation<>(true, prev - 1);
                    })  // t=1
                    .then(t -> {
                        log.info("step: {}, t: {}", iw.incr(), t);
                        Assertions.assertEquals(1, t);
                        return t;
                    })
                    .then(t -> {
                        log.info("step: {}, t: {}, throwing uncompensated exception", iw.incr(), t);
                        throw new IllegalStateException("Unrecoverable exception without compensation");
                    })
                    .then(t -> {
                        // this will not run
                        iw.incr();
                        return null;
                    })
                    .startFirst();
        } catch (Exception e) {
            ex = e;
            log.info("Exception caught (expected behaviour)", e);
        }
        Assertions.assertNotNull(ex);
        Assertions.assertEquals(6, iw.getValue());
    }
}
