package com.curtisnewbie.common.util;

import com.baomidou.mybatisplus.core.conditions.*;
import com.baomidou.mybatisplus.core.conditions.query.*;
import com.baomidou.mybatisplus.core.mapper.*;
import com.curtisnewbie.common.dao.*;
import org.springframework.util.*;

import java.util.function.*;

/**
 * Utils for Mapper
 *
 * @author yongj.zhuang
 */
public final class MapperUtils {
    private MapperUtils() {
    }

    /**
     * Select one by a column
     */
    public static <T> T selectOneBy(String column, Object value, BaseMapper<T> baseMapper) {
        final QueryWrapper<T> w = new QueryWrapper<T>()
                .eq(column, value);
        return baseMapper.selectOne(w);
    }

    /**
     * Select one and convert it, if the selected record is null, a null value is returned directly without conversion
     */
    public static <T, V> V selectAndConvert(Wrapper<T> wrapper, BaseMapper<T> baseMapper, Function<T, V> converter) {
        Assert.notNull(wrapper, "wrapper == null");
        Assert.notNull(converter, "converter == null");
        T t = baseMapper.selectOne(wrapper);
        if (t == null)
            return null;
        return converter.apply(t);
    }

    /**
     * Select a record and get id from it
     */
    public static <T extends DaoSkeleton> Integer selectAndMapId(Wrapper<T> wrapper, BaseMapper<T> baseMapper) {
        Assert.notNull(wrapper, "wrapper == null");

        T t = baseMapper.selectOne(wrapper);
        if (t == null)
            return null;

        return t.getId();
    }
}
