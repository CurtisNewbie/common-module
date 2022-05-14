package com.curtisnewbie.common.util;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Random Utils
 *
 * @author yongj.zhuang
 */
public final class RandomUtils {

    private static final SecureRandom secureRandom = new SecureRandom();
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyDDmmHHmmss");
    private static final int NUMERIC_LEN = 10;
    private static final int ALPHABET_LEN = 52;
    private static final int ALPHA_NUMERIC_LEN = NUMERIC_LEN + ALPHABET_LEN;
    /** 0-9 */
    private static final char[] numeric = new char[NUMERIC_LEN];
    /** a-zA-Z */
    private static final char[] alphabet = new char[ALPHABET_LEN];
    /** numeric + alphabet */
    private static final char[] alphaNumeric = new char[ALPHA_NUMERIC_LEN];

    static {
        // 0-9
        for (int i = 0; i < NUMERIC_LEN; i++) {
            numeric[i] = (char) ('0' + i);
        }
        shuffle(numeric, 3);

        // a-zA-Z
        for (int i = 0; i < 26; i++) {
            alphabet[i] = (char) ('a' + i);
            alphabet[i + 26] = (char) ('A' + i);
        }
        shuffle(alphabet, 3);

        System.arraycopy(numeric, 0, alphaNumeric, 0, NUMERIC_LEN);
        System.arraycopy(alphabet, 0, alphaNumeric, NUMERIC_LEN, ALPHABET_LEN);
        shuffle(alphaNumeric, 3);
    }

    private RandomUtils() {

    }

    /**
     * Opinionated way to build a sequence number
     * <p>
     * The format is: $prefix + timestamp() + randomNumeric($numericLen)
     */
    public static String sequence(String prefix, int numericLen) {
        return concat(prefix, timestamp(), randomNumeric(numericLen));
    }

    /**
     * Convenient method to build a sequence number
     */
    public static String concat(String prefix, String... segments) {
        if (segments.length == 0)
            return prefix;

        StringBuilder sb = new StringBuilder(prefix);
        for (String s : segments) {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * Timestamp in forms of 'yyyyDDmmHHmmss'
     */
    public static String timestamp() {
        return LocalDateTime.now().format(dateTimeFormatter);
    }

    /**
     * {@code System.currentTimeMillis() } as a string
     */
    public static String currentTimeMillis() {
        return System.currentTimeMillis() + "";
    }

    /**
     * Random combination of alphabet characters
     *
     * @param prefix prefix
     * @param len    length
     */
    public static String randomAlphabet(String prefix, int len) {
        Objects.requireNonNull(prefix);
        return prefix + randomAlphabet(len);
    }

    /**
     * Random combination of alphabet characters
     *
     * @param len length
     */
    public static String randomAlphabet(int len) {
        return random(false, true, len);
    }

    /**
     * Random combination of numeric and alphabet characters
     *
     * @param prefix prefix
     * @param len    length
     */
    public static String randomAlphaNumeric(String prefix, int len) {
        Objects.requireNonNull(prefix);
        return prefix + randomAlphaNumeric(len);
    }

    /**
     * Random combination of numeric and alphabet characters
     *
     * @param len length
     */
    public static String randomAlphaNumeric(int len) {
        return random(true, true, len);
    }

    /**
     * Random combination of numeric characters
     *
     * @param len length
     */
    public static String randomNumeric(int len) {
        return random(true, false, len);
    }

    /**
     * Random combination of numeric characters
     *
     * @param prefix prefix
     * @param len    length
     */
    public static String randomNumeric(String prefix, int len) {
        Objects.requireNonNull(prefix);
        return prefix + randomNumeric(len);
    }

    /**
     * Random combination of numeric and alphabet characters
     *
     * @param useNumeric  use numeric char
     * @param useAlphabet use alphabets char
     * @param len         length
     */
    public static String random(boolean useNumeric, boolean useAlphabet, int len) {
        if (!useNumeric && !useAlphabet)
            throw new IllegalArgumentException("Both numeric and alphabet characters are not used");
        if (len < 0)
            throw new IllegalArgumentException("Length < 0");

        if (len == 0) return "";

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char c;
            if (useNumeric && useAlphabet)
                c = selectRandom(alphaNumeric, ALPHA_NUMERIC_LEN);
            else if (useNumeric)
                c = selectRandom(numeric, NUMERIC_LEN);
            else
                c = selectRandom(alphabet, ALPHABET_LEN);

            sb.append(c);
        }
        return sb.toString();
    }

    /** Select a char from the given array randomly */
    private static char selectRandom(char[] chars, int len) {
        final int i = secureRandom.nextInt(len);
        return chars[i];
    }

    /** Shuffle a char array */
    public static void shuffle(char[] chars, int times) {
        for (int i = 0; i < times; i++) {
            shuffle(chars);
        }
    }

    /** Shuffle a char array */
    public static void shuffle(char[] chars) {
        for (int i = chars.length - 1; i > 0; i--) {
            final int j = secureRandom.nextInt(i + 1);
            // swap i and j
            char t = chars[i];
            chars[i] = chars[j];
            chars[j] = t;
        }
    }

}
