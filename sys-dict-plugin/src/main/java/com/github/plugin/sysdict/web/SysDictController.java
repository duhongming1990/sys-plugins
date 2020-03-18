package com.github.plugin.sysdict.web;

import com.github.plugin.sysdict.bean.DictDemo;
import com.github.plugin.sysdict.service.SysDictService;
import com.github.plugin.sysdict.utils.DictUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.github.plugin.sysdict.utils.DictUtils.EN;

/**
 * @Author duhongming
 * @Email 935720334@qq.com
 * @Date 2020/3/16 22:07
 */
@RequestMapping("/dict")
@RestController
public class SysDictController {

    @GetMapping("/init")
    public void init(){
        DictUtils.me.language();
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
