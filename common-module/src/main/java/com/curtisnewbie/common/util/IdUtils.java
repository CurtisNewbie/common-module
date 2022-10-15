package com.curtisnewbie.common.util;

/**
 * Utils for generating id
 *
 * @author yongj.zhuang
 */
public enum IdUtils {

    INSTANCE;

    /** 1 January 2022 00:00:00 */
    private static final long START_TIME = 1640995200000L;
    /** Machine Code for current instance */
    private static final String MACHINE_CODE = RandomUtils.randomNumeric(6);
    /** Max number of bits for sequenceNo (0~16383) */
    private static final long MAX_SEQNO_BITS = 14;

    /** Mask for seqNo, 1|1 -> 1|100000000000000 -> 0|011111111111111 */
    private static final long SEQNO_MASK = ~(-1L << MAX_SEQNO_BITS);

    /** previous timestamp */
    private long timestamp = 0L;
    /** previous seqNo */
    private long seqNo = 0L;

    IdUtils() {
    }

    /**
     * Generate id (at most 25 characters) with prefix
     *
     * @see #gen()
     */
    public static String gen(String prefix) {
        return prefix + gen();
    }

    /**
     * Generate Id
     * <p>
     * The id consists of [64 bits long] + [6 digits machine_code]
     * <p>
     * The 64 bits long consists of: [sign bit (1 bit)] + [timestamp (49 bits, ~1487.583 years)] + [sequenceNo (14 bits,
     * 0~16383)]
     * </p>
     * <p>
     * The max value of Long is 9223372036854775807, which is a string with 19 characters, so the generated id will be
     * of at most 25 characters
     * <p>
     */
    public static String gen() {
        return INSTANCE.generate();
    }

    private synchronized String generate() {
        final long currTimestamp = System.currentTimeMillis();
        if (currTimestamp == timestamp) {
            this.seqNo = (this.seqNo + 1) & SEQNO_MASK;

            // we just assume that the concurrency wouldn't be so high, that it reaches 16383 within a millisecond
            // but if that happens, we will have to wait for the next timestamp
            if (seqNo == 0) {
                while (System.currentTimeMillis() == timestamp) ; // keep looping until next timestamp
                return generate();
            }
        } else {
            // reset the sequenceNo
            this.seqNo = 0L;
            timestamp = currTimestamp;
        }
        return (((currTimestamp - START_TIME) << MAX_SEQNO_BITS) | this.seqNo) + MACHINE_CODE;
    }

}
