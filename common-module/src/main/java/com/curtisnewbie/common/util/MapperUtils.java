package com.curtisnewbie.common.util;

import com.baomidou.mybatisplus.core.conditions.*;
import com.baomidou.mybatisplus.core.conditions.query.*;
import com.baomidou.mybatisplus.core.conditions.update.*;
import com.baomidou.mybatisplus.core.mapper.*;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.curtisnewbie.common.dao.*;
import org.springframework.util.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Utils for Mapper
 *
 * @author yongj.zhuang
 */
public final class MapperUtils {
    private MapperUtils() {
    }

    /**
     * Update by a column
     */
    public static <T> int updateBy(String column, Object value, BaseMapper<T> baseMapper, T entity) {
        final QueryWrapper<T> w = new QueryWrapper<T>()
                .eq(column, value);
        return baseMapper.update(entity, w);
    }

    /**
     * Update only one record by a column that equals the value
     */
    public static <T> int updateOneEq(SFunction<T, ?> col, Object value, BaseMapper<T> baseMapper, T entity) {
        return baseMapper.update(entity,
                new LambdaQueryWrapper<T>()
                        .eq(col, value)
                        .last("limit 1")
        );
    }

    /**
     * Update by a column that equals the value
     */
    public static <T> int updateEq(SFunction<T, ?> col, Object value, BaseMapper<T> baseMapper, T entity) {
        return baseMapper.update(entity,
                new LambdaQueryWrapper<T>()
                        .eq(col, value)
        );
    }

    /**
     * Select one by a column that equals the value
     */
    public static <T> T selectOneEq(SFunction<T, ?> column, Object value, BaseMapper<T> baseMapper) {
        return baseMapper.selectOne(
                new LambdaQueryWrapper<T>()
                        .eq(column, value)
        );
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
     * Select a list of entities and convert it
     */
    public static <T, V> List<V> selectListAndConvert(Wrapper<T> wrapper, BaseMapper<T> baseMapper, Function<T, V> converter) {
        Assert.notNull(converter, "converter == null");
        List<T> tl = baseMapper.selectList(wrapper);
        return tl.stream().map(converter).collect(Collectors.toList());
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

    /**
     * IN condition
     */
    public static <T> LambdaQueryWrapper<T> in(SFunction<T, ?> inFunc, Collection<?> c) {
        return new LambdaQueryWrapper<T>().in(inFunc, c);
    }

    /**
     * Equals condition
     */
    public static <T, V> LambdaQueryWrapper<T> eq(SFunction<T, ?> eqFunc, V val) {
        return new LambdaQueryWrapper<T>().eq(eqFunc, val);
    }

    /**
     * Not in condition
     */
    public static <T, V> LambdaQueryWrapper<T> notIn(SFunction<T, ?> notInFunc, Collection<V> coll) {
        return new LambdaQueryWrapper<T>().notIn(notInFunc, coll);
    }

    /**
     * Less than condition
     */
    public static <T, V> LambdaQueryWrapper<T> lt(SFunction<T, ?> ltFunc, V val) {
        return new LambdaQueryWrapper<T>().lt(ltFunc, val);
    }

    /**
     * Less than or equal condition
     */
    public static <T, V> LambdaQueryWrapper<T> le(SFunction<T, ?> leFunc, V val) {
        return new LambdaQueryWrapper<T>().le(leFunc, val);
    }

    /**
     * Greater than or equal condition
     */
    public static <T, V> LambdaQueryWrapper<T> ge(SFunction<T, ?> geFunc, V val) {
        return new LambdaQueryWrapper<T>().ge(geFunc, val);
    }

    /**
     * Greater than condition
     */
    public static <T, V> LambdaQueryWrapper<T> gt(SFunction<T, ?> gtFunc, V val) {
        return new LambdaQueryWrapper<T>().gt(gtFunc, val);
    }

    /**
     * SELECT statement
     */
    @SafeVarargs
    public static <T> LambdaQueryWrapper<T> select(SFunction<T, ?>... columns) {
        return new LambdaQueryWrapper<T>().select(columns);
    }

    /**
     * Not Equals condition
     */
    public static <T, V> LambdaQueryWrapper<T> ne(SFunction<T, V> neFunc, V val) {
        return new LambdaQueryWrapper<T>().ne(neFunc, val);
    }

    /**
     * SET statement
     */
    public static <T, V> LambdaUpdateWrapper<T> set(SFunction<T, V> setFunc, V val) {
        return new LambdaUpdateWrapper<T>().set(setFunc, val);
    }

    /**
     * Limit offset, limit statement
     */
    public static <T> LambdaQueryWrapper<T> limit(long offset, long limit) {
        return new LambdaQueryWrapper<T>().last(PagingUtil.limit(offset, limit));
    }

}
