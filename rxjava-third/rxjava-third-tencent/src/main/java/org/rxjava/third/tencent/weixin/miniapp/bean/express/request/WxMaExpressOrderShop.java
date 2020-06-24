package org.rxjava.third.tencent.weixin.miniapp.bean.express.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品信息对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxMaExpressOrderShop implements Serializable {
    private static final long serialVersionUID = 7256509453502211830L;

    /**
     * 商家小程序的路径
     * <p>
     * 是否必填： 是
     * 描述： 建议为订单页面
     */
    @SerializedName("wxa_path")
    private String wxaPath;

    /**
     * 商品缩略图url
     * <p>
     * 是否必填： 是
     */
    @SerializedName("img_url")
    private String imgUrl;

    /**
     * 商品名称
     * <p>
     * 是否必填： 是
     */
    @SerializedName("goods_name")
    private String goodsName;

    /**
     * 商品数量
     * <p>
     * 是否必填： 是
     */
    @SerializedName("goods_count")
    private Integer goodsCount;

}
