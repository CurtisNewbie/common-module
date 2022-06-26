package com.curtisnewbie.common.annotation;

import com.fasterxml.jackson.annotation.*;

import java.lang.annotation.*;

/**
 * Date/LocalDateTime with format 'dd/MM/yyyy HH:mm'
 *
 * @author yongj.zhuang
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
public @interface JsonDateTimeFormat {
}
