package org.rxjava.third.tencent.weixin.miniapp.bean.express.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rxjava.third.tencent.weixin.miniapp.util.json.WxMaGsonBuilder;

import java.io.Serializable;

/**
 * 配置面单打印员请求对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxMaExpressPrinterUpdateRequest implements Serializable {
    private static final long serialVersionUID = 9119040050963924127L;

    /**
     * 打印员 openid
     * <p>
     * 是否必填： 是
     */
    private String openid;

    /**
     * 更新类型
     * <p>
     * 是否必填： 是
     * 描述： bind表示绑定，unbind表示解除绑定
     */
    @SerializedName("update_type")
    private String updateType;

    /**
     * 打印员面单打印权限
     * <p>
     * 是否必填： 否
     * 描述： 用于平台型小程序设置入驻方的打印员面单打印权限，同一打印员最多支持10个tagid，使用逗号分隔，如填写123,456，表示该打印员可以拉取到tagid为123和456的下的单，非平台型小程序无需填写该字段
     */
    @SerializedName("tagid_list")
    private String tagidList;

    public String toJson() {
        return WxMaGsonBuilder.create().toJson(this);
    }
}
