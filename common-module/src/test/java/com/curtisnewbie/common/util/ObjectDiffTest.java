package com.curtisnewbie.common.util;

import lombok.*;
import org.junit.jupiter.api.*;

/**
 * Test of {@link ObjectDiff}
 *
 * @author yongj.zhuang
 */
public class ObjectDiffTest {

    @Test
    public void should_identify_and_apply_diff() {
        Dummy origin = new Dummy();
        origin.setAge(13);
        origin.setName("fake dummy");
        origin.setType(Type.FAKE);

        Dummy updated = new Dummy();
        updated.setType(Type.REAL);
        updated.setName("real dummy");

        ObjectDiff.from(updated).applyDiffTo(origin);

        Assertions.assertEquals(origin.getAge(), 13);
        Assertions.assertEquals(origin.getName(), "real dummy");
        Assertions.assertEquals(origin.getType(), Type.REAL);
    }

    @Data
    private static class Dummy {
        private Integer age;
        private String name;
        private Type type;
    }

    private enum Type {
        REAL, FAKE;
    }

}
