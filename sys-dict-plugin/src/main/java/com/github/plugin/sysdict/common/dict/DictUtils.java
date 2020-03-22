package com.github.plugin.sysdict.common.dict;

import com.github.plugin.sysdict.service.SpringContextHolder;
import com.github.plugin.sysdict.service.SysDictService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author duhongming
 * @version 1.0
 * @description 字典工具类
 * @date 2020/3/19 22:48
 */
@Getter
@Setter
@Accessors(fluent = true)
@Slf4j
public class DictUtils {

    /**
     * 从Spring容器中获取SysDictService
     */
    private static final SysDictService sysDictService = SpringContextHolder.getBean(SysDictService.class);

    /**
     * 单例使用该工具类
     */
    public static final DictUtils me = new DictUtils();

    /**
     * 目前缓存的key数
     */
    private static final Integer INITIAL_CAPACITY = 16;
    private static final Integer MAX_CAPACITY = 512;
    /**
     * 缓存在5秒内没有更新则重新读取数据
     */
    private static final Integer EXPIRE_TIME = 5;

    /**
     * 用户的LRU算法:
     */
    private static LoadingCache<Key, String> localCache = CacheBuilder.newBuilder()
            .initialCapacity(INITIAL_CAPACITY).maximumSize(MAX_CAPACITY)
            //缓存在5秒内没有更新则重新读取数据
            .expireAfterWrite(EXPIRE_TIME, TimeUnit.SECONDS)
            .build(new CacheLoader<Key, String>() {
                //默认的数据加载实现,当调用get取值的时候,如果key没有对应的值,就调用这个方法进行加载.
                @Override
                public String load(Key key) {
                    String mapKey = Key.REDIS_KEY.replace("${k}", key.typeCode()) + key.language();
                    String result = sysDictService.getByRedis(mapKey, key.dictValue());
                    if (StringUtils.isNotBlank(result)) {
                        return result;
                    }
                    return sysDictService.getByDatabase(key);
                }
            });

    private DictUtils() {
        super();
    }

    /**
     * @param key            联合key
     * @param defaultContent 默认content
     * @return
     */
    public String getDictName(Key key, String defaultContent) {
        try {
            String value = localCache.get(key);
            if (StringUtils.isBlank(value)) {
                return defaultContent;
            } else {
                return value;
            }
        } catch (ExecutionException e) {
            log.error("从Guava缓存中获取异常:{}",e);
        }
        return defaultContent;
    }

    /**
     * @param key 联合key
     * @return
     */
    public String getDictName(Key key) {
        return getDictName(key, StringUtils.EMPTY);
    }
}
