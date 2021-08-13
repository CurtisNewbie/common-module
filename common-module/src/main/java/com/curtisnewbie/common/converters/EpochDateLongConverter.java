package com.curtisnewbie.common.converters;

import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * Custom Date -> Long (Epoch time) Converters
 *
 * @author yongjie.zhuang
 */
public class EpochDateLongConverter implements Converter<Date, Long> {

    @Override
    public Long convert(Date d) {
        if (d == null)
            return null;

        return d.getTime();
    }
}
