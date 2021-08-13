package com.curtisnewbie.common.converters;

import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * Custom Long (epoch time) -> Date converter
 *
 * @author yongjie.zhuang
 */
public class EpochLongDateConverter implements Converter<Long, Date> {

    @Override
    public Date convert(Long epochTime) {
        if (epochTime == null)
            return null;

        return new Date(epochTime);
    }

}
