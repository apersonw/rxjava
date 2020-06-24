package org.rxjava.third.tencent.weixin.mp.bean.marketing;

import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.rxjava.third.tencent.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
@Data
public class WxMpAdLeadResult implements Serializable {
    private static final long serialVersionUID = -1526796632563660821L;
    protected static final JsonParser JSON_PARSER = new JsonParser();

    @SerializedName("page_info")
    private WxMpAdLeadPageInfo pageInfo;
    @SerializedName("list")
    private List<WxMpAdLead> adLeads;

    public static WxMpAdLeadResult fromJson(String json) {

        return WxMpGsonBuilder.create().fromJson(
                JSON_PARSER.parse(json).getAsJsonObject().get("data"),
                new TypeToken<WxMpAdLeadResult>() {
                }.getType());
    }
}
