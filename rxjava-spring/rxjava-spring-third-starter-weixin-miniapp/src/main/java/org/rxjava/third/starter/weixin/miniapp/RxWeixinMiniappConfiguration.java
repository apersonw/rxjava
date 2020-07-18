package org.rxjava.third.starter.weixin.miniapp;

import org.rxjava.third.weixin.miniapp.api.WxMaService;
import org.rxjava.third.weixin.miniapp.api.impl.WxMaServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author happy 2019-05-02 14:32
 * 微信小程序配置
 */
@Configuration
public class RxWeixinMiniappConfiguration {
    @Bean
    WxMaService wxMaService(){
        return new WxMaServiceImpl();
    }
}
