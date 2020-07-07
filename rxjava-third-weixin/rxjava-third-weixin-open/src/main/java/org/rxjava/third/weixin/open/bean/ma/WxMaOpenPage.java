package org.rxjava.third.weixin.open.bean.ma;

import lombok.Data;

import java.io.Serializable;

/**
 */
@Data
public class WxMaOpenPage implements Serializable {
    private String navigationBarBackgroundColor;
    private String navigationBarTextStyle;
    private String navigationBarTitleText;
    private String backgroundColor;
    private String backgroundTextStyle;
    private Boolean enablePullDownRefresh;
    private Integer onReachBottomDistance;
    private Boolean disableScroll;

}
