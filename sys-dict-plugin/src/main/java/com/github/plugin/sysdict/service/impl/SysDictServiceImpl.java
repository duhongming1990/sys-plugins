package com.github.plugin.sysdict.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.plugin.sysdict.bean.DictDemo;
import com.github.plugin.sysdict.bean.SysDict;
import com.github.plugin.sysdict.common.dict.DictSign;
import com.github.plugin.sysdict.common.dict.Key;
import com.github.plugin.sysdict.dao.SysDictMapper;
import com.github.plugin.sysdict.service.SysDictService;
import com.google.common.collect.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author duhongming
 * @version 1.0
 * @description 字典服务实现
 * @date 2020/3/19 22:44
 */
@Service("sysDictService")
public class SysDictServiceImpl implements SysDictService {

    @Resource
    private SysDictMapper sysDictMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void init() {
        //中文字典
        Table<String, String, String> chDicts = HashBasedTable.create();
        //英文字典
        Table<String, String, String> enDicts = HashBasedTable.create();

        //从DB获取源数据
        List<SysDict> sysDicts = findAll();

        //初始化Table对象
        for (SysDict sysDict : sysDicts) {
            chDicts.put(sysDict.getTypeCode(), sysDict.getDictValue(), sysDict.getDictName());
            enDicts.put(sysDict.getTypeCode(), sysDict.getDictValue(), sysDict.getDictEnName());
        }

        Map<String, Map<String, String>> chMaps = chDicts.rowMap();
        Map<String, Map<String, String>> enMaps = enDicts.rowMap();

        //初始化到Json文件
        try {
            JSON.writeJSONString(new FileOutputStream(new File("dictionay-ch.json")),chMaps);
            JSON.writeJSONString(new FileOutputStream(new File("dictionay-en.json")),chMaps);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //初始化Redis对象
        chMaps.forEach((k, v) -> putAll(Key.REDIS_KEY.replace("${k}", k) + Key.CH, v));
        enMaps.forEach((k, v) -> putAll(Key.REDIS_KEY.replace("${k}", k) + Key.EN, v));
    }

    @Override
    public List<SysDict> findAll() {
        return sysDictMapper.findAll();
    }

    @Override
    public void putAll(String key, Map<String, String> map) {
        BiMap<String,String> biMap = HashBiMap.create(map);
        stringRedisTemplate.opsForHash().putAll(key, biMap);
        //反转key，用于导入Excel数据
        stringRedisTemplate.opsForHash().putAll(key+"r", biMap.inverse());
    }

    @Override
    public String getByRedis(String typeCode, String dictValue) {
        return (String) stringRedisTemplate.opsForHash().get(typeCode, dictValue);
    }

    @Override
    public String getByDatabase(Key key) {
        SysDict sysDict = sysDictMapper.findByTypeCodeAndDictValue(key.typeCode(), key.dictValue());
        if (Objects.nonNull(sysDict)) {
            String dictName;
            //判断国际化字典
            if (StringUtils.equals(key.language(), Key.CH)) {
                dictName = sysDict.getDictName();
            } else {
                dictName = sysDict.getDictEnName();
            }
            //放入到Redis中
            stringRedisTemplate.opsForHash().put(key.typeCode(),key.dictValue(),dictName);
            return dictName;
        }
        return StringUtils.EMPTY;
    }

    @Override
    @DictSign
    public DictDemo dictDemo() {
        DictDemo dictDemo = new DictDemo();
        dictDemo.setRound("127");
        dictDemo.setIpoType("2");
        dictDemo.setCompanyIpoStatus("3");
        return dictDemo;
    }

    @Override
    @DictSign
    public List<DictDemo> dictDemos() {
        return Lists.newArrayList(dictDemo());
    }
}
