package com.github.plugin.sysdict.common.dict;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author duhongming
 * @version 1.0
 * @description 字典域标记注解
 * @date 2020/3/19 22:48
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DictSign {

}
