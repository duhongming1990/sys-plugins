package com.github.plugin.sysdict.common.dict;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author duhongming
 * @version 1.0
 * @description 字典域标记转换注解
 * @date 2020/3/19 22:48
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DictField {

    /**
     * 类型码默认为成员变量名
     * @return
     */
    String typeCode() default "";

    /**
     * 新字段默认值
     * @return
     */
    String newFieldName() default "";

    /**
     * 自定义处理函数
     * @return
     */
    String funcation() default "";
}
