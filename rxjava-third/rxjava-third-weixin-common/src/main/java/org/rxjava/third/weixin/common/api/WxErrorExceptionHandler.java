package org.rxjava.third.weixin.common.api;

import org.rxjava.third.weixin.common.error.WxErrorException;

/**
 * WxErrorException处理器.
 */
public interface WxErrorExceptionHandler {

    void handle(WxErrorException e);

}
