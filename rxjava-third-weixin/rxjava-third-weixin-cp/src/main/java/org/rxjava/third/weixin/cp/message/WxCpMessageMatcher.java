package org.rxjava.third.weixin.cp.message;

import org.rxjava.third.weixin.cp.bean.WxCpXmlMessage;

/**
 * 消息匹配器，用在消息路由的时候
 */
public interface WxCpMessageMatcher {

    /**
     * 消息是否匹配某种模式
     */
    boolean match(WxCpXmlMessage message);

}