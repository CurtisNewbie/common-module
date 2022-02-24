package com.curtisnewbie.common.util;

import com.baomidou.mybatisplus.core.conditions.*;
import com.baomidou.mybatisplus.core.mapper.*;

import java.util.*;
import java.util.function.Function;

/**
 * Enhanced Mapper
 *
 * @author yongj.zhuang
 */
public interface EnhancedMapper<T> extends BaseMapper<T> {

    /**
     * Update by a column
     */
    default int updateBy(String column, Object value, T entity) {
        return MapperUtils.updateBy(column, value, this, entity);
    }

    /**
     * Select one by a column
     */
    default T selectOneBy(String column, Object value) {
        return MapperUtils.selectOneBy(column, value, this);
    }

    /**
     * Select a list of entities and convert it
     */
    default <V> List<V> selectListAndConvert(Wrapper<T> wrapper, Function<T, V> converter) {
        return MapperUtils.selectListAndConvert(wrapper, this, converter);
    }

    /**
     * Select one and convert it, if the selected record is null, a null value is returned directly without conversion
     */
    default <V> V selectAndConvert(Wrapper<T> wrapper, Function<T, V> converter) {
        return MapperUtils.selectAndConvert(wrapper, this, converter);
    }
}
