package com.github.plugin.sysdict.dao;

import com.github.plugin.sysdict.bean.SysDict;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SysDictMapper {
    List<SysDict> findAll();
}