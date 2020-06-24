package org.rxjava.third.tencent.miniapp.bean;

import org.rxjava.third.tencent.miniapp.util.json.WxMaGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhfish
 */
@Data
public class WxMaShareInfo implements Serializable {
  private static final long serialVersionUID = -8053613683499632226L;

  private String openGId;

  public static WxMaShareInfo fromJson(String json) {
    return WxMaGsonBuilder.create().fromJson(json, WxMaShareInfo.class);
  }
}
