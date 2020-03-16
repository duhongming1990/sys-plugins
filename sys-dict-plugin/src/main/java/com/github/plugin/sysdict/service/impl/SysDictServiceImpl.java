package com.github.plugin.sysdict.service.impl;

import com.github.plugin.sysdict.bean.SysDict;
import com.github.plugin.sysdict.dao.SysDictMapper;
import com.github.plugin.sysdict.service.SysDictService;
import com.google.common.collect.Maps;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author duhongming
 * @Email 935720334@qq.com
 * @Date 2020/3/16 21:54
 */
@Service("sysDictService")
public class SysDictServiceImpl implements SysDictService {

    @Resource
    private SysDictMapper sysDictMapper;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<SysDict> findAll() {
        return sysDictMapper.findAll();
    }

    @Override
    public void handleDictCache(){
        HashMap<String, String> dicts = Maps.newHashMap();
        dicts.put("a", "a");
        redisTemplate.opsForHash().putAll("test",dicts);
    }
}
