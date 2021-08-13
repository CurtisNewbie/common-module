package com.curtisnewbie.common.converters;

import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * Custom Date -> String (Epoch time) Converters
 *
 * @author yongjie.zhuang
 */
public class EpochDateStringConverter implements Converter<Date, String> {

    @Override
    public String convert(Date d) {
        if (d == null)
            return null;

        return String.valueOf(d.getTime());
    }
}
