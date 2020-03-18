package com.github.plugin.sysdict.common.annotation;

public @interface DictSign {
    /**
     * 类型码
     * @return
     */
    String typeCode() default "";

    /**
     * 处理函数
     * @return
     */
    String funcation() default "";
}
