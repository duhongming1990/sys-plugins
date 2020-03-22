package com.github.plugin.sysdict.web;

import com.github.plugin.sysdict.bean.DictDemo;
import com.github.plugin.sysdict.common.response.JsonResult;
import com.github.plugin.sysdict.common.dict.DictUtils;
import com.github.plugin.sysdict.common.dict.Key;
import com.github.plugin.sysdict.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    /**
     * 初始化字典
     */
    @GetMapping("/init")
    public JsonResult<String> init(){
        sysDictService.init();
        return new JsonResult<>().setMessage("字典初始化成功！");
    }

    @GetMapping("/demo")
    public JsonResult<DictDemo> dict(){
        DictDemo dictDemo = sysDictService.dictDemo();
        return new JsonResult<>().setData(dictDemo);
    }

    @GetMapping("/demos")
    public JsonResult<List<DictDemo>> dicts(){
        List<DictDemo> dictDemo = sysDictService.dictDemos();
        return new JsonResult<>().setData(dictDemo);
    }
}
