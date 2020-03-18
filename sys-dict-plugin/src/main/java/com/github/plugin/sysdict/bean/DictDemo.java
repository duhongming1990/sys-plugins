package com.github.plugin.sysdict.bean;

import com.github.plugin.sysdict.common.annotation.DictSign;
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

    @DictSign
    private String round;

    @DictSign
    private String ipoType;

    @DictSign(funcation = "handleIpoInfo(round,ipoType)")
    private String ipoInfo;

    @DictSign
    private String companyIpoStatus;
}
