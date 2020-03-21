package com.github.plugin.sysdict.bean;

import com.github.plugin.sysdict.common.dict.DictField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author duhongming
 * @Email 935720334@qq.com
 * @Date 2020/3/18 21:56
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

    @DictField(funcation = "handleIpoInfo(round,ipoType)")
    private String ipoInfo;

    @DictField
    private String companyIpoStatus;
}
