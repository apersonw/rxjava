package org.rxjava.third.tencent.weixin.miniapp.message;

import org.rxjava.third.tencent.weixin.miniapp.bean.WxMaMessage;

/**
 * 消息匹配器，用在消息路由的时候.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public interface WxMaMessageMatcher {

    /**
     * 消息是否匹配某种模式.
     *
     * @param message 消息
     * @return 是否匹配
     */
    boolean match(WxMaMessage message);

}
