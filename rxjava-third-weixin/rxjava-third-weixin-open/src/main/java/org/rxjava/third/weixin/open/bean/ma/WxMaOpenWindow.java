package top.rxjava.third.weixin.open.bean.ma;

import lombok.Data;

import java.io.Serializable;

/**
 * window对象
 */
@Data
public class WxMaOpenWindow implements Serializable {
    private String navigationBarBackgroundColor;

    private String navigationBarTextStyle;

    private String navigationBarTitleText;

    private String navigationStyle;

    private String backgroundColor;

    private String backgroundTextStyle;

    private String backgroundColorTop;

    private String backgroundColorBottom;

    private Boolean enablePullDownRefresh;

    private Integer onReachBottomDistance;
}
