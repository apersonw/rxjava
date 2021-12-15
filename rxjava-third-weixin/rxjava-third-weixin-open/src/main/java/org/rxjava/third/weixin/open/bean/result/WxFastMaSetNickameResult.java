package top.rxjava.third.weixin.open.bean.result;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设置小程序名称结果类.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxFastMaSetNickameResult extends WxOpenResult {
    private static final long serialVersionUID = 8022192589710319473L;

    /**
     * 审核编号.
     */
    @SerializedName("audit_id")
    Long auditId;

    /**
     * 材料说明
     */
    @SerializedName("wording")
    String wording;

}
