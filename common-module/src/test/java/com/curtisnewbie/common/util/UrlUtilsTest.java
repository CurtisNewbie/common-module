package com.curtisnewbie.common.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test for {@link UrlUtils}
 *
 * @author yongj.zhuang
 */
public class UrlUtilsTest {

    @Test
    public void should_get_segment_at_index() {
        final String path = "/auth-service/open/api/yo/";
        assertEquals("auth-service", UrlUtils.segment(0, path));
        assertEquals("open", UrlUtils.segment(1, path));
        assertEquals("api", UrlUtils.segment(2, path));
        assertNull(UrlUtils.segment(4, path));
    }
}
