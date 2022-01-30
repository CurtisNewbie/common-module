package com.curtisnewbie.common.dao;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.springframework.util.Assert;

/**
 * is_del flag
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

    public static boolean isDeleted(IsDel isDel) {
        Assert.notNull(isDel, "isDel == null");
        return isDel == NORMAL;
    }
}


