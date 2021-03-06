package com.curtisnewbie.common.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/*
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT "primary key",

  ...

  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'when the record is created',
  create_by VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'who created this record',
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'when the record is updated',
  update_by VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'who updated this record',
  is_del TINYINT NOT NULL DEFAULT '0' COMMENT '0-normal, 1-deleted',

 */

/**
 * Base class for DAO object, contains commonly used table field
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

    /**
     * Set createTime to now if absent
     *
     * @deprecated use {@link EnableMBTraceInterceptor} instead
     */
    @Deprecated
    public void setCreateTimeIfAbsent() {
        if (createTime == null)
            createTime = LocalDateTime.now();
    }

    /**
     * Set updateTime to now if absent
     *
     * @deprecated use {@link EnableMBTraceInterceptor} instead
     */
    @Deprecated
    public void setUpdateTimeIfAbsent() {
        if (updateTime == null)
            updateTime = LocalDateTime.now();
    }

    /**
     * set createBy and createTime (if absent)
     *
     * @deprecated use {@link EnableMBTraceInterceptor} instead
     */
    @Deprecated
    public void onCreate(String createdBy) {
        this.setCreateBy(createdBy);
        setCreateTimeIfAbsent();
    }

    /**
     * set updateBy and updateTime (if absent)
     *
     * @deprecated use {@link EnableMBTraceInterceptor} instead
     */
    @Deprecated
    public void onUpdate(String updatedBy) {
        this.setUpdateBy(updatedBy);
        setUpdateTimeIfAbsent();
    }
}

