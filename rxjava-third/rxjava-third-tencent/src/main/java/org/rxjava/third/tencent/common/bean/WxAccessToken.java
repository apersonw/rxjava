package org.rxjava.third.tencent.common.bean;

import java.io.Serializable;

import lombok.Data;
import org.rxjava.third.tencent.common.util.json.WxGsonBuilder;

/**
 * access token.
 *
 * @author Daniel Qian
 */
@Data
public class WxAccessToken implements Serializable {
  private static final long serialVersionUID = 8709719312922168909L;

  private String accessToken;

  private int expiresIn = -1;

  public static WxAccessToken fromJson(String json) {
    return WxGsonBuilder.create().fromJson(json, WxAccessToken.class);
  }

}
