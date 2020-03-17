package com.github.plugin.sysdict.utils;

import com.github.plugin.sysdict.bean.SysDict;
import com.github.plugin.sysdict.config.SpringContextHolder;
import com.github.plugin.sysdict.dao.SysDictMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.groupingBy;

/**
 * @Author duhongming
 * @Email 19919902414@189.cn
 * @Date 2019/4/17 20:13
 * 字典工具类
 */
@Slf4j
public class DictUtils {
    /**
     * 从Spring容器中获取SysDictMapper
     */
    private final SysDictMapper mapper = SpringContextHolder.getBean(SysDictMapper.class);

    /**
     * 目前字典数量：57
     */
    private final ConcurrentHashMap<String, List<SysDict>> DICT_DATA_CONTAINER = new ConcurrentHashMap(128);

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
        List<SysDict> entities = mapper.selectDictAll();
        Map<String, List<SysDict>> dictsGroupByNameCode = entities.stream().collect(groupingBy(SysDict::getDictionaryNameCode));
        DICT_DATA_CONTAINER.putAll(dictsGroupByNameCode);
    }

    /**
     * 运行时,每10毫秒更新一个字典
     *
     * @return
     */
    public Boolean refresh() {
        try {
            Set<String> sets = new HashSet<>();
            List<SysDict> entities = mapper.selectDictAll();
            for (SysDict entity : entities) {
                sets.add(entity.getDictionaryNameCode());
            }
            for (String type : sets) {
                TimeUnit.MILLISECONDS.sleep(10);
                recacheDictData(type);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * redis订阅消息时，更新特定字典
     *
     * @param type
     * @return
     */
    public Boolean recacheDictData(String type) {
        try {
            List<SysDict> dictionaryInfoEntities = mapper.selectDictByNameCode(type);
            DICT_DATA_CONTAINER.put(type, dictionaryInfoEntities);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * dictionary_name_code 字典类型
     *
     * @param type
     * @return
     */
    public List<SysDict> getDictList(String type) {
        return DICT_DATA_CONTAINER.get(type);
    }

    /**
     * @param code           字典key
     * @param type           字典类型
     * @param defaultContent 字典不存在，默认value值
     * @return
     */
    public String getDictValue(String code, String type, String defaultContent) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(code)) {
            List<SysDict> dicts = getDictList(type);
            return dicts.stream().filter((SysDict d) -> type.equals(d.getDictionaryNameCode()) && code.equals(d.getDictionaryContentCode()))
                    .findFirst()
                    .map(SysDict::getDictionaryContent)
                    .orElse("未知Value");
        }
        return defaultContent;
    }

    /**
     * @param value       字典value
     * @param type        字典类型
     * @param defaultCode 字典不存在，默认code值
     * @return
     */
    public String getDictCode(String value, String type, String defaultCode) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)) {
            List<SysDict> dicts = getDictList(type);
            return dicts.stream()
                    .filter((SysDict d) -> type.equals(d.getDictionaryNameCode()) && value.equals(d.getDictionaryContent()))
                    .findFirst()
                    .map(SysDict::getDictionaryContentCode)
                    .orElse("未知Key");
        }
        return defaultCode;
    }

    public Map<String,List<SysDict>> getDictDataContainer(){
        return DICT_DATA_CONTAINER;
    }

}
