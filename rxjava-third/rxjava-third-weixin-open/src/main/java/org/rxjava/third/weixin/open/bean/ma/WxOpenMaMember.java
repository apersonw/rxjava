package org.rxjava.third.weixin.open.bean.ma;

import lombok.Data;

import java.io.Serializable;

/**
 * 微信开放平台小程序成员对象
 */
@Data
public class WxOpenMaMember implements Serializable {
    /**
     * 人员对应的唯一字符串
     */
    private String userstr;
}
