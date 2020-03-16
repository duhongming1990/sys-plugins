package com.github.plugin.sysdict;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.github.plugin.sysdict.dao")
@SpringBootApplication
public class SysDictPluginApplication {

	public static void main(String[] args) {
		SpringApplication.run(SysDictPluginApplication.class, args);
	}

}
