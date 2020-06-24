package org.rxjava.third.tencent.miniapp.bean.code;

import org.rxjava.third.tencent.miniapp.util.json.WxMaGsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信代码请求上传参数
 *
 * @author <a href="https://github.com/charmingoh">Charming</a>
 * @since 2018-04-26 19:44:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxMaCodeCommitRequest implements Serializable {
  private static final long serialVersionUID = 7495157056049312108L;
  /**
   * 代码库中的代码模版ID
   */
  private Long templateId;
  /**
   * 第三方自定义的配置
   */
  private WxMaCodeExtConfig extConfig;
  /**
   * 代码版本号，开发者可自定义
   */
  private String userVersion;
  /**
   * 代码描述，开发者可自定义
   */
  private String userDesc;

  public String toJson() {
    return WxMaGsonBuilder.create().toJson(this);
  }
}
