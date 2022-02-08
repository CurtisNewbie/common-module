package com.curtisnewbie.common.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * Base class for DAO object, contains commonly used table field
 * <pre>
 * {@code
 *
 * id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT "primary key",
 *
 * ...
 *
 * create_time DATETIME DEFAULT NOW() COMMENT 'when the record is created',
 * create_by VARCHAR(255) COMMENT 'who created this record',
 * update_time DATETIME COMMENT 'when the record is updated',
 * update_by VARCHAR(255) COMMENT 'who updated this record'
 * }
 * </pre>
 *
 * @author yongjie.zhuang
 */
@Data
public class DaoSkeleton {

    /** primary key */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** when the record is created */
    @TableField("create_time")
    private LocalDateTime createTime;

    /** who created this record */
    @TableField("create_by")
    private String createBy;

    /** when the record is updated */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /** who updated this record */
    @TableField("update_by")
    private String updateBy;

    /** whether current record is deleted */
    @TableField("is_del")
    private IsDel isDel;

    /**
     * Check if current record is deleted
     *
     * @throws IllegalArgumentException if {@code isDel == null}
     */
    public boolean isDeleted() {
        Assert.notNull(isDel, "isDel == null");
        return isDel.isDeleted();
    }
}
