package com.github.plugin.sysdict.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SysDict {
    private String typeCode;

    private String typeName;

    private String dictName;

    private String dictEnName;

    private String dictValue;

    private String dictFullName;

    private String dictFullEnName;

    private String dictFullValue;
}