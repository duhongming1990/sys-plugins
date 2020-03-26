package com.github.plugin.setoperation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author duhongming
 * @version 1.0
 * @description 通过标签找相关公司
 * @date 2020/3/26 11:30
 */
@Service
public class TagService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 创建标签
     */
    public void createTag() {
        //tag1 1~30w
        //tag2 30w~60w
        //tag3 60w~90w
        //......
        for (int tagId = 1; tagId <= 10; tagId++) {
            Set<ZSetOperations.TypedTuple<String>> sets = new HashSet<>(30_0000);
            for (int companyId = 30_0000 * (tagId - 1); companyId <= 30_0000 * (tagId); companyId++) {
                //每个公司不分高低之分，分数都是1
                sets.add(new DefaultTypedTuple<>(companyId + "", 1.0));
            }
            redisTemplate.opsForZSet().add("tag" + tagId, sets);
        }
    }

    /**
     * 合并标签并计数
     */
    public void unoinTag() {
        Set<String> tags = new HashSet<>();
        for (int i = 1; i <= 10; i++) {
            tags.add("tag" + i);
        }
        redisTemplate.opsForZSet().unionAndStore("none_tag", tags, "company_id");
    }

    /**
     * 重合度高的top10的标签企业
     */
    public void top10Company() {
        Set<String> sets = redisTemplate.opsForZSet().reverseRangeByScore("company_id", 1, 10, 0, 10);
        System.out.println("sets = " + sets);
    }
}