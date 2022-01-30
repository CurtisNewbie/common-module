package com.curtisnewbie.common.dao;

import com.baomidou.mybatisplus.annotation.TableField;

import java.time.LocalDateTime;

/**
 * Base class for DAO object, contains commonly used table field
 *
 * @author yongjie.zhuang
 */
public class DaoSkeleton {

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
}
