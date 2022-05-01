package com.curtisnewbie.common.util;

import com.baomidou.mybatisplus.core.conditions.*;
import com.baomidou.mybatisplus.core.mapper.*;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

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
     *
     * @see #updateEq(SFunction, Object, Object)
     * @see #updateOneEq(SFunction, Object, Object)
     */
    @Deprecated
    default int updateBy(String column, Object value, T entity) {
        return MapperUtils.updateBy(column, value, this, entity);
    }

    /**
     * Update by a column that equals the value
     */
    default int updateEq(SFunction<T, ?> col, Object value, T entity) {
        return MapperUtils.updateEq(col, value, this, entity);
    }

    /**
     * Update only one record by a column that equals the value
     */
    default int updateOneEq(SFunction<T, ?> column, Object value, T entity) {
        return MapperUtils.updateOneEq(column, value, this, entity);
    }

    /**
     * Select one by a column
     *
     * @see #selectOneEq(SFunction, Object, BaseMapper)
     */
    @Deprecated
    default T selectOneBy(String column, Object value) {
        return MapperUtils.selectOneBy(column, value, this);
    }

    /**
     * Select one by a column that equals the value
     */
    default T selectOneEq(SFunction<T, ?> column, Object value, BaseMapper<T> baseMapper) {
        return MapperUtils.selectOneEq(column, value, baseMapper);
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
