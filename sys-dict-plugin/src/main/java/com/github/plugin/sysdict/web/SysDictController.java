package com.github.plugin.sysdict.web;

import com.github.plugin.sysdict.service.SysDictService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author duhongming
 * @Email 935720334@qq.com
 * @Date 2020/3/16 22:07
 */
@RequestMapping("/")
@RestController
public class SysDictController {

    @Resource
    private SysDictService sysDictService;

    @GetMapping("/exec")
    public void exec(){
        sysDictService.findAll();
        sysDictService.handleDictCache();
    }
}
