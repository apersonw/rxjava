package top.rxjava.third.weixin.cp.api.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.cp.api.WxCpOaService;
import top.rxjava.third.weixin.cp.api.WxCpService;
import top.rxjava.third.weixin.cp.bean.oa.*;
import top.rxjava.third.weixin.cp.util.json.WxCpGsonBuilder;

import java.util.Date;
import java.util.List;

import static top.rxjava.third.weixin.cp.constant.WxCpApiPathConsts.Oa.*;

/**
 * 企业微信 OA 接口实现
 */
@RequiredArgsConstructor
public class WxCpOaServiceImpl implements WxCpOaService {
    private final WxCpService mainService;

    private static final int MONTH_SECONDS = 30 * 24 * 60 * 60;
    private static final int USER_IDS_LIMIT = 100;

    @Override
    public List<WxCpCheckinData> getCheckinData(Integer openCheckinDataType, Date startTime, Date endTime,
                                                List<String> userIdList) throws WxErrorException {
        if (startTime == null || endTime == null) {
            throw new RuntimeException("starttime and endtime can't be null");
        }

        if (userIdList == null || userIdList.size() > USER_IDS_LIMIT) {
            throw new RuntimeException("用户列表不能为空，不超过 " + USER_IDS_LIMIT + " 个，若用户超过 " + USER_IDS_LIMIT + " 个，请分批获取");
        }

        long endtimestamp = endTime.getTime() / 1000L;
        long starttimestamp = startTime.getTime() / 1000L;

        if (endtimestamp - starttimestamp < 0 || endtimestamp - starttimestamp >= MONTH_SECONDS) {
            throw new RuntimeException("获取记录时间跨度不超过一个月");
        }

        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();

        jsonObject.addProperty("opencheckindatatype", openCheckinDataType);
        jsonObject.addProperty("starttime", starttimestamp);
        jsonObject.addProperty("endtime", endtimestamp);

        for (String userid : userIdList) {
            jsonArray.add(userid);
        }

        jsonObject.add("useridlist", jsonArray);

        final String url = this.mainService.getWxCpConfigStorage().getApiUrl(GET_CHECKIN_DATA);
        String responseContent = this.mainService.post(url, jsonObject.toString());
        JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
        return WxCpGsonBuilder.create()
                .fromJson(
                        tmpJsonElement.getAsJsonObject().get("checkindata"),
                        new TypeToken<List<WxCpCheckinData>>() {
                        }.getType()
                );
    }

