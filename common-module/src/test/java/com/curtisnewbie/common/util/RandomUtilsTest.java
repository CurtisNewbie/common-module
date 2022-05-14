package com.curtisnewbie.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.curtisnewbie.common.util.RandomUtils.*;

/**
 * @author yongj.zhuang
 */
@Slf4j
public class RandomUtilsTest {

    @Test
    public void should_generate_random() {
        String random = randomAlphaNumeric(15);
        Assertions.assertNotNull(random);
        log.info("alphaNumeric: {}", random);

        random = randomAlphabet(15);
        Assertions.assertNotNull(random);
        log.info("alphabet: {}", random);

        random = randomNumeric(15);
        Assertions.assertNotNull(random);
        log.info("numeric: {}", random);

        random = randomAlphaNumeric("PRE", 15);
        Assertions.assertNotNull(random);
        log.info("alphaNumeric: {}", random);

        random = randomAlphabet("PRE", 15);
        Assertions.assertNotNull(random);
        log.info("alphabet: {}", random);

        random = randomNumeric("PRE", 15);
        Assertions.assertNotNull(random);
        log.info("numeric: {}", random);

        final String seq = sequence("OD", 7);
        Assertions.assertNotNull(seq);
        log.info("sequence: {}", seq);
    }

}
