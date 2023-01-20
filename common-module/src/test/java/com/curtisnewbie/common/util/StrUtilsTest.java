package com.curtisnewbie.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StrUtilsTest {

    @Test
    public void should_shrink() {
        Assertions.assertNull(StrUtils.shrink(null, 30));
        Assertions.assertEquals("123123", StrUtils.shrink("123123", 6));
        Assertions.assertEquals("123", StrUtils.shrink("123123", 3));
        Assertions.assertEquals("123123", StrUtils.shrink("123123", -1));
    }

}