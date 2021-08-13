package com.curtisnewbie.common.converters;

import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * Custom String (Epoch time) -> Date converter
 *
 * @author yongjie.zhuang
 */
public class EpochStringDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String str) {
        if (str == null)
            return null;

        return new Date(Long.parseLong(str));
    }

}
