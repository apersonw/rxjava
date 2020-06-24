package org.rxjava.third.tencent.weixin.cp.api.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.rxjava.third.tencent.weixin.common.WxType;
import org.rxjava.third.tencent.weixin.common.error.WxError;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.cp.api.WxCpAgentService;
import org.rxjava.third.tencent.weixin.cp.api.WxCpService;
import org.rxjava.third.tencent.weixin.cp.bean.WxCpAgent;
import org.rxjava.third.tencent.weixin.cp.util.json.WxCpGsonBuilder;

import java.util.List;

import static org.rxjava.third.tencent.weixin.cp.constant.WxCpApiPathConsts.Agent.*;


/**
 * 管理企业号应用
 */
@RequiredArgsConstructor
public class WxCpAgentServiceImpl implements WxCpAgentService {
    private static final JsonParser JSON_PARSER = new JsonParser();

    private final WxCpService mainService;

    @Override
    public WxCpAgent get(Integer agentId) throws WxErrorException {
        if (agentId == null) {
            throw new IllegalArgumentException("缺少agentid参数");
        }

        final String url = String.format(this.mainService.getWxCpConfigStorage().getApiUrl(AGENT_GET), agentId);
        return WxCpAgent.fromJson(this.mainService.get(url, null));
    }

    @Override
    public void set(WxCpAgent agentInfo) throws WxErrorException {
        String url = this.mainService.getWxCpConfigStorage().getApiUrl(AGENT_SET);
        String responseContent = this.mainService.post(url, agentInfo.toJson());
        JsonObject jsonObject = JSON_PARSER.parse(responseContent).getAsJsonObject();
        if (jsonObject.get("errcode").getAsInt() != 0) {
            throw new WxErrorException(WxError.fromJson(responseContent, WxType.CP));
        }
    }

    @Override
    public List<WxCpAgent> list() throws WxErrorException {
        String url = this.mainService.getWxCpConfigStorage().getApiUrl(AGENT_LIST);
        String responseContent = this.mainService.get(url, null);
        JsonObject jsonObject = JSON_PARSER.parse(responseContent).getAsJsonObject();
        if (jsonObject.get("errcode").getAsInt() != 0) {
            throw new WxErrorException(WxError.fromJson(responseContent, WxType.CP));
        }

        return WxCpGsonBuilder.create().fromJson(jsonObject.get("agentlist").toString(), new TypeToken<List<WxCpAgent>>() {
        }.getType());
    }

}
