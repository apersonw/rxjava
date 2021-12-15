package top.rxjava.third.weixin.common.util.json;

import com.google.gson.*;
import top.rxjava.third.weixin.common.bean.WxNetCheckResult;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 */
public class WxNetCheckResultGsonAdapter implements JsonDeserializer<WxNetCheckResult> {


    @Override
    public WxNetCheckResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        WxNetCheckResult result = new WxNetCheckResult();

        JsonArray dnssJson = json.getAsJsonObject().get("dns").getAsJsonArray();
        List<WxNetCheckResult.WxNetCheckDnsInfo> dnsInfoList = new ArrayList<>();
        if (dnssJson != null && dnssJson.size() > 0) {
            for (int i = 0; i < dnssJson.size(); i++) {
                JsonObject buttonJson = dnssJson.get(i).getAsJsonObject();
                WxNetCheckResult.WxNetCheckDnsInfo dnsInfo = new WxNetCheckResult.WxNetCheckDnsInfo();
                dnsInfo.setIp(GsonHelper.getString(buttonJson, "ip"));
                dnsInfo.setRealOperator(GsonHelper.getString(buttonJson, "real_operator"));
                dnsInfoList.add(dnsInfo);
            }
        }

        JsonArray pingsJson = json.getAsJsonObject().get("ping").getAsJsonArray();
        List<WxNetCheckResult.WxNetCheckPingInfo> pingInfoList = new ArrayList<>();
        if (pingsJson != null && pingsJson.size() > 0) {
            for (int i = 0; i < pingsJson.size(); i++) {
                JsonObject pingJson = pingsJson.get(i).getAsJsonObject();
                WxNetCheckResult.WxNetCheckPingInfo pingInfo = new WxNetCheckResult.WxNetCheckPingInfo();
                pingInfo.setIp(GsonHelper.getString(pingJson, "ip"));
                pingInfo.setFromOperator(GsonHelper.getString(pingJson, "from_operator"));
                pingInfo.setPackageLoss(GsonHelper.getString(pingJson, "package_loss"));
                pingInfo.setTime(GsonHelper.getString(pingJson, "time"));
                pingInfoList.add(pingInfo);
            }
        }
        result.setDnsInfos(dnsInfoList);
        result.setPingInfos(pingInfoList);
        return result;
    }

}
