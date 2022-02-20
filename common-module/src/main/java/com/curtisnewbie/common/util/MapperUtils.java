package com.curtisnewbie.common.util;

import com.baomidou.mybatisplus.core.conditions.query.*;
import com.baomidou.mybatisplus.core.mapper.*;

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
}
