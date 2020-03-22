package com.github.plugin.sysdict.bean;

import com.github.plugin.sysdict.common.dict.DictField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author duhongming
 * @version 1.0
 * @description TODO
 * @date 2020/3/22 10:02
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DictDemo {

    @DictField
    private String round;

    @DictField
    private String ipoType;

    @DictField(funcation = "handleIpoInfo")
    private String ipoInfo;

    @DictField
    private String companyIpoStatus;

    public String handleIpoInfo() {
        if (StringUtils.equals(round, "上市及以后")) {
            return round + "(" + ipoType + ")";
        }else {
            return round;
        }
    }
}
