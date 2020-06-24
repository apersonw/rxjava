package org.rxjava.third.tencent.weixin.miniapp.bean.express.request;


import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rxjava.third.tencent.weixin.miniapp.constant.WxMaConstants;

import java.io.Serializable;

/**
 * 保价信息对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxMaExpressOrderInsured implements Serializable {
    private static final long serialVersionUID = -8636857630937445422L;

    /**
     * 是否保价
     * <p>
     * 是否必填： 是
     * 描述： 0 表示不保价，1 表示保价
     */
    @SerializedName("use_insured")
    private final Integer useInsured = WxMaConstants.OrderAddInsured.INSURED_PROGRAM;

    /**
     * 保价金额
     * <p>
     * 是否必填： 是
     * 描述： 单位是分，比如: 10000 表示 100 元
     */
    @SerializedName("insured_value")
    @Builder.Default
    private final Integer insuredValue = WxMaConstants.OrderAddInsured.DEFAULT_INSURED_VALUE;

}
