package org.rxjava.third.weixin.open.bean;

import org.rxjava.third.weixin.open.util.json.WxOpenGsonBuilder;

import java.io.Serializable;

/**
 */
public class WxOpenComponentAccessToken implements Serializable {
    private static final long serialVersionUID = 2134550135400443725L;

    private String componentAccessToken;

    private int expiresIn = -1;

    public static WxOpenComponentAccessToken fromJson(String json) {
        return WxOpenGsonBuilder.create().fromJson(json, WxOpenComponentAccessToken.class);
    }

    public String getComponentAccessToken() {
        return componentAccessToken;
    }

    public void setComponentAccessToken(String componentAccessToken) {
        this.componentAccessToken = componentAccessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
