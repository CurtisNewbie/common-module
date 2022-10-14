package com.curtisnewbie.common.util;

import com.baomidou.mybatisplus.core.conditions.*;
import com.baomidou.mybatisplus.core.mapper.*;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.*;
import com.curtisnewbie.common.vo.*;

import java.util.*;
import java.util.function.Function;

/**
 * Enhanced Mapper
 *
 * @author yongj.zhuang
 */
public interface EnhancedMapper<T> extends BaseMapper<T> {

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
     * Select one by a column that equals the value (with limit 1)
     */
    default T selectOneEq(SFunction<T, ?> column, Object value) {
        return this.selectOne(MapperUtils.eq(column, value).last("limit 1"));
    }

    /**
     * Select one by a column that equals the value
     */
    default List<T> selectEq(SFunction<T, ?> column, Object value) {
        return this.selectList(MapperUtils.eq(column, value));
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

    /**
     * Select page and convert
     */
    default <T, V> PageableList<V> selectPageAndConvert(Wrapper<T> wrapper, Page p, Function<T, V> converter) {
        Wrapper gw = wrapper;
        final Page rp = this.selectPage(p, gw);
        return PagingUtil.toPageableList(rp, converter);
    }

}
