package org.rxjava.third.tencent.weixin.miniapp.bean.express.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 包裹商品详情对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxMaExpressOrderCargoDetail implements Serializable {
    private static final long serialVersionUID = 5988620921216969796L;

    /**
     * 商品名
     * <p>
     * 是否必填： 是
     * 描述： 不超过128字节
     */
    private String name;

    /**
     * 商品数量
     * <p>
     * 是否必填： 是
     */
    private Integer count;
}
