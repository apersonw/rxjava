package org.rxjava.third.tencent.common.bean;

import java.io.Serializable;

import lombok.Data;
import org.rxjava.third.tencent.common.util.json.WxGsonBuilder;

/**
 * 卡券Api签名.
 *
 * @author YuJian
 * @version 15/11/8
 */
@Data
public class WxCardApiSignature implements Serializable {
  private static final long serialVersionUID = 158176707226975979L;

  private String appId;

  private String cardId;

  private String cardType;

  private String locationId;

  private String code;

  private String openId;

  private Long timestamp;

  private String nonceStr;

  private String signature;

  @Override
  public String toString() {
    return WxGsonBuilder.create().toJson(this);
  }
}
