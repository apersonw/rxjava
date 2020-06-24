package org.rxjava.third.tencent.common.api;

import org.rxjava.third.tencent.common.error.WxErrorException;

/**
 * WxErrorException处理器.
 *
 * @author Daniel Qian
 */
public interface WxErrorExceptionHandler {

  void handle(WxErrorException e);

}
