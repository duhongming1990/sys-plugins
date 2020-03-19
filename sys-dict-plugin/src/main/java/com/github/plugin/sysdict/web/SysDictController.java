package com.github.plugin.sysdict.web;

import com.github.plugin.sysdict.bean.DictDemo;
import com.github.plugin.sysdict.common.utils.DictUtils;
import com.github.plugin.sysdict.common.utils.Key;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duhongming
 * @version 1.0
 * @description TODO
 * @date 2020/3/19 22:46
 */
@RequestMapping("/dict")
@RestController
public class SysDictController {

    @GetMapping("/init")
    public String init(){
        return DictUtils.me.getDictName(Key.of().typeCode("round").dictValue("1"));
    }

    @GetMapping("/demo")
    public DictDemo dict(){
        DictDemo dictDemo = new DictDemo();
        dictDemo.setRound("1");
        dictDemo.setIpoType("2");
        dictDemo.setCompanyIpoStatus("3");
        return dictDemo;
    }
}
