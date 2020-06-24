package org.rxjava.third.tencent.weixin.common.api;

import org.rxjava.third.tencent.weixin.common.error.WxErrorException;

/**
 * WxErrorException处理器.
 */
public interface WxErrorExceptionHandler {

    void handle(WxErrorException e);

}
