package com.curtisnewbie.common.util;

import java.security.*;
import java.util.*;

/**
 * Random token generator
 *
 * @author yongj.zhuang
 */
public class RandTokenGenerator {

    private static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

    private final SecureRandom random = new SecureRandom();
    private final int byteLen;

    public RandTokenGenerator() {
        this(30);
    }

    public RandTokenGenerator(int byteLen) {
        this.byteLen = byteLen;
    }

    /**
     * Generate random token
     */
    public String generate() {
        byte[] buffer = new byte[byteLen];
        random.nextBytes(buffer);
        return encoder.encodeToString(buffer);
    }
}
