package org.rxjava.third.tencent.weixin.mp.enums;

/**
 * 微信卡券类型.
 *
 * @author chenyixin
 * @date 2019-09-07 23:33
 **/
public enum WxCardType {
    MEMBER_CARD("MEMBER_CARD"),
    GROUPON("GROUPON"),
    CASH("CASH"),
    DISCOUNT("DISCOUNT"),
    GIFT("GIFT"),
    GENERAL_COUPON("GENERAL_COUPON");

    private String code;

    WxCardType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