    @Override
    public List<WxCpCheckinOption> getCheckinOption(Date datetime, List<String> userIdList) throws WxErrorException {
        if (datetime == null) {
            throw new RuntimeException("datetime can't be null");
        }

        if (userIdList == null || userIdList.size() > USER_IDS_LIMIT) {
            throw new RuntimeException("用户列表不能为空，不超过 " + USER_IDS_LIMIT + " 个，若用户超过 " + USER_IDS_LIMIT + " 个，请分批获取");
        }

        JsonArray jsonArray = new JsonArray();
        for (String userid : userIdList) {
            jsonArray.add(userid);
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("datetime", datetime.getTime() / 1000L);
        jsonObject.add("useridlist", jsonArray);

        final String url = this.mainService.getWxCpConfigStorage().getApiUrl(GET_CHECKIN_OPTION);
        String responseContent = this.mainService.post(url, jsonObject.toString());
        JsonElement tmpJsonElement = new JsonParser().parse(responseContent);

        return WxCpGsonBuilder.create()
                .fromJson(
                        tmpJsonElement.getAsJsonObject().get("info"),
                        new TypeToken<List<WxCpCheckinOption>>() {
                        }.getType()
                );
    }

    @Override
    public WxCpApprovalInfo getApprovalInfo(@NonNull Date startTime, @NonNull Date endTime,
                                            Integer cursor, Integer size, List<WxCpApprovalInfoQueryFilter> filters) throws WxErrorException {

        if (cursor == null) {
            cursor = 0;
        }

        if (size == null) {
            size = 100;
        }

        if (size < 0 || size > 100) {
            throw new IllegalArgumentException("size参数错误,请使用[1-100]填充，默认100");
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("starttime", startTime.getTime() / 1000L);
        jsonObject.addProperty("endtime", endTime.getTime() / 1000L);
        jsonObject.addProperty("size", size);
        jsonObject.addProperty("cursor", cursor);

        if (filters != null && !filters.isEmpty()) {
            JsonArray filterJsonArray = new JsonArray();
            for (WxCpApprovalInfoQueryFilter filter : filters) {
                filterJsonArray.add(new JsonParser().parse(filter.toJson()));
            }
            jsonObject.add("filters", filterJsonArray);
        }

        final String url = this.mainService.getWxCpConfigStorage().getApiUrl(GET_APPROVAL_INFO);
        String responseContent = this.mainService.post(url, jsonObject.toString());

        return WxCpGsonBuilder.create().fromJson(responseContent, WxCpApprovalInfo.class);
    }

    @Override
    public WxCpApprovalInfo getApprovalInfo(@NonNull Date startTime, @NonNull Date endTime) throws WxErrorException {
        return this.getApprovalInfo(startTime, endTime, null, null, null);
    }

    @Override
    public WxCpApprovalDetailResult getApprovalDetail(@NonNull String spNo) throws WxErrorException {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sp_no", spNo);

        final String url = this.mainService.getWxCpConfigStorage().getApiUrl(GET_APPROVAL_DETAIL);
        String responseContent = this.mainService.post(url, jsonObject.toString());

        return WxCpGsonBuilder.create().fromJson(responseContent, WxCpApprovalDetailResult.class);
    }

    @Override
    public WxCpApprovalDataResult getApprovalData(Date startTime, Date endTime, Long nextSpnum) throws WxErrorException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("starttime", startTime.getTime() / 1000L);
        jsonObject.addProperty("endtime", endTime.getTime() / 1000L);
        if (nextSpnum != null) {
            jsonObject.addProperty("next_spnum", nextSpnum);
        }

        final String url = this.mainService.getWxCpConfigStorage().getApiUrl(GET_APPROVAL_DATA);
        String responseContent = this.mainService.post(url, jsonObject.toString());
        return WxCpGsonBuilder.create().fromJson(responseContent, WxCpApprovalDataResult.class);
    }

    @Override
    public List<WxCpDialRecord> getDialRecord(Date startTime, Date endTime, Integer offset, Integer limit)
            throws WxErrorException {
        JsonObject jsonObject = new JsonObject();

        if (offset == null) {
            offset = 0;
        }

        if (limit == null || limit <= 0) {
            limit = 100;
        }

        jsonObject.addProperty("offset", offset);
        jsonObject.addProperty("limit", limit);

        if (startTime != null && endTime != null) {

            long endtimestamp = endTime.getTime() / 1000L;
            long starttimestamp = startTime.getTime() / 1000L;

            if (endtimestamp - starttimestamp < 0 || endtimestamp - starttimestamp >= MONTH_SECONDS) {
                throw new RuntimeException("受限于网络传输，起止时间的最大跨度为30天，如超过30天，则以结束时间为基准向前取30天进行查询");
            }

            jsonObject.addProperty("start_time", starttimestamp);
            jsonObject.addProperty("end_time", endtimestamp);
        }

        final String url = this.mainService.getWxCpConfigStorage().getApiUrl(GET_DIAL_RECORD);
        String responseContent = this.mainService.post(url, jsonObject.toString());
        JsonElement tmpJsonElement = new JsonParser().parse(responseContent);

        return WxCpGsonBuilder.create().fromJson(tmpJsonElement.getAsJsonObject().get("record"),
                new TypeToken<List<WxCpDialRecord>>() {
                }.getType()
        );
    }

    @Override
    public WxCpTemplateResult getTemplateDetail(@NonNull String templateId) throws WxErrorException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("template_id", templateId);
        final String url = this.mainService.getWxCpConfigStorage().getApiUrl(GET_TEMPLATE_DETAIL);
        String responseContent = this.mainService.post(url, jsonObject.toString());
        return WxCpGsonBuilder.create().fromJson(responseContent, WxCpTemplateResult.class);
    }
}
