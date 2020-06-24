package org.rxjava.third.tencent.weixin.miniapp.bean.express.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rxjava.third.tencent.weixin.miniapp.util.json.WxMaGsonBuilder;

import java.io.Serializable;

/**
 * 绑定、解绑物流账号请求对象
 *
 * @author <a href="https://github.com/mr-xiaoyu">xiaoyu</a>
 * @since 2019-11-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxMaExpressBindAccountRequest implements Serializable {
    private static final long serialVersionUID = 3868003945297939946L;

    /**
     * 类型
     * <p>
     * 是否必填： 是
     * 描述： bind表示绑定，unbind表示解除绑定
     */
    @SerializedName("type")
    private String type;

    /**
     * 快递公司客户编码
     * <p>
     * 是否必填： 是
     */
    @SerializedName("biz_id")
    private String bizId;

    /**
     * 快递公司ID
     * <p>
     * 是否必填： 是
     */
    @SerializedName("delivery_id")
    private String deliveryId;

    /**
     * 快递公司客户密码
     * <p>
     * 是否必填： 是
     */
    @SerializedName("password")
    private String password;

    /**
     * 备注内容（提交EMS审核需要）
     * <p>
     * 是否必填： 是
     */
    @SerializedName("remark_content")
    private String remarkContent;

    public String toJson() {
        return WxMaGsonBuilder.create().toJson(this);
    }

}
