package org.rxjava.third.weixin.miniapp.bean.code;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rxjava.third.weixin.miniapp.util.json.WxMaGsonBuilder;

import java.io.Serializable;

/**
 * 微信代码请求上传参数
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
