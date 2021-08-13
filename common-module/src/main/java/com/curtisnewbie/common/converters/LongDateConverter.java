package com.curtisnewbie.common.converters;

import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * Custom long -> Date converter
 *
 * @author yongjie.zhuang
 */
public class LongDateConverter implements Converter<Long, Date> {

    @Override
    public Date convert(Long epochTime) {
        if (epochTime == null)
            return null;

        return new Date(epochTime);
    }

}
