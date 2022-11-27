package com.curtisnewbie.common.formatters;

import com.curtisnewbie.common.util.DateUtils;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Epoch-based formatter for LocalDateTime
 *
 * @author yongj.zhuang
 */
public class LocalDateTimeEpochFormatter implements Formatter<LocalDateTime> {

    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        boolean isTimestamp = true;
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) {
                isTimestamp = false;
                break;
            }
        }

        if (isTimestamp) {
            return LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(text)), TimeZone.getDefault().toZoneId());
        }
        return LocalDateTime.parse(text, DateTimeFormatter.ISO_LOCAL_DATE_TIME); // fallback to ISO format
    }

    @Override
    public String print(LocalDateTime ldt, Locale locale) {
        return DateUtils.getEpochTime(ldt) + "";
    }
}
