package com.github.plugin.sysdict.dao;

import com.github.plugin.sysdict.bean.SysDict;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author duhongming
 * @version 1.0
 * @description 字典Mapper
 * @date 2020/3/19 22:40
 */
@Repository
public interface SysDictMapper {
    /**
     * 查询所有字典
     * @return
     */
    List<SysDict> findAll();

    /**
     * 查询单个字典
     * @param typeCode  类型码
     * @param dictValue value值
     * @return
     */
    SysDict findByTypeCodeAndDictValue(@Param("typeCode") String typeCode, @Param("dictValue") String dictValue);
}