package top.rxjava.third.weixin.miniapp.bean.express.result;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.rxjava.third.weixin.miniapp.util.json.WxMaGsonBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 运单信息返回结果对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxMaExpressOrderInfoResult implements Serializable {
    private static final JsonParser JSON_PARSER = new JsonParser();
    private static final long serialVersionUID = -9166603059965942285L;

    /**
     * 错误码
     */
    private Integer errcode;

    /**
     * 错误信息
     */
    private String errmsg;
    /**
     * 订单ID
     */
    @SerializedName("order_id")
    private String orderId;

    /**
     * 运单ID
     */
    @SerializedName("waybill_id")
    private String waybillId;

    /**
     * 运单 html 的 BASE64 结果
     */
    @SerializedName("print_html")
    private String printHtml;

    /**
     * 运单信息
     */
    @SerializedName("waybill_data")
    private List<Map<String, String>> waybillData;


    public static WxMaExpressOrderInfoResult fromJson(String json) {
        return WxMaGsonBuilder.create().fromJson(json, WxMaExpressOrderInfoResult.class);
    }

    public static List<WxMaExpressOrderInfoResult> toList(String json) {
        JsonObject jsonObject = JSON_PARSER.parse(json).getAsJsonObject();
        return WxMaGsonBuilder.create().fromJson(jsonObject.get("order_list").toString(),
                new TypeToken<List<WxMaExpressOrderInfoResult>>() {
                }.getType());
    }
}
