package com.curtisnewbie.common.util;

import lombok.*;
import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;

/**
 * Test of {@link ObjectDiff}
 *
 * @author yongj.zhuang
 */
@Slf4j
public class ObjectDiffTest {

    @Test
    public void should_identify_and_apply_diff() {
        Dummy origin = new Dummy();
        origin.setAge(13);
        origin.setName("fake dummy");
        origin.setType(Type.FAKE);
        origin.setDesc("yoyo");
        log.info("origin: {}", origin);

        Dummy updated = new Dummy();
        updated.setType(Type.REAL);
        updated.setName("real dummy");
        updated.setDesc("yoyo");
        log.info("updated: {}", updated);

        final ObjectDiff<Dummy> objectDiff = ObjectDiff.from(updated);
        objectDiff.diff(origin);

        Assertions.assertTrue(objectDiff.checkFieldDiff("type").isDifferent());
        Assertions.assertTrue(objectDiff.checkFieldDiff("name").isDifferent());
        Assertions.assertFalse(objectDiff.checkFieldDiff("age").isDifferent());
        Assertions.assertFalse(objectDiff.checkFieldDiff("desc").isDifferent());

        objectDiff.applyDiffTo(origin);
        log.info("applied: {}", origin);

        Assertions.assertEquals(13, origin.getAge());
        Assertions.assertEquals("real dummy", origin.getName());
        Assertions.assertEquals(Type.REAL, origin.getType());
    }

    @Data
    private static class Dummy {
        private Integer age;
        private String name;
        private Type type;
        private String desc;
    }

    private enum Type {
        REAL, FAKE;
    }

}
