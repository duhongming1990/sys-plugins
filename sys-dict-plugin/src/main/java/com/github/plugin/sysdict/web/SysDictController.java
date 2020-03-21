package com.github.plugin.sysdict.web;

import com.github.plugin.sysdict.bean.DictDemo;
import com.github.plugin.sysdict.common.response.JsonResult;
import com.github.plugin.sysdict.common.utils.DictUtils;
import com.github.plugin.sysdict.common.utils.Key;
import com.github.plugin.sysdict.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duhongming
 * @version 1.0
 * @description TODO
 * @date 2020/3/19 22:56
 */
@RequestMapping("/dict")
@RestController
public class SysDictController {
    @Autowired
    private SysDictService sysDictService;

    @GetMapping("/init")
    public String init(){
        return DictUtils.me.getDictName(Key.of().typeCode("round").dictValue("1"));
    }

    @GetMapping("/demo")
    public JsonResult<DictDemo> dict(){
        DictDemo dictDemo = sysDictService.dictDemo();
        return new JsonResult<>().setData(dictDemo);
    }
}
