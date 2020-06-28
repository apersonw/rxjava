package org.rxjava.third.weixin.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * ticket类型枚举
 */
@Getter
@RequiredArgsConstructor
public enum TicketType {
    /**
     * jsapi
     */
    JSAPI("jsapi"),
    /**
     * sdk
     */
    SDK("sdk"),
    /**
     * 微信卡券
     */
    WX_CARD("wx_card");

    /**
     * type代码
     */
    private final String code;

}
