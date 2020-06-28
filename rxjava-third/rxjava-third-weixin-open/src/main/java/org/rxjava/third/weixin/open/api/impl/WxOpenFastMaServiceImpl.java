package org.rxjava.third.weixin.open.api.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.rxjava.third.weixin.common.error.WxErrorException;
import org.rxjava.third.weixin.miniapp.api.impl.WxMaServiceImpl;
import org.rxjava.third.weixin.miniapp.config.WxMaConfig;
import org.rxjava.third.weixin.open.api.WxOpenComponentService;
import org.rxjava.third.weixin.open.api.WxOpenFastMaService;
import org.rxjava.third.weixin.open.bean.ma.WxFastMaCategory;
import org.rxjava.third.weixin.open.util.json.WxOpenGsonBuilder;
import org.rxjava.third.weixin.open.bean.result.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class WxOpenFastMaServiceImpl extends WxMaServiceImpl implements WxOpenFastMaService {
    private WxOpenComponentService wxOpenComponentService;
    private WxMaConfig wxMaConfig;
    private String appId;

    public WxOpenFastMaServiceImpl(WxOpenComponentService wxOpenComponentService, String appId, WxMaConfig wxMaConfig) {
        this.wxOpenComponentService = wxOpenComponentService;
        this.appId = appId;
        this.wxMaConfig = wxMaConfig;
        initHttp();
    }

    @Override
    public WxMaConfig getWxMaConfig() {
        return wxMaConfig;
    }

    @Override
    public String getAccessToken(boolean forceRefresh) throws WxErrorException {
        return wxOpenComponentService.getAuthorizerAccessToken(appId, forceRefresh);
    }

    @Override
    public WxFastMaAccountBasicInfoResult getAccountBasicInfo() throws WxErrorException {
        String response = get(OPEN_GET_ACCOUNT_BASIC_INFO, "");
        return WxOpenGsonBuilder.create().fromJson(response, WxFastMaAccountBasicInfoResult.class);
    }

    @Override
    public WxFastMaSetNickameResult setNickname(String nickname, String idCard, String license, String namingOtherStuff1, String namingOtherStuff2) throws WxErrorException {
        JsonObject params = new JsonObject();
        params.addProperty("nick_name", nickname);
        params.addProperty("id_card", idCard);
        params.addProperty("license", license);
        params.addProperty("naming_other_stuff_1", namingOtherStuff1);
        params.addProperty("naming_other_stuff_2", namingOtherStuff2);
        String response = post(OPEN_SET_NICKNAME, GSON.toJson(params));
        return WxOpenGsonBuilder.create().fromJson(response, WxFastMaSetNickameResult.class);
    }

    @Override
    public WxFastMaQueryNicknameStatusResult querySetNicknameStatus(String auditId) throws WxErrorException {
        JsonObject params = new JsonObject();
        params.addProperty("audit_id", auditId);
        String response = post(OPEN_API_WXA_QUERYNICKNAME, GSON.toJson(params));
        return WxOpenGsonBuilder.create().fromJson(response, WxFastMaQueryNicknameStatusResult.class);
    }

    @Override
    public WxFastMaCheckNickameResult checkWxVerifyNickname(String nickname) throws WxErrorException {
        JsonObject params = new JsonObject();
        params.addProperty("nick_name", nickname);
        String response = post(OPEN_CHECK_WX_VERIFY_NICKNAME, GSON.toJson(params));
        return WxOpenGsonBuilder.create().fromJson(response, WxFastMaCheckNickameResult.class);
    }

    @Override
    public WxOpenResult modifyHeadImage(String headImgMediaId, float x1, float y1, float x2, float y2) throws WxErrorException {
        JsonObject params = new JsonObject();
        params.addProperty("head_img_media_id", headImgMediaId);
        params.addProperty("x1", x1);
        params.addProperty("y1", y1);
        params.addProperty("x2", x2);
        params.addProperty("y2", y2);
        String response = post(OPEN_MODIFY_HEADIMAGE, GSON.toJson(params));
        return WxOpenGsonBuilder.create().fromJson(response, WxOpenResult.class);
    }

    @Override
    public WxOpenResult modifySignature(String signature) throws WxErrorException {
        JsonObject params = new JsonObject();
        params.addProperty("signature", signature);
        String response = post(OPEN_MODIFY_SIGNATURE, GSON.toJson(params));
        return WxOpenGsonBuilder.create().fromJson(response, WxOpenResult.class);
    }

    @Override
    public WxOpenResult componentRebindAdmin(String taskid) throws WxErrorException {
        JsonObject params = new JsonObject();
        params.addProperty("taskid", taskid);
        String response = post(OPEN_COMPONENT_REBIND_ADMIN, GSON.toJson(params));
        return WxOpenGsonBuilder.create().fromJson(response, WxOpenResult.class);
    }

    @Override
    public String getAllCategories() throws WxErrorException {
        return get(OPEN_GET_ALL_CATEGORIES, "");
    }

    @Override
    public WxOpenResult addCategory(List<WxFastMaCategory> categoryList) throws WxErrorException {
        Map<String, Object> map = new HashMap<>();
        map.put("categories", categoryList);
        String response = post(OPEN_ADD_CATEGORY, WxOpenGsonBuilder.create().toJson(map));
        return WxOpenGsonBuilder.create().fromJson(response, WxOpenResult.class);
    }

    @Override
    public WxOpenResult deleteCategory(int first, int second) throws WxErrorException {
        JsonObject params = new JsonObject();
        params.addProperty("first", first);
        params.addProperty("second", second);
        String response = post(OPEN_DELETE_CATEGORY, GSON.toJson(params));
        return WxOpenGsonBuilder.create().fromJson(response, WxOpenResult.class);
    }

    @Override
    public WxFastMaBeenSetCategoryResult getCategory() throws WxErrorException {
        String response = get(OPEN_GET_CATEGORY, "");
        return WxOpenGsonBuilder.create().fromJson(response, WxFastMaBeenSetCategoryResult.class);
    }

    @Override
    public WxOpenResult modifyCategory(WxFastMaCategory category) throws WxErrorException {
        String response = post(OPEN_MODIFY_CATEGORY, GSON.toJson(category));
        return WxOpenGsonBuilder.create().fromJson(response, WxOpenResult.class);
    }

    private JsonArray toJsonArray(List<String> strList) {
        JsonArray jsonArray = new JsonArray();
        if (strList != null && !strList.isEmpty()) {
            for (String str : strList) {
                jsonArray.add(str);
            }
        }
        return jsonArray;
    }
}
