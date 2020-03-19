package com.github.plugin.sysdict.common.annotation;
/**
 * @author duhongming
 * @version 1.0
 * @description 字典标记转换注解
 * @date 2020/3/19 22:48
 */
public @interface DictSign {

    /**
     * 类型码
     * @return
     */
    String typeCode() default "";

    /**
     * 新字段默认值
     * @return
     */
    String newFieldName() default "";

    /**
     * 处理函数
     * @return
     */
    String funcation() default "";
}
