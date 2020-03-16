package com.github.plugin.sysdict.service;

import com.github.plugin.sysdict.bean.SysDict;

import java.util.List;

public interface SysDictService {
    List<SysDict> findAll();

    void handleDictCache();
}
