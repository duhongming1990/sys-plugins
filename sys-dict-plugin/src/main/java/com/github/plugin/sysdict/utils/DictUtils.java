package com.github.plugin.sysdict.utils;

import com.github.plugin.sysdict.bean.SysDict;
import com.github.plugin.sysdict.config.SpringContextHolder;
import com.github.plugin.sysdict.dao.SysDictMapper;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Map;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2019/4/17 20:13
 * 字典工具类
 */
@Getter
@Setter
@Accessors(fluent = true)
@Slf4j
public class DictUtils {
    public static final String EN = "en";
    private static final String EN_KEY = "dict:${k}:en";

    public static final String CH = "ch";
    private static final String CH_KEY = "dict:${k}:ch";

    private String language = CH;

    /**
     * 从Spring容器中获取SysDictMapper、StringRedisTemplate
     */
    private final SysDictMapper sysDictMapper = SpringContextHolder.getBean(SysDictMapper.class);
    private final StringRedisTemplate redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);

    //中文字典
    private final Table<String, String, String> chDicts = HashBasedTable.create();
    //英文字典
    private final Table<String, String, String> enDicts = HashBasedTable.create();


    /**
     * 单例使用该工具类
     */
    public static final DictUtils me = new DictUtils();

    private DictUtils() {
        super();
        //初始化字典容器
        init();
    }

    /**
     * 初始化，请求一次数据库
     */
    private void init() {

        //从DB获取源数据
        List<SysDict> sysDicts = sysDictMapper.findAll();

        //初始化Table对象
        for (SysDict sysDict : sysDicts) {
            chDicts.put(sysDict.getTypeCode(), sysDict.getDictValue(), sysDict.getDictName());
            enDicts.put(sysDict.getTypeCode(), sysDict.getDictValue(), sysDict.getDictEnName());
        }

        //初始化Redis对象
        Map<String, Map<String, String>> chMaps = chDicts.rowMap();
        Map<String, Map<String, String>> enMaps = enDicts.rowMap();
        chMaps.forEach((k, v) -> redisTemplate.opsForHash().putAll(CH_KEY.replace("${k}", k), v));
        enMaps.forEach((k, v) -> redisTemplate.opsForHash().putAll(EN_KEY.replace("${k}", k), v));
    }

    /**
     * @param typeCode       类型码
     * @param dictValue      value值
     * @param defaultContent 默认content
     * @return
     */
    public String getDictName(String typeCode, String dictValue, String defaultContent) {
        if (StringUtils.isNotBlank(typeCode) && StringUtils.isNotBlank(dictValue)) {
            if (StringUtils.endsWith(CH, language)) {
                return chDicts.get(typeCode, dictValue);
            } else if (StringUtils.endsWith(EN, language)) {
                return enDicts.get(typeCode, dictValue);
            }
        }
        return defaultContent;
    }
}
