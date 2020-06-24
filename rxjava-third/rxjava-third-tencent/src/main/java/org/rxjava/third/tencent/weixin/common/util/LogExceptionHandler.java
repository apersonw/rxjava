package org.rxjava.third.tencent.weixin.common.util;

import org.rxjava.third.tencent.weixin.common.api.WxErrorExceptionHandler;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogExceptionHandler implements WxErrorExceptionHandler {

    private Logger log = LoggerFactory.getLogger(WxErrorExceptionHandler.class);

    @Override
    public void handle(WxErrorException e) {

        this.log.error("Error happens", e);

    }

}
