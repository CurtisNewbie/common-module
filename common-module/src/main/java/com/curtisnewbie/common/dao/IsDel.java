package com.curtisnewbie.common.dao;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.springframework.util.Assert;

/**
 * is_del flag
 * <p>
 * Make sure you have following configuration:
 * </p>
 * <pre>
 * mybatis-plus:
 *     typeEnumsPackage:  com.curtisnewbie.common.dao
 * </pre>
 *
 * @author yongjie.zhuang
 * @see IsDelSerializer
 * @see IsDelDeserializer
 */
@Getter
public enum IsDel {

    /** normal */
    NORMAL(0),

    /** isDeleted */
    DELETED(1);

    @JsonValue
    @EnumValue
    private final int value;

    IsDel(int value) {
        this.value = value;
    }

    public boolean isDeleted() {
        return this == DELETED;
    }
}


