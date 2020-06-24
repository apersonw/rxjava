package org.rxjava.third.tencent.weixin.mp.api.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.rxjava.third.tencent.weixin.common.WxType;
import org.rxjava.third.tencent.weixin.common.error.WxError;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.mp.api.WxMpService;
import org.rxjava.third.tencent.weixin.mp.api.WxMpUserTagService;
import org.rxjava.third.tencent.weixin.mp.bean.tag.WxTagListUser;
import org.rxjava.third.tencent.weixin.mp.bean.tag.WxUserTag;
import org.rxjava.third.tencent.weixin.mp.util.json.WxMpGsonBuilder;

import java.util.List;

import static org.rxjava.third.tencent.weixin.mp.enums.WxMpApiUrl.UserTag.*;

/**
 *
 */
@RequiredArgsConstructor
public class WxMpUserTagServiceImpl implements WxMpUserTagService {
    private final WxMpService wxMpService;

    @Override
    public WxUserTag tagCreate(String name) throws WxErrorException {
        JsonObject json = new JsonObject();
        JsonObject tagJson = new JsonObject();
        tagJson.addProperty("name", name);
        json.add("tag", tagJson);

        String responseContent = this.wxMpService.post(TAGS_CREATE, json.toString());
        return WxUserTag.fromJson(responseContent);
    }

    @Override
    public List<WxUserTag> tagGet() throws WxErrorException {
        String responseContent = this.wxMpService.get(TAGS_GET, null);
        return WxUserTag.listFromJson(responseContent);
    }

    @Override
    public Boolean tagUpdate(Long id, String name) throws WxErrorException {
        JsonObject json = new JsonObject();
        JsonObject tagJson = new JsonObject();
        tagJson.addProperty("id", id);
        tagJson.addProperty("name", name);
        json.add("tag", tagJson);

        String responseContent = this.wxMpService.post(TAGS_UPDATE, json.toString());
        WxError wxError = WxError.fromJson(responseContent, WxType.MP);
        if (wxError.getErrorCode() == 0) {
            return true;
        }

        throw new WxErrorException(wxError);
    }

    @Override
    public Boolean tagDelete(Long id) throws WxErrorException {
        JsonObject json = new JsonObject();
        JsonObject tagJson = new JsonObject();
        tagJson.addProperty("id", id);
        json.add("tag", tagJson);

        String responseContent = this.wxMpService.post(TAGS_DELETE, json.toString());
        WxError wxError = WxError.fromJson(responseContent, WxType.MP);
        if (wxError.getErrorCode() == 0) {
            return true;
        }

        throw new WxErrorException(wxError);
    }

    @Override
    public WxTagListUser tagListUser(Long tagId, String nextOpenid) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("tagid", tagId);
        json.addProperty("next_openid", StringUtils.trimToEmpty(nextOpenid));

        String responseContent = this.wxMpService.post(TAG_GET, json.toString());
        return WxTagListUser.fromJson(responseContent);
    }

    @Override
    public boolean batchTagging(Long tagId, String[] openids) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("tagid", tagId);
        JsonArray openidArrayJson = new JsonArray();
        for (String openid : openids) {
            openidArrayJson.add(openid);
        }
        json.add("openid_list", openidArrayJson);

        String responseContent = this.wxMpService.post(TAGS_MEMBERS_BATCHTAGGING, json.toString());
        WxError wxError = WxError.fromJson(responseContent, WxType.MP);
        if (wxError.getErrorCode() == 0) {
            return true;
        }

        throw new WxErrorException(wxError);
    }

    @Override
    public boolean batchUntagging(Long tagId, String[] openids) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("tagid", tagId);
        JsonArray openidArrayJson = new JsonArray();
        for (String openid : openids) {
            openidArrayJson.add(openid);
        }
        json.add("openid_list", openidArrayJson);

        String responseContent = this.wxMpService.post(TAGS_MEMBERS_BATCHUNTAGGING, json.toString());
        WxError wxError = WxError.fromJson(responseContent, WxType.MP);
        if (wxError.getErrorCode() == 0) {
            return true;
        }

        throw new WxErrorException(wxError);
    }

    @Override
    public List<Long> userTagList(String openid) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("openid", openid);

        String responseContent = this.wxMpService.post(TAGS_GETIDLIST, json.toString());

        return WxMpGsonBuilder.create().fromJson(
                new JsonParser().parse(responseContent).getAsJsonObject().get("tagid_list"),
                new TypeToken<List<Long>>() {
                }.getType());
    }
}
