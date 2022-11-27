package com.curtisnewbie.common.formatters;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;


/**
 * Epoch-based formatter for Date
 *
 * @author yongj.zhuang
 */
public class DateEpochFormatter implements Formatter<Date> {

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        return new Date(Long.parseLong(text));
    }

    @Override
    public String print(Date d, Locale locale) {
        return d.getTime() + "";
    }
}
