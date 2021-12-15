package top.rxjava.third.weixin.open.bean.result;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.rxjava.third.weixin.open.util.json.WxOpenGsonBuilder;

import java.util.List;

/**
 * 微信开放平台小程序第三方提交代码的页面配置列表.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WxOpenMaPageListResult extends WxOpenResult {
    private static final long serialVersionUID = 6982848180319905444L;

    @SerializedName("page_list")
    List<String> pageList;

    @Override
    public String toString() {
        return WxOpenGsonBuilder.create().toJson(this);
    }

}
