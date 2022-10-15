package com.curtisnewbie.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class IdUtilsTest {

    @Test
    public void should_generate_id() {
        Set<String> dupSet = new HashSet<>();
        for (int i = 0; i < 100000; i++) {
            final String id = IdUtils.gen();
            log.info("[{}] Id: {}, len: {}", i, id, id.length());
            Assertions.assertTrue(id.length() <= 25);
            Assertions.assertTrue(dupSet.add(id), String.format("Found duplicated id: %s", id));
        }
    }

}