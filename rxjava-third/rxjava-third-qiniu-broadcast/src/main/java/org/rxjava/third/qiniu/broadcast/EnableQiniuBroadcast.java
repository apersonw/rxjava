package org.rxjava.third.qiniu.broadcast;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author happy 2019-06-04 10:22
 * 开启直播
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RxQiniuBroadcastConfiguration.class})
public @interface EnableQiniuBroadcast {
}