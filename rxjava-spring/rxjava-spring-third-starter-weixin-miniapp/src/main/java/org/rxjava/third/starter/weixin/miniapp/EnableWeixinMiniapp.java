package org.rxjava.third.starter.weixin.miniapp;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author happy
 * 开启微信小程序
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RxWeixinMiniappConfiguration.class})
public @interface EnableWeixinMiniapp {
}