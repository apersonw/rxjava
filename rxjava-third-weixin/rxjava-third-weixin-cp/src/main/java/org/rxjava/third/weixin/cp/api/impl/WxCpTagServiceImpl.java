package top.rxjava.third.weixin.cp.api.impl;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.cp.api.WxCpService;
import top.rxjava.third.weixin.cp.api.WxCpTagService;
import top.rxjava.third.weixin.cp.bean.WxCpTag;
import top.rxjava.third.weixin.cp.bean.WxCpTagAddOrRemoveUsersResult;
import top.rxjava.third.weixin.cp.bean.WxCpTagGetResult;
import top.rxjava.third.weixin.cp.bean.WxCpUser;
import top.rxjava.third.weixin.cp.util.json.WxCpGsonBuilder;

import java.util.List;

import static top.rxjava.third.weixin.cp.constant.WxCpApiPathConsts.Tag.*;

/**
 * 标签管理接口.
 */
@RequiredArgsConstructor
public class WxCpTagServiceImpl implements WxCpTagService {
    private final WxCpService mainService;

    @Override
    public String create(String name, Integer id) throws WxErrorException {
        JsonObject o = new JsonObject();
        o.addProperty("tagname", name);

        if (id != null) {
            o.addProperty("tagid", id);
        }
        return this.create(o);
    }

    @Override
    public String create(String tagName) throws WxErrorException {
        JsonObject o = new JsonObject();
        o.addProperty("tagname", tagName);
        return this.create(o);
    }

    private String create(JsonObject param) throws WxErrorException {
        String url = this.mainService.getWxCpConfigStorage().getApiUrl(TAG_CREATE);
        String responseContent = this.mainService.post(url, param.toString());
        JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
        return tmpJsonElement.getAsJsonObject().get("tagid").getAsString();
    }

    @Override
    public void update(String tagId, String tagName) throws WxErrorException {
        String url = this.mainService.getWxCpConfigStorage().getApiUrl(TAG_UPDATE);
        JsonObject o = new JsonObject();
        o.addProperty("tagid", tagId);
        o.addProperty("tagname", tagName);
        this.mainService.post(url, o.toString());
    }

    @Override
    public void delete(String tagId) throws WxErrorException {
        String url = String.format(this.mainService.getWxCpConfigStorage().getApiUrl(TAG_DELETE), tagId);
        this.mainService.get(url, null);
    }

    @Override
    public List<WxCpTag> listAll() throws WxErrorException {
        String url = this.mainService.getWxCpConfigStorage().getApiUrl(TAG_LIST);
        String responseContent = this.mainService.get(url, null);
        JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
        return WxCpGsonBuilder.create()
                .fromJson(
                        tmpJsonElement.getAsJsonObject().get("taglist"),
                        new TypeToken<List<WxCpTag>>() {
                        }.getType()
                );
    }

    @Override
    public List<WxCpUser> listUsersByTagId(String tagId) throws WxErrorException {
        String url = String.format(this.mainService.getWxCpConfigStorage().getApiUrl(TAG_GET), tagId);
        String responseContent = this.mainService.get(url, null);
        JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
        return WxCpGsonBuilder.create()
                .fromJson(
                        tmpJsonElement.getAsJsonObject().get("userlist"),
                        new TypeToken<List<WxCpUser>>() {
                        }.getType()
                );
    }

    @Override
    public WxCpTagAddOrRemoveUsersResult addUsers2Tag(String tagId, List<String> userIds, List<String> partyIds) throws WxErrorException {
        String url = this.mainService.getWxCpConfigStorage().getApiUrl(TAG_ADD_TAG_USERS);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("tagid", tagId);
        this.addUserIdsAndPartyIdsToJson(userIds, partyIds, jsonObject);

        return WxCpTagAddOrRemoveUsersResult.fromJson(this.mainService.post(url, jsonObject.toString()));
    }

    @Override
    public WxCpTagAddOrRemoveUsersResult removeUsersFromTag(String tagId, List<String> userIds, List<String> partyIds) throws WxErrorException {
        String url = this.mainService.getWxCpConfigStorage().getApiUrl(TAG_DEL_TAG_USERS);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("tagid", tagId);
        this.addUserIdsAndPartyIdsToJson(userIds, partyIds, jsonObject);

        return WxCpTagAddOrRemoveUsersResult.fromJson(this.mainService.post(url, jsonObject.toString()));
    }

    private void addUserIdsAndPartyIdsToJson(List<String> userIds, List<String> partyIds, JsonObject jsonObject) {
        if (userIds != null) {
            JsonArray jsonArray = new JsonArray();
            for (String userId : userIds) {
                jsonArray.add(new JsonPrimitive(userId));
            }
            jsonObject.add("userlist", jsonArray);
        }

        if (partyIds != null) {
            JsonArray jsonArray = new JsonArray();
            for (String userId : partyIds) {
                jsonArray.add(new JsonPrimitive(userId));
            }
            jsonObject.add("partylist", jsonArray);
        }
    }

    @Override
    public WxCpTagGetResult get(String tagId) throws WxErrorException {
        if (tagId == null) {
            throw new IllegalArgumentException("缺少tagId参数");
        }

        String url = String.format(this.mainService.getWxCpConfigStorage().getApiUrl(TAG_GET), tagId);
        String responseContent = this.mainService.get(url, null);
        return WxCpTagGetResult.fromJson(responseContent);
    }
}
