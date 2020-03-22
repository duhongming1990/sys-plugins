package com.github.plugin.sysdict.common.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author duhongming
 * @version 1.0
 * @description 字典Key封装Bean
 * @date 2020/3/19 22:44
 */
@Getter
@Setter
@NoArgsConstructor(staticName = "of")
@Accessors(fluent = true)
public class Key {

    public static final String REDIS_KEY = "dict:${k}:";

    /**
     * 国际化定义
     */
    public static final String EN = "en";
    public static final String CH = "ch";

    /**
     * 类型码
     */
    private String typeCode;

    /**
     * value值
     */
    private String dictValue;

    /**
     * 国际化语言
     */
    private String language = CH;
}
