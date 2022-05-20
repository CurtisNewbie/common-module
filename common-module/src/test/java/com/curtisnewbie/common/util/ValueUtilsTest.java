package com.curtisnewbie.common.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.curtisnewbie.common.util.ValueUtils.*;

/**
 * @author yongj.zhuang
 */
@Slf4j
public class ValueUtilsTest {

    @Test
    public void should_set_if_null_or_true() {
        Dummy d1 = new Dummy();
        d1.setDegree("d1-degree");
        d1.setName("d1-name");
        d1.setPosition("d1-pos");

        Dummy d2 = new Dummy();
        d2.setName("d2-name");
        setIfNull(d2::setDegree, d2.getDegree(), "none");
        setIfValNull(d2::setDegree, d2.getDegree(), d1::getDegree);
        setIfValNull(d2::setName, d2.getName(), d1::getName);
        setIfTrue(d2::setPosition, d2.getPosition() == null, d1::getPosition);

        Assertions.assertNotNull(d2.getDegree());
        Assertions.assertEquals("none", d2.getDegree());
        Assertions.assertNotNull(d2.getName());
        Assertions.assertEquals("d2-name", d2.getName());
        Assertions.assertNotNull(d2.getPosition());
        log.info("d2: {}", d2);
    }

    @Data
    private static class Dummy {

        private String name;
        private String degree;
        private String position;

    }
}
