package com.github.plugin.sysdict.service;

import com.github.plugin.sysdict.bean.DictDemo;
import com.github.plugin.sysdict.bean.SysDict;
import com.github.plugin.sysdict.common.dict.Key;

import java.util.List;
import java.util.Map;
/**
 * @author duhongming
 * @version 1.0
 * @description 字典服务
 * @date 2020/3/19 22:41
 */
public interface SysDictService {

    /**
     * 初始化字典容器
     */
    void init();

    /**
     * 查询所有字典
     * @return
     */
    List<SysDict> findAll();

    /**
     * 放入到Redis中
     * @param key 类型码
     * @param map k-v键值对
     */
    void putAll(String key, Map<String, String> map);

    /**
     * 通过Redis取
     * @param typeCode  类型码
     * @param dictValue value值
     * @return
     */
    String getByRedis(String typeCode, String dictValue);

    /**
     * 通过Database取
     * @param key 字典Key封装Bean
     * @return
     */
    String getByDatabase(Key key);

    /**
     * 演示demo
     * @return
     */
    DictDemo dictDemo();
    List<DictDemo> dictDemos();
}
