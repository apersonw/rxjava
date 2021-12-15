package top.rxjava.third.weixin.miniapp.bean.code;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.rxjava.third.weixin.miniapp.util.json.WxMaGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 提交审核的请求
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxMaCodeSubmitAuditRequest implements Serializable {
    private static final long serialVersionUID = 8854979405505241314L;
    /**
     * 提交审核项的一个列表（至少填写1项，至多填写5项）
     */
    @SerializedName("item_list")
    private List<WxMaCategory> itemList;

    public String toJson() {
        return WxMaGsonBuilder.create().toJson(this);
    }
}
